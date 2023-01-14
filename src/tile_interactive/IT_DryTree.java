package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile{
    GamePanel gp;
    public IT_DryTree(GamePanel gp,int col,int row)
    {
        super(gp,col,row);
        this.gp = gp;
        this.worldX = gp.TILE_SIZE * col;
        this.worldY = gp.TILE_SIZE*row;

        down1 = setupImage("tiles_interactive/dryTree",gp.TILE_SIZE,gp.TILE_SIZE);
        destructible = true;
        life = 3;

    }
    public boolean isCorrectItem(Entity entity)
    {
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_axe)
        {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE()
    {
        gp.playSE(12);
    }
    public InteractiveTile getDestroyedForm()
    {
        InteractiveTile tile = new IT_Trunk(gp,worldX/gp.TILE_SIZE,worldY/gp.TILE_SIZE);
        return tile;
    }
}
