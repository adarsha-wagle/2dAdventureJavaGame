package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class UI  {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica,purisaB;


//    Color color;
//    public boolean messageOn = false;
//    public String message = "";
//    int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    BufferedImage keyImage;
    public boolean gameFinished = false;
    public String currentDialogue = "";//npc dialogue : old man


    //FOR TITLE
    public int commandNum = 0;

    //Health UI
    BufferedImage heart_full,heart_half,heart_blank,crystal_full,crystal_blank;

    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;

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

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
    }
    public void addMessage(String text)
    {
//        this.color = color;
//        message = text;
//        messageOn = true;
        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

//        System.out.println(gp.gameState);
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
            drawMessage();
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
        //CHARACTER STATE
        if(gp.gameState == gp.characterState)
        {
            drawCharacterScreen();
            drawInventory();
        }
        //OPTIONS STATE
        if(gp.gameState == gp.optionState)
        {
            drawOptionScreen();
        }
        //GAME OVER STATE
        if(gp.gameState == gp.gameOverState)
        {
            drawGameOverScreen();
        }
    }
    public void drawGameOverScreen()
    {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
        text = "Gave Over";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.TILE_SIZE *4;
        g2.drawString(text,x,y);

        //MAIN
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        //RETRY
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y+=gp.TILE_SIZE*4;
        g2.drawString(text,x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-40,y);
        }
        //RETURN BACK TO THE TITLE SCREEN
        text = "Quit";
        x = getXforCenteredText(text);
        y+=55;
        g2.drawString(text,x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-40,y);
        }
    }
    public void drawOptionScreen()
    {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //SUB WINDOW
        int frameX = gp.TILE_SIZE*6;
        int frameY = gp.TILE_SIZE;
        int frameWidth = gp.TILE_SIZE*8;
        int frameHeight = gp.TILE_SIZE * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch (subState)
        {
            case 0:options_top(frameX,frameY);break;
            case 1:options_fullScreenNotification(frameX,frameY);   break;
            case 2:options_control(frameX,frameY); break;
            case 3: options_endGameConfirmation(frameX,frameY);break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX,int frameY)
    {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX  = getXforCenteredText(text);
        textY = frameY + gp.TILE_SIZE;
        g2.drawString(text,textX,textY);

        //FULL SCREEN ON/OFF
        textX = frameX + gp.TILE_SIZE;
        textY+= gp.TILE_SIZE *2;
        g2.drawString("Full Screen",textX,textY);
        if(commandNum == 0)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed )
            {
                if(gp.fullScreenOn==false)
                {
                    gp.fullScreenOn = true;
                }
                else if (gp.fullScreenOn)
                {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }
        //MUSIC
        textY+=gp.TILE_SIZE;
        g2.drawString("Music",textX,textY);
        if(commandNum == 1)
        {
            g2.drawString(">",textX-25,textY);
        }
        //SE
        textY+=gp.TILE_SIZE;
        g2.drawString("SE",textX,textY);
        if(commandNum == 2)
        {
            g2.drawString(">",textX-25,textY);
        }
        //CONTROL
        textY+=gp.TILE_SIZE;
        g2.drawString("Control",textX,textY);
        if(commandNum == 3)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed)
            {
                subState = 2;
                commandNum = 0;
            }
        }
        //END GAME
        textY+=gp.TILE_SIZE;
        g2.drawString("End Game",textX,textY);
        if(commandNum == 4)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed)
            {
                subState = 3;
                commandNum = 0;
            }
        }
        //BACK
        textY+=gp.TILE_SIZE*2;
        g2.drawString("Back",textX,textY);
        if(commandNum == 5)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed)
            {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //FULL SCREEN CHECK BOX
        textX = (int) (frameX +  gp.TILE_SIZE *4.5f);
        textY = frameY + gp.TILE_SIZE * 2+24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,24,24);
        if(gp.fullScreenOn)
        {
            g2.fillRect(textX,textY,24,24);
        }

        //MUSIC VOLUME
        textY += gp.TILE_SIZE;
        g2.drawRect(textX,textY,120,24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);


        //SOUND EFFECTS
        textY += gp.TILE_SIZE;
        g2.drawRect(textX,textY,120,24);
        volumeWidth = 24 * gp.soundEff.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        gp.config.saveConfig();
    }
    public void options_fullScreenNotification(int frameX, int frameY)
    {
        int textX = frameX + gp.TILE_SIZE;
        int textY = frameY + gp.TILE_SIZE;
        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        for (String line: currentDialogue.split("\n"))
        {
            g2.drawString(line,textX,textY);
            textY += 40;
        }
        //BACK
        textY = frameY + gp.TILE_SIZE * 9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0)
        {
            g2.drawString(">",textX-25,textY) ;
            if(commandNum == 0)
            {
                g2.drawString(">",textX-25,textY);
                if(gp.keyH.enterPressed)
                {
                    subState = 0;
                }
            }
        }
    }
    public void options_control(int frameX, int frameY)
    {
        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.TILE_SIZE;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.TILE_SIZE;
        textY += gp.TILE_SIZE;
        g2.drawString("Move",textX,textY); textY+=gp.TILE_SIZE;
        g2.drawString("Confirm",textX,textY); textY+=gp.TILE_SIZE;
        g2.drawString("Attack",textX,textY); textY+=gp.TILE_SIZE;
        g2.drawString("Shoot/Cast",textX,textY); textY+=gp.TILE_SIZE;
        g2.drawString("Character Screen",textX,textY); textY+=gp.TILE_SIZE;
        g2.drawString("Pause",textX,textY); textY+=gp.TILE_SIZE;
        g2.drawString("Options",textX,textY);

        textX =frameX + gp.TILE_SIZE * 6;
        textY = frameY + gp.TILE_SIZE * 2;
        g2.drawString("WASD",textX,textY); textY += gp.TILE_SIZE;
        g2.drawString("Enter",textX,textY); textY += gp.TILE_SIZE;
        g2.drawString("LeftClick",textX,textY); textY += gp.TILE_SIZE;
        g2.drawString("F",textX,textY); textY += gp.TILE_SIZE;
        g2.drawString("C",textX,textY); textY += gp.TILE_SIZE;
        g2.drawString("P",textX,textY); textY += gp.TILE_SIZE;
        g2.drawString("ESC",textX,textY);

        //BACK
        textX= frameX + gp.TILE_SIZE;
        textY = frameY + gp.TILE_SIZE*9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0)
        {
            g2.drawString("Back",textX,textY);
            if(commandNum == 0)
            {
                g2.drawString(">",textX-25,textY);
                if(gp.keyH.enterPressed)
                {
                    subState = 0;
                    commandNum = 3;
                }
            }
        }

    }
    public void options_endGameConfirmation(int frameX,int frameY)
    {
        int textX = frameX + gp.TILE_SIZE;
        int textY = frameY + gp.TILE_SIZE*3;
        currentDialogue = "Quit the game and \n return the to the screen?";
        for (String line:currentDialogue.split("\n"))
        {
            g2.drawString(line,textX,textY);
            textY+=40;
        }
        //YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY+=gp.TILE_SIZE*3;
        g2.drawString(text,textX,textY);
        if(commandNum == 0)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed)
            {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }
        //NO
         text = "No";
        textX    = getXforCenteredText(text);
        textY += gp.TILE_SIZE;
        g2.drawString(text,textX,textY);
        if(commandNum == 1)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed)
            {
                subState = 0;
                commandNum = 4;
            }
        }

    }

    public void drawInventory()
    {
        int frameX = gp.TILE_SIZE * 12;
        int frameY= gp.TILE_SIZE;
        int frameWidth = (gp.TILE_SIZE * 6)+12;
        int frameHeight = (gp.TILE_SIZE *5)+12;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY  + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.TILE_SIZE +5;

        //DRAW PLAYER'S ITEMS
        for(int i = 0;i<gp.player.inventory.size();i++)
        {
            //EQUIP CURSOR : highlighting current equipment
            if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i)==gp.player.currentShield )
            {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.TILE_SIZE+2,gp.TILE_SIZE,10,10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
            slotX+=gp.TILE_SIZE+5;
            //if index row1 limits then we shift to new row i.e.2
            if(i == 4 || i == 9 || i == 14)
            {
                slotY += slotSize;
                slotX = slotXStart;
            }
        }
        //CURSOR
        int cursorX =slotXStart + (slotSize * slotCol) ;
        int cursorY=slotYStart + (slotSize * slotRow)   ;
        int cursorWidth = gp.TILE_SIZE;
        int cursorHeight = gp.TILE_SIZE;

        //Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));//changing thickness of line
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        //DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY= frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.TILE_SIZE * 3;
        drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

        //DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.TILE_SIZE;
        g2.setFont(g2.getFont().deriveFont(20F));

        int itemIndex = getItemIndexOnSlot();
        if(itemIndex<gp.player.inventory.size())
        {
            for (String line: gp.player.inventory.get(itemIndex).itemDescription.split("\n"))
            {
               g2.drawString(line,textX,textY);
               textY+=32;
            }
        }
    }
    public int getItemIndexOnSlot()
    {
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }
    public void drawMessage()
    {
        int messageX = gp.TILE_SIZE ;
        int messageY = gp.TILE_SIZE*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
        for (int i = 0;i<message.size();i++)
        {
            if(message.get(i)!=null)
            {
                g2.setColor(Color.black);
                g2.drawString(message.get(i),messageX+2,messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX,messageY);
                int counter = messageCounter.get(i)+1;//messageCounter
                messageCounter.set(i,counter);//set the counter to the array
                messageY += 50;
                if(messageCounter.get(i)>180)
                {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }
    public void drawCharacterScreen()
    {
        //Create A Frame
        final int frameX = gp.TILE_SIZE*2 ;
        final int frameY = gp.TILE_SIZE * 2;
        final int frameWidth = gp.TILE_SIZE *5;
        final int frameHeight = gp.TILE_SIZE * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY  + gp.TILE_SIZE;
        final int lineHeight  = 32;

        //NAMES:LEFT SIDE
        g2.drawString("Level",textX,textY);
        textY+=lineHeight;
        g2.drawString("Life",textX,textY);
        textY+=lineHeight;
        g2.drawString("Mana",textX,textY);
        textY+=lineHeight;
        g2.drawString("Stream",textX,textY);
        textY+=lineHeight;
        g2.drawString("Dexterity",textX,textY);
        textY+=lineHeight;
        g2.drawString("Attack",textX,textY);
        textY+=lineHeight;
        g2.drawString("Defense",textX,textY);
        textY+=lineHeight;
        g2.drawString("Exp",textX,textY);
        textY+=lineHeight;
        g2.drawString("Next Level",textX,textY);
        textY+=lineHeight;
        g2.drawString("Coin",textX,textY);
        textY+=lineHeight+15;
        g2.drawString("Weapon",textX,textY);
        textY+=lineHeight+15;
        g2.drawString("Shield",textX,textY);

        //VALUES: RIGHT SIDE
        int tailX = (frameX + frameWidth)-30;
        //reset texty
        textY  = frameY + gp.TILE_SIZE;
        String value;
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.life + "/"+gp.player.maxLife);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;
        value = String.valueOf(gp.player.mana + "/"+gp.player.maxMana);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);

        textY+=lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);

        textY+=lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);

        textY+=lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);

        textY+=lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);

        textY+=lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);

        textY+=lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);

        textY+=lineHeight;
        g2.drawImage(gp.player.currentWeapon.down1,tailX-gp.TILE_SIZE,textY-20,null);
        textY+=gp.TILE_SIZE;
        g2.drawImage(gp.player.currentShield.down1,tailX-gp.TILE_SIZE,textY-20,null);

        textY+=lineHeight+40;
        g2.drawString("Press 'c' to exit",frameX+25,textY);
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
        //DRAW MAX MANA
        x = (gp.TILE_SIZE/2)-5;
        y =(int) (gp.TILE_SIZE*1.5);
        i = 0;

        while(i<gp.player.maxMana)
        {

            g2.drawImage(crystal_blank,x,y,null);
            i++;
            x+=35;

        }
        //DRAW MANA
        x = (gp.TILE_SIZE/2)-5;
        y =(int) (gp.TILE_SIZE*1.5);
        i = 0;
        while(i<gp.player.mana)
        {
            g2.drawImage(crystal_full,x,y,null);
            i++;
            x+=35;
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

        text = "Load Game";
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
 public int getXforAlignToRightText(String text,int tailX)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int  x = tailX - length;

        return x;
    }

}
