package co.icreated.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.bean.ValueLabelBean;
import co.icreated.portal.service.CommonService;

@RestController
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	CommonService commonService;

	
	@GetMapping("/reference/docstatus/{language}/{value}")
	public String getReferenceDocStatus(@PathVariable String language, @PathVariable String value) {
		// AD_Reference_ID = 131 _DocStatus	
		return commonService.getReferenceValue(language, 131, value);
	}
	
	@GetMapping("/reference/tendertype/{language}/{value}")
	public String getReferenceTenderType(@PathVariable String language, @PathVariable String value) {
		// AD_Reference_ID = 214 C_Payment TenderType
		return commonService.getReferenceValue(language, 214, value);
	}
	
	@GetMapping("/reference/creditcardtypes")
	public List<ValueLabelBean> getReferenceCreditCard() {
		// AD_Reference_ID = 149 CreditCardType
		return commonService.getValueLabelList("en_US", 149);
	}

}