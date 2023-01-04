package co.icreated.portal.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MUser;
import org.compiere.util.DB;
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

  IdempierePasswordEncoder passwordEncoder;

  ServletContext servletContext;

  public UserController(Properties ctx, UserService userService,
      IdempierePasswordEncoder passwordEncoder, ServletContext servletContext) {
    this.ctx = ctx;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.servletContext = servletContext;
  }


  /**
   * Send email link for password updating
   *
   * @param email
   * @return
   */
  @Override
  public ResponseEntity<Void> sendEmailLink(@Valid CommonStringDto commonStringDto) {

    int AD_User_ID = DB.getSQLValue(null,
        "SELECT AD_User_ID FROM AD_User WHERE isActive='Y' AND UPPER(email) LIKE ?",
        commonStringDto.getValue().toUpperCase());
    if (AD_User_ID <= 0) {
      throw new AdempiereException("User doesn't exist");
    }

    MUser user = MUser.get(ctx, AD_User_ID);
    MClient client = MClient.get(ctx);

    InputStream inputStream = servletContext.getResourceAsStream(this.emaillinkBodyFile);
    String bodyEmail =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
            .collect(Collectors.joining("\n"));

    String uuid = UUID.randomUUID().toString();

    // Binding variables: Name, Frontend URL for changing password
    bodyEmail = MessageFormat.format(bodyEmail, user.getName(), this.frontendUrl + "/" + uuid);

    if (client.sendEMail(commonStringDto.getValue(), this.emaillinkTitle, bodyEmail, null, true)) {
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

    int AD_User_ID = DB.getSQLValue(null,
        "SELECT AD_User_ID FROM AD_User WHERE isActive='Y' AND lastResult LIKE ?", token);
    if (AD_User_ID <= 0) {
      throw new PortalPreconditionException("User doesn't exist");
    }

    MUser user = MUser.get(ctx, AD_User_ID);
    passwordEncoder.setSalt(user.getSalt());

    boolean ok = userService.changePassword(passwordDto.getConfirmPassword(), user.getAD_User_ID());

    if (ok) {
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
    boolean isValid = passwordEncoder.matches(pass, getSessionUser().getPassword());

    if (!isValid) {
      throw new PortalPreconditionException("Password not valid");
    }

    boolean ok =
        userService.changePassword(passwordDto.getConfirmPassword(), getSessionUser().getUserId());
    if (ok) {

      final SessionUser authenticatedUser =
          userService.findSessionUserByValue(getSessionUser().getUsername());

      String token =
          Jwts.builder().signWith(SecurityConfig.SECRET).setSubject(authenticatedUser.getUsername())
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
