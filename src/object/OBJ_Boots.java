package object;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Boots extends SuperObject {
    public OBJ_Boots()
    {
        name = "Boots";
        try
        {
            image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("objects/boots.png")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
