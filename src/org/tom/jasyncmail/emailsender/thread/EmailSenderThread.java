package org.tom.jasyncmail.emailsender.thread;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.tom.jasyncmail.properties.JAsyncSystemProperties;
import org.tom.jasyncmail.util.LoggerHelper;

/**
 * This thread runs to make sure that queued emails get sent when there are
 * available threads to be used. this thread dies after the last email is sent.
 *
 * @author Tommaso Ribaudo
 */
public class EmailSenderThread extends Thread {

    private boolean waitMore;
    private final List<Mail> toSend;

    private EmailSender emailSender;
    //Sender Settings
    private final int tickTime;
    private final int maxEmailsPerBatch;
    private final int maxNumberOfRetries;

    public EmailSenderThread() {
        waitMore = false;
        toSend = new ArrayList<>();
        tickTime = JAsyncSystemProperties.TICK_TIME;
        maxEmailsPerBatch = JAsyncSystemProperties.MAX_EMAILS_PER_BATCH;
        maxNumberOfRetries = JAsyncSystemProperties.MAX_NUMBER_OF_RETRIES;
    }

    @Override
    public void run() {
        //LoggerHelper.printWithTime(EmailSenderThread.class.getName(), true);
        while (true) {
            emailSender = EmailSender.getInstance(); //TODO can this be outside of the loop?
            this.waitTickTime();
            synchronized (getToSend()) {
                if (!toSend.isEmpty()) {
                    this.processBatch();
                }
            }
        }
    }

    private void waitTickTime() {
        try {
            Thread.sleep(tickTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(EmailSenderThread.class.getName()).log(Level.WARNING, "Jasync sender help thread interrupted unexpedectly,"
                    + " starting thread process earlier. {0}\n", ex.getMessage());
        }
    }

    /**
     * Prepares a batch of MimeMessages to be sent. Once done it checks the
     * emails that have been successfully sent, and removes them from the toSend
     * list.
     */
    private void processBatch() {
        //Logger.getLogger(EmailSenderThread.class.getName()).log(Level.INFO, "Sending queued emails.\n");
        HashMap<MimeMessage, Mail> batchHM = new HashMap<>();
        List<MimeMessage> batch = new ArrayList<>();
        List<Mail> removeThese = new ArrayList<>(); //the emails succesfully sent are to be removed from the toSend list.
        int count = 0;

        Iterator i = getToSend().iterator();
        while (i.hasNext() && count < this.maxEmailsPerBatch) {
            try {
                count++;
                Mail m = (Mail) i.next();
                //checking if the system tried to send this email the maxnumberofretries
                if (m.getNumberOfTries() < this.maxNumberOfRetries) {
                    MimeMessage mm = m.getMimeMessage(emailSender.getSession());
                    batch.add(mm);
                    batchHM.put(mm, m);
                } else { //Already tried to send this email 3 times. We cant send it, we need to log and remove it
                    Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE,
                            "Unsuccessfully tried to send email \"{0}\"", m.getSubject());
                    removeThese.add(m);
                }
            } catch (MessagingException ex) {
                Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<MimeMessage> successfullySent = this.sendEmailsInBatch(batch);

        for (MimeMessage mm : successfullySent) {
            removeThese.add(batchHM.get(mm));
        }

        this.getToSend().removeAll(removeThese);

    }

    /**
     *
     * @param messages the batch of emails to be sent
     * @return a list of successfully sent MimeMessages.
     */
    private List<MimeMessage> sendEmailsInBatch(List<MimeMessage> messages) { //TODO: when there is an error this whole thing stops but the whole batch is removed from the list!!
        List<MimeMessage> batchResult = new ArrayList<>();
        try {
            try (Transport t = emailSender.getSession().getTransport()) {
                t.connect();
                for (MimeMessage m : messages) {
                    t.sendMessage(m, m.getAllRecipients());
                    batchResult.add(m);

                }
            }
        } catch (NoSuchProviderException ex) { //TODO: When does this error happen?
            Logger.getLogger(EmailSenderThread.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderThread.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return batchResult;
    }

    public List<Mail> getToSend() {
        return toSend;
    }
}
