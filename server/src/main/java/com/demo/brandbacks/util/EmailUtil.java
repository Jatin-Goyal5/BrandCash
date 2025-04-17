package com.demo.brandbacks.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {

	  @Autowired
	    private JavaMailSender mailSender;
	
	public void sendEmail(String content) {
		 try {
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo("jatinji145@gmail.com");
	            message.setSubject("Order Confirmation - " + content);
	            message.setText("Thank you for your order! Your order ID is " + content);

	            mailSender.send(message);
	        } catch (MailException e) {
	            throw new RuntimeException("Failed to send email", e);
	        }
	}
}
