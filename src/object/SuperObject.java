package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX,worldY;
    public void draw(Graphics2D g2, GamePanel gamePanel)
    {
        int screenX = worldX-gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY-gamePanel.player.worldY + gamePanel.player.screenY;

        if(worldX + gamePanel.TILE_SIZE>gamePanel.player.worldX - gamePanel.player.screenX
                && worldX-gamePanel.TILE_SIZE < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY +gamePanel.TILE_SIZE> gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY) {

            g2.drawImage(image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
        }
    }

}
