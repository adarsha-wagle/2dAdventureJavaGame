package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_30,arial_80;


    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    BufferedImage keyImage;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp)
    {
        this.gp = gp;
        arial_30 = new Font("TimesRoman",Font.ITALIC,25);
        arial_80 = new Font("Arial",Font.BOLD,60);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }
    public void showMessage(String text)
    {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        if (gameFinished) {
            String text;
            int textLength;
            int x,y;


            g2.setFont(arial_30);
            g2.setColor(Color.white);
            text = "You found the treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.SCREEN_WIDTH/2-gp.TILE_SIZE-textLength/2;
            y = gp.SCREEN_HEIGHT/2-gp.TILE_SIZE-(gp.TILE_SIZE*3);
            g2.drawString(text,x,y);

            text = "Your Time is :"+dFormat.format(playTime)+"!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.SCREEN_WIDTH/2-gp.TILE_SIZE-textLength/2;
            y = gp.SCREEN_HEIGHT/2-gp.TILE_SIZE+(gp.TILE_SIZE*4);
            g2.drawString(text,x,y);

            g2.setFont(arial_80);
            g2.setColor(Color.yellow);
            text = "Congratulations";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.SCREEN_WIDTH/2-gp.TILE_SIZE-textLength/2;
            y = gp.SCREEN_HEIGHT/2-gp.TILE_SIZE+(gp.TILE_SIZE*2);
            g2.drawString(text,x,y);


            gp.gameThread = null;
        } else {
            g2.setFont(arial_30);
            g2.setColor(Color.yellow);
            g2.drawImage(keyImage, gp.TILE_SIZE / 4, gp.TILE_SIZE / 4, gp.TILE_SIZE, gp.TILE_SIZE, null);
            g2.drawString("x " + gp.player.hasKey, 45, 55);
            playTime+=(double)1/60;
            g2.drawString("Time:"+dFormat.format(playTime),gp.TILE_SIZE*11,65);
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.TILE_SIZE / 2, gp.TILE_SIZE * 5);
                messageCounter++;
                if (messageCounter > 120)//120 frame or 2sec and d
                {
                    messageCounter = 0;
                    messageOn = false;

                }
            }
        }
    }
}
