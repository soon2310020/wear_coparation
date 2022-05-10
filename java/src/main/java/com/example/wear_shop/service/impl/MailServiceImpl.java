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
        message.setText("Cảm ơn bạn đã đặt hàng nhân viên cửa hàng sẽ sớm liên hệ với bạn để xác nhận đơn hàng, "+
                " để biết thông tin đơn hàng vui lòng liên hệ 0865279193");

        // Send Message!
        this.emailSender.send(message);

    }
}
