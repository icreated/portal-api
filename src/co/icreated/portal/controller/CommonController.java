package co.icreated.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.service.CommonService;

@RestController
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	CommonService commonService;

	

}