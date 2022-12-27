package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp)

    {
            super(gp);
            name = "Heart";

            image = setupImage("objects/heart_full",gp.TILE_SIZE,gp.TILE_SIZE);
            image2 = setupImage("objects/heart_half",gp.TILE_SIZE,gp.TILE_SIZE);
            image3 = setupImage("objects/heart_blank",gp.TILE_SIZE,gp.TILE_SIZE);

    }
}
