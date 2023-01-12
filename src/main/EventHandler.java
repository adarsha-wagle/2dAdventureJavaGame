package main;


import java.awt.Rectangle;

public class EventHandler {

    GamePanel gp;
//    Rectangle eventRect;
//    int eventRectDefaultX,eventRectDefaultY;
    int previousEventX,previousEventY;
    boolean canTouchEvent = true;
EventRect eventRect[][];

    public EventHandler(GamePanel gp)
    {
        this.gp = gp;
        eventRect =new EventRect[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        int col = 0;
        int row = 0;
        while(col<gp.MAX_WORLD_COL && row<gp.MAX_WORLD_ROW) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if(col == gp.MAX_WORLD_COL)
            {
                col = 0;
                row++;
            }
        }
    }
    public void checkEvent()
        {
            //check if the player character is more than 1 tile away from the last event
            int xDistance = Math.abs(gp.player.worldX-previousEventX);
            int yDistance = Math.abs(gp.player.worldY-previousEventY);
            int distance = Math.max(xDistance,yDistance);//taking maximum distance place moves after hitting
            if(distance>gp.TILE_SIZE)
            {
                canTouchEvent = true;

            }
            if(canTouchEvent) {


                //take damage
                if (hit(23, 19, "any") == true) {
                    damagePit(23, 19, gp.dialogueState);
                }
                //heal
                if (hit(23, 12, "up") == true) {
                    healingPool(23, 12, gp.dialogueState);
                }
            }
            //teleport
            if(hit(23,14,"any")==true)
            {
                teleport(gp.dialogueState);
            }

        }
    public boolean hit(int col,int row,String reqDirection)
    {
        boolean hit = false;
        eventRect[col][row].eventDone = false;
        //Getting player's current solidArea position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        //Getting event Rectangle solidArea position;
        eventRect[col][row].x = col*gp.TILE_SIZE+eventRect[col][row].x;
        eventRect[col][row].y = row*gp.TILE_SIZE+eventRect[col][row].y;
        //Check if player is colliding with event Rectangle
        if(gp.player.solidArea.intersects(eventRect[col][row]) && (eventRect[col][row].eventDone == false))
        {
            //if player is facing certain direction then ....
            if(gp.player.direction.contentEquals(reqDirection)||reqDirection.contentEquals("any"))
            {
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        //After checking the collision reset the solidArea x and y
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }
    public void damagePit(int col,int row,int gameState)
    {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit";
        gp.player.life -= 1;
        eventRect[col][row].eventDone= true;
        canTouchEvent = false;
    }
    public void healingPool(int col,int row,int gameState)
    {
        if(gp.keyH.enterPressed == true)
        {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water.\nYour life and mana has been recovered.";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }

    }
    public void teleport(int gameState)
    {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport";
        gp.player.worldX = gp.TILE_SIZE * 37;
        gp.player.worldY = gp.TILE_SIZE * 10;

    }
}

