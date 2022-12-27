package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp)

    {
        super(gp);

        name = "Key";
        down1 = setupImage("objects/key",gp.TILE_SIZE,gp.TILE_SIZE);//scaling image

    }
}
