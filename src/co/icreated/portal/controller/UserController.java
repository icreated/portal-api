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

import org.compiere.model.MClient;
import org.compiere.model.MUser;
import org.compiere.model.X_AD_User;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.SecurityConfig;
import co.icreated.portal.bean.PasswordBean;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.Token;
import co.icreated.portal.exception.OldPasswordNotCorrectException;
import co.icreated.portal.service.UserService;
import co.icreated.portal.utils.IdempierePasswordEncoder;
import co.icreated.portal.utils.Misc;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/user")
public class UserController {
	
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

	@Autowired
	UserService userService;
	
	@Autowired
	private IdempierePasswordEncoder passwordEncoder;
	
	@Autowired
	private Properties ctx;
	
	@Autowired
	ServletContext servletContext; 
	
	
	/**
	 * Send email link for password updating
	 * @param email
	 * @return
	 */
	@PostMapping("/password/emaillink")
	public ResponseEntity sendEmailLink(@RequestBody Token email) {

		int AD_User_ID = DB.getSQLValue(null, "SELECT AD_User_ID FROM AD_User WHERE isActive='Y' AND UPPER(email) LIKE ?", email.getToken().toUpperCase());
		if (AD_User_ID <=0 ) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
			.body("User doesn't exist");
		}
		
		MUser user = MUser.get(ctx, AD_User_ID);
		
		
		MClient client = MClient.get(ctx);

		InputStream inputStream = servletContext.getResourceAsStream(this.emaillinkBodyFile);
	    String bodyEmail = new BufferedReader(
	    	      new InputStreamReader(inputStream, StandardCharsets.UTF_8))
	    	        .lines()
	    	        .collect(Collectors.joining("\n"));
	    
	    String uuid = UUID.randomUUID().toString();
	    
	    // Binding variables: Name, Frontend URL for changing password
	    bodyEmail = MessageFormat.format(bodyEmail, 
	    		user.getName(),
	    		this.frontendUrl+"/"+uuid);
		
		if (client.sendEMail(email.getToken(), this.emaillinkTitle, bodyEmail, null, true)) {
			user.setLastResult(uuid);
			return user.save() ?
					ResponseEntity.ok().build() :
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error saving token");	
						
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body("Email not sent");			
		}

	}
	
	
	/**
	 * Password Validation by Email
	 * @param passwordBean
	 * @return
	 */
	@PostMapping("/password/validate")
	public ResponseEntity passwordValidate(@RequestBody PasswordBean passwordBean) {
		

		
		if (!Misc.areSet(passwordBean.getPassword(), passwordBean.getNewPassword(), passwordBean.getConfirmPassword())) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
			.body("Passwords are not set");
		}
		
		if (!passwordBean.getNewPassword().equals(passwordBean.getConfirmPassword())) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
			.body("Passwords are not matching");
		}
		
		// Here is our token
		String token = passwordBean.getPassword();
		
		int AD_User_ID = DB.getSQLValue(null, "SELECT AD_User_ID FROM AD_User WHERE isActive='Y' AND lastResult LIKE ?", token);
		if (AD_User_ID <=0 ) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
			.body("User doesn't exist");
		}
		
		MUser user = MUser.get(ctx, AD_User_ID);

		passwordEncoder.setSalt(user.getSalt());
        CharSequence pass = passwordBean.getPassword();
		
		boolean ok = userService.changePassword(passwordBean.getConfirmPassword(), user.getAD_User_ID());

		return ok ?
				ResponseEntity.ok().build() :
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Password not updated");	
	}
	
	
	/**
	 * Password changing
	 * @param passwordBean
	 * @param sessionUser
	 * @return
	 */
	@PutMapping("/password/change")
	public ResponseEntity<Token> changePassword(@RequestBody PasswordBean passwordBean, @AuthenticationPrincipal SessionUser sessionUser) {
		
		passwordEncoder.setSalt(sessionUser.getSalt());
        CharSequence pass = passwordBean.getPassword();
        boolean isValid = passwordEncoder.matches(pass , sessionUser.getPassword());

        if (!isValid) {
        	throw new OldPasswordNotCorrectException();
        }
		
		boolean ok = userService.changePassword(passwordBean.getConfirmPassword(), sessionUser.getUserId());
		if (ok) {
			
			final SessionUser authenticatedUser = userService.findSessionUserByValue(sessionUser.getEmail());
			
	        String token = Jwts.builder()
	                .signWith(SecurityConfig.SECRET)
	                .setSubject(authenticatedUser.getUsername())
	                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
	                .compact();
			return ResponseEntity.status(HttpStatus.OK).body(new Token(token));
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

}
