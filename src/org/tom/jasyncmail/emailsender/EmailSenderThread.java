package org.tom.jasyncmail.emailsender;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.tom.jasyncmail.model.Mail;

import org.tom.jasyncmail.model.Properties;

/**
 *
 * @author Tommaso Ribaudo
 */
public class EmailSenderThread extends Thread {

    private Mail mail;
    private Properties properties;

    public EmailSenderThread(Mail mail, Properties properties) {
        this.mail = mail;
        this.properties = properties;
    }

    @Override
    public void run() {
        try {
            mail.send(properties);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, "Jasync couldnt send email {0}", ex.getMessage());
        }
    }
}
