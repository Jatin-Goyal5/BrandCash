package com.demo.brandbacks.service;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.brandbacks.model.QrCode;
import com.demo.brandbacks.repository.QrcodeRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QrCodeService {

	@Autowired
	QrcodeRepository qrCodeRepo;
	
	public void saveQrcode() {
		QrCode qrcode = new QrCode();
		qrcode.setProductId("1");
		qrcode.setQrCodeId("21321313");
		qrcode.setStatus(1);
		qrCodeRepo.save(qrcode);
	}

	public QrCode getQrCode(String qrCodeId) {
		try {
			Optional<QrCode> qrCode = qrCodeRepo.findByQrCodeId(qrCodeId);
			log.info("{}", qrCode.get());
			return qrCode.isPresent() ? qrCode.get(): null;
		}catch(Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage generateQrCodeImage(String data) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        image.createGraphics().drawImage(MatrixToImageWriter.toBufferedImage(matrix), 0, 0, null);
        return image;
    }
}
