package org.tom.jasyncmail.testers;

import org.tom.jasyncmail.emailsender.EmailSender;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.model.Properties;

/**
 * Remember to put your own email where asked
 * @author Tommaso Ribaudo
 */
public class JAsyncMailTester {

    public static void main(String[] args) {
        //!Run this if you want to test passing no properties
        //sendingAnEmail(); //Normally  Doesn't Pass

        //!Run this if you want to try with wrong credentials        
        //Declaring the properties wrong
        Properties properties = new Properties("wrong@email.com", "wrongPassword123", "smtp.gmail.com", "587"); //only set once at the beginning of your application
        //sendingAnEmail(); //Normally Doesn't Pass

        //!Run This for normal conditions
        //Declaring the properties to be used during the whole process
        properties = new Properties("[YOUR_EMAIL_HERE]", "[YOUR_EMAIL_PASSWORD_HERE]", "smtp.gmail.com", "587"); //only set once at the beginning of your application

        //tests
        sendingAnEmail(); //Normally Passes
        sendingMultipleEmails(); //Normally Passes
    }

    private static void sendingAnEmail() {
        printTime("Sending an email", true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        emailSenderInstance.sendEmail(new Mail("[RECIPIENT_EMAIL_HERE]", "sendingAnEmailTest subject", "sendingAnEmailTest Body"));
        printTime("Sending an email Test", false);
    }

    private static void sendingMultipleEmails() {
        printTime("Sending multiple emails", true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        for (int i = 0; i < 10; i++) {
            emailSenderInstance.sendEmail(new Mail("[RECIPIENT_EMAIL_HERE]", "sendingMultipleEmails subject #" + i, "sendingMultipleEmails Body #" + i));
        }
        printTime("Sending multiple emails", false);
    }

    /**
     * either prints started methodName or ended methodName depending on the
     * boolean value
     *
     * @param methodName
     * @param started
     */
    private static void printTime(String methodName, boolean started) {
        if (started) {
            System.out.println("Started " + methodName + " at: " + System.currentTimeMillis());
        } else {
            System.out.println("Ended " + methodName + " at: " + System.currentTimeMillis());
        }
    }

}
