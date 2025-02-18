package com.rt.market.service;

import com.rt.Except4Support;
import com.rt.market.conf.js.ConfJsMarket;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    private final String from;

    private final String adminMail;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.from = ConfJsMarket.getInstance().getApp().getMailFrom();
        this.adminMail = ConfJsMarket.getInstance().getApp().getMailAdmin();
    }


    public void sendEmailToClient(String clientEmail, String subject, String body) {
        try {
            sendEmail(clientEmail, subject, body);
        } catch (MessagingException ex) {
            throw new Except4Support(
                    "ErrMail01",
                    "Ошибка отправки email-сообщения к " + clientEmail,
                    ex.getMessage(),
                    ex
            );
        }
    }

    public void sendEmailToAdmin(String subject, String body) {
        try {
            sendEmail(adminMail, subject, body);
        } catch (MessagingException ex) {
            throw new Except4Support(
                    "ErrMail02",
                    "Ошибка отправки email-сообщения к " + adminMail,
                    ex.getMessage(),
                    ex
            );
        }
    }

    @Async
    protected void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }
}
