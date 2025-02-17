package com.rt.config;

import com.rt.market.conf.js.ConfJsMarket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(ConfJsMarket.getInstance().getApp().getMailSmtpHost());
        mailSender.setUsername(ConfJsMarket.getInstance().getApp().getMailSmtpUser());
        mailSender.setPassword(ConfJsMarket.getInstance().getApp().getMailSmtpPass());
        mailSender.setPort(ConfJsMarket.getInstance().getApp().getMailSmtpPort());
        mailSender.setProtocol("smtp");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", ConfJsMarket.getInstance().getApp().isMailSmtpAuth());
        props.put("mail.smtp.starttls.enable", ConfJsMarket.getInstance().getApp().isMailStartTls());

        return mailSender;
    }
}
