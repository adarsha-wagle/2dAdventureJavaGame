package main;

import tile_interactive.InteractiveTile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Config {
    GamePanel gp;
    public Config(GamePanel gp)
    {
        this.gp = gp;

    }
    public void saveConfig()
    {
        try
        {
        BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
        //FULL SCREEN
            if(gp.fullScreenOn)
            {
                bw.write("On");
            }
            if(!gp.fullScreenOn)
            {
                bw.write("Off");
            }
            bw.newLine();

            //MUSIC VOLUME
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //SE VOLUME
            bw.write(String.valueOf(gp.soundEff.volumeScale));
            bw.newLine();

            bw.close();

        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void loadConfig()
    {
        try
        {

        BufferedReader br = new BufferedReader(new FileReader("config.txt"));
        String s = br.readLine();
        //FUL SCREEN
            if(s.equals("On"))
            {
                gp.fullScreenOn = true;
            }
            if(s.equals("Off"))
            {
                gp.fullScreenOn = false;
            }
            //MUSIC VOLUME
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            //SE VOLUME
            s = br.readLine();
            gp.soundEff.volumeScale = Integer.parseInt(s);
        }catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
