package entity;

import main.GamePanel;
import main.KeyHandler;

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

    int hasKey = 0;
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
        solidArea.width = 32;
        solidArea.height = 30;
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
                    break;
                case "Door":
                    if(hasKey>0){
                        System.out.println("name"+gamePanel.obj[i].name);
                        System.out.println("i"+i);
                        gamePanel.obj[i] = null;//destroy door
                        gamePanel.playSE(3);
                        hasKey--;
                    }
                    break;
                case "Boots":
                    playerSpeed+=2;
                    gamePanel.obj[i] = null;
                    gamePanel.playSE(2);
                    timer.schedule(new TimerTask(){
                        @Override
                        public void run()
                        {
                            playerSpeed = 4;
                            timer.cancel();
                        }
                    },BOOT_POWER_DURATION*1000);
                    break;
            }
//            System.out.println("Keys"+hasKey);

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
        }
        g2d.drawImage(image,screenX,screenY,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE,null);//the position of the
        // player does not change

    }
}
