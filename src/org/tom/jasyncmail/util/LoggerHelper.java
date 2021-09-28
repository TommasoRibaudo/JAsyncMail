package org.tom.jasyncmail.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tommaso Ribaudo
 */
public class LoggerHelper {

    /**
     * either prints started methodName or ended methodName depending on the
     * boolean value
     *
     * @param className
     * @param started
     */
    public static void printWithTime(String className, boolean started) {
        String processPosition = started ? "Started" : "Ended";
        Logger.getLogger(className).log(Level.INFO, processPosition + " {0} process at: {1}\n", new Object[]{className, System.currentTimeMillis()});
    }
    
}
