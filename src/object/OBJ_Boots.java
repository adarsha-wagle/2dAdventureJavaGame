package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp)
    {
        super(gp);
        name = "Boots";
        down1 = setupImage("objects/boots");

    }
}
