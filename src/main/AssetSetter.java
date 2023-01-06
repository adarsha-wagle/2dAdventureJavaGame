package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }
    public void setObject()
    {
        int i = 0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*21;
        gp.obj[i].worldY = gp.TILE_SIZE*22;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*22;
        gp.obj[i].worldY = gp.TILE_SIZE*22;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*33;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*36;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*38;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*30;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*32;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
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
