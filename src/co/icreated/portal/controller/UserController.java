package co.icreated.portal.controller;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.validation.Valid;

import org.compiere.model.MUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.model.EmailDto;
import co.icreated.portal.api.model.ForgottenPasswordDto;
import co.icreated.portal.api.model.PasswordDto;
import co.icreated.portal.api.model.UserDto;
import co.icreated.portal.api.service.UsersApi;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.config.SecurityConfig;
import co.icreated.portal.exceptions.PortalBusinessException;
import co.icreated.portal.exceptions.PortalInvalidInputException;
import co.icreated.portal.security.Authenticated;
import co.icreated.portal.service.EmailService;
import co.icreated.portal.service.UserService;
import co.icreated.portal.utils.IdempierePasswordEncoder;
import co.icreated.portal.utils.PStringUtils;
import io.jsonwebtoken.Jwts;

@RestController
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserController implements UsersApi, Authenticated {

  @Value("${frontend-url}")
  private String frontendUrl;

  @Value("${jwt.expiration-time}")
  private long jwtExpirationTime;
  // Email title
  @Value("${email.passwordlink.title}")
  private String emaillinkTitle;
  // Email body template
  @Value("${email.passwordlink.body-file}")
  private String emaillinkBodyFile;

  Properties ctx;

  UserService userService;

  EmailService emailService;

  IdempierePasswordEncoder idempierePasswordEncoder;


  public UserController(Properties ctx, UserService userService, EmailService emailService,
      IdempierePasswordEncoder idempierePasswordEncoder) {
    this.ctx = ctx;
    this.userService = userService;
    this.emailService = emailService;
    this.idempierePasswordEncoder = idempierePasswordEncoder;
  }


  @Override
  public ResponseEntity<Void> sendEmailToken(@Valid EmailDto emailDto) {


    MUser user = userService.getUserByParam(emailDto.getValue().toUpperCase(),
        "isActive='Y' AND UPPER(email) LIKE ?");

    String uuid = UUID.randomUUID().toString();

    String bodyEmail = emailService.getMsgBody(this.emaillinkBodyFile, user.getName(),
        String.join("/", frontendUrl, uuid));

    if (emailService.sendEmail(emailDto.getValue(), this.emaillinkTitle, bodyEmail)) {
      user.setLastResult(uuid);
      if (user.save()) {
        return ResponseEntity.ok().build();
      }
    }
    throw new PortalBusinessException("Email not sent");
  }


  @Override
  public ResponseEntity<Void> updateForgottenPassword(String token,
      @Valid ForgottenPasswordDto passwordDto) {

    if (!PStringUtils.areSet(passwordDto.getNewPassword(), passwordDto.getConfirmPassword())) {
      throw new PortalInvalidInputException("Passwords are not set");
    }

    if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
      throw new PortalInvalidInputException("Passwords are not matching");
    }

    MUser user = userService.getUserByParam(token, "isActive='Y' AND lastResult LIKE ?");

    idempierePasswordEncoder.setSalt(user.getSalt());

    if (userService.changePassword(passwordDto.getConfirmPassword(), user)) {
      return ResponseEntity.ok().build();
    }
    throw new PortalBusinessException("Password not updated");
  }


  @Override
  public ResponseEntity<UserDto> updatePassword(@Valid PasswordDto passwordDto) {

    if (!PStringUtils.areSet(passwordDto.getPassword(), passwordDto.getNewPassword(),
        passwordDto.getConfirmPassword())) {
      throw new PortalInvalidInputException("Passwords are not set");
    }

    idempierePasswordEncoder.setSalt(getSessionUser().getSalt());
    CharSequence pass = passwordDto.getPassword();
    if (!idempierePasswordEncoder.matches(pass, getSessionUser().getPassword())) {
      throw new PortalInvalidInputException("Current Password not valid");
    }

    MUser user =
        userService.getUserByParam(getSessionUser().getUserId(), "isActive='Y' AND AD_User_ID = ?");


    if (userService.changePassword(passwordDto.getConfirmPassword(), user)) {
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

    throw new PortalBusinessException("Password not updated");
  }



}
