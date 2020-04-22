package co.icreated.portal.service;

import java.util.Properties;

import org.compiere.model.MRefList;
import org.compiere.util.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	CLogger log = CLogger.getCLogger(CommonService.class);
	
	@Autowired
	Properties ctx;
	
	

}
