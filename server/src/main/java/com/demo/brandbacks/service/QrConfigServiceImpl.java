package com.demo.brandbacks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.brandbacks.dto.QrConfigDto;
import com.demo.brandbacks.model.QrConfig;
import com.demo.brandbacks.repository.QrConfigRepository;

@Service
public class QrConfigServiceImpl implements QrConfigService {

	@Autowired
	QrConfigRepository qrConfigRepo;
	
	@Override
	public QrConfig saveQrConfig(QrConfigDto qrConfigDto) throws Exception {
		try {
			
			QrConfig qrConfig = qrConfigRepo.findAll().size() == 0 ? new QrConfig(): qrConfigRepo.findAll().get(0);
			if(qrConfigDto.getHeight() == 0)
				throw new Exception("Height should not null");
			
			if(qrConfigDto.getLength() == 0)
				throw new Exception("Length should not null");
			qrConfig.setHeight(qrConfigDto.getHeight());
			qrConfig.setLength(qrConfigDto.getLength());
			qrConfig.setQrSize(qrConfigDto.getQrSize());
			qrConfig.setTemplateName(qrConfigDto.getTemplateName());
			qrConfigRepo.save(qrConfig);
			return qrConfig;
		}catch(Exception exp) {
			throw exp;
		}
		
	}

	
}
