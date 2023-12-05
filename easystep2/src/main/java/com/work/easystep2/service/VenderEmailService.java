package com.work.easystep2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderToken;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class VenderEmailService {

    @Autowired
    private VenderTokenService tokenService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    //發送驗證連結
    public void sendHtmlMail(Vender vender) throws MessagingException {
        VenderToken vendertoken = tokenService.findByVender(vender);
        SimpleMailMessage message = new SimpleMailMessage();
        String token = vendertoken.getToken();
        message.setFrom("d0287400@gmail.com");
        message.setTo(vender.getEmail());
        message.setSubject("主旨：Hello" + vender.getUsername() + "點選下方連結驗證信箱");
        message.setText("http://localhost:8084/easystep/Vender/action?token=" + token);

        javaMailSender.send(message);
    }

    //發送密碼
    public void sendMailPwd(Vender vender, String newpwd) throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("d0287400@gmail.com");
        message.setTo(vender.getEmail());
        message.setSubject("主旨：Hello" + vender.getUsername());

        message.setText("密碼：" + newpwd);
        javaMailSender.send(message);
    }

    //發送驗證連結第二種方式
    public void sendHtmlMail2(Vender vender) throws MessagingException {
        VenderToken vendertoken = tokenService.findByVender(vender);

        //先確認已經創鍵token
        if (vendertoken != null) {
            String token = vendertoken.getToken();
            Context context = new Context();
            context.setVariable("title", "EastStep驗證信箱");
            context.setVariable("link", "http://localhost:8084/easystep/Vender/action?token=" + token);

            String body = templateEngine.process("Vender/verification", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(vender.getEmail());
            helper.setSubject("信箱驗證");
            helper.setText(body, true);
            javaMailSender.send(message);
        }
    }
	
}
