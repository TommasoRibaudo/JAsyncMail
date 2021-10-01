package org.tom.jasyncmail.tester;

import org.tom.jasyncmail.emailsender.EmailSender;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.properties.MailProperties;
import org.tom.jasyncmail.util.LoggerHelper;

/**
 * Remember to put your own email where asked
 *
 * @author Tommaso Ribaudo
 */
public class JAsyncMailBasicsTester {

    public static void main(String[] args) {
        //!Run this if you want to test passing no properties
        //sendingAnEmail(); //Normally  Doesn't Pass

        //Declaring the properties wrong
        MailProperties properties = new MailProperties("wrong@email.com", "wrongPassword123", "smtp.gmail.com", "587" ); //only set once at the beginning of your application

        //!Run this if you want to try with wrong credentials   
        sendingAnEmail(); //Normally Doesn't Pass

        //Declaring the properties to be used during the whole process
        properties = new MailProperties(TesterProps.EMAIL, TesterProps.PASSWORD, "smtp.gmail.com", "587"); //only set once at the beginning of your application

        //!Run This for normal conditions
        //sendingAnEmail(); //Normally Passes
        //sendingMultipleEmails(); //Normally Passes
        
        //!Run this to insert in the queue a wrong email
        //sendingAnEmailToNonExistentRecipient(); //Normally Passes
        //sendingMultipleEmails(); //Normally Passes
    }   

    private static void sendingAnEmail() {
        LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendingAnEmailTest subject", "sendingAnEmailTest Body"));
        LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), false);
    }

    private static void sendingAnEmailToNonExistentRecipient() {
        LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        emailSenderInstance.sendEmail(new Mail(TesterProps.WRONG_EMAIL, "sendingAnEmailTest subject", "sendingAnEmailTest Body"));
        LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), false);
    }

    private static void sendingMultipleEmails() {
        LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        for (int i = 0; i < 5; i++) {
            emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendingMultipleEmails subject #" + i, "sendingMultipleEmails Body #" + i));
        }
        LoggerHelper.printWithTime(JAsyncMailBasicsTester.class.getName(), false);
    }

}
