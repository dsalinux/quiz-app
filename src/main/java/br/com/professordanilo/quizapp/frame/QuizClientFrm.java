package br.com.professordanilo.quizapp.frame;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;

public class QuizClientFrm extends JFrame {

    private static final String TITLE = "Quiz";
    
    public QuizClientFrm() throws HeadlessException {
        super(TITLE);
    }
    
}
