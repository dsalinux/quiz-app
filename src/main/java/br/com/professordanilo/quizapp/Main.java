/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp;

import br.com.professordanilo.quizapp.frame.QuizManager;
import br.com.professordanilo.quizapp.util.LogUtil;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author danilo
 */
public class Main {
    public static void main(String[] args) {
        
        try {
            Map<String, LookAndFeelInfo> lafs = new HashMap<>();
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LogUtil.error(Main.class, ex.getMessage());
        }
        
        new QuizManager().setVisible(true);
    }
    
}
