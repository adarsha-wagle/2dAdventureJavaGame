package entity;

import main.GamePanel;

public class Projectile extends Entity{
//    GamePanel gp;
    public Projectile(GamePanel gp )
    {
        super(gp);
//        this.gp = gp;
    }
    public void set(int worldX,int worldY,String direction, boolean alive, Entity user )
    {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;//reset the life to max value every time you shoot projectile

        //setting solid area OPTIONAL
        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 36;//default 42
        solidArea.height = 24;//default 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void update()
    {
        if(user == gp.player)
        {
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            if(monsterIndex!=999)
            {
                gp.player.damageMonster(monsterIndex,attack);
                alive = false;//the projectile dies
            }
        }
        if(user != gp.player) {

        }
        switch (direction)
        {
            case "up":worldY-=speed;break;
            case "down":worldY+=speed;break;
            case "left":worldX -=speed;break;
            case "right":worldX+=speed;break;
        }
        life--;
        if(life<=0)//after 80 frames
        {
            alive = false;
        }
        spriteCounter++;
        if(spriteCounter > 12)
        {
            spriteNum = spriteNum == 1?2:1;
            spriteCounter = 0;
        }
    }
}
