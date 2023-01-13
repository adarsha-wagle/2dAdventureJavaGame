package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    GamePanel gp;
    public OBJ_Heart(GamePanel gp)

    {
            super(gp);
            this.gp = gp;
            name = "Heart";
            value = 2;
            down1 = setupImage("objects/heart_full",gp.TILE_SIZE,gp.TILE_SIZE);
            image = setupImage("objects/heart_full",gp.TILE_SIZE,gp.TILE_SIZE);
            image2 = setupImage("objects/heart_half",gp.TILE_SIZE,gp.TILE_SIZE);
            image3 = setupImage("objects/heart_blank",gp.TILE_SIZE,gp.TILE_SIZE);

    }
    public void use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;

    }
}
