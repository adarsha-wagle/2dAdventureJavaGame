package main;

import object.OBJ_Key;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI  {
    GamePanel gp;
    Font roman_25,arial_80,roman_40;


    Color color;
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
        roman_25 = new Font("TimesRoman",Font.ITALIC|Font.BOLD,25);
        roman_40 = new Font("TimesRoman",Font.ITALIC|Font.BOLD,40);
        arial_80 = new Font("Arial",Font.BOLD,60);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }
    public void showMessage(String text,Color color)
    {
        this.color = color;
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        if (gameFinished) {
            String text;
            int textLength;
            int x,y;


            g2.setFont(roman_40);
            g2.setColor(Color.white);
            text = "You found the treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x =(( gp.SCREEN_WIDTH/2)-gp.TILE_SIZE)-(textLength/2);
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

            //KEY INFORMATION
            g2.setFont(roman_25);
            g2.setColor(Color.yellow);
            g2.drawImage(keyImage, gp.TILE_SIZE / 4, gp.TILE_SIZE / 4, gp.TILE_SIZE, gp.TILE_SIZE, null);
            g2.drawString("x " + gp.player.hasKey, 46, 55);

            //TIME INFORMATION
            playTime+=(double)1/60;
            g2.drawString("Time:"+dFormat.format(playTime),gp.TILE_SIZE*13,40);

            if (messageOn) {
                g2.setColor(color);
                g2.setFont(g2.getFont().deriveFont(25F));
                g2.drawString(message, gp.TILE_SIZE /4, gp.TILE_SIZE * 4);
                messageCounter++;
                if (messageCounter > 60)//120 frame or 2sec and d
                {
                    messageCounter = 0;
                    messageOn = false;

                }
            }
        }
    }


}
