package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    GamePanel gp;
    public OBJ_Coin_Bronze(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        value = 1;
        down1 = setupImage("objects/coin_bronze",gp.TILE_SIZE,gp.TILE_SIZE);
    }
    public void use(Entity entity)
    {
        gp.playSE(1);
        gp.ui.addMessage("Coin +"+value);
        gp.player.coin += value;
        
    }
}
