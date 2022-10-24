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
        tile = new Tile[50];//number of tiles
        mapTileNum = new int[gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];//new int [50[[50] if not changed
        getTileImage();//loads tiles in an array
        loadMap("/maps/worldV2.txt");
    }
    public void getTileImage()
    {
            //PLACEHOLDER
            /*
            We don't use the tile 0 to 9 but we set a placeholder image so we can prevent null pointer exception when scanning array
             */
            setUp(0,"grass00",false);
            setUp(1,"grass00",false);
            setUp(2,"grass00",false);
            setUp(3,"grass00",false);
            setUp(4,"grass00",false);
            setUp(5,"grass00",false);
            setUp(6,"grass00",false);
            setUp(7,"grass00",false);
            setUp(8,"grass00",false);
            setUp(9,"grass00",false);

            //PLACEHOLDER END

            //ACTUAL TILE SETUP
                //GRASS
            setUp(10,"grass00",false);
            setUp(11,"grass01",false);
                //WATER
            setUp(12,"water00",true);
            setUp(13,"water01",true);
            setUp(14,"water02",true);
            setUp(15,"water03",true);
            setUp(16,"water04",true);
            setUp(17,"water05",true);
            setUp(18,"water06",true);
            setUp(19,"water07",true);
            setUp(20,"water08",true);
            setUp(21,"water09",true);
            setUp(22,"water10",true);
            setUp(23,"water11",true);
            setUp(24,"water12",true);
            setUp(25,"water13",true);
                //ROAD
            setUp(26,"road00",false);
            setUp(27,"road01",false);
            setUp(28,"road02",false);
            setUp(29,"road03",false);
            setUp(30,"road04",false);
            setUp(31,"road05",false);
            setUp(32,"road06",false);
            setUp(33,"road07",false);
            setUp(34,"road08",false);
            setUp(35,"road09",false);
            setUp(36,"road10",false);
            setUp(37,"road11",false);
            setUp(38,"road12",false);
                //EARTH
            setUp(39,"earth",false);
                //WALL
            setUp(40,"wall",true);
                //TREE
            setUp(41,"tree",true);


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
