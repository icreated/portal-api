package co.icreated.portal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.model.ValueLabelDto;
import co.icreated.portal.service.CommonService;

@RestController
@RequestMapping("/common")
public class CommonController {

  CommonService commonService;

  public CommonController(CommonService commonService) {
    this.commonService = commonService;
  }

  /**
   * Get docStatus Value with translation
   *
   * @param language
   * @param value
   * @return
   */
  @GetMapping("/reference/docstatus/{language}/{value}")
  public String getReferenceDocStatus(@PathVariable String language, @PathVariable String value) {
    // AD_Reference_ID = 131 _DocStatus
    return commonService.getReferenceValue(language, 131, value);
  }

  /**
   * Get tenderTyper with translation
   *
   * @param language
   * @param value
   * @return
   */
  @GetMapping("/reference/tendertype/{language}/{value}")
  public String getReferenceTenderType(@PathVariable String language, @PathVariable String value) {
    // AD_Reference_ID = 214 C_Payment TenderType
    return commonService.getReferenceValue(language, 214, value);
  }

  /**
   * Get credit card list
   *
   * @return
   */
  @GetMapping("/reference/creditcardtypes")
  public List<ValueLabelDto> getReferenceCreditCard() {
    // AD_Reference_ID = 149 CreditCardType
    return commonService.getValueLabelList(149);
  }

}
