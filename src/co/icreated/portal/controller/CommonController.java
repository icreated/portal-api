package co.icreated.portal.controller;

import org.compiere.model.I_AD_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.bean.Token;
import co.icreated.portal.service.UserService;

@RestController
public class CommonController {
	
	@Autowired
	UserService userService;

	
	@GetMapping("/hello")
	public I_AD_User  hello() {
		
		I_AD_User user = userService.getUser();
		
		return user;
	}

}
