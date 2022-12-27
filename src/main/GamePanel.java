package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable{

    /*
    This class is responsible for
    1.defining tile size
    2.defining size of world
    3.creating game loop
    4.listening key inputs
    5.instantiating player
    6.updating player movement
    7.instantiating tiles
     */

    //INITIAL SETUP FOR SCREEN
    final int ORIGINAL_TILE_SIZE = 16;//tile size
    final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;//48*48 tile
    public final int MAX_SCREEN_COL = 16;
   public final int MAX_SCREEN_ROW = 12;
   public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;//768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;//576 pixels

    //World Settings
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;


    int FPS = 60;

    //LOADING MAP
    TileManager tileManager = new TileManager(this);

    //FOR CHECKING INPUT LIKE PLAYER MOVEMENT
   public KeyHandler keyH = new KeyHandler(this);

    //SETTING SOUND EFFECT AND MUSIC
    Sound music  = new Sound();
    Sound soundEff = new Sound();
    Thread gameThread;

    //COLLISION CHECKER FOR PLAYER AND TILES
    public CollisionChecker cChecker = new CollisionChecker(this) ;

    //FOR SETTING UP NPC AND OBJECTS
    public AssetSetter aSetter = new AssetSetter(this);

    //UI FOR PLAY,PAUSE,ETC
     public UI ui = new UI(this) ;

     //EVENT HANDLER
     public EventHandler eHandler = new EventHandler(this);

   //ENTITY AND OBJECT
   public Player player = new Player(this,keyH);
   public Entity[] obj = new Entity[10];
   public Entity[] npc = new Entity[10];
   public Entity[] monster = new Entity[20];
   ArrayList<Entity> entityList = new ArrayList<>();

   //GAME STATE LIKE PAUSE,PLAY,DIALOG MODE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);//if set to true, all the drawing from this component done in an offscreen painting buffer//for better rendering performance

        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);//game panel is focused to get key input
    }
    public void setupGame()
    {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
//        playMusic(0);
        gameState = titleState;
    }
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0/FPS;//1billion nano sec or 1sec divided by fps = 0.01667 sec
        double nextDrawTime = System.nanoTime() + drawInterval;//returns current system time
        while (gameThread!=null)
        {
           // long currentTime = System.nanoTime();//returns the current value of the jvm's high resolution time source in nanoseconds.

            //1.Update:update information such as character position
            update();
            //2.DRAW:draw the screen with the uploaded information
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime<0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime+=drawInterval;
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void update()//responsible for changing player position
    {

        if(gameState == playState)
        {

        player.update();
        //NPC
            for (int i = 0;i<npc.length;i++)
            {
                if(npc[i]!=null)
                {
                    npc[i].update();
                }
            }
            for (int i = 0;i<monster.length;i++)
            {
                if(monster[i]!=null)
                {
                    monster[i].update();
                }
            }
        }
        if(gameState == pauseState)
        {
            //pause
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //TITLE SCREEN
        if (gameState == titleState) {
                ui.draw(g2d);
        } else {
            //Tile
            tileManager.draw(g2d);
            //ADDING ENTITIES TO THE LIST
            //adding player
            entityList.add(player);
            //adding npx
            for(int i = 0;i<npc.length;i++)
            {
                if(npc[i]!=null)
                {
                    entityList.add(npc[i]);
                }
            }
            //adding objects
            for (int i  = 0;i< obj.length;i++)
            {
                if(obj[i]!=null){
                    entityList.add(obj[i]);
                }
            }
            //adding monster
            for (int i = 0;i<monster.length;i++)
            {
                if(monster[i]!= null)
                {
                    entityList.add(monster[i]);
                }
            }
            //SORTING ON THE BASIS OF WORLDY
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });
            //DRAW ENTITIES
            for (int i = 0;i<entityList.size();i++)
            {
                entityList.get(i).draw(g2d);
            }
           entityList.clear();

            //ui
            ui.draw(g2d);

            g2d.dispose();//to save memory
        }
    }
    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic()
    {
        music.stop();
    }
    public void playSE(int i)
    {
        soundEff.setFile(i);
        soundEff.play();
    }
}
