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

	
	@GetMapping("/reference/docstatus/{value}")
	public String getReferenceDocStatus(@PathVariable String value) {
		// AD_Reference_ID = 131 _DocStatus	
		return commonService.getReferenceValue(131, value);
	}
	
	@GetMapping("/reference/tendertype/{value}")
	public String getReferenceTenderType(@PathVariable String value) {
		// AD_Reference_ID = 214 C_Payment TenderType
		return commonService.getReferenceValue(214, value);
	}
	
	@GetMapping("/reference/creditcardtypes")
	public List<ValueLabelBean> getReferenceCreditCard() {
		// AD_Reference_ID = 149 CreditCardType
		return commonService.getValueLabelList(149);
	}

}