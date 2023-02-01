package co.icreated.portal.service;

import java.util.List;
import java.util.Properties;

import org.compiere.model.MRefList;
import org.compiere.util.CLogger;
import org.compiere.util.ValueNamePair;
import org.springframework.stereotype.Service;

import co.icreated.portal.api.model.ValueLabelDto;
import co.icreated.portal.mapper.CommonMapper;

@Service
public class CommonService {

  CLogger log = CLogger.getCLogger(CommonService.class);

  Properties ctx;
  CommonMapper commonMapper;


  public CommonService(Properties ctx, CommonMapper commonMapper) {
    this.ctx = ctx;
    this.commonMapper = commonMapper;
  }


  public String getReferenceValue(String AD_Language, int AD_Reference_ID, String value) {
    String result = MRefList.getListName(AD_Language, AD_Reference_ID, value);
    if (result.isBlank()) {
      result = MRefList.getListName("en_US", AD_Reference_ID, value);
    }
    return result;
  }


  public List<ValueLabelDto> getValueLabelList(int AD_Reference_ID) {

    ValueNamePair[] valueNamePairList = MRefList.getList(ctx, AD_Reference_ID, false);
    return commonMapper.vnpListToValueLabelDtoList(valueNamePairList);
  }
}
