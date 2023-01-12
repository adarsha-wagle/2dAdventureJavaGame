package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public OBJ_Rock(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        name = "Rock";
        speed = 8;
        maxLife  = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage()
    {
        up1 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        right1 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        right2 = setupImage("projectile/rock_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
    }
    public boolean haveResource(Entity user)
    {
        boolean haveResource = false;
        if(user.ammo>=useCost)
        {
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user)
    {
        user.ammo-=useCost;
    }
}
