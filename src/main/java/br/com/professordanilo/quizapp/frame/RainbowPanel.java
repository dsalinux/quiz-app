/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author danilo
 */
public class RainbowPanel extends JLabel {
    
    private int min = 50, max = 200, animationDelay = 10;
    private int r = min;
    private int g = min;
    private int b = max;
    
    private int corAtual = 1;
    
    private Timer animationTimer;
    
    public RainbowPanel() {
        setForeground(Color.WHITE);
        setHorizontalAlignment(CENTER);
        setPreferredSize(new Dimension(100, 30));
    }
    public RainbowPanel(Dimension dimension) {
        setForeground(Color.white);
        setHorizontalAlignment(CENTER);
        setPreferredSize(dimension);
    }
    public RainbowPanel(Color foreground, Dimension dimension) {
        setForeground(foreground);
        setHorizontalAlignment(CENTER);
        setPreferredSize(dimension);
    }

    public void startAnimation(){
        if(animationTimer == null){
            animationTimer = new Timer(animationDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
        Color cor1 = new Color(r, g, b);
        Color cor2 = new Color(b, r, g);
        Color cor3 = new Color(g, b, r);
        LinearGradientPaint lgp = new LinearGradientPaint(
                new Point(0, 0),
                new Point(getWidth(), 0),
                new float[]{0f, 0.5f, 1f},
                new Color[]{cor1, cor2, cor3});
        g2d.setPaint(lgp);
        
        g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
        addColor();
        super.paintComponent(gr);
    }
    
    private void addColor(){
        if(animationTimer != null && animationTimer.isRunning())
        switch (corAtual){
            case 1://aumenta vermelho
                r++;
                if(r >= max){
                    corAtual++;
                }
                break;
            case 2://diminui azul
                b--;
                if(b <= min){
                    corAtual++;
                }
                break;
            case 3://aumenta vede
                g++;
                if(g >= max){
                    corAtual++;
                }
                break;
            case 4://diminui vermelho
                r--;
                if(r <= min){
                    corAtual++;
                }
                break;
            case 5://aumenta azul
                b++;
                if(b >= max){
                    corAtual++;
                }
                break;
            case 6://diminui verde
                g--;
                if(g <= min){
                    corAtual = 1;
                }
                break;
        }
       
    }
    
}
