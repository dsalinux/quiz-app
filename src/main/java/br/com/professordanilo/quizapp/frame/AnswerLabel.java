package br.com.professordanilo.quizapp.frame;

import br.com.professordanilo.quizapp.util.AudioUtil;
import br.com.professordanilo.quizapp.util.LogUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class AnswerLabel extends JLabel {
    
    private Color defaultColor1 = new Color(26, 88, 144);
    private Color defaultColor2 = new Color(112, 210, 255);
    private Color selectColor1 = new Color(255, 255, 205);
    private Color selectColor2 = new Color(255, 180, 60);
    private Color correctColor1 = new Color(0, 255, 140);
    private Color correctColor2 = new Color(0, 180, 60);
    private Color incorrectColor1 = new Color(255, 0, 140);
    private Color incorrectColor2 = new Color(180, 0, 60);
    private int radius = 40;
    private CompoundBorder border = new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new RoundedBorder(radius,defaultColor1));
    private Color[] colors = new Color[]{defaultColor1, defaultColor2};
    private boolean selected = false;

    private Timer animationTimer;
    private int animationDelay = 300, countAnimationsCorrectQuestion = 0, countAnimationsIncorrectQuestion = 0;
    
    public AnswerLabel() {
        super();
        setBorder(border);
        setFont(new Font(Font.SANS_SERIF, 0, 28));
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
//        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        setForeground(Color.BLACK);
//        setOpaque(true);
    }
    public AnswerLabel(Color color1, Color color2) {
        this();
        this.defaultColor1 = color1;
        this.defaultColor2 = color2;

    }
    
    public void selectAnswer(boolean select){
        if(select){
            AudioUtil.play(AudioUtil.SELECT_ANSWER);
        }
        selected = select;
        repaint();
    }
    
    public void showIncorrect(){
        AudioUtil.play(AudioUtil.FAIL);
        startAnimationIncorrectQuestion();
    }
    
    public void showCorrect(){
        AudioUtil.play(AudioUtil.SUCCESS);
        startAnimationCorrectQuestion();
    }
    
    private void startAnimationIncorrectQuestion(){
        if(animationTimer == null){
            animationTimer = new Timer(animationDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(countAnimationsIncorrectQuestion > 15){
                        countAnimationsIncorrectQuestion = 0;
                        repaint();
                        stopAnimation();
                        LogUtil.error(AnswerLabel.class, countAnimationsIncorrectQuestion+" - É pra ser 0");
                        return;
                    }
                    countAnimationsIncorrectQuestion++;
                    repaint();
                }
            });
            animationTimer.start();
        } else {
            if(!animationTimer.isRunning()){
                animationTimer.restart();
            }
        }
    }
    private void startAnimationCorrectQuestion(){
        if(animationTimer == null){
            animationTimer = new Timer(animationDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(countAnimationsCorrectQuestion > 15){
                        countAnimationsCorrectQuestion = 0;
                        repaint();
                        stopAnimation();
                        return;
                    }
                    countAnimationsCorrectQuestion++;
                    repaint();
                }
            });
            animationTimer.start();
        } else {
            if(!animationTimer.isRunning()){
                animationTimer.restart();
            }
        }
    }
    public void stopAnimation(){
        animationTimer.stop();
    }
    
    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g2d = (Graphics2D) gr.create();
        Map<RenderingHints.Key, Object> map = new HashMap<>();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(countAnimationsCorrectQuestion>0){
            if(countAnimationsCorrectQuestion%2==1){
                colors = new Color[]{correctColor1, correctColor2};
            } else {
                colors = new Color[]{defaultColor1, defaultColor2};
            }
        } else if(countAnimationsIncorrectQuestion > 0){
            if(countAnimationsIncorrectQuestion%2==1){
                colors = new Color[]{incorrectColor1, incorrectColor2};
            } else {
                colors = new Color[]{defaultColor1, defaultColor2};
            }
        } else if(selected) {
            colors = new Color[]{selectColor1, selectColor2};
        } else {
            colors = new Color[]{defaultColor1, defaultColor2};
        }
        LinearGradientPaint lgp = new LinearGradientPaint(
                new Point(0, 0),
                new Point(0, getHeight()),
                new float[]{0f, 1f},
                colors);
        g2d.setPaint(lgp);
        g2d.fillRoundRect(5, 5, (getWidth()-10), getHeight()-10, radius, radius);
        super.paintComponent(gr);
    }
     private static class RoundedBorder implements Border {
        
        private int radius;
        private Color color;
        
        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+10, this.radius+10, this.radius+20, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.drawRoundRect(x,y,width-1,height-1,radius,radius);
        }
    }
}
