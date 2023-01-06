package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;
    public MON_GreenSlime(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;

        //setting solid area of slime
        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 36;//default 42
        solidArea.height = 24;//default 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage()
    {
        up1 = setupImage("monsters/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setupImage("monsters/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setupImage("monsters/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setupImage("monsters/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setupImage("monsters/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setupImage("monsters/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
        right1 = setupImage("monsters/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        right2 = setupImage("monsters/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);

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
    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
