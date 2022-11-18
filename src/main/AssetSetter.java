package main;

import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }
    public void setObject()
    {
        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].worldX = gp.TILE_SIZE*21;
        gp.obj[0].worldY = gp.TILE_SIZE*22;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = gp.TILE_SIZE*23;
        gp.obj[1].worldY = gp.TILE_SIZE*25;

    }
    public void setNPC()
    {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.TILE_SIZE*21;
        gp.npc[0].worldY = gp.TILE_SIZE*21;
    }
}
