package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp)
    {
        super(gp);
        type = type_axe;
        name = "Woodcutter's Axe";
        down1 = setupImage("objects/axe",gp.TILE_SIZE,gp.TILE_SIZE);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        itemDescription = "!Woodcutter's Axe\nA bit rusty but can cut some\ntrees.";
    }
}
