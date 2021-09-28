package org.tom.jasyncmail.tester;

import org.tom.jasyncmail.emailsender.EmailSender;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.model.Properties;
import org.tom.jasyncmail.util.LoggerHelper;

/**
 * Remember to put your own email where asked
 *
 * @author Tommaso Ribaudo
 */
public class JAsyncMailStressTester {

    public static void main(String[] args) {
        //Declaring the properties to be used during the whole process
        Properties properties = new Properties(TesterProps.EMAIL, TesterProps.PASSWORD, "smtp.gmail.com", "587", true, false, true); //only set once at the beginning of your application

        //sendingStressTestEmails_1(); //Normally Passes
        sendingStressTestEmails_2();
        //TODO Stress test over a long period of time with variable interval and number of email
        //sendingStressTestEmails_3();
    }

    private static void sendingAnEmail() {
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendingAnEmailTest subject", "sendingAnEmailTest Body"));
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), false);

    }

    private static void sendingStressTestEmails_1() {
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        for (int i = 0; i < 25; i++) {
            emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendingStressTestEmails_1 subject #" + i, "sendingStressTestEmails_1 Body #" + i));
        }
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), false);
    }

    private static void sendingStressTestEmails_2() {
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        for (int i = 0; i < 100; i++) {
            emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendingStressTestEmails_2 subject #" + i, "sendingStressTestEmails_2 Body #" + i));
        }
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), false);
    }

    private static void sendingStressTestEmails_3() {
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), true);
        EmailSender emailSenderInstance = EmailSender.getInstance();
        for (int i = 0; i < 500; i++) {
            emailSenderInstance.sendEmail(new Mail(TesterProps.EMAIL, "sendingStressTestEmails_3 subject #" + i, "sendingStressTestEmails_3 Body #" + i));
        }
        LoggerHelper.printWithTime(JAsyncMailStressTester.class.getName(), false);
    }
}
