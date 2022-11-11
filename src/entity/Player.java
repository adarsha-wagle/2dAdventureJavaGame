package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Timer;


public class Player extends Entity{

/*
player is in center of the screen and background is moving
 */
    //screenX and screenY does not change throughout the game
    public final  int screenX;
    public final int screenY;


    KeyHandler keyH;

    //FOR BOOT POWER
    public final int BOOT_POWER_DURATION = 20;
    Timer timer = new Timer();

    public Player(GamePanel gP,KeyHandler keyH){
        super(gP);


        this.keyH = keyH;
        screenX = gp.SCREEN_WIDTH/2-(gp.TILE_SIZE/2);//768/2-24 = 360pixel
        screenY = gp.SCREEN_HEIGHT/2-(gp.TILE_SIZE/2);//576/2-24 = 264pixel

       // FOR COLLISION
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues()
    {
        //player position in world map
        worldX = gp.TILE_SIZE * 23;//1104px 23 is arbitrary no. use for initial position
        worldY = gp.TILE_SIZE * 21;//1008px similarly 21 is arbitrary no.
        playerSpeed = 4;
        direction = "left";

//        Player Status
        maxLife = 6;//1 life = half heart
        life = maxLife;//player current life
    }
    public void getPlayerImage()
    {

        stand1 = setupPlayer("player/boy_stand_1");
        stand2 = setupPlayer("player/boy_stand_2");
        up1 = setupPlayer("player/boy_up_1");
        up2 = setupPlayer("player/boy_up_2");
        down1 = setupPlayer("player/boy_down_1");
        down2 = setupPlayer("player/boy_down_2");
        left1 = setupPlayer("player/boy_left_1");
        left2 = setupPlayer("player/boy_left_2");
        right1 = setupPlayer("player/boy_right_1");
        right2 = setupPlayer("player/boy_right_2");
    }

    public void update()//responsible for changing player position
    {
        if(keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed) {
            if (keyH.upPressed) {

                direction = "up";
            }  if (keyH.downPressed) {
                direction = "down";
            }  if (keyH.leftPressed) {
                direction = "left";


            }  if (keyH.rightPressed) {
                direction = "right";
            }
            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);
            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);
            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);


            //if collision is false player can move
            if (!collisionOn) {

                switch (direction) {
                    case "up" -> worldY -= playerSpeed;
                    case "down" -> worldY += playerSpeed;
                    case "left" -> worldX -= playerSpeed;
                    case "right" -> worldX += playerSpeed;
                }
            }
        }
        else {
            direction = "stand";
        }

        spriteCounter++;
        if(spriteCounter > 12)
        {
            spriteNum = spriteNum == 1?2:1;

            spriteCounter = 0;
        }

    }
    public void pickUpObject(int i)
    {
        if(i!=999)//if index is 999 then we have not touched anything
        {


        }
    }
    public void interactNPC(int i)
    {
        if(i!=999)
        {
            System.out.println("Hitting npc");
            if(gp.keyH.talkPressed)
            {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }

        }
        gp.keyH.talkPressed = false;
    }
    public void draw(Graphics2D g2d)
    {
       /* g2d.setColor(Color.white);
        g2d.fillRect(worldX,playerY,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE);
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
            case "stand" ->{
                if (spriteNum == 1) image = stand1;
                if (spriteNum == 2) image = stand2;
            }
        }
        g2d.drawImage(image,screenX,screenY,null);//the position of the
        // player does not change

        //PLAYER COLLISION CHECKER
//        g2d.setColor(Color.red);
//        g2d.drawRect(screenX+solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);

    }
}
