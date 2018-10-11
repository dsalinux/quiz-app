package br.com.professordanilo.quizapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {


    public static Properties getAudioProperties() {
        InputStream input = null;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("audio.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }

}
