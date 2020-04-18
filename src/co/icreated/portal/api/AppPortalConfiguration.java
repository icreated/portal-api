package co.icreated.portal.api;

import java.util.Properties;

import javax.sql.DataSource;

import org.compiere.db.CConnection;
import org.compiere.util.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("co.icreated")
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
	
	@Bean("dataSource")
	DataSource getDataSource() {
		
		return DB.getDatabase().getDataSource(CConnection.get());
		
	}
	
}


