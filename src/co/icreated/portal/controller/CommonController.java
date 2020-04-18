package co.icreated.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.bean.Token;

@RestController
public class CommonController {

	
	@GetMapping("/hello")
	public Token hello() {
		
		
		return new Token("Hello");
	}

}
