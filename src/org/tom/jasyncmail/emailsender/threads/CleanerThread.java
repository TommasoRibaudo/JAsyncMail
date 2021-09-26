package org.tom.jasyncmail.emailsender.threads;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.tom.jasyncmail.emailsender.EmailSender;

/**
 * this threads takes care of cleaning the thread list to make
 * space for new threads
 * @author Tommaso Ribaudo
 */
public class CleanerThread extends Thread
{
    @Override
    public void run(){
        //get instance of EmailSender
        EmailSender es = EmailSender.getInstance(); //TODO test if this actually changes while the thread is running.
        while(true)
        {
            //wait for a while
            try {
                Thread.sleep(es.TICK_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(CleanerThread.class.getName()).log(Level.WARNING, "Jasync thread cleaner interrupted unexpedectly," 
                + " starting thread cleaning process earlier... {0}", ex.getMessage());               
            }
            //clean the thread list
            this.cleanThreads(es);
        }
        
        

    }

    public void cleanThreads(EmailSender es) {
        for (Thread t : es.getThreads()) {
            if (!t.isAlive()) {
                es.getThreads().remove(t);
            }
        }
    }
}
