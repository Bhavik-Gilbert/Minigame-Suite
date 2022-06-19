package Tools;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;

import Application.Volume;
import Pages.Page;
import Pages.MiniGames.Games;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class SoundPlayer {
    private static Clip clip;
    private static AudioInputStream music;
    private static boolean mute = false;
    private static boolean playing = false;
    private static Page.PAGETYPE pageType;
    private static Label songName;

    public static void playMusic(String filePath) {
        if (playing || mute) return;

        try {
            music = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(music);
            playing = true;
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            setClipVolume(clip, Volume.getVolume());
            setSongLabel(filePath);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Playing Music");
            e.printStackTrace();
        }
    }

    public static void playSound(String filePath) {
        if (mute)
            return;

        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File(filePath));
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(sound);
            soundClip.start();
            setClipVolume(soundClip, Volume.getSFX());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Playing Sound Effect");
        }
    }

    public static void stopMusic() {
        if(clip == null) return;

        clip.stop();
        playing = false;
    }

    private static void setClipVolume(Clip sound, float volume) {
        FloatControl gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public static void changeVolume(float volume) {
        if(clip == null) return;        
        setClipVolume(clip, volume);
    }

    public static boolean getMute() {
        return mute;
    }

    public static void toggleMute() {
        mute = !mute;

        if(!mute) {
            if(SoundPlayer.pageType == null) return;

            switch(SoundPlayer.pageType) {
                case MAIN:
                    playMusic(Page.getSoundPath());
                    break;
                case GAME:
                    playMusic(Games.getSoundPath());
                    break;
                default:
                    playMusic(Page.getSoundPath());
            }
            
        }
    }

    public static void setMusicHome(MenuItem musicMenu) {
        pageType = Page.PAGETYPE.MAIN;
    }

    public static void setMusicGame(MenuItem musicMenu) {
        pageType = Page.PAGETYPE.GAME;
    }

    public static Page.PAGETYPE getPageType() {
        return pageType;    
    }

    public static void setSongLabel(Label songName) {
        SoundPlayer.songName = songName;
    }

    private static void setSongLabel(String filePath) {
        if(SoundPlayer.songName == null) return;
        
        String song = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        SoundPlayer.songName.setText(song);
    }
}
