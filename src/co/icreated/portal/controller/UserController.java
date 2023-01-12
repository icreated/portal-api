package co.icreated.portal.controller;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.validation.Valid;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.UsersApi;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.config.SecurityConfig;
import co.icreated.portal.exceptions.PortalPreconditionException;
import co.icreated.portal.model.CommonStringDto;
import co.icreated.portal.model.PasswordDto;
import co.icreated.portal.model.UserDto;
import co.icreated.portal.security.Authenticated;
import co.icreated.portal.service.EmailService;
import co.icreated.portal.service.UserService;
import co.icreated.portal.utils.IdempierePasswordEncoder;
import co.icreated.portal.utils.StringUtils;
import io.jsonwebtoken.Jwts;

@RestController
public class UserController implements UsersApi, Authenticated {

  @Value("${frontend-url}")
  private String frontendUrl;

  @Value("${jwt.expiration-time}")
  private long jwtExpirationTime;
  /**
   * Password forgot Email Title
   */
  @Value("${email.passwordlink.title}")
  private String emaillinkTitle;
  /**
   * Email template
   */
  @Value("${email.passwordlink.body-file}")
  private String emaillinkBodyFile;

  Properties ctx;

  UserService userService;

  EmailService emailService;

  IdempierePasswordEncoder passwordEncoder;


  public UserController(Properties ctx, UserService userService, EmailService emailService,
      IdempierePasswordEncoder passwordEncoder) {
    this.ctx = ctx;
    this.userService = userService;
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
  }


  /**
   * Send email link for password updating
   *
   * @param email
   * @return
   */
  @Override
  public ResponseEntity<Void> sendEmailLink(@Valid CommonStringDto commonStringDto) {

    MUser user = userService.getUserByParam(commonStringDto.getValue().toUpperCase(),
        "isActive='Y' AND UPPER(email) LIKE ?");

    String uuid = UUID.randomUUID().toString();

    String bodyEmail = emailService.getMsgBody(this.emaillinkBodyFile, user.getName(),
        String.join("/", frontendUrl, uuid));

    if (emailService.sendEmail(commonStringDto.getValue(), this.emaillinkTitle, bodyEmail)) {
      user.setLastResult(uuid);
      if (user.save()) {
        return ResponseEntity.ok().build();
      }
    }
    throw new AdempiereException("Email not sent");
  }


  /**
   * Password Validation by Email
   *
   * @param passwordBean
   * @return
   */
  @Override
  public ResponseEntity<Void> validateToken(@Valid PasswordDto passwordDto) {



    if (!StringUtils.areSet(passwordDto.getPassword(), passwordDto.getNewPassword(),
        passwordDto.getConfirmPassword())) {
      throw new PortalPreconditionException("Passwords are not set");
    }

    if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
      throw new PortalPreconditionException("Passwords are not matching");
    }

    // Here is our token
    String token = passwordDto.getPassword();

    MUser user = userService.getUserByParam(token, "isActive='Y' AND lastResult LIKE ?");

    passwordEncoder.setSalt(user.getSalt());

    if (userService.changePassword(passwordDto.getConfirmPassword(), user.getAD_User_ID())) {
      return ResponseEntity.ok().build();
    }
    throw new PortalPreconditionException("Password not updated");

  }


  /**
   * Password changing
   *
   * @param passwordDto
   * @return
   */
  @Override
  public ResponseEntity<UserDto> changePassword(@RequestBody PasswordDto passwordDto) {

    if (!StringUtils.areSet(passwordDto.getPassword(), passwordDto.getNewPassword(),
        passwordDto.getConfirmPassword())) {
      throw new PortalPreconditionException("Passwords are not set");
    }

    passwordEncoder.setSalt(getSessionUser().getSalt());
    CharSequence pass = passwordDto.getPassword();
    if (!passwordEncoder.matches(pass, getSessionUser().getPassword())) {
      throw new PortalPreconditionException("Password not valid");
    }

    if (userService.changePassword(passwordDto.getConfirmPassword(),
        getSessionUser().getUserId())) {
      final SessionUser authenticatedUser =
          userService.findSessionUserByValue(getSessionUser().getUsername());

      String token = Jwts.builder() //
          .signWith(SecurityConfig.SECRET) //
          .setSubject(authenticatedUser.getUsername()) //
          .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime)).compact();

      UserDto userDto = new UserDto() //
          .id(getSessionUser().getUserId()) //
          .username(getSessionUser().getUsername()) //
          .name(getSessionUser().getName()) //
          .token(token);

      return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    throw new AdempiereException("Password not updated");

  }


}
