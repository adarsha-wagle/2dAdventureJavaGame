package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {
 GamePanel gp;
   public int worldX ,worldY,playerSpeed;//worldX to
    public BufferedImage up1,up2,down1,down2,right1,right2,left1,left2,stand1,stand2;
    public String direction = "down";//player direction

    //for player animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle( 0,0,48,48);//default collision box

    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    //FOR MANAGING DIALOGUES
    String dialogue[] = new String[20];//dialogue for npc's
    int dialogueIndex = 0;//responsible for rendering multiple dialogues

   //CHARACTER HEALTH
 public int maxLife;
 public int life;

//SUPER OBJECT
 public BufferedImage image,image2,image3;//image 2 and 3 for heart
 public String name;
 public boolean collision = false;
  public Entity(GamePanel gp){
   this.gp = gp;
  }
  public void setAction()
  {

  }
  public void speak(){
   if(dialogue[dialogueIndex]==null)//if dialogue reaches the maximum index then reset index to zero
   {
    dialogueIndex = 0;
   }
   gp.ui.currentDialogue = dialogue[dialogueIndex];
   dialogueIndex++;
   //changing the npc direction on the basis of player direction
   switch (gp.player.direction)
   {
    case "up"://player direction
     direction = "down";//changing npc direction
     break;
    case "down":
     direction = "up";
     break;
    case "left":
     direction = "right";
     break;
    case "right":
     direction = "left";
     break;

   }

  }
  public void update(){
   setAction();
   collisionOn = false;
   gp.cChecker.checkTile(this);
   gp.cChecker.checkObject(this,false);
   gp.cChecker.checkPlayer(this);
   if (!collisionOn) {

    switch (direction) {
     case "up" -> worldY -= playerSpeed;
     case "down" -> worldY += playerSpeed;
     case "left" -> worldX -= playerSpeed;
     case "right" -> worldX += playerSpeed;
    }
   }




 spriteCounter++;
        if(spriteCounter > 12) {
         spriteNum = spriteNum == 1 ? 2 : 1;

         spriteCounter = 0;
        }
 }


 public BufferedImage setupImage(String imagePath)
 {
  UtilityTool uTool = new UtilityTool();
  BufferedImage image = null;
  try
  {
   image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath+".png")));
   image = uTool.scaledImage(image,gp.TILE_SIZE,gp.TILE_SIZE);
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
  return image;
 }
 public void draw(Graphics2D g2  )
 {
  BufferedImage image = null;
  int screenX = worldX-gp.player.worldX + gp.player.screenX;
  int screenY = worldY-gp.player.worldY + gp.player.screenY;

  if(worldX + gp.TILE_SIZE>gp.player.worldX - gp.player.screenX
          && worldX-gp.TILE_SIZE < gp.player.worldX + gp.player.screenX
          && worldY +gp.TILE_SIZE> gp.player.worldY - gp.player.screenY
          && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
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
   g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
  }
 }
}
