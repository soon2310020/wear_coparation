package com.example.wear_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    @Autowired
    public JavaMailSender emailSender;

    @ResponseBody
    @RequestMapping("/sendSimpleEmail")
    public String sendSimpleEmail() {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wear_shop");
        message.setTo("nguyenhuuuyen123@gmail.com");
        message.setSubject("WEAR_SHOP");
        message.setText("Cảm ơn bạn đã đặt hàng vui lòng truy cập link để biết thông tin đơn hàng");

        // Send Message!
        this.emailSender.send(message);

        return "Email Sent!";
    }
}
