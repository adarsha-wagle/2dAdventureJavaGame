package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp)
    {
        super(gp);
        direction = "down";
        speed = 1;//speed of npc old man
        getImage();
        setDialogue();
    }
    public void getImage()
    {
        up1 = setupImage("npc/oldman_up_1",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setupImage("npc/oldman_up_2",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setupImage("npc/oldman_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setupImage("npc/oldman_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setupImage("npc/oldman_left_1",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setupImage("npc/oldman_left_2",gp.TILE_SIZE,gp.TILE_SIZE);
        right1 = setupImage("npc/oldman_right_1",gp.TILE_SIZE,gp.TILE_SIZE);
        right2 = setupImage("npc/oldman_right_2",gp.TILE_SIZE,gp.TILE_SIZE);
    }
    //Responsible for npc dialogue
    public void setDialogue()
    {
        dialogue[0] = "Hello, lad!!";
        dialogue[1] = "So you've come to this island \n to find the treasure?";
        dialogue[2] = "I used to be a mighty wizard but now... \nI'm cursed to go out";
        dialogue[3] = "Well, good luck on you.";
    }
//    CHARACTER BEHAVIOUR
    //AI OF NPC OLD MAN
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;//includes 1 to 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    public void speak() {
        //do this character specific stuff
        super.speak();//calls speak method in entity class
    }

}
