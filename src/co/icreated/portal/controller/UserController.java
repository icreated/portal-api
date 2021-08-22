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
import org.compiere.util.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.SecurityConfig;
import co.icreated.portal.bean.FrontendUser;
import co.icreated.portal.bean.PasswordDto;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.TokenDto;
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
	
	@Autowired
	CacheManager cacheManager;
	
	
	/**
	 * Send email link for password updating
	 * @param email
	 * @return
	 */
	@PostMapping("/password/emaillink")
	public ResponseEntity sendEmailLink(@RequestBody TokenDto email) {

		int AD_User_ID = DB.getSQLValue(null, "SELECT AD_User_ID FROM AD_User WHERE isActive='Y' AND UPPER(email) LIKE ?", email.getToken().toUpperCase());
		if (AD_User_ID <=0 ) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
			.body("User doesn't exist");
		}
		
		MUser user = new MUser(ctx, AD_User_ID, null);
		
		
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
	public ResponseEntity passwordValidate(@RequestBody PasswordDto passwordBean) {
		

		
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
		
		MUser user = new MUser(ctx, AD_User_ID, null);

		passwordEncoder.setSalt(user.getSalt());
        CharSequence pass = passwordBean.getPassword();
		
		boolean ok = userService.changePassword(passwordBean.getConfirmPassword(), user.getAD_User_ID());

		if (ok) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Password not updated");
		}

	}
	
	
	/**
	 * Password changing
	 * @param passwordBean
	 * @param sessionUser
	 * @return
	 */
	@PostMapping("/password/change")
	public ResponseEntity<FrontendUser> changePassword(@RequestBody PasswordDto passwordBean, @AuthenticationPrincipal SessionUser sessionUser) {
		
		if (!Misc.areSet(passwordBean.getPassword(), passwordBean.getNewPassword(), passwordBean.getConfirmPassword())) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		}
		
		passwordEncoder.setSalt(sessionUser.getSalt());
        CharSequence pass = passwordBean.getPassword();
        boolean isValid = passwordEncoder.matches(pass , sessionUser.getPassword());

        if (!isValid) {
        	return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
		
		boolean ok = userService.changePassword(passwordBean.getConfirmPassword(), sessionUser.getUserId());
		if (ok) {
			// clear cache to get renewed user
			cacheManager.getCache(UserService.CACHE).clear();
			
			final SessionUser authenticatedUser = userService.findSessionUserByValue(sessionUser.getUsername());
			
	        String token = Jwts.builder()
	                .signWith(SecurityConfig.SECRET)
	                .setSubject(authenticatedUser.getUsername())
	                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
	                .compact();
	        
	        FrontendUser frontendUser = new FrontendUser(sessionUser.getUserId(), sessionUser.getUsername(), sessionUser.getName(), token);
	        
			return ResponseEntity.status(HttpStatus.OK).body(frontendUser);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

}
