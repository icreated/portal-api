package co.icreated.portal.service;

import java.util.List;
import java.util.Properties;

import org.compiere.model.MRefList;
import org.compiere.util.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.ValueLabelBean;

@Service
public class CommonService {
	
	CLogger log = CLogger.getCLogger(CommonService.class);
	
	@Autowired
	Properties ctx;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public String getReferenceValue(String AD_Language, int AD_Reference_ID, String value) {
		
		return MRefList.getListName(AD_Language, AD_Reference_ID, value);
	}
	
	
	public List<ValueLabelBean> getValueLabelList(String AD_Language, int AD_Reference_ID) {
		
	    String sql = "SELECT Value, COALESCE(trl.Name, l.Name) FROM AD_Ref_List l "
	    		+ "LEFT JOIN AD_Ref_List_Trl trl ON l.AD_Ref_List_ID=trl.AD_Ref_List_ID AND trl.AD_Language = ? "
	    		+ "WHERE l.AD_Reference_ID = ? AND l.isActive='Y'";
			return jdbcTemplate.query(sql,
					new Object[]{AD_Language, AD_Reference_ID},
					(rs, rowNum) ->
						new ValueLabelBean(rs.getString(2), rs.getString(1))
	        );	
	}
}
