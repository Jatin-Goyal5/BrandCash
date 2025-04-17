package com.demo.brandbacks.util;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class MessageUtil {
	
	public static final String BASE_NAME= "messages";

	public static String getMessage(String key) {
		ResourceBundle resBundle;
		resBundle = ResourceBundle.getBundle(BASE_NAME);
		
		if(resBundle.containsKey(key)) {
			String value = resBundle.getString(key);
			return new String (value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		}
		
		return null;
		
	}
}
