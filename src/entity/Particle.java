package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity {
    Entity generator;
    Color color;
    int xd;
    int yd;
    int maxLife;
    int size;
    public Particle(GamePanel gp,Entity generator,Color color , int size, int speed, int maxLife, int xd, int yd){
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size    = size;
        this.xd = xd;
        this.yd = yd;
        this.speed = speed;
        this.maxLife = maxLife;
        life = maxLife;
        int offset = (gp.TILE_SIZE/2) - (size/2);
        worldX = generator.worldX+offset;//particle position x
        worldY = generator.worldY+offset;
    }
    public void update()
    {
        life--;
        worldX+=xd*speed;
        worldY+=yd*speed;
        //ADDING GRAVITY TO THE PARTICLE
        if(life < maxLife/3)
        {
            System.out.println("here");
            yd++;
        }
        if(life == 0)
        {
            alive = false;
        }
    }
    public void draw(Graphics2D g2)
    {
        int screenX = worldX-gp.player.worldX+gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.setColor(color);
        g2.fillRect(screenX,screenY,size,size);
    }
}
