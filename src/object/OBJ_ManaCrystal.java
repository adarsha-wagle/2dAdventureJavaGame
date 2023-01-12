package object;

import com.sun.tools.jconsole.JConsoleContext;
import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;
    public OBJ_ManaCrystal(GamePanel gp)
    {
        super(gp);
        this.gp = gp;

        name = "Mana Crystal";
        image = setupImage("objects/manacrystal_full",gp.TILE_SIZE,gp.TILE_SIZE);
        image2 = setupImage("objects/manacrystal_blank",gp.TILE_SIZE,gp.TILE_SIZE);
//        System.out.println(image);
//        System.out.println(image2);

    }
}
