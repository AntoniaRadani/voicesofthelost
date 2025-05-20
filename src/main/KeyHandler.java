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
                gp.gameState = gp.pauseState; // paused
            }
            if (code == KeyEvent.VK_E) {
                gp.toggleFullscreen();
            }

            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
                // in play mode
                gp.gameState = gp.characterStatus; // character status mode
                characterStatus(code);
            }
        }
        else if(gp.gameState == gp.characterStatus) {
            characterStatus(code);
            if (code == KeyEvent.VK_SHIFT)
                gp.gameState = gp.playState;
        }
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_ESCAPE)
                gp.gameState = gp.playState;
        }
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_F)
                gp.gameState = gp.playState;
        }
    }

    public void characterStatus(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSE(1);  // trebuie sa adaug un sound
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                gp.playSE(1);
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                gp.playSE(1);
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSE(1);
            }
        }
        if (code == KeyEvent.VK_E) {
            gp.toggleFullscreen();  // vei avea nevoie de această metodă în GamePanel
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
