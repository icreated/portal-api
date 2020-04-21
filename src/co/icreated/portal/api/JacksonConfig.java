package co.icreated.portal.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfig {
	
	
	@Bean("customJacksonMapper")
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		 MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		 jsonConverter.setObjectMapper(getObjectMapper());
		 return jsonConverter;
	}
	

    public ObjectMapper getObjectMapper() {

		ObjectMapper mapper = new ObjectMapper();
//	    mapper.disable(MapperFeature.AUTO_DETECT_CREATORS,
//	            MapperFeature.AUTO_DETECT_FIELDS,
//	            MapperFeature.AUTO_DETECT_GETTERS,
//	            MapperFeature.AUTO_DETECT_IS_GETTERS);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		
		return mapper;
    }
}