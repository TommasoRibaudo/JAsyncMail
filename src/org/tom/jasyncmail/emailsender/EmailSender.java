package org.tom.jasyncmail.emailsender;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.tom.jasyncmail.emailsender.thread.EmailSenderThread;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.properties.MailProperties;

/**
 *
 * @author Tommaso Ribaudo
 */
public class EmailSender {

    public Session getSession() {
        return session;
    }

    /**
     * instance of EmailSender
     */
    private static EmailSender instance = null;
    
    //Helper thread
    private EmailSenderThread sender;

    private final Session session;

    private EmailSender() {
        this.sender = new EmailSenderThread();
        this.sender.setName("Sender-Thread");
        MailProperties properties = MailProperties.getInstance();
        session = Session.getInstance(properties.toProperties(), new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getEmail(), properties.getPassword());
            }
        });

    }

    public static EmailSender getInstance() {
        //check if properties are set
        if (MailProperties.getInstance() != null) //if null it will throw an error by itself
        {
            if (instance == null) {
                instance = new EmailSender();
                instance.getSender().start(); //starting sender helper process for queued emails
            }
            return instance;
        } else {
            throw new IllegalStateException("Properties not set.\n");
        }
    }

    public void sendEmail(Mail mail) {
        synchronized (this.sender.getToSend()) {
            this.sender.getToSend().add(mail);
        }
    }

    public EmailSenderThread getSender() {
        return sender;
    }

}
