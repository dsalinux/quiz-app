package br.com.professordanilo.quizapp.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author danilo
 */
public class AudioUtil {

    @InjectAudio
    private static String AUDIO_FOLDER;
    private static final String[] KEYS = new String[]{"AUDIO_FOLDER", "TICK_TOCK", "COMPLETE_TIME", "FAIL", "SUCCESS", "THRILLER", "SHOW_QUESTION", "SELECT_ANSWER"};
    @InjectAudio
    public static String TICK_TOCK;
    @InjectAudio
    public static String COMPLETE_TIME;
    @InjectAudio
    public static String FAIL;
    @InjectAudio
    public static String SUCCESS;
    @InjectAudio
    public static String THRILLER;
    @InjectAudio
    public static String SHOW_QUESTION;
    @InjectAudio
    public static String SELECT_ANSWER;

    //Inject attributs from properties
    static {
        Properties properties = PropertiesUtil.getAudioProperties();
        if (properties == null || properties.isEmpty()) {
            LogUtil.error(AudioUtil.class, "audio.properties não configurado corretamente.");
        } else {
            for (Field field : AudioUtil.class.getDeclaredFields()) {
                if (field.isAnnotationPresent(InjectAudio.class)) {
                    InjectAudio injectAudio = field.getAnnotation(InjectAudio.class);
                    String propertiesKey;
                    if (injectAudio.name().equals("none")) {
                        propertiesKey = field.getName();
                    } else {
                        propertiesKey = injectAudio.name();
                    }
                    try {
                        field.set(AudioUtil.class, properties.get(propertiesKey));
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        LogUtil.fatal(AudioUtil.class, ex);
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static void play(String audio) {
        new Thread(() -> {
            try {
                LogUtil.debug(AudioUtil.class, "Reproduzindo: " + audio);
                InputStream inputStream = new FileInputStream("./" + AUDIO_FOLDER + "/" + audio);
                InputStream bufferedIn = new BufferedInputStream(inputStream);
                AudioInputStream sound = AudioSystem.getAudioInputStream(bufferedIn);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                long time = new Double(Math.ceil(clip.getMicrosecondLength() / 1000)).longValue();
                Thread.sleep(time);
                clip.stop();
                clip.close();
                sound.close();
                bufferedIn.close();
                inputStream.close();
                LogUtil.debug(AudioUtil.class, "Tempo do audio: " + time / 1000 + "s");

            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException ex) {
                LogUtil.fatal(AudioUtil.class, ex.getMessage());
                ex.printStackTrace();
            }
        }).start();
    }

    public static void loop(String audio, int timeMs) {
        new Thread(() -> {
            try {
                LogUtil.debug(AudioUtil.class, "Reproduzindo: " + audio);
                InputStream inputStream = new FileInputStream("./" + AUDIO_FOLDER + "/" + audio);
                InputStream bufferedIn = new BufferedInputStream(inputStream);
                AudioInputStream sound = AudioSystem.getAudioInputStream(bufferedIn);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                long time = new Double(Math.ceil(clip.getMicrosecondLength() / 1000)).longValue();
                Thread.sleep(timeMs);
                clip.stop();
                clip.close();
                sound.close();
                bufferedIn.close();
                inputStream.close();
                LogUtil.debug(AudioUtil.class, "Tempo do audio: " + time / 1000 + "s");

            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException ex) {
                LogUtil.fatal(AudioUtil.class, ex);
                ex.printStackTrace();
            }
        }).start();
    }
    
    public static void loop(LinkedHashMap<String, Integer> audios) {
        new Thread(() -> {
            try {
                for (Map.Entry<String, Integer> entry : audios.entrySet()) {
                    LogUtil.debug(AudioUtil.class, "Reproduzindo: " + entry.getKey());
                    InputStream inputStream = new FileInputStream("./" + AUDIO_FOLDER + "/" + entry.getKey());
                    InputStream bufferedIn = new BufferedInputStream(inputStream);
                    AudioInputStream sound = AudioSystem.getAudioInputStream(bufferedIn);
                    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(sound);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    Integer timeClip = new Double(Math.ceil(clip.getMicrosecondLength() / 1000)).intValue();
                    Integer time = entry.getValue();
                    if(time == null || time.equals(0)){
                        time = timeClip;
                    }
                    Thread.sleep(time);
                    clip.stop();
                    LogUtil.debug(AudioUtil.class, "Tempo do audio: " + time / 1000 + "s");
                }

            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException ex) {
                LogUtil.fatal(AudioUtil.class, ex);
                ex.printStackTrace();
            }
        }).start();
    }
}
