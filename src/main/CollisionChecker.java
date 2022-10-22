package main;
import entity.Entity;
public class CollisionChecker {
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }
    //checks if player touches tile or not
    public void checkTile(Entity entity)//using entity instead of player because we want to check collision of others object
    {
        int entityLeftWorldX = entity.worldX+entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY+entity.solidArea.y+entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gamePanel.TILE_SIZE;
        int entityRightCol = entityRightWorldX/gamePanel.TILE_SIZE;
        int entityTopRow = entityTopWorldY/gamePanel.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY/gamePanel.TILE_SIZE;

        int tileNum1,tileNum2;
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.playerSpeed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision)//if player is in two tile that are solid then stop movement
                {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.playerSpeed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision)//if player is in two tile that are solid then stop movement
                {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.playerSpeed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision)//if player is in two tile that are solid then stop movement
                {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.playerSpeed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision)//if player is in two tile that are solid then stop movement
                {
                    entity.collisionOn = true;
                }
            }
        }
    }
    public int checkObject(Entity entity,boolean player)
    {
        int index = 999;//if it is monster or npc's return this
        for (int i = 0; i<gamePanel.obj.length;i++)
        {
            if(gamePanel.obj[i]!=null)
            {
                //get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //get the object's solid area position
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX + gamePanel.obj[i].solidArea.x;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY + gamePanel.obj[i].solidArea.y;

                switch(entity.direction)
                {
                    case "up":
                        entity.solidArea.y-=entity.playerSpeed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.playerSpeed;
                        break;
                    case "left":
                        entity.solidArea.x-=entity.playerSpeed;
                        break;
                    case "right":
                        entity.solidArea.x+=entity.playerSpeed;
                        break;
                }
                if(entity.solidArea.intersects(gamePanel.obj[i].solidArea))
                {
//                    System.out.println("collided to the object");
                    if(gamePanel.obj[i].collision)
                    {
                        entity.collisionOn = true;
                    }
                    if(player)
                    {
                        index = i;//if it is player we return i
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
