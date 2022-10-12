package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyH;
    public Player(GamePanel gP,KeyHandler keyH){
        this.gamePanel = gP;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues()
    {
        playerX = 100;
        playerY = 100;
        playerSpeed = 4;
        direction = "down";
    }
    public void getPlayerImage()
    {
        try{
//            up1 = ImageIO.read(ClassLoader.getSystemResourceAsStream("player/boy_up_1.png"));
//            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_up_2.png"));
                /*
                    throws warning in the above code
                 */
            up1 = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_up_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_right_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_down_2.png")));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void update()//responsible for changing player position
    {
        if(keyH.upPressed)
        {
            direction = "up";
            playerY-=playerSpeed;
        }
        else if(keyH.downPressed)
        {
            direction = "down";
            playerY +=playerSpeed;
        }else if(keyH.rightPressed)
        {
            direction = "right";
            playerX +=playerSpeed;
        }else if(keyH.leftPressed)
        {
            direction = "left";
            playerX -=playerSpeed;
        }
        else
        {
            direction = "down";
        }
        spriteCounter++;

        if(spriteCounter > 12)
        {
            spriteNum = spriteNum == 1?2:1;

            spriteCounter = 0;
        }

    }
    public void draw(Graphics2D g2d)
    {
       /* g2d.setColor(Color.white);
        g2d.fillRect(playerX,playerY,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE);
        */

        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
            }
            case "down" -> {
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
            }
            case "left" -> {
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
            }
            case "right" -> {
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
            }
        }
        g2d.drawImage(image,playerX,playerY,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE,null);

    }
}
