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

            image = setupImage("objects/heart_full");
            image2 = setupImage("objects/heart_half");
            image3 = setupImage("objects/heart_blank");

    }
}
