package org.tom.jasyncmail.emailsender.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.tom.jasyncmail.emailsender.EmailSender;
import org.tom.jasyncmail.model.Mail;

/**
 * This thread runs to make sure that queued emails get sent when there are
 * available threads to be used. this thread dies after the last email is sent.
 *
 * @author Tommaso Ribaudo
 */
public class EmailSenderHelperThread extends Thread {

    @Override
    public void run() {
        //get instance of EmailSender
        EmailSender es = EmailSender.getInstance(); //TODO test if this actually changes while the thread is running.
        boolean flag = true;
        
        while (flag) {
            if (es.getMails().isEmpty()) {
                flag = false;
                break;
            }

            //wait for a while
            try {
                Thread.sleep(es.TICK_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(EmailSenderHelperThread.class.getName()).log(Level.WARNING, "Jasync sender help thread interrupted unexpedectly,"
                        + " starting thread process earlier... {0}", ex.getMessage());
            }
            this.sendQueuedEmails(es);
        }
    }

    private void sendQueuedEmails(EmailSender es) {
        for (Mail m : es.getMails()) {
            if (es.getThreads().size()<EmailSender.MAX_NUMBER_OF_THREADS) {
                es.getMails().remove(m);
                es.sendEmail(m);
            } else {
                break;
            }
        }
    }
}
