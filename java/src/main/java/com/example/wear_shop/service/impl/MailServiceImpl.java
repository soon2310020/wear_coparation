package com.example.wear_shop.service.impl;

import com.example.wear_shop.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    public JavaMailSender emailSender;
    @Override
    public void sendMail(String userMail, String url, Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("WearShop");
        message.setTo(userMail);
        message.setSubject("WEARSHOP_ORDER");
        message.setText("Cảm ơn bạn đã đặt hàng vui lòng truy cập link "+url+id+
                " để biết thông tin đơn hàng");

        // Send Message!
        this.emailSender.send(message);

    }
}
