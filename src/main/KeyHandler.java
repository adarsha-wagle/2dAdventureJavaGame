package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,leftPressed,rightPressed,talkPressed;
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
}
