package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_DryTree;

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

        i++;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*33;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*39;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.obj[i] = new OBJ_Heart(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*30;
        gp.obj[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.obj[i] = new OBJ_ManaCrystal(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*29;
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
        gp.monster[i].worldX = gp.TILE_SIZE*33;
        gp.monster[i].worldY = gp.TILE_SIZE*21;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*11;
        gp.monster[i].worldY = gp.TILE_SIZE*11;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*36;
        gp.monster[i].worldY = gp.TILE_SIZE*20;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*12;
        gp.monster[i].worldY = gp.TILE_SIZE*10;
    }
    public void setInteractiveTile()
    {
        int i = 0;
        gp.iTile[i] = new IT_DryTree(gp,27,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,28,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,29,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,30,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,31,12);

    }
}
