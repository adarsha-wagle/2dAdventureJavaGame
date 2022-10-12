package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int ORIGINAL_TILE_SIZE = 16;//tile size
    final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;//48*48 tile
    final int MAX_SCREEN_COL = 16;
    final int MAX_SCREEN_ROW = 12;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;//768 pixels
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;//576 pixels

    int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this,keyH);
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);//if set to true, all the drawing from this component done in an offscreen painting buffer//for better rendering performance
        startGameThread();
        this.addKeyListener(keyH);
        this.setFocusable(true);//game panel is focused to get key input
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
                if(remainingTime<0)
                    remainingTime = 0;
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
        player.draw(g2d);

        g2d.dispose();//to save memory
    }
}
