package com.cotodel.hrms.web.properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySources({@PropertySource("classpath:/application-prod.properties"),
    @PropertySource(value = "classpath:/application-dev.properties", ignoreResourceNotFound = true)})

public class ApplicationConstantConfig {

	@Value("${token.service.base.url}")
	public String tokenServiceBaseUrl;

	@Value("${user.service.base.url}")
	public String userServiceBaseUrl;

	@Value("${employer.service.base.url}")
	public String employerServiceBaseUrl;
	
	@Value("${master.service.base.url}")
	public String masterServiceBaseUrl;
	
	@Value("${gst.service.base.url}")
	public String gstServiceBaseUrl;
	
}
