package com.demo.brandbacks.dto;

import lombok.Data;

@Data
public class QrConfigDto {

	String templateName;
	int length;
	int height;
	int qrSize;
}
