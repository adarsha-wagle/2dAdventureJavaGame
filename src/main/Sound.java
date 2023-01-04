package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];//to store the file path
    public Sound()
    {
        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");

        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/powerdown.wav");

        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/parry.wav");
        soundURL[8] = getClass().getResource("/sound/hitmonster.wav");

        soundURL[9] = getClass().getResource("/sound/levelup.wav");

        soundURL[10] = getClass().getResource("/sound/cursor.wav");
    }
    public void setFile( int i)
    {
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch(Exception e)
        {
            System.out.println("unable to open sound filel");
            e.printStackTrace();}
    }
    public void play()
    {
        clip.start();
    }
    public void loop()
    {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop()
    {
    clip.stop();
    }

}
