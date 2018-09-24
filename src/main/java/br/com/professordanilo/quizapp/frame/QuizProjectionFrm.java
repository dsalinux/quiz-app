package br.com.professordanilo.quizapp.frame;

import br.com.professordanilo.quizapp.util.ImageUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class QuizProjectionFrm extends JFrame {

    private static final String TITLE = "Quiz";

    private final Icon ICON_MALE = new ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/player-male.png"));
    private final Icon ICON_FEMALE = new ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/player-female.png"));
    private final Icon ICON_GROUP = new ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/player-group.png"));

    private JLabel questao = new JLabel();
    private JLabel[] respostas = new JLabel[]{new JLabel(), new JLabel()};

    private JPanel panelAguardando;

    private JPanel panelQuestoes;
    private JPanel panelInfoPlayers;
    private JPanel panelCabecalho;
    private JPanel panelQuiz;

    private enum FrameState {
        WAIT,
        QUESTION,
        RESULT
    }

    public QuizProjectionFrm() throws HeadlessException {
        super(TITLE);
        setLayout(new BorderLayout());
//        setPreferredSize(new Dimension(400, 300));

        createPainelQuizQuestions();
        createWaitPanel();
        configLayout();
//        startWaitPanel();
    }

    private void createPainelQuizQuestions() {
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
        JButton botao = new JButton("Clique aqui");
        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startWaitPanel();
            }
        });

        panelQuestoes.add(botao);

//        createPanelHead();
        createPanelPlayers();
        createWaitPanel();

        configLayout();

        panelQuiz.add(panelQuestoes, BorderLayout.CENTER);

        add(panelQuiz);
//        startWaitPanel();
    }

    private void createPanelHead(JPanel panel) {
        try {
            panelCabecalho = new JPanel(new GridBagLayout());
            panelCabecalho.setPreferredSize(new Dimension(300, 130));
            panelCabecalho.setOpaque(true);
            panelCabecalho.setBackground(Color.white);
            panelCabecalho.setBorder(new MatteBorder(new Insets(0, 0, 0, 15), Color.lightGray));
//            MatteBorder linha = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray);
//            panelCabecalho.setBorder(linha);
            BufferedImage bImage = ImageIO.read(getClass().getResource("/br/com/professordanilo/quizapp/images/logo.png"));
//            ImageIcon image = new ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/logo.png"));
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
        panelInfoPlayers.setBackground(new Color(240, 240, 240));
        panelInfoPlayers.setOpaque(true);
        createPanelHead(panelInfoPlayers);
//        panelCompetidores.setBackground(Color.white);
//        JLabel iconPlayer1 = new JLabel();
//        iconPlayer1.setPreferredSize(new Dimension(50, 50));
//        iconPlayer1.setBorder(new LineBorder(Color.blue));
//        panelInfoPlayers.add(iconPlayer1);

        JLabel player1 = new JLabel("<html><body><h2>Danilo Souza Almeida</h2></body></html>");
        player1.setIcon(ICON_MALE);
        player1.setBorder(new MatteBorder(new Insets(0, 0, 0, 15), Color.green));
        player1.setPreferredSize(new Dimension(300, 100));
        player1.setHorizontalAlignment(JLabel.CENTER);
        player1.setHorizontalTextPosition(JLabel.CENTER);
        player1.setVerticalTextPosition(JLabel.BOTTOM);
        panelInfoPlayers.add(player1);
        JLabel player2 = new JLabel("<html><body><h2>Nayara Gabriella</h2></body></html>");
        player2.setBorder(new MatteBorder(new Insets(0, 0, 0, 15), Color.ORANGE));
        player2.setIcon(ICON_FEMALE);
        player2.setHorizontalAlignment(JLabel.CENTER);
        player2.setHorizontalTextPosition(JLabel.CENTER);
        player2.setVerticalTextPosition(JLabel.BOTTOM);
        panelInfoPlayers.add(player2);
        panelQuiz.add(panelInfoPlayers, BorderLayout.EAST);
    }

    private void createWaitPanel() {
        try {
            panelAguardando = new JPanel(new BorderLayout());
            
            JLabel aguarde = new JLabel("Aguardando a pergunta...");
            
            aguarde.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
            aguarde.setOpaque(true);
            aguarde.setBackground(Color.white);
            aguarde.setHorizontalAlignment(JLabel.CENTER);
            aguarde.setHorizontalTextPosition(JLabel.CENTER);
            aguarde.setVerticalTextPosition(JLabel.BOTTOM);
            
            BufferedImage bImage = ImageIO.read(getClass().getResource("/br/com/professordanilo/quizapp/images/logo.png"));
            aguarde.setIcon(new ImageIcon(ImageUtil.resizeToMaxValue(bImage, 260, 260)));
            
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
        setPreferredSize(new Dimension(600, 300));
        pack();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
                return false;
            }
        });

//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                System.out.println(e.getKeyCode());
//                 if(KeyEvent.VK_ESCAPE == e.getKeyCode()){
//                     System.out.println("Escape");
//                 }
//            }
//            
//        });
    }

    public static void main(String[] args) {
        QuizProjectionFrm frame = new QuizProjectionFrm();
        frame.setVisible(true);


    }

}
