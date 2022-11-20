package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp)
    {
        super(gp);
        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        //setting solid area of slime
        solidArea.x = 3;
        solidArea.y = 10;solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage()
    {
        up1 = setupImage("monsters/greenslime_down_1");
        up2 = setupImage("monsters/greenslime_down_2");
        down1 = setupImage("monsters/greenslime_down_1");
        down2 = setupImage("monsters/greenslime_down_2");
        left1 = setupImage("monsters/greenslime_down_1");
        left2 = setupImage("monsters/greenslime_down_2");
        right1 = setupImage("monsters/greenslime_down_1");
        right2 = setupImage("monsters/greenslime_down_2");

    }
    public void setAction(){
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
}