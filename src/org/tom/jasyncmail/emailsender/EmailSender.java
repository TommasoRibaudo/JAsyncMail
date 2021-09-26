package org.tom.jasyncmail.emailsender;

import java.util.ArrayList;

import org.tom.jasyncmail.emailsender.threads.CleanerThread;
import org.tom.jasyncmail.emailsender.threads.EmailSenderThread;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.tom.jasyncmail.emailsender.threads.EmailSenderHelperThread;
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
    public static int MAX_NUMBER_OF_THREADS = 50; //TODO can you change this number from the tester?
    /**
     * Used by helper threads (Cleaner, Sender) to run their routine checks
     */
    public static int TICK_TIME = 1000;

    private List<Mail> mails;
    private List<Thread> threads;

    //Helper threads
    private CleanerThread cleaner;
    private EmailSenderHelperThread sender;

    private EmailSender() {
        this.mails = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.cleaner = new CleanerThread();
        this.cleaner.start(); //starting cleaning process
        this.sender = new EmailSenderHelperThread();
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
        if (threads.size() < MAX_NUMBER_OF_THREADS) {
            //Normal process
            EmailSenderThread emailSenderThread = new EmailSenderThread(mail, Properties.getInstance());
            Thread thread = new Thread(emailSenderThread);
            thread.start();
            threads.add(thread);
        } else {
            //adding to queued emails and starting up the sender helper.
            mails.add(mail);
            this.startSender();
        }
    }

    /**
     * This should be used instead of directly accessing the thread. It starts
     * the sender helper thread only if its not already running.
     */
    public void startSender() {
        if (!this.sender.isAlive()) {
            this.sender.start();
        }
    }

    public List<Mail> getMails() {
        return mails;
    }

    public void setMails(List<Mail> mails) {
        this.mails = mails;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }
}
