package entity;

import jdk.jshell.execution.Util;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends Entity{

/*
player is in center of the screen and background is moving
 */
    //screenX and screenY does not change throughout the game
    public final  int screenX;
    public final int screenY;

    public int hasKey = 0;
    GamePanel gamePanel;
    KeyHandler keyH;

    public final int BOOT_POWER_DURATION = 20;
    Timer timer = new Timer();

    public Player(GamePanel gP,KeyHandler keyH){

        this.gamePanel = gP;
        this.keyH = keyH;
        screenX = gamePanel.SCREEN_WIDTH/2-(gamePanel.TILE_SIZE/2);//768/2-24 = 360pixel
        screenY = gamePanel.SCREEN_HEIGHT/2-(gamePanel.TILE_SIZE/2);//576/2-24 = 264pixel

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
        worldX = gamePanel.TILE_SIZE * 23;//1104px 23 is arbitrary no. use for initial position
        worldY = gamePanel.TILE_SIZE * 21;//1008px similarly 21 is arbitrary no.
        playerSpeed = 4;
        direction = "left";
    }
    public void getPlayerImage()
    {

        stand1 = setupPlayer("boy_stand_1");
        stand2 = setupPlayer("boy_stand_2");
        up1 = setupPlayer("boy_up_1");
        up2 = setupPlayer("boy_up_2");
        down1 = setupPlayer("boy_down_1");
        down2 = setupPlayer("boy_down_2");
        left1 = setupPlayer("boy_left_1");
        left2 = setupPlayer("boy_left_2");
        right1 = setupPlayer("boy_right_1");
        right2 = setupPlayer("boy_right_2");
    }
    public BufferedImage setupPlayer(String imageName)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/"+imageName+".png")));
            image = uTool.scaledImage(image,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return image;
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
            gamePanel.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gamePanel.cChecker.checkObject(this,true);
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
            String objectName = gamePanel.obj[i].name;
            switch (objectName)
            {
                case "Key":
                    hasKey++;
                    gamePanel.obj[i] = null;//destroy the key
                    gamePanel.playSE(1);
                    gamePanel.ui.showMessage("You got a key.",new Color(240, 221, 98));
                    break;
                case "Door":
                    if(hasKey>0){
                        System.out.println("name"+gamePanel.obj[i].name);
                        System.out.println("i"+i);
                        gamePanel.obj[i] = null;//destroy door
                        gamePanel.playSE(3);
                        hasKey--;
                        gamePanel.ui.showMessage("You opened the door",new Color(135, 75, 32));
                    }
                    else {
                        gamePanel.ui.showMessage("You need a key!",new Color(135, 75, 32));
                    }
                    break;
                case "Boots":
                    playerSpeed+=2;
                    gamePanel.obj[i] = null;
                    gamePanel.playSE(2);
                    gamePanel.ui.showMessage("Speeding Up",new Color(25, 111, 247));
                    timer.schedule(new TimerTask(){
                        @Override
                        public void run()
                        {
                            playerSpeed = 4;
                            gamePanel.playSE(5);
                            timer.cancel();
                        }
                    },BOOT_POWER_DURATION*1000);
                    break;
                case "Chest":
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSE(4);
                    break;

            }

        }
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
