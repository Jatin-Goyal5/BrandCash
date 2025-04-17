package com.demo.brandbacks.service;

import com.demo.brandbacks.dto.QrConfigDto;
import com.demo.brandbacks.model.QrConfig;

public interface QrConfigService {

	public QrConfig saveQrConfig(QrConfigDto qrConfigDto) throws Exception;
}
