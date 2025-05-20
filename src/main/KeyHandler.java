package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    public boolean escPressed;
    public boolean shiftPressed;
    public boolean fPressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // returns the integer keyCode associated with the key
        int code = e.getKeyCode();

        if(gp.gameState == gp.playState) {
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
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_F) {
                fPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
                if (gp.gameState == gp.playState) // if it s in play mode
                    gp.gameState = gp.pauseState; // paused
                else if (gp.gameState == gp.pauseState) // if the game is paused
                    gp.gameState = gp.playState; // playing
            }

            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
                if (gp.gameState == gp.playState) // in play mode
                    gp.gameState = gp.characterStatus; // character status mode
                else if (gp.gameState == gp.characterStatus) // if it is in character status
                    gp.gameState = gp.playState; // switch to play mode
            }
        }
        else if(gp.gameState == gp.dialogueState || gp.gameState == gp.characterStatus){
            if(code == KeyEvent.VK_N)
                gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
        if(code == KeyEvent.VK_ESCAPE){
            escPressed = false;
        }
        if(code == KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
    }
}
