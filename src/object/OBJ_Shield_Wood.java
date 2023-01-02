package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp)
    {
        super(gp);
        name ="Wood Shield";
        down1 = setupImage("objects/shield_wood",gp.TILE_SIZE,gp.TILE_SIZE);
        defenseValue = 1;
    }
}
