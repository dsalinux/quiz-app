package br.com.professordanilo.quizapp.util;

import org.apache.logging.log4j.LogManager;

/**
 *
 * @author danilo
 */
public class LogUtil {
    
    public static void debug(Class clazz, String message){
        LogManager.getLogger(clazz).debug(message);
    }
    public static void info(Class clazz, String message){
        LogManager.getLogger(clazz).info(message);
    }
    public static void warn(Class clazz, String message){
        LogManager.getLogger(clazz).warn(message);
    }
    public static void error(Class clazz, String message){
        LogManager.getLogger(clazz).error(message);
    }
    public static void fatal(Class clazz, String message){
        LogManager.getLogger(clazz).fatal(message);
    }
    public static void fatal(Class clazz, Throwable throwable){
        LogManager.getLogger(clazz).fatal(throwable);
    }
    
}
