package main;

import object.OBJ_Key;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI  {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica,purisaB;


    Color color;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    BufferedImage keyImage;
    public boolean gameFinished = false;
    public String currentDialogue = "";//npc dialogue : old man

//    double playTime;
//    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp)
    {
        this.gp = gp;
       //INSTANTIATE FONT
        try {


            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            System.out.println("Cannot load font");
            e.printStackTrace();
        } catch (FontFormatException e) {
            System.out.println("Cannot create font");
            e.printStackTrace();
        }
    }
    public void showMessage(String text,Color color)
    {
        this.color = color;
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;

//        g2.setFont(maruMonica);
        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        //play state
        if(gp.gameState == gp.playState)
        {
            //do play stuff later
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState)
        {
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState)
        {
            drawDialogueScreen();
        }
    }
    public void drawDialogueScreen()
    {
        int x=gp.TILE_SIZE*2;
        int y=gp.TILE_SIZE/2;
        int width = gp.SCREEN_WIDTH-(gp.TILE_SIZE*5);
        int height = gp.TILE_SIZE*4;
        //DIALOG WINDOW
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,26F));
        x+=gp.TILE_SIZE;
        y+=gp.TILE_SIZE;
        //Breaking lines of the dialogue so that it will not overflow
        for (String line : currentDialogue.split("\n"))
        {
            g2.drawString(line,x,y);
            y+=40;
        }

    }
    public void drawSubWindow(int x,int y,int width,int height  )
    {
        Color c = new Color(0,0,0,200);//black color with transparency
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));//defies the width of outlines of graphics which are rendered with a Graphics 2d
        g2.drawRoundRect(x+5,y+4,width-10,height-10,25,25);
    }
    public void drawPauseScreen()
    {
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.SCREEN_HEIGHT/2;
        g2.drawString(text,x,y);
    }
    public int getXforCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int  x = gp.SCREEN_WIDTH/2-length/2;

        return x;
    }

}
