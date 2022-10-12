package entity;

import java.awt.image.BufferedImage;

public class Entity {
    int playerX,playerY,playerSpeed;
    public BufferedImage up1,up2,down1,down2,right1,right2,left1,left2;
    public String direction;//player direction

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
