package org.tom.jasyncmail.tester;

import org.tom.jasyncmail.emailsender.EmailSender;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.properties.MailProperties;
import org.tom.jasyncmail.util.LoggerHelper;

/**
 * This class is used to test the JAsyncMail class. It simulates a long-term
 * mail server. 100 messages in total are sent to the email sender at random
 * intervals and random quantities.
 *
 * @author Tommaso Ribaudo
 */
public class JAsyncMailLongTermTester {

    public static void main(String[] args) {
        //Declaring the properties to be used during the whole process
        MailProperties properties = new MailProperties(TesterProps.EMAIL, TesterProps.PASSWORD, "smtp.gmail.com", "587"); //only set once at the beginning of your application
        int count = 0;
        int total = 100;
        while (count < total) {
            //get a random number btw 1 and 10
            int numberOfEmails = (int) (Math.random() * 10) + 1;
            //get a random number btw 1 and 5
            int interval = (int) (Math.random() * 5) + 1;
            waitFor(interval * 60);
            System.out.println("Sending " + numberOfEmails + " emails after waiting " + interval + " minutes");
            sendMultipleEmails(numberOfEmails);
        }

    }

    private static void sendingAnEmail() {
        //LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendingAnEmailTest subject", "sendingAnEmailTest Body"));
        //LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), false);
    }

    private static void sendMultipleEmails(int numberOfEmails) {
        //LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        for (int i = 0; i < numberOfEmails; i++) {
            emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendMultipleEmailsTest subject", "sendMultipleEmailsTest Body"));
        }
    }

    private static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
