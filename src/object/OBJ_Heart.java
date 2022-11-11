package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Heart extends SuperObject {
    GamePanel gp;
    public OBJ_Heart(GamePanel gp)

    {
        this.gp = gp;
        name = "Heart";
        try
        {
            image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("objects/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("objects/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("objects/heart_blank.png")));
           image = uTool.scaledImage(image,gp.TILE_SIZE,gp.TILE_SIZE);
           image2= uTool.scaledImage(image2,gp.TILE_SIZE,gp.TILE_SIZE);
           image3= uTool.scaledImage(image3,gp.TILE_SIZE,gp.TILE_SIZE);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
