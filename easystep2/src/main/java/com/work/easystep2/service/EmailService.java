package com.work.easystep2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerToken;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private TravelerTokenService tokenService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	//發送驗證連結
	public void sendHtmlMail(Traveler traveler)throws MessagingException{
		TravelerToken travelerToken = tokenService.findByTraveler(traveler);
		SimpleMailMessage message = new SimpleMailMessage();
		String token = travelerToken.getToken();
		message.setFrom("d0287400@gmail.com");
		message.setTo(traveler.getEmail());
		message.setSubject("主旨：Hello" + traveler.getUsername() + "點選下方連結驗證信箱");
		message.setText("http://localhost:8084/easystep/Traveler/action?token=" + token);

		javaMailSender.send(message);
	}
	
	//發送密碼
	public void sendMailPwd(Traveler traveler, String newpwd)throws MessagingException{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("d0287400@gmail.com");
		message.setTo(traveler.getEmail());
		message.setSubject("主旨：Hello" + traveler.getUsername());
	
		message.setText("密碼：" + newpwd);
		javaMailSender.send(message);
	}
	
	
	//發送驗證連結第二種方式
	public void sendHtmlMail2(Traveler traveler)throws MessagingException{
		TravelerToken travelerToken = tokenService.findByTraveler(traveler);
	
		//先確認已經創鍵token
		if(travelerToken!=null) {
			String token=travelerToken.getToken();
			Context context=new Context();
			context.setVariable("title", "EasyStep驗證信箱");
			context.setVariable("link", "http://localhost:8084/easystep/Traveler/action?token=" + token);
			
			String body = templateEngine.process("Traveler/verification", context);
			
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setTo(traveler.getEmail());
			helper.setSubject("信箱驗證");
			helper.setText(body,true);
			javaMailSender.send(message);
		}
	}

}
