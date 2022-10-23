package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
/*
This class is responsible for
1.loading tiles from file.
2.storing tile number from file.
3.rendering tile based upon tile number
 */
    GamePanel gamePanel;
   public Tile[] tile;
   public int mapTileNum[][];
    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        tile = new Tile[6];
        mapTileNum = new int[gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];//new int [50[[50] if not changed
        getTileImage();//loads tiles in an array
        loadMap("/maps/world01.txt");
    }
    public void getTileImage()
    {
            setUp(0,"grass",false);
            setUp(1,"wall",true);
            setUp(2,"water",true);
            setUp(3,"earth",false);
            setUp(4,"tree",true);
            setUp(5,"sand",false);

    }
    public void setUp(int index,String imageName,boolean collision)
    {
        UtilityTool uTool = new UtilityTool();
        try
        {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("tiles/"+imageName+".png")));
            tile[index].image = uTool.scaledImage(tile[index].image,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE);
            tile[index].collision = collision;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath)//splitting num and assigning tile number to the array mapTileNum[ ][ ]
    {
        try
        {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col<gamePanel.MAX_WORLD_COL && row<gamePanel.MAX_WORLD_ROW)
            {
                String line = br.readLine();//loads one whole row

                while(col < gamePanel.MAX_WORLD_COL)
                {
                    String numbers[] = line.split(" ");//string
                    int num = Integer.parseInt(numbers[col]);//converts string to integer
                    mapTileNum[col][row] = num;//stores in an array
                    col++;
                }
                if(col == gamePanel.MAX_WORLD_COL)
                {
                    col = 0;
                    row++;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
    }
    public void draw(Graphics2D g2d)//rendering tiles base upon the tile number
    {
       int worldCol = 0;
       int worldRow = 0;

       while(worldCol < gamePanel.MAX_WORLD_COL && worldRow< gamePanel.MAX_WORLD_ROW    )

       {

           int tileNum = mapTileNum[worldCol][worldRow];//getting tile number
           int worldX = worldCol * gamePanel.TILE_SIZE;//x coordinate of tile
           int worldY = worldRow * gamePanel.TILE_SIZE;//y coordinate of tile
           int screenX = worldX-gamePanel.player.worldX + gamePanel.player.screenX;
           int screenY = worldY-gamePanel.player.worldY + gamePanel.player.screenY;

           if(worldX + gamePanel.TILE_SIZE>gamePanel.player.worldX - gamePanel.player.screenX
                   && worldX-gamePanel.TILE_SIZE < gamePanel.player.worldX + gamePanel.player.screenX
               && worldY +gamePanel.TILE_SIZE> gamePanel.player.worldY - gamePanel.player.screenY
               && worldY - gamePanel.TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY) {

               g2d.drawImage(tile[tileNum].image, screenX, screenY, null);
           }
           worldCol++;
           if(worldCol == gamePanel.MAX_WORLD_COL)
           {
               worldCol = 0;
               worldRow++;
           }
       }
    }
}
