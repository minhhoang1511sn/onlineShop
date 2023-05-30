package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String senderMail;

    @Autowired private JavaMailSender mailSender;

    /**
     * Method takes as parameters the recipient's email address,
     * the email subject, and the body of the email in HTML format.
     * @throws MessagingException
     */
    @Override
    public void sendMailForgotPassword(String to, String subject,
                                       String htmlBody) throws MessagingException {

        // used to create the email message
        MimeMessage message = mailSender.createMimeMessage();
        //sed to set the message properties, such as the subject,
        // sender, recipient, and the body of the email.
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setSubject(subject);
        helper.setFrom(senderMail);
        helper.setTo(to);

        // You can also add CC and BCC
        // helper.setCc(cc);
        // helper.setBcc(bcc);

        // Method is used to set the content of the email.
        // The second parameter (true) indicates that
        // the content is in HTML format.
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }
}
