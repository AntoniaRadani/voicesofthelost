package main;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Sound {
    Clip clip;
    FloatControl volumeControl;
    int volumeLevel = 3; // 0 = mute, 5 = max
    float[] volumeScale = { -80f, -20f, -12f, -5f, 1f, 6f };

    URL[] soundURL = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sound/unlock.wav");
        soundURL[2] = getClass().getResource("/sound/coin.wav");
        soundURL[3] = getClass().getResource("/sound/dooropen.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(volumeLevel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setVolume(int volume) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Convertim [0 - 100] în decibeli
            float min = volumeControl.getMinimum(); // ex: -80.0f
            float max = volumeControl.getMaximum(); // ex: 6.0f

            float gain = (max - min) * (volume / 100.0f) + min;
            volumeControl.setValue(gain);
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
