package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyH;
    public Player(GamePanel gP,KeyHandler keyH){
        this.gamePanel = gP;
        this.keyH = keyH;
        setDefaultValues();
    }
    public void setDefaultValues()
    {
        playerX = 100;
        playerY = 100;
        playerSpeed = 4;
    }
    public void update()//responsible for changing player position
    {
        if(keyH.upPressed)
        {
            playerY-=playerSpeed;
        }
        else if(keyH.downPressed)
        {
            playerY +=playerSpeed;
        }else if(keyH.rightPressed)
        {
            playerX +=playerSpeed;
        }else if(keyH.leftPressed)
        {
            playerX -=playerSpeed;
        }
    }
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
        g2d.fillRect(playerX,playerY,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE);
    }
}
