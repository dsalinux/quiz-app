package br.com.professordanilo.quizapp.frame;

import br.com.professordanilo.quizapp.entity.Event;
import br.com.professordanilo.quizapp.entity.Player;
import br.com.professordanilo.quizapp.entity.Question;
import br.com.professordanilo.quizapp.util.AppIcons;
import br.com.professordanilo.quizapp.util.AudioUtil;
import br.com.professordanilo.quizapp.util.ImageUtil;
import br.com.professordanilo.quizapp.util.exception.BusinessException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class QuizProjectionFrm extends JFrame {

    private static final String TITLE = "Quiz";

    private AnswerLabel questionLabel = new AnswerLabel(new Color(255, 140, 140), new Color(198, 77, 77));
    private AnswerLabel[] respostas = new AnswerLabel[]{new AnswerLabel(), new AnswerLabel(), new AnswerLabel(), new AnswerLabel()};

    private JPanel panelAguardando;

    private JPanel panelQuestions;
    private JPanel panelInfoPlayers;
    private JPanel panelCabecalho;
    private JPanel panelQuiz;
    private FrameState frameState;
    private String htmlTagInit = "<html><p \"line-height:1\" align=\"JUSTIFY\">";
    private String htmlTagClose = "</p></html>";

    private Event event;

    private Question question;
    private int answerSelected = 0;
    
    JLabel player1, player2;

    public enum FrameState {
        WAIT,
        QUESTION,
        RESULT
    }

    public QuizProjectionFrm(Event event) throws HeadlessException {
        super(TITLE);
        this.event = event;
        setLayout(new BorderLayout());
//        setPreferredSize(new Dimension(400, 300));

        createPainelQuizQuestions();
        createWaitPanel();
        configLayout();
        startWaitPanel();
    }

    private void createPainelQuizQuestions() {
        panelQuiz = new JPanel(new BorderLayout());
        panelQuiz.setPreferredSize(getPreferredSize());
        questionLabel.setText("");
        questionLabel.setForeground(Color.WHITE);

        panelQuestions = new JPanel();
        panelQuestions.setOpaque(true);
        panelQuestions.setBackground(Color.black);
//        panelQuestions.setPreferredSize(new Dimension(getWidth(), getHeight()));
//        panelQuestions.add(questionLabel, BorderLayout.CENTER);
        GridBagLayout bagLayout = new GridBagLayout();
        bagLayout.columnWeights = new double[1];
        bagLayout.columnWeights[0] = 1.0;
        bagLayout.rowHeights = new int[5];
        panelQuestions.setLayout(bagLayout);
        for (AnswerLabel resposta : respostas) {
            resposta.setText("");
            resposta.setHorizontalAlignment(JLabel.LEADING);
            resposta.setForeground(Color.WHITE);
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.gridheight = GridBagConstraints.BOTH;
        questionLabel.setMinimumSize(new Dimension(100, 250));
        panelQuestions.add(questionLabel, gbc);
        gbc.gridy = 1;
        panelQuestions.add(respostas[0], gbc);
        gbc.gridy = 2;
        panelQuestions.add(respostas[1], gbc);
        gbc.gridy = 3;
        panelQuestions.add(respostas[2], gbc);
        gbc.gridy = 4;
        panelQuestions.add(respostas[3], gbc);
        createPanelPlayers();

        configLayout();

        panelQuiz.add(panelQuestions, BorderLayout.CENTER);

        add(panelQuiz);
    }

    private void createPanelHead(JPanel panel) {
        try {
            panelCabecalho = new JPanel(new GridBagLayout());
            panelCabecalho.setPreferredSize(new Dimension(300, 130));
            panelCabecalho.setOpaque(true);
            panelCabecalho.setBackground(Color.black);
            panelCabecalho.setBorder(new MatteBorder(new Insets(0, 0, 0, 15), Color.black));
//            MatteBorder linha = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray);
//            panelCabecalho.setBorder(linha);
            InputStream is;
            if (event.getLogo() != null && event.getLogo().length > 1) {
                is = new java.io.ByteArrayInputStream(event.getLogo());
            } else {
                is = getClass().getResourceAsStream("/br/com/professordanilo/quizapp/images/logo.png");
            }
            BufferedImage bImage = ImageIO.read(is);
            JLabel eventIcon = new JLabel(new ImageIcon(ImageUtil.resizeToMaxValue(bImage, 260, 260)));
            eventIcon.setHorizontalAlignment(JLabel.CENTER);
            eventIcon.setHorizontalAlignment(JLabel.CENTER);
            panelCabecalho.add(eventIcon);
            panel.add(panelCabecalho, BorderLayout.NORTH);
        } catch (IOException ex) {
            Logger.getLogger(QuizProjectionFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createPanelPlayers() {
        panelInfoPlayers = new JPanel(new GridLayout(3, 1));
        panelInfoPlayers.setBackground(Color.black);
        panelInfoPlayers.setOpaque(true);
        createPanelHead(panelInfoPlayers);
//        panelCompetidores.setBackground(Color.white);
//        JLabel iconPlayer1 = new JLabel();
//        iconPlayer1.setPreferredSize(new Dimension(50, 50));
//        iconPlayer1.setBorder(new LineBorder(Color.blue));
//        panelInfoPlayers.add(iconPlayer1);

        player1 = new JLabel("<html><body><h2></h2></body></html>");
        player1.setIcon(null);
        player1.setBorder(new MatteBorder(new Insets(0, 0, 0, 15), Color.black));
        player1.setPreferredSize(new Dimension(300, 100));
        player1.setHorizontalAlignment(JLabel.CENTER);
        player1.setHorizontalTextPosition(JLabel.CENTER);
        player1.setVerticalTextPosition(JLabel.BOTTOM);
        panelInfoPlayers.add(player1);
        player2 = new JLabel("<html><body><h2></h2></body></html>");
        player2.setBorder(new MatteBorder(new Insets(0, 0, 0, 15), Color.black));
        player2.setIcon(null);
        player2.setHorizontalAlignment(JLabel.CENTER);
        player2.setHorizontalTextPosition(JLabel.CENTER);
        player2.setVerticalTextPosition(JLabel.BOTTOM);
        panelInfoPlayers.add(player2);
        panelQuiz.add(panelInfoPlayers, BorderLayout.EAST);
    }
    
    public void updatePlayers(Player p1, Player p2){
        player1.setText("<html><body><h2>"+p1.getName()+"</h2></body></html>");
        updateIconPlayer(player1, p1);
        player2.setText("<html><body><h2>"+p2.getName()+"</h2></body></html>");
        updateIconPlayer(player2, p2);
    }
    private void updateIconPlayer(JLabel player, Player p){
        switch (p.getPlayerType()) {
            case FEMALE:
                player.setIcon(AppIcons.ICON_FEMALE);
                break;
            case MALE:
                player.setIcon(AppIcons.ICON_MALE);
                break;
            case GROUP:
                player.setIcon(AppIcons.ICON_GROUP);
                break;
        }
    }

    private void createWaitPanel() {
        try {
            panelAguardando = new JPanel(new BorderLayout());

            JLabel aguarde = new JLabel();

            aguarde.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
            aguarde.setOpaque(true);
            aguarde.setBackground(Color.DARK_GRAY);
            aguarde.setHorizontalAlignment(JLabel.CENTER);
            aguarde.setHorizontalTextPosition(JLabel.CENTER);
            aguarde.setVerticalTextPosition(JLabel.BOTTOM);
            InputStream is;
            if (event.getLogo() != null && event.getLogo().length > 1) {
                is = new java.io.ByteArrayInputStream(event.getLogo());
            } else {
                is = getClass().getResourceAsStream("/br/com/professordanilo/quizapp/images/logo.png");
            }
            BufferedImage bImage = ImageIO.read(is);
//            int size = getContentPane().getWidth()<getContentPane().getHeight()?getContentPane().getWidth():getContentPane().getHeight();
//            size = (int) (size * 0.8);
            int size = 700;
            aguarde.setIcon(new ImageIcon(ImageUtil.resizeToMaxValue(bImage, size, size)));

            panelAguardando.add(aguarde, BorderLayout.CENTER);

            RainbowPanel panelAnimationTop = new RainbowPanel(new Dimension(100, 80));
            RainbowPanel panelAnimationBottom = new RainbowPanel(new Dimension(100, 80));
            panelAguardando.add(panelAnimationTop, BorderLayout.NORTH);
            panelAguardando.add(panelAnimationBottom, BorderLayout.SOUTH);
            panelAnimationTop.startAnimation();
            panelAnimationBottom.startAnimation();
            panelAguardando.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(QuizProjectionFrm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateQuestion() {
        int qtQuestao = question.getQuestion().length();
        int tamanho = fontSize(qtQuestao);
        String htmlTagInit = "<html><p \"line-height:1\" style=\"font:"+ tamanho+"px;\" align=\"JUSTIFY\">";
        questionLabel.setText(htmlTagInit+question.getQuestion()+htmlTagClose);

    }
    
    private int fontSize(int questionLength){
        int tamanho = 24;
        int base = 8;
        if(questionLength > 1500) {
            tamanho = base;
        } else if(questionLength > 1000) {
            tamanho = Double.valueOf(base * 1.1).intValue();
        } else if(questionLength > 900) {
            tamanho = Double.valueOf(base * 1.2).intValue();
        } else if(questionLength > 850) {
            tamanho = Double.valueOf(base * 1.3).intValue();
        } else if(questionLength > 800) {
            tamanho = Double.valueOf(base * 1.4).intValue();
        } else if(questionLength > 750) {
            tamanho = Double.valueOf(base * 1.5).intValue();
        } else if(questionLength > 700) {
            tamanho = Double.valueOf(base * 1.6).intValue();
        } else if(questionLength > 650) {
            tamanho = Double.valueOf(base * 1.7).intValue();
        } else if(questionLength > 600) {
            tamanho = Double.valueOf(base * 1.8).intValue();
        } else if(questionLength > 550) {
            tamanho = Double.valueOf(base * 1.9).intValue();
        } else if(questionLength > 500) {
            tamanho = Double.valueOf(base * 2.0).intValue();
        } else if(questionLength > 450) {
            tamanho = Double.valueOf(base * 2.1).intValue();
        } else if(questionLength > 400) {
            tamanho = Double.valueOf(base * 2.2).intValue();
        } else if(questionLength > 350) {
            tamanho = Double.valueOf(base * 2.3).intValue();
        } else if(questionLength > 300) {
            tamanho = Double.valueOf(base * 2.4).intValue();
        } else if(questionLength > 250) {
            tamanho = Double.valueOf(base * 2.5).intValue();
        } else if(questionLength > 200) {
            tamanho = Double.valueOf(base * 2.6).intValue();
        }
        return tamanho;
    }

    private void updateAnswer() {
        respostas[0].setText(htmlTagInit+"A)"+question.getAnswer1()+htmlTagClose);
        respostas[1].setText(htmlTagInit+"B)"+question.getAnswer2()+htmlTagClose);
        respostas[2].setText(htmlTagInit+"C)"+question.getAnswer3()+htmlTagClose);
        respostas[3].setText(htmlTagInit+"D)"+question.getAnswer4()+htmlTagClose);
    }

    public void resetQuestion() {
        questionLabel.setText("");
        for (AnswerLabel resposta : respostas) {
            resposta.setText("");
        }
    }
    
    public void selectAnswer(int answer){
        if(answerSelected == answer){
            answerSelected = 0;
        }
        resetSelectedAnswer();
        answerSelected = answer;
        if(answerSelected > 0){
            respostas[answer-1].selectAnswer(true);
        }
    }
    private void resetSelectedAnswer(){
        for (AnswerLabel resposta : respostas) {
            resposta.selectAnswer(false);
        }
    }
    
    public void successAnswer(){
        respostas[question.getCorrectAnswer()-1].showCorrect();
    }
    public void failAnswer(){
        respostas[question.getCorrectAnswer()-1].showIncorrect();
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
        setDefaultCloseOperation(HIDE_ON_CLOSE);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 300));
        //pack();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
                return false;
            }
        });
    }

    public void startProjectionQuest(Question question) {
        AudioUtil.play(AudioUtil.SHOW_QUESTION);
        this.question = question;
        changeFrameState(FrameState.QUESTION);
        updateQuestion();

    }

    public void startProjectionAnswer() throws BusinessException {
        if (question == null) {
            throw new BusinessException("Questão não enviada para projeção.");
        }
        updateAnswer();
    }

    public void stopProjectionQuest() {
        changeFrameState(FrameState.WAIT);
    }

    public FrameState getFrameState() {
        return frameState;
    }

    private void changeFrameState(FrameState frameState) {
        this.frameState = frameState;
        switch (frameState) {
            case QUESTION:
                resetQuestion();
                stopWaitPanel();
                break;
            case RESULT:
                break;
            case WAIT:
                startWaitPanel();
                resetQuestion();
                break;
        }
    }

    public static void main(String[] args) {
        Event event = new Event();
        new QuizProjectionFrm(event).setVisible(true);
    }

}
