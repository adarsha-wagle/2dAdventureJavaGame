package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {
    public boolean upPressed,downPressed,leftPressed,rightPressed,talkPressed;
    public boolean enterPressed;//for healing

    public boolean mousePressed;
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        //TITLE STATE
        if(gp.gameState == gp.titleState)
        {
            //0 for new game,1 for load game and 2 for quit
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum<0)
                {
                    gp.ui.commandNum=2;
                }

            } if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
            if(gp.ui.commandNum>2)
            {
                gp.ui.commandNum=0;
            }
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                if(gp.ui.commandNum == 0)
                {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1)
                {
                    //Add Later:Load Game

                }
                if(gp.ui.commandNum == 2)
                {
                    System.exit(0);
                }
            }
        }
        //PLAY STATE
        if(gp.gameState == gp.playState) {


            if (e.getKeyCode() == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                rightPressed = true;
            }
            //if player presses T key then talk to npc
            if(e.getKeyCode()==KeyEvent.VK_T)
            {
                talkPressed = true;
            }
            if(e.getKeyCode()==KeyEvent.VK_ENTER)
            {
                enterPressed = true;
            }
            //pausing game
            if (e.getKeyCode() == KeyEvent.VK_P) {
//            gp.gameState = gp.gameState == gp.playState ? gp.pauseState:gp.playState;

                    gp.gameState = gp.pauseState;

            }

        }

        //PAUSE STATE
        else if(gp.gameState == gp.pauseState)
        {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }

        }

        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                gp.gameState = gp.playState;
            }
            if(e.getKeyCode()== KeyEvent.VK_T)
            {
                gp.npc[0].speak();
                System.out.println("k pressed");
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            upPressed = false;
        }if(e.getKeyCode() == KeyEvent.VK_S)
        {
            downPressed = false;
        }if(e.getKeyCode() == KeyEvent.VK_A)
        {
            leftPressed = false;
        }if(e.getKeyCode() == KeyEvent.VK_D)
        {
            rightPressed = false;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked"  );
    }

    @Override
    public void mousePressed(MouseEvent e) {
    if(gp.gameState ==gp.playState)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            System.out.println("left mouse");
            mousePressed = true;
        }
    }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(gp.gameState == gp.playState)
        {
            if(e.getButton()==MouseEvent.BUTTON1)
            {
            mousePressed  = false;
//            gp.player.attacking = false;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
