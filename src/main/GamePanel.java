package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

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
    public final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;//2400px total pixel used by whole game
    public final int WORLD_HEIGHT  = TILE_SIZE * MAX_WORLD_ROW;//2400px total pixel used by whole game

    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this) ;
    public AssetSetter aSetter = new AssetSetter(this);
   public Player player = new Player(this,keyH);
   public SuperObject obj[] = new SuperObject[10];
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);//if set to true, all the drawing from this component done in an offscreen painting buffer//for better rendering performance

        this.addKeyListener(keyH);
        this.setFocusable(true);//game panel is focused to get key input
    }
    public void setupGame()
    {
        aSetter.setObject();
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
        player.update();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //tile
        tileManager.draw(g2d);
        //object : chest,door,key,etc
        for (int i = 0;i<obj.length;i++){
            if(obj[i]!=null)//drawing if only object is created
            {
//                System.out.println(obj[i].name);
                obj[i].draw(g2d,this) ;
            }
        }
        //player
        player.draw(g2d);

        g2d.dispose();//to save memory
    }
}
