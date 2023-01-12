package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;


public class Player extends Entity{

/*
player is in center of the screen and background is moving
 */
    //screenX and screenY does not change throughout the game
    public final  int screenX;
    public final int screenY;

    int standCounter = 0;
    KeyHandler keyH;

    //FOR BOOT POWER
    public final int BOOT_POWER_DURATION = 20;
    Timer timer = new Timer();

    //INVENTORY
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gP,KeyHandler keyH){
        super(gP);
        this.gp = gP;

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

        //Change this number if you want to change attacking range
//        attackArea.width = 36;
//        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues()
    {
        //player position in world map
//        worldX = gp.TILE_SIZE * 23;//1104px 23 is arbitrary no. use for initial position
//        worldY = gp.TILE_SIZE * 21;//1008px similarly 21 is arbitrary no.
        worldX = gp.TILE_SIZE*35;
        worldY = gp.TILE_SIZE*21;
        speed = 4;
        direction = "left";

//        Player Status
        level = 1;
        maxLife = 6;//1 life = half heart
        life = maxLife;//player current life
        maxMana = 4;
        mana = maxMana;
//        ammo = 10;//TODO Ammo
        strength = 1;//the more strength he has, the more damage he gives
        dexterity = 1;//the more dexterity he has, the less damage he receives.
        exp = 0;
        nextLevelExp = 5;//how much experience you need to level up
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
//        projectile = new OBJ_Rock(gp);//TODO Ammo
        attack  = getAttack();//the total attack value is decided by strength and weapon
        defense = getDefense();//the total defense value is decided by dexterity and shield
    }
    public void setItems()
    {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }
    public int getAttack()
    {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense()
    {
        return defense = dexterity * currentShield.defenseValue;
    }
    public void getPlayerImage()
    {

        stand1 = setupImage("player/boy_stand_1",gp.TILE_SIZE,gp.TILE_SIZE);
        stand2 = setupImage("player/boy_stand_2",gp.TILE_SIZE,gp.TILE_SIZE);
        up1 = setupImage("player/boy_up_1",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setupImage("player/boy_up_2",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setupImage("player/boy_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setupImage("player/boy_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setupImage("player/boy_left_1",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setupImage("player/boy_left_2",gp.TILE_SIZE,gp.TILE_SIZE);
        right1 = setupImage("player/boy_right_1",gp.TILE_SIZE,gp.TILE_SIZE);
        right2 = setupImage("player/boy_right_2",gp.TILE_SIZE,gp.TILE_SIZE);
    }
    public void getPlayerAttackImage()
    {
        if(currentWeapon.type == type_sword) {


            attackUp1 = setupImage("player/boy_attack_up_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackUp2 = setupImage("player/boy_attack_up_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackDown1 = setupImage("player/boy_attack_down_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackDown2 = setupImage("player/boy_attack_down_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackLeft1 = setupImage("player/boy_attack_left_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
            attackLeft2 = setupImage("player/boy_attack_left_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
            attackRight1 = setupImage("player/boy_attack_right_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
            attackRight2 = setupImage("player/boy_attack_right_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setupImage("player/boy_axe_up_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackUp2 = setupImage("player/boy_axe_up_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackDown1 = setupImage("player/boy_axe_down_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackDown2 = setupImage("player/boy_axe_down_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
            attackLeft1 = setupImage("player/boy_axe_left_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
            attackLeft2 = setupImage("player/boy_axe_left_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
            attackRight1 = setupImage("player/boy_axe_right_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
            attackRight2 = setupImage("player/boy_axe_right_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
        }

    }

    public void update()//responsible for changing player position
    {
        if(attacking)//if player is attacking
        {
            handleAttack();
        }

      else if(keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed||keyH.talkPressed||keyH.mousePressed) {
            if (keyH.upPressed) {

                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }
            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK EVENT
            gp.eHandler.checkEvent();
            gp.keyH.talkPressed = false;
            gp.keyH.enterPressed = false;


            //  CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //if collision is false player can move
            if (!collisionOn && !keyH.talkPressed && !keyH.mousePressed) {
                attacking = false;
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }


            gp.keyH.talkPressed = false;
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = spriteNum == 1 ? 2 : 1;

                spriteCounter = 0;
            }
        }

        else {
//            direction = "stand";//TODO disable if player has weapon and enable if player doesnot have weapon
            standCounter++;
            if(standCounter == 20)
            {
                spriteNum = 1;
                standCounter = 0;
            }
        }
        //you cannot shoot if previous projectile is still alive
        if(gp.keyH.shotKeyPressed && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this))
        {
            projectile.set(worldX,worldY,direction,true,this);
            //ADD IT TO THE LIST
            //SUBTRACT THE COST(Mana,Ammo)
            projectile.subtractResource(this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
            gp.playSE(11);
        }
        if(invincible)
        {
            invicibleCounter++;
            if(invicibleCounter>60)
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
    public void handleAttack()//attacking
    {
        spriteCounter++;
        if(spriteCounter <= 5)
        {
            spriteNum = 1;
        }
        if(spriteCounter>5 && spriteCounter <= 20)
        {
            spriteNum = 2;

            //save the current worldX,worldY,solidArea
            int currentWorldX  = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for this attack Area
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }
            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //Check monster collision with the updated worldX,worldY and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex,attack);
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter>20)
        {
            System.out.println("came");
            spriteCounter = 0;
            spriteNum = 1;
            attacking = false;
        }
        System.out.println(spriteCounter);
    }
    public void pickUpObject(int i)
    {
        if(i!=999)//if index is 999 then we have not touched anything
        {
            String text;
            if(inventory.size()!= maxInventorySize)
            {
                inventory.add(gp.obj[i]);
                gp.playSE(1);
                text = "Got a " + gp.obj[i].name + "!";

            }
            else {
                text = "You cannot carry any more!";
            }
            gp.ui.addMessage(text);
            gp.obj[i] = null;

        }
    }
    public void interactNPC(int i)
    {
        if(i!=999) {
//            System.out.println("Hitting npc");
//            System.out.println("Key pressed " + gp.keyH.talkPressed);
            if (gp.keyH.talkPressed) {

                gp.gameState = gp.dialogueState;
                System.out.println("i " + i);
                gp.npc[i].speak();
            }

        }
        if(gp.keyH.mousePressed)
        {
            gp.playSE(7);
            attacking = true;
        }

    }
    public void contactMonster(int i )
    {
        if(i!=999)//player touches monster
        {
            if(!invincible && !gp.monster[i].dying)
            {
                gp.playSE(6);
                int damage = gp.monster[i].attack-defense;
                if(damage<0)
                {
                    damage = 0;
                }
                life-=damage;
                invincible = true;
            }

        }
    }
    public void damageMonster(int i,int attack )
    {
        if (i!=999)
        {
//            System.out.println("Hit");
            if(!gp.monster[i].invincible)
            {
                gp.playSE(8);
                int damage = attack-gp.monster[i].defense;
                if(damage<0)
                {
                    damage = 0;
                }
                gp.monster[i].life -=damage;
                gp.ui.addMessage(damage+" damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life<=0)
                {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("killed the "+gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp + "+gp.monster[i].exp);
                    exp+=gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
//        else
//        {
//            System.out.println("miss");
//        }
    }
    public void checkLevelUp()
    {
        if(exp>=nextLevelExp)
        {
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife+=2;
            strength++;
            dexterity++;
            attack = getAttack();//player strength and dexterity has been increased so we need to recalculate
            defense = getDefense();
            gp.playSE(9);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level "+ level + "now!\n"+"You feel stronger";
        }
    }
    public void selectItem()
    {
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if(itemIndex<inventory.size())
        {
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe)
            {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield)
            {
                currentShield = selectedItem;
                defense = getDefense();

            }
            if(selectedItem.type == type_consumable)
            {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void draw(Graphics2D g2d)
    {
       /* g2d.setColor(Color.white);
        g2d.fillRect(worldX,playerY,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE);
        */

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up" -> {
                 if(attacking)
                 {
                     tempScreenY = screenY - gp.TILE_SIZE;
                     if(spriteNum == 1) image = attackUp1;
                     if(spriteNum == 2) image = attackUp2;
                 }
             if(!attacking) {
                     if (spriteNum == 1) image = up1;
                     if (spriteNum == 2) image = up2;
                 }

            }
            case "down" -> {
                if (attacking) {
                    if (spriteNum == 1) image = attackDown1;
                    if (spriteNum == 2) image = attackDown2;
                } if(!attacking) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
            }
            case "left" -> {
                if (attacking) {
                    tempScreenX = screenX - gp.TILE_SIZE;
                    if (spriteNum == 1) image = attackLeft1;
                    if (spriteNum == 2) image = attackLeft2;
                } if(!attacking) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
            }
            case "right" -> {
                if (attacking) {
                    if (spriteNum == 1) image = attackRight1;
                    if (spriteNum == 2) image = attackRight2;
                } if(!attacking) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }
            case "stand" ->{
                if(!attacking)
                {
                if (spriteNum == 1) image = stand1;
                if (spriteNum == 2) image = stand2;
                }
                if(attacking)
                {
                    if(spriteNum == 1) image = attackDown1;
                    if(spriteNum == 2) image = attackDown2;
                }

            }
        }

        if(invincible)
        {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }
        g2d.drawImage(image,tempScreenX,tempScreenY,null);//the position of the
        // player does not change
        //RESET
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        //PLAYER COLLISION CHECKER
        g2d.setColor(Color.red);
        g2d.drawRect(screenX+solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);

        //DEBUG
//        g2d.setFont(new Font("Arial",Font.PLAIN,26));
//        g2d.setColor(Color.white);
//        g2d.drawString("Invincible"+invicibleCounter,10,400);

    }
}
