package br.com.professordanilo.quizapp.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuizClientFrm extends JFrame {

    private static final String TITLE = "Quiz";
    
    private JLabel questao = new JLabel();
    private JLabel[] respostas = new JLabel[]{new JLabel()};
    
    private JPanel panelQuestoes;
    private JPanel panelCompetidores;
    
    public QuizClientFrm() throws HeadlessException {
        super(TITLE);
        setLayout(new BorderLayout());
        questao.setText("Questão");
        respostas[0].setText("Teste Resposta");
        panelQuestoes = new JPanel(new BorderLayout());
        panelQuestoes.setOpaque(true);
        panelQuestoes.setBackground(Color.white);
        panelQuestoes.add(questao, BorderLayout.WEST);
        panelCompetidores = new JPanel(new BorderLayout());
        panelCompetidores.setOpaque(true);
        panelCompetidores.setBackground(Color.white);
        panelCompetidores.add(respostas[0], BorderLayout.WEST);
        add(panelCompetidores, BorderLayout.WEST);
        configLayout();
    }
    
    private void configLayout(){
        pack();
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new QuizClientFrm().setVisible(true);
    }
    
}
