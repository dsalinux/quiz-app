/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp;

import br.com.professordanilo.quizapp.frame.QuizManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author danilo
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    public static void main(String[] args) {
        try {
            Map<String, LookAndFeelInfo> lafs = new HashMap<>();
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println(info);
                if ("GTK+".equals(info.getName())) {
                    lafs.put("GTK+",info);
                } else if("Nimbus".equals(info.getName())){
                    lafs.put("Nimbus",info);
                }
            }
            if(lafs.containsKey("GTK+")){
                UIManager.setLookAndFeel(lafs.get("GTK+").getClassName());
            } else if(lafs.containsKey("Nimbus")){
                UIManager.setLookAndFeel(lafs.get("Nimbus").getClassName());
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuizManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuizManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuizManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
        } 
        logger.log(Level.ERROR, "testes");
        logger.error("Erros de testes");
        new QuizManager().setVisible(true);
    }
    
}
