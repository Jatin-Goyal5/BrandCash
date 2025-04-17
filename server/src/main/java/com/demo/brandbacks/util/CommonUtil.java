package com.demo.brandbacks.util;

import java.util.UUID;

public final class CommonUtil {

	 public static String generateReferenceId() {
	        // Get current time in milliseconds
	        long timestamp = System.currentTimeMillis();
	        
	        // Generate a random UUID (Universally Unique Identifier)
	        String uniqueId = UUID.randomUUID().toString();
	        
	        // Combine timestamp and UUID to form a reference ID
	        return "REF"+ uniqueId;
	    }

}
