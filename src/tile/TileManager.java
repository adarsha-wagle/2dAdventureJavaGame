package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNum[][];
    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.MAX_SCREEN_COL][gamePanel.MAX_SCREEN_ROW];
        getTileImage();
        loadMap("/maps/map01.txt");
    }
    public void getTileImage()
    {
        try
        {
            tile[0] = new Tile();

            tile[0].image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("tiles/grass.png")));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("tiles/water.png")));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("tiles/wall.png")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath)
    {
        try
        {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col<gamePanel.MAX_SCREEN_COL && row<gamePanel.MAX_SCREEN_ROW)
            {
                String line = br.readLine();
                while(col < gamePanel.MAX_SCREEN_COL)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gamePanel.MAX_SCREEN_COL)
                {
                    col = 0;
                    row++;
                }
            }
        }
        catch(Exception e)
        {

        }
    }
    public void draw(Graphics2D g2d)
    {
       int col = 0;
       int row = 0;
       int x = 0;
       int y = 0;
       while(col < gamePanel.MAX_SCREEN_COL && row< gamePanel.MAX_SCREEN_ROW    )

       {
           int tileNum = mapTileNum[col][row];
           g2d.drawImage(tile[tileNum].image,x,y,gamePanel.TILE_SIZE,gamePanel.TILE_SIZE,null);
           col++;
           x+=gamePanel.TILE_SIZE;
           if(col == gamePanel.MAX_SCREEN_COL)
           {
               col = 0;
               x = 0;
               row++;
               y+=gamePanel.TILE_SIZE;
           }
       }
    }
}
