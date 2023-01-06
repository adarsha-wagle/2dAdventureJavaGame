package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp)
    {
        super(gp);
        type = type_shield;
        name ="Blue Shield";
        down1 = setupImage("objects/shield_blue",gp.TILE_SIZE,gp.TILE_SIZE);
        defenseValue = 1;
        itemDescription = "!" + name + "/n A shiny blue shield.";
    }
}
