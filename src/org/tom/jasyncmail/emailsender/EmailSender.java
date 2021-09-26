package org.tom.jasyncmail.emailsender;

import java.util.LinkedList;
import java.util.Queue;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.model.Properties;

/**
 *
 * @author Tommaso Ribaudo
 */
public class EmailSender {

    /**
     * instance of EmailSender
     */
    private static EmailSender instance = null;

    public static int MAX_NUMBER_OF_THREADS = 50;

    private int maxNumberOfThreads;
    private int currentNumberOfThreads;

    private Queue<Mail> mailList;
    private Queue<Thread> threadList;

    private EmailSender() {
        this.maxNumberOfThreads = MAX_NUMBER_OF_THREADS;
        this.currentNumberOfThreads = 0;
        this.mailList = new LinkedList<Mail>();
        this.threadList = new LinkedList<Thread>();
    }

    public static EmailSender getInstance() {
        //check if properties are set
        if (Properties.getInstance() != null) //if null it will throw an error by itself
        {
            if (instance == null) {
                instance = new EmailSender();
            }
            return instance;
        } else {
            throw new IllegalStateException("Properties not set");
        }
    }

    public void sendEmail(Mail mail) {
        EmailSenderThread emailSenderThread = new EmailSenderThread(mail, Properties.getInstance());
        Thread thread = new Thread(emailSenderThread);
        thread.start();
    }
}
