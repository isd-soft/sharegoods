package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sharegoods.inth3rship.common.Constants.serverUrl;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(List<String> emails, Item newItem) {
        String subject = "New post on Sharegoods - " + newItem.getTitle();
        String message = "Description: " + newItem.getDescription() + "\n\nread more info on " + serverUrl + "/items/" + newItem.getId();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        for (String email: emails) {
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            try {
                javaMailSender.send(mailMessage);
            } catch (MailException e) {
                e.printStackTrace();
            }
        }
    }

}
