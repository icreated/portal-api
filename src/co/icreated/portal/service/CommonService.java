package co.icreated.portal.service;

import java.util.List;
import java.util.Properties;

import org.compiere.model.MRefList;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.VOpenItem;
import co.icreated.portal.bean.ValueLabelBean;

@Service
public class CommonService {
	
	CLogger log = CLogger.getCLogger(CommonService.class);
	
	@Autowired
	Properties ctx;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public String getReferenceValue(int AD_Reference_ID, String value) {
		
		return MRefList.getListName(ctx, AD_Reference_ID, value);
	}
	
	
	public List<ValueLabelBean> getValueLabelList(int AD_Reference_ID) {
		
	    String sql = "SELECT Value, Name FROM AD_Ref_List WHERE AD_Reference_ID = ? AND isActive='Y'";
			return jdbcTemplate.query(sql,
					new Object[]{AD_Reference_ID},
					(rs, rowNum) ->
						new ValueLabelBean(rs.getString("Name"), rs.getString("Value"))
	        );	
	}
}
