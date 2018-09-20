package br.com.professordanilo.quizapp.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Label;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class QuizProjectionFrm extends JFrame {

    private static final String TITLE = "Quiz";

    private JLabel questao = new JLabel();
    private JLabel[] respostas = new JLabel[]{new JLabel(), new JLabel()};

    private JPanel panelAguardando;

    private JPanel panelQuestoes;
    private JPanel panelCompetidores;
    private JPanel panelCabecalho;
    private JPanel panelQuiz;

    public QuizProjectionFrm() throws HeadlessException {
        super(TITLE);
        setLayout(new BorderLayout());
//        setPreferredSize(new Dimension(400, 300));
        
        createPainelQuizQuestions();
        createWaitPanel();
        configLayout();
    }

    private void createPainelQuizQuestions(){
        panelQuiz = new JPanel(new BorderLayout());
        panelQuiz.setPreferredSize(getPreferredSize());
        questao.setText("Questão");
        respostas[0].setText("Teste Resposta");
        respostas[1].setText("Teste Resposta");

        panelQuestoes = new JPanel(new BorderLayout());
        panelQuestoes.setOpaque(true);
        panelQuestoes.setBackground(Color.white);
        panelQuestoes.setPreferredSize(new Dimension(getWidth(), getHeight()));
        panelQuestoes.add(questao, BorderLayout.CENTER);

        
        createPanelHead();
        createPanelPlayers();
        createWaitPanel();
        

        panelQuiz.add(panelCabecalho, BorderLayout.NORTH);
        panelQuiz.add(panelQuestoes, BorderLayout.CENTER);
        panelQuiz.add(panelCompetidores, BorderLayout.EAST);

        add(panelQuiz);
//        startWaitPanel();
    }
    
    private void createPanelHead(){
        panelCabecalho = new JPanel(new GridBagLayout());
        panelCabecalho.setPreferredSize(new Dimension(300, 130));
        panelCabecalho.setOpaque(true);
        panelCabecalho.setBackground(Color.white);
        MatteBorder linha = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray);
        panelCabecalho.setBorder(linha);
        ImageIcon image = new ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/logo.png"));
        JLabel jLabel = new JLabel(image);
        jLabel.setIconTextGap(JLabel.CENTER);
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        panelCabecalho.add(jLabel);
    }
    
    private void createPanelPlayers(){
        panelCompetidores = new JPanel(new GridLayout(2, 2));
        panelCompetidores.setBackground(new Color(240, 240, 240));
        panelCompetidores.setOpaque(true);
//        panelCompetidores.setBackground(Color.white);
        panelCompetidores.add(new JLabel(new ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/player-male.png"))));
        panelCompetidores.add(new JLabel("Danilo Souza Almeida"));
        panelCompetidores.add(new JLabel(new ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/player-female.png"))));
        panelCompetidores.add(new JLabel("Nayara Gabriela"));
    }
    
    private void createWaitPanel(){
        panelAguardando = new JPanel(new BorderLayout());

        JLabel aguarde = new JLabel("Aguardando a pergunta...");
        
        aguarde.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        aguarde.setOpaque(true);
        aguarde.setBackground(Color.white);
        aguarde.setHorizontalAlignment(JLabel.CENTER);
        panelAguardando.add(aguarde, BorderLayout.CENTER);

        RainbowPanel panelAnimationTop = new RainbowPanel(new Dimension(100, 80));
        RainbowPanel panelAnimationBottom = new RainbowPanel(new Dimension(100, 80));
        panelAguardando.add(panelAnimationTop, BorderLayout.NORTH);
        panelAguardando.add(panelAnimationBottom, BorderLayout.SOUTH);
        panelAnimationTop.startAnimation();
        panelAnimationBottom.startAnimation();
        panelAguardando.setVisible(false);
        
    }
    
    private void startWaitPanel() {
        add(panelAguardando);
        panelQuiz.setVisible(false);
        panelAguardando.setVisible(true);
    }

    public void stopWaitPanel() {
        add(panelQuiz);
        panelAguardando.setVisible(false);
        panelQuiz.setVisible(true);
    }

    private void configLayout() {
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        new QuizProjectionFrm().setVisible(true);
    }

}
