package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
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
//        gp.obj[0] = new OBJ_Door(gp);
//        gp.obj[0].worldX = gp.TILE_SIZE*21;
//        gp.obj[0].worldY = gp.TILE_SIZE*22;


    }
    public void setNPC()
    {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.TILE_SIZE*9;
        gp.npc[0].worldY = gp.TILE_SIZE*10;
    }
    public void setMonster()
    {
        int i = 0;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*11;
        gp.monster[i].worldY = gp.TILE_SIZE*10;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*11;
        gp.monster[i].worldY = gp.TILE_SIZE*11;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*12;
        gp.monster[i].worldY = gp.TILE_SIZE*11;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*12;
        gp.monster[i].worldY = gp.TILE_SIZE*10;
    }
}
