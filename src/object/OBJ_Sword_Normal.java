package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    public OBJ_Sword_Normal(GamePanel gp)
    {
        super(gp);
        type = type_sword;
        name = "Normal Sword";
        down1 = setupImage("objects/sword_normal",gp.TILE_SIZE,gp.TILE_SIZE);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        itemDescription = "!" + name + "/n An old sword.";

    }
}
