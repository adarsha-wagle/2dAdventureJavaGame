package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

    //FOR TITLE
    public int commandNum = 0;

    //Health UI
    BufferedImage heart_full,heart_half,heart_blank;

    public UI(GamePanel gp)
    {
        this.gp = gp;
       //INSTANTIATE FONT
        try {


            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            System.out.println("Cannot load font");
            e.printStackTrace();
        } catch (FontFormatException e) {
            System.out.println("Cannot create font");
            e.printStackTrace();
        }

        //CREATE HEART OBJECT
        Entity heart =new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }
    public void showMessage(String text,Color color)
    {
        this.color = color;
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;


        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        //Title State
        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }
        //play state
        if(gp.gameState == gp.playState)
        {
            //do play stuff later
            drawPlayerLife();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState)
        {   drawPlayerLife();
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState)
        {      drawPlayerLife();
            drawDialogueScreen();
        }
    }
    public void drawPlayerLife()
    {
        int x = gp.TILE_SIZE/2;
        int y = gp.TILE_SIZE/2;
        int i = 0;
        //Draw Max Life
        while(i<gp.player.maxLife/2)
        {
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+=gp.TILE_SIZE;
        }
        //Reset
         x = gp.TILE_SIZE/2;
         y = gp.TILE_SIZE/2;
         i = 0;
         //Draw Current Life
        while(i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life)
            {
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.TILE_SIZE;
        }
    }
    public void drawTitleScreen()
    {
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
        //Title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        String text = "Blue Boy Adventure";
        int x = getXforCenteredText(text);
        int y = gp.TILE_SIZE*3;
        //shadow of text
        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);
        //actual color of text
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //Blue Boy Image
        x = gp.SCREEN_WIDTH/2-(gp.TILE_SIZE*2)/2;
        y+=gp.TILE_SIZE*1.5;
        g2.drawImage(gp.player.down1,x,y,gp.TILE_SIZE*2,gp.TILE_SIZE*2,null);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        text = "New Game";
        x = getXforCenteredText(text);
        y+= gp.TILE_SIZE*3.5;
        g2.drawString(text,x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.TILE_SIZE,y);
        }

        text = "Load GAme";
        x = getXforCenteredText(text);
        y+=gp.TILE_SIZE;
        g2.drawString(text,x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.TILE_SIZE,y);
        }
        text = "Quit";
        x = getXforCenteredText(text);
        y+=gp.TILE_SIZE;
        g2.drawString(text,x,y);
        if(commandNum == 2)
        {
            g2.drawString(">",x-gp.TILE_SIZE,y);
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
