package org.tom.jasyncmail.tester;

import org.tom.jasyncmail.emailsender.EmailSender;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.properties.MailProperties;

/**
 *
 * @author Tommaso Ribaudo
 */
public class JAsyncMailOutlookTester {

    public static void main(String[] args) {
        //Declaring the properties to be used during the whole process
        MailProperties properties = new MailProperties(TesterProps.OUTLOOK_EMAIL, TesterProps.OUTLOOK_PASSWORD, "smtp-mail.outlook.com", "587"); //only set once at the beginning of your application
        sendingAnEmail();
        sendingMultipleEmails();
    }

    private static void sendingAnEmail() {
        EmailSender emailSenderInstance = EmailSender.getInstance();
        emailSenderInstance.sendEmail(new Mail(TesterProps.OUTLOOK_EMAIL, "sendingAnEmailTest subject", "sendingAnEmailTest Body"));
    }

    private static void sendingMultipleEmails() { 
        EmailSender emailSenderInstance = EmailSender.getInstance();
        for (int i = 0; i < 5; i++) {
            emailSenderInstance.sendEmail(new Mail(TesterProps.OUTLOOK_EMAIL, "sendingMultipleEmails subject #" + i, "sendingMultipleEmails Body #" + i));
        }
    }

}
