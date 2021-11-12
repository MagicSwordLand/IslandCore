package net.brian.islandcore.common.objects;

import org.apache.commons.logging.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IslandLogger {
    private final static Logger logger = Logger.getLogger("IslandLogger");
    public static void logInfo(String message){
        logger.log(Level.INFO,message);
    }
}
