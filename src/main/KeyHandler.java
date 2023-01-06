package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {
    public boolean upPressed,downPressed,leftPressed,rightPressed,talkPressed,shotKeyPressed;
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

            titleState(e.getKeyCode());
        }
        //PLAY STATE
        else if(gp.gameState == gp.playState) {
            playState(e.getKeyCode());
        }

        //PAUSE STATE
        else if(gp.gameState == gp.pauseState)
        {
            pauseState(e.getKeyCode());
        }

        //DIALOGUE STATE
       else  if(gp.gameState == gp.dialogueState)
        {
            dialogueState(e.getKeyCode());
        }
        //CHARACTER STATE
       else if(gp.gameState == gp.characterState)
        {

            characterState(e.getKeyCode());
        }
    }

    public void titleState(int code)
    {
        //0 for new game,1 for load game and 2 for quit
        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum<0)
            {
                gp.ui.commandNum=2;
            }

        } if (code == KeyEvent.VK_DOWN) {
        gp.ui.commandNum++;
        if(gp.ui.commandNum>2)
        {
            gp.ui.commandNum=0;
        }
    }
        if(code == KeyEvent.VK_ENTER)
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
    public void playState(int code)
    {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        //if player presses T key then talk to npc
        if(code==KeyEvent.VK_T)
        {
            talkPressed = true;
        }
        if(code==KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        //pausing game
        if (code == KeyEvent.VK_P) {
//            gp.gameState = gp.gameState == gp.playState ? gp.pauseState:gp.playState;

            gp.gameState = gp.pauseState;

        }
        if(code == KeyEvent.VK_C)
        {
            System.out.println("pressesd c");
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_F)
        {
            shotKeyPressed = true;
        }

    }

    public void pauseState(int code)
    {

        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code)
    {
        if (code == KeyEvent.VK_ENTER)
        {
            gp.gameState = gp.playState;
        }
        if(code== KeyEvent.VK_T)
        {
            gp.npc[0].speak();
            System.out.println("k pressed");
        }
    }
    public void characterState(int code)
    {
        if(code == KeyEvent.VK_C)
        {

            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_W)
        {
            if(gp.ui.slotRow!=0)
            {
            gp.playSE(10);
            gp.ui.slotRow--;
            }
        }if(code == KeyEvent.VK_A)
        {
            if(gp.ui.slotCol!=0) {
                gp.playSE(10);
                gp.ui.slotCol--;
            }
        }if(code == KeyEvent.VK_S)
        {
            if(gp.ui.slotRow!=3) {
                gp.playSE(10);
                gp.ui.slotRow++;
            }
        }if(code == KeyEvent.VK_D)
        {
            if(gp.ui.slotCol!=4) {
                gp.playSE(10);
                gp.ui.slotCol++;
            }
        }
        if(code == KeyEvent.VK_ENTER)
        {
            gp.player.selectItem();
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
        }if(e.getKeyCode() == KeyEvent.VK_F)
        {
            shotKeyPressed = false;
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
