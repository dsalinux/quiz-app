package br.com.professordanilo.quizapp.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author danilo
 */
public class AppIcons {

    public static final Icon ICON_MALE = new ImageIcon(AppIcons.class.getResource("/br/com/professordanilo/quizapp/images/player-male.png"));;
    public static final Icon ICON_FEMALE = new ImageIcon(AppIcons.class.getResource("/br/com/professordanilo/quizapp/images/player-female.png"));
    public static final Icon ICON_GROUP = new ImageIcon(AppIcons.class.getResource("/br/com/professordanilo/quizapp/images/player-group.png"));
    
    public static final ImageIcon DEFAULT_LOGO = new ImageIcon(AppIcons.class.getResource("/br/com/professordanilo/quizapp/images/logo.png"));

    public static BufferedImage DEFAULT_LOGO_BUFFERED;

    static {
        try {
            DEFAULT_LOGO_BUFFERED = ImageIO.read(AppIcons.class.getResource("/br/com/professordanilo/quizapp/images/logo.png"));
        } catch (IOException ex) {
            Logger.getLogger(AppIcons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
