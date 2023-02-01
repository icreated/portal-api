package co.icreated.portal.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@Import({})
@PropertySource(value = {"classpath:webportal.properties"})
public class PortalConfig {


  @Autowired
  private Environment env;

  @Bean("ctx")
  Properties getCtx() {

    Properties props = new Properties();
    props.setProperty("#AD_Client_ID", env.getProperty("ctx.AD_Client_ID"));
    props.setProperty("#AD_Org_ID", env.getProperty("ctx.AD_Org_ID"));
    return props;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }

}


