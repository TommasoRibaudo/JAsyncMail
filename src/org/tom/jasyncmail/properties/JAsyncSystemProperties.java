    package org.tom.jasyncmail.properties;

/**
 *
 * @author Tommaso Ribaudo
 */
public class JAsyncSystemProperties {
    /**
     * Used by Sender thread to pause between their routine checks.
     */
    public final static int TICK_TIME = 60000;
    /**
     * Used by Sender thread to keep the number of sent emails to a maximum of 50 per cycle.
     */
    public final static int MAX_EMAILS_PER_BATCH = 50;
    /**
     * Maximum number of retries for sending a particular email
     */
    public final static int MAX_NUMBER_OF_RETRIES = 3;
}
