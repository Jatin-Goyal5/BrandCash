package com.demo.brandbacks.util;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlMappingUtils {

	@Value("${decentro.base.url}")
	private String decentroBaseURL;
	
    @Value("${decentro.endpoint.verification.upi}")
    private String upiVerificationEndpoint;

    public String getUPIVerificationURL() {
    	 String url =  MessageFormat.format("{0}{1}{2}", decentroBaseURL, "", upiVerificationEndpoint);
    	 return url;

    }
    
}

