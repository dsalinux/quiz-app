package br.com.professordanilo.quizapp.util;

import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.awt.Component;
import javax.swing.JOptionPane;

public class SwingUtil {


    public static void addMessageError(String message){
        addMessageError(null, message);
    }
    
    public static void addMessageError(Component component, String message){
        addMessage(component, message, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    public static void addMessageError(Component component, SystemException exception){
        addMessageError(component, exception.getMessage());
    }
    public static void addMessageError(Component component, BusinessException exception){
        addMessageError(component, exception.getMessage());
    }
    public static void addMessageError(SystemException exception){
        addMessageError(null, exception.getMessage());
    }
    public static void addMessageError(BusinessException exception){
        addMessageError(null, exception.getMessage());
    }
    
    public static void addMessageWarn(String message){
        addMessageWarn(null, message);
    }
    public static void addMessageWarn(Component component, String message){
        addMessage(component, message, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    public static void addMessageWarn(SystemException exception){
        addMessageWarn(null, exception.getMessage());
    }
    public static void addMessageWarn(BusinessException exception){
        addMessageWarn(null, exception.getMessage());
    }
    public static void addMessageWarn(Component component, SystemException exception){
        addMessageWarn(component, exception.getMessage());
    }
    public static void addMessageWarn(Component component, BusinessException exception){
        addMessageWarn(component, exception.getMessage());
    }
    
    public static void addMessageInfo(String message){
        addMessageInfo(null, message);
    }
    public static void addMessageInfo(Component component, String message){
        addMessage(component, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void addMessage(Component component, String message, String title, int messageType){
        JOptionPane.showMessageDialog(component, message, title, messageType);
    }
    
}
