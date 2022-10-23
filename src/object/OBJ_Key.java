package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Key extends SuperObject{
    GamePanel gp;
    public OBJ_Key(GamePanel gp)

    {
        this.gp = gp;
        name = "Key";
        try
        {
            image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("objects/key.png")));
            uTool.scaledImage(image,gp.TILE_SIZE,gp.TILE_SIZE);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
