import javax.sound.sampled.*;

import java.applet.AudioClip;
import java.io.File;
import java.io.InputStream;
import java.util.*;

public class audioManager {
    private static ArrayList<audioClip> sounds = new ArrayList<>();
    private static float masterVolume = 0.1f;

    public static class audioClip {
        String name;
        Clip clip;
        
        public audioClip(String name, Clip clip) {
            this.name = name;
            this.clip = clip;
        }
    }

    public static void loadSound (String name, String path) {
        try {
        System.out.println("Attempting to load: " + path); // Debug
        
        File audioFile = new File(path);
        if (!audioFile.exists()) {
            throw new RuntimeException("File not found at: " + audioFile.getAbsolutePath());
        }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            sounds.add(new audioClip(name, clip));
            
            System.out.println("Successfully loaded: " + name);
        } catch(Exception e) {
            System.err.println("ERROR loading sound '" + name + "'");
            e.printStackTrace();
        }
    }

    // utk cari clip by name    
    private static Clip findClip (String name) {
        for (audioClip audio : sounds) {
            if (audio.name.equals(name)) {
                return audio.clip;
            } 
        }
        return null;
    }

    private static void setVolume (Clip clip, float volume) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);// dB scale formula
            control.setValue(dB);
            
        }
    }

    public static void play(String name, boolean loop) {
        Clip clip = findClip(name);
        if (clip != null) {
            clip.setFramePosition(0); 
            setVolume(clip, masterVolume); 
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }
    }
}
