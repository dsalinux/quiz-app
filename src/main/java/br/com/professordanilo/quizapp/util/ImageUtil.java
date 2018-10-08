/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp.util;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author danilo
 */
public class ImageUtil {

    /**
     * Resize image for max with or max height
     *
     * @param image Original Image
     * @param with Max with
     * @param height Max Height
     * @return New Image resized
     */
    public static Image resizeToMaxValue(BufferedImage image, Integer with, Integer height) {

        Double newWith = (double) image.getWidth();
        Double newHeight = (double) image.getHeight();

        Double imgProporcao = null;
        if (newWith >= with) {
            imgProporcao = (newHeight / newWith);
            newWith = (double) with;
            newHeight = (newWith * imgProporcao);
            while (newHeight > height) {
                newWith = (double) (--height);
                newHeight = (newWith * imgProporcao);
            }
        } else if (newHeight >= height) {
            imgProporcao = (newWith / newHeight);
            newHeight = (double) height;
            while (newWith > with) {
                newHeight = (double) (--height);
                newWith = (newHeight * imgProporcao);
            }
        }

        return image.getScaledInstance(newWith.intValue(), newHeight.intValue(), Image.SCALE_SMOOTH);
    }

}
