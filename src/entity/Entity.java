package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {
 GamePanel gp;
   public int worldX ,worldY, speed;//worldX to
    public BufferedImage up1,up2,down1,down2,right1,right2,left1,left2,stand1,stand2;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
    public String direction = "down";//player direction

    //for player animation

    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle( 0,0,48,48);//default collision box
    public Rectangle attackArea  =new Rectangle(0,0,0,0);
    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    //COUNTER
    public int invicibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
 public int spriteCounter = 0;

    //FOR MANAGING DIALOGUES
    String dialogue[] = new String[20];//dialogue for npc's
    public int dialogueIndex = 0;//responsible for rendering multiple dialogues

   //CHARACTER HEALTH
 public int maxLife;
 public int life;
 public int maxMana;
 public int mana;
 public int ammo;

 public int level;
 public int strength;
 public int dexterity;
 public int attack;
 public int defense;
 public int exp;
 public int nextLevelExp;
 public int coin;
 public Entity currentWeapon;
 public Entity currentShield;
 public Projectile projectile;
public Entity user;


 //ITEM ATTRIBUTES
 public int attackValue;
 public int defenseValue;
 public String itemDescription = "";
 public int useCost;


 //TYPE
 public int type;//0 = player,1= npc,2 = monster
 public final int type_player = 0;
 public final int type_npc = 1;
 public final int type_monster = 2;
 public final int type_sword = 3;
 public final int type_axe = 4;
 public final int type_shield = 5;
 public final int type_consumable = 6;
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
  public void damageReaction(){}
 public void use(Entity entity){}
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
   gp.cChecker.checkEntity(this,gp.npc);
   gp.cChecker.checkEntity(this,gp.monster);
   boolean contactPlayer = gp.cChecker.checkPlayer(this);
   if(this.type == type_monster && contactPlayer)
   {
        damagePlayer(attack);
   }
   if (!collisionOn) {

    switch (direction) {
     case "up" -> worldY -= speed;
     case "down" -> worldY += speed;
     case "left" -> worldX -= speed;
     case "right" -> worldX += speed;
    }
   }

 spriteCounter++;
        if(spriteCounter > 12) {
         spriteNum = spriteNum == 1 ? 2 : 1;

         spriteCounter = 0;
        }
   if(invincible)
   {
    invicibleCounter++;
    if(invicibleCounter>40)
    {
     invincible = false;
     invicibleCounter=0;
    }
   }
   if(shotAvailableCounter < 30)
   {
    shotAvailableCounter++;
   }
 }


 public BufferedImage setupImage(String imagePath,int width,int height)
 {
  UtilityTool uTool = new UtilityTool();
  BufferedImage image = null;
  try
  {
   image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath+".png")));
   image = uTool.scaledImage(image,width,height);
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
  return image;
 }
 public void damagePlayer(int attack)
 {
  if(!gp.player.invincible)
  {
   gp.playSE(6);
   int damage = attack-gp.player.defense;
   if(damage<0)
   {
    damage = 0;
   }
   //we can give damage
   gp.player.life-=damage;
   gp.player.invincible = true;
  }
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
   //Monster HP Bar
   if(type == 2 && hpBarOn)
   {
    double oneScale = (double) gp.TILE_SIZE/maxLife;
    double hpBarValue = oneScale * life;
   g2.setColor(new Color(35,35,35));
   g2.fillRect(screenX-1,screenY-16,gp.TILE_SIZE+2,12);
   g2.setColor(new Color(255,0,30));
   g2.fillRect(screenX,screenY-15,(int)hpBarValue,10);
   hpBarCounter++;
   if(hpBarCounter>600)//after 10 sec the bar disappears
   {
    hpBarCounter = 0;
    hpBarOn = false;
   }
   }
   if(invincible)
   {
    hpBarOn = true;
    hpBarCounter = 0;
    changeAlpha(g2,0.4f);
   }
   if(dying)
   {
    dyingAnimation(g2);
   }
   g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
   //reset transparency
changeAlpha(g2,1f);
  }
 }
 public void dyingAnimation(Graphics2D g2)
 {
   dyingCounter++;
   if(dyingCounter<=5)
   {changeAlpha(g2,0f);
   }
   if(dyingCounter>5 && dyingCounter<=10)
   {
      changeAlpha(g2,1f);
   }
   if(dyingCounter>10 && dyingCounter<=15)
   {
    changeAlpha(g2,0f);
   }
   if(dyingCounter>15 && dyingCounter<=20)
   {
    changeAlpha(g2,1f);
   }
   if(dyingCounter>20 && dyingCounter<=25)
   {
    changeAlpha(g2,0f);
   }if(dyingCounter>25 && dyingCounter<=30)
   {
    changeAlpha(g2,1f);
   }if(dyingCounter>30 && dyingCounter<=40)
   {
    changeAlpha(g2,0f);
   }if(dyingCounter>40)
   {

    alive = false;
   }
 }
 public void changeAlpha(Graphics2D g2,float value)
 {
  g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,value));

 }
}
