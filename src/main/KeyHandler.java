package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,leftPressed,rightPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            upPressed = true;
        }if(e.getKeyCode() == KeyEvent.VK_S)
        {
             downPressed = true;
        }if(e.getKeyCode() == KeyEvent.VK_A)
        {
            leftPressed = true;
        }if(e.getKeyCode() == KeyEvent.VK_D)
        {
            rightPressed = true;
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
