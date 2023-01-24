package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    public final int MAX_SCREEN_COL = 20;
   public final int MAX_SCREEN_ROW = 12;
   public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;//768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;//576 pixels

    //World Settings
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;

    //FOR FULL SCREEN
    int screenWidth2 = SCREEN_WIDTH;
    int screenHeight2 = SCREEN_HEIGHT;
    BufferedImage tempScreen;
    Graphics2D g2d;


    public boolean fullScreenOn = false;

    int FPS = 60;

    //LOADING MAP
    TileManager tileManager = new TileManager(this);

    //FOR CHECKING INPUT LIKE PLAYER MOVEMENT
   public KeyHandler keyH = new KeyHandler(this);

    //SETTING SOUND EFFECT AND MUSIC
    Sound music  = new Sound();
    Sound soundEff = new Sound();

    //CONFIG FILE
    Config config = new Config(this);
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
   public Entity[] obj = new Entity[20];
   public Entity[] npc = new Entity[10];
   public Entity[] monster = new Entity[20];
   public InteractiveTile[] iTile = new InteractiveTile[50];
   ArrayList<Entity> entityList = new ArrayList<>();
   public ArrayList<Entity> projectileList = new ArrayList<>();
   public ArrayList<Entity> particleList = new ArrayList<>();

   //GAME STATE LIKE PAUSE,PLAY,DIALOG MODE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
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
        aSetter.setInteractiveTile();
//        playMusic(0);
        gameState = titleState;
        tempScreen = new BufferedImage(SCREEN_WIDTH,SCREEN_HEIGHT,BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D) tempScreen.getGraphics();
        if(fullScreenOn)
        {
            setFullScreen();
        }
    }
    public  void retry()
    {
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    public void restart()
    {
        player.setDefaultValues();
        player.setItems();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        }
    public void setFullScreen()
    {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
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
            drawToTempScreen();//draw everything to the buffered image
            drawToScreen();//draw buffered image to the screen
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
                    if(monster[i].alive == true && monster[i].dying == false)
                    {
                    monster[i].update();

                    }
                    if(monster[i].alive == false)
                    {
                    monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for (int i = 0;i<projectileList.size();i++)
            {
                if(projectileList.get(i)!=null)
                {
                    if(projectileList.get(i).alive == true)
                    {
                    projectileList.get(i).update();

                    }
                    if(projectileList.get(i).alive == false)
                    {
                        projectileList.remove(i);
                    }
                }
            }
            for (int i = 0;i<particleList.size();i++)
            {
                if(particleList.get(i)!=null)
                {
                    if(particleList.get(i).alive == true)
                    {
                    particleList.get(i).update();

                    }
                    if(particleList.get(i).alive == false)
                    {
                        particleList.remove(i);
                    }
                }
            }
            for (int i = 0;i< iTile.length;i++) {
                if(iTile[i]!= null)
                {
                    iTile[i].update();
                }
            }

        }
        if(gameState == pauseState)
        {
            //pause
        }

    }
    public void drawToTempScreen() {
        //TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2d);
        } else {
            //Tile
            tileManager.draw(g2d);
            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].draw(g2d);
                }
            }
            //ADDING ENTITIES TO THE LIST

            //adding player
            entityList.add(player);
            //adding npx
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            //adding objects
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            //adding monster
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
//                System.out.println(projectileList.get(i));
                if (projectileList.get(i) != null) {
//                    System.out.println(projectileList.get(i));
                    entityList.add(projectileList.get(i));//projectile on the list will be render
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
//                System.out.println(projectileList.get(i));
                if (particleList.get(i) != null) {
//                    System.out.println(projectileList.get(i));
                    entityList.add(particleList.get(i));//projectile on the list will be render
                }
            }
            //SORTING ON THE BASIS OF WORLDY
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2d);
            }
            entityList.clear();

            //ui
            ui.draw(g2d);

        }
    }
    public void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
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
