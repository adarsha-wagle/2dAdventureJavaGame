package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Boots extends SuperObject {
    GamePanel gp;
    public OBJ_Boots(GamePanel gp)
    {
        this.gp = gp;
        name = "Boots";
        try
        {
            image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("objects/boots.png")));
            uTool.scaledImage(image,gp.TILE_SIZE,gp.TILE_SIZE);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
