package Tools;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class SoundPlayer {
    private static Clip clip;
    private static AudioInputStream music;
    private static boolean mute = false;
    private static boolean playing = false;

    public static void playMusic(String filePath, boolean loop) {
        if ((playing && loop) || mute) return;

        try {
            music = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(music);
            playing = true;
            if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            else clip.start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Playing Music");
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if(clip == null) return;

        clip.stop();
        playing = false;
    }

    public static boolean getMute() {
        return mute;
    }

    public static void toggleMute() {
        mute = !mute;

        if(!mute) {
            playMusic(Constants.getHomeSoundPath(), true);
        }
    }
}
