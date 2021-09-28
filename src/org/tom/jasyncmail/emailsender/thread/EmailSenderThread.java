package org.tom.jasyncmail.emailsender.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.tom.jasyncmail.emailsender.EmailSender;
import org.tom.jasyncmail.model.Mail;
import org.tom.jasyncmail.util.LoggerHelper;

/**
 * This thread runs to make sure that queued emails get sent when there are
 * available threads to be used. this thread dies after the last email is sent.
 *
 * @author Tommaso Ribaudo
 */
public class EmailSenderThread extends Thread {

    private boolean waitMore;
    private List<Mail> toSend;

    private EmailSender emailSender;

    public EmailSenderThread() {
        waitMore = false;
        toSend = new ArrayList<>();
    }

    @Override
    public void run() {
        //LoggerHelper.printWithTime(EmailSenderThread.class.getName(), true);
        while (true) {
            emailSender = EmailSender.getInstance(); //TODO can this be outside of the loop?
            //wait for a while
            try {
                int extraWait = 0;
                if (waitMore) { //waits 25 extra seconds when theres the "Too Many Logins" error
                    setWaitMore(false);
                    extraWait = 25000;
                }
                Thread.sleep(EmailSender.TICK_TIME + extraWait);
            } catch (InterruptedException ex) {
                Logger.getLogger(EmailSenderThread.class.getName()).log(Level.WARNING, "Jasync sender help thread interrupted unexpedectly,"
                        + " starting thread process earlier. {0}\n", ex.getMessage());
            }

            //if ToSend is still empty, sleep some more!
            if (toSend.isEmpty()) {
                continue;
            }
            this.sendQueuedEmails();
        }
    }

    private void sendQueuedEmails() {
        //Logger.getLogger(EmailSenderThread.class.getName()).log(Level.INFO, "Sending queued emails.\n");
        List<Mail> removeThese = new ArrayList<>();
        List<MimeMessage> batch = new ArrayList<>();
        int count = 0;
        synchronized (getToSend()) {
            Iterator i = getToSend().iterator();
            while (i.hasNext() && count < EmailSender.MAX_EMAILS_PER_BATCH) {
                try {
                    count++;
                    Mail m = (Mail) i.next();
                    batch.add(m.getMimeMessage(emailSender.getSession()));
                    removeThese.add(m);
                } catch (MessagingException ex) {
                    Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.sendEmailsInBatch(batch);
            this.getToSend().removeAll(removeThese);
        }
    }

    private void sendEmailsInBatch(List<MimeMessage> messages) { //TODO: when there is an error this whole thing stops but the whole batch is removed from the list!!
        try {
            try (Transport t = emailSender.getSession().getTransport()) {
                t.connect();
                for (MimeMessage m : messages) {
                    t.sendMessage(m, m.getAllRecipients());
                }
            }
        } catch (NoSuchProviderException ex) { //TODO: When does this error happen?
            Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setWaitMore(boolean waitMore) {
        this.waitMore = waitMore;
    }

    public List<Mail> getToSend() {
        return toSend;
    }
}
