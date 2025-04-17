package com.demo.brandbacks.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "qr_config")
	public class QrConfig {

	    @Id
	    private String id;

	    private String templateName;

	    private int length;

	    private int height;

	    private int qrSize;
}