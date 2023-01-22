package co.icreated.portal.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {


  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    converters.add(mappingJackson2HttpMessageConverter());
  }


  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    jsonConverter.setObjectMapper(getObjectMapper());
    return jsonConverter;
  }


  public ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    // mapper.disable(MapperFeature.AUTO_DETECT_CREATORS,
    // MapperFeature.AUTO_DETECT_FIELDS,
    // MapperFeature.AUTO_DETECT_GETTERS,
    // MapperFeature.AUTO_DETECT_IS_GETTERS);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

    return mapper;
  }

}
