package org.icreated.portal.api;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("org.icreated")
@PropertySource(value= {"classpath:application.properties"})
public class AppPortalConfiguration  {
	
	@Autowired
	private Environment env;
	
	@Bean("ctx")
	Properties getCtx() {
		
		Properties props = new Properties();
		props.setProperty("#AD_Client_ID", env.getProperty("ctx.AD_Client_ID"));
		props.setProperty("#AD_Org_ID", env.getProperty("ctx.AD_Org_ID"));
		return props;
	}
	
}


