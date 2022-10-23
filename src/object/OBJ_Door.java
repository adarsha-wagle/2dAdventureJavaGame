package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Door extends SuperObject {

    GamePanel gp;
    public OBJ_Door(GamePanel gp)
    {
        this.gp = gp;
        name = "Door";
        try
        {
            image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("objects/door.png")));
            uTool.scaledImage(image,gp.TILE_SIZE,gp.TILE_SIZE);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        collision = true;
    }
}


