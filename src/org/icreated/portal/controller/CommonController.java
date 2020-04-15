package org.icreated.portal.controller;

import org.icreated.portal.bean.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
	
	@GetMapping("/hello")
	public Token hello() {
		return new Token("Hello");
	}

}
