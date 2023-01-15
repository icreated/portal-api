package co.icreated.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.model.ValueLabelDto;
import co.icreated.portal.api.service.CommonApi;
import co.icreated.portal.service.CommonService;

@RestController
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CommonController implements CommonApi {

  @Value("${reference.creditCardType}")
  int REFERENCE_CREDIT_CARD_TYPE;

  @Value("${reference._docStatus}")
  int REFERENCE_DOCSTATUS;

  @Value("${reference.tenderType}")
  int REFERENCE_TENDERTYPE;

  CommonService commonService;

  public CommonController(CommonService commonService) {
    this.commonService = commonService;
  }


  /**
   * Get credit card list
   *
   * @return
   */
  @Override
  public ResponseEntity<List<ValueLabelDto>> getCreditCardTypes() {
    // AD_Reference_ID = 149 CreditCardType
    return ResponseEntity.ok(commonService.getValueLabelList(REFERENCE_CREDIT_CARD_TYPE));
  }


  /**
   * Get tenderTyper with translation
   *
   * @param language
   * @param value
   * @return
   */
  @Override
  public ResponseEntity<String> getDocStatus(String language, String value) {
    // AD_Reference_ID = 131 _DocStatus
    return ResponseEntity.ok(commonService.getReferenceValue(language, REFERENCE_DOCSTATUS, value));
  }


  /**
   * Get docStatus Value with translation
   *
   * @param language
   * @param value
   * @return
   */
  @Override
  public ResponseEntity<String> getTenderType(String language, String value) {
    // AD_Reference_ID = 214 C_Payment TenderType
    return ResponseEntity
        .ok(commonService.getReferenceValue(language, REFERENCE_TENDERTYPE, value));
  }

}
