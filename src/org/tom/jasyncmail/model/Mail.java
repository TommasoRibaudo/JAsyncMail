package org.tom.jasyncmail.model;

import java.io.Serializable;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Tommaso Ribaudo
 */
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String to;
    private final String subject;
    private final String body;

    public Mail(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void send(Properties properties) throws AddressException, MessagingException {
        Session session = Session.getInstance(properties.toProperties(), new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getEmail(), properties.getPassword());
            }
        });
        // Used to debug SMTP issues
        //session.setDebug(true);trues
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(properties.getEmail()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    }
}
