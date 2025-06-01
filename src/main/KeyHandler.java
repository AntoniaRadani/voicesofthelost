package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

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
    public boolean qPressed;
    public boolean ctrlPressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

 /*   @Override
    public void keyPressed(KeyEvent e) {
        // returns the integer keyCode associated with the key
        int code = e.getKeyCode();

        if(gp.gameState == gp.playState) {

            inputNumber(code);

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
            if(code == KeyEvent.VK_Q){
                qPressed = true;
            }
            if(code == KeyEvent.VK_CONTROL){
                ctrlPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
                gp.gameState = gp.pauseState; // paused
                gp.syncGameState();
            }
            if (code == KeyEvent.VK_E) {
                gp.toggleFullscreen();
            }
            if (code == KeyEvent.VK_1) {
                gp.adjustZoom(true); // zoom in
            }
            if (code == KeyEvent.VK_0) {
                gp.adjustZoom(false); // zoom out
            }

            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
                // in play mode
                gp.gameState = gp.characterStatus; // character status mode
                gp.syncGameState();
                characterStatus(code);
            }
            if( code == KeyEvent.VK_M ) {
                gp.showFullMap = !gp.showFullMap;
            }
            if( code == KeyEvent.VK_X ) {
                if ( gp.map.miniMapOn == false ) {
                    gp.map.miniMapOn = true;
                } else {
                    gp.map.miniMapOn = false;
                }
            }
            if( code == KeyEvent.VK_L ) {
                if ( gp.showLighting == false ) {
                    gp.showLighting = true;
                } else {
                    gp.showLighting = false;
                }
            }
        }
        else if(gp.gameState == gp.characterStatus) {
            characterStatus(code);
            if(code == KeyEvent.VK_SHIFT) {
                gp.gameState = gp.playState;
                gp.syncGameState();
            }
            if(code == KeyEvent.VK_ENTER)
                gp.player.selectItem();
        }
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
                gp.syncGameState();
            }

        }
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_F) {
                gp.player.attacking = true;
                gp.gameState = gp.playState;
                gp.syncGameState();
            }
        } else if (gp.gameState == gp.mapState) {
            mapState(code);
        }
    } */

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (gp.gameState) {

            // 1. PLAY STATE
            case 1 -> {
                inputNumber(code);

                switch (code) {
                    case KeyEvent.VK_W -> upPressed = true;
                    case KeyEvent.VK_S -> downPressed = true;
                    case KeyEvent.VK_A -> leftPressed = true;
                    case KeyEvent.VK_D -> rightPressed = true;
                    case KeyEvent.VK_ENTER -> enterPressed = true;
                    case KeyEvent.VK_ESCAPE -> {
                        escPressed = true;
                        gp.gameState = gp.pauseState;
                        gp.syncGameState();
                    }
                    case KeyEvent.VK_SHIFT -> {
                        shiftPressed = true;
                        gp.gameState = gp.characterStatus;
                        gp.syncGameState();
                        characterStatus(code);
                    }
                    case KeyEvent.VK_F -> fPressed = true;
                    case KeyEvent.VK_Q -> qPressed = true;
                    case KeyEvent.VK_CONTROL -> ctrlPressed = true;
                    case KeyEvent.VK_E -> gp.toggleFullscreen();
                    case KeyEvent.VK_1 -> gp.adjustZoom(true);
                    case KeyEvent.VK_0 -> gp.adjustZoom(false);
                    case KeyEvent.VK_M -> gp.showFullMap = !gp.showFullMap;
                    case KeyEvent.VK_X -> gp.map.miniMapOn = !gp.map.miniMapOn;
                    case KeyEvent.VK_L -> gp.showLighting = !gp.showLighting;
                }
            }

            // 2. CHARACTER STATUS STATE
            case 3 -> {
                characterStatus(code);

                if (code == KeyEvent.VK_SHIFT) {
                    gp.gameState = gp.playState;
                    gp.syncGameState();
                }
                if (code == KeyEvent.VK_ENTER) {
                    gp.player.selectItem();
                }
            }

            // 3. PAUSE STATE
            case 2 -> {
                if (code == KeyEvent.VK_ESCAPE) {
                    gp.gameState = gp.playState;
                    gp.syncGameState();
                }
            }

            // 4. DIALOGUE STATE
            case 4 -> {
                if (code == KeyEvent.VK_F) {
                    gp.player.attacking = true;
                    gp.gameState = gp.playState;
                    gp.syncGameState();
                }
            }

            // 5. TRAP ROOM STATE (pentru introducere cod sau dialog inițial)
            case 5 -> {
                if (code == KeyEvent.VK_F) {
                    gp.gameState = gp.playState;
                    gp.syncGameState();
                }
            }

            // 6. MAP STATE
            case 10 -> {
                if (code == KeyEvent.VK_M) {
                    gp.gameState = gp.playState;
                    gp.syncGameState();
                }
            }
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
            gp.toggleFullscreen();
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
        if(code == KeyEvent.VK_CONTROL){
            ctrlPressed = false;
        }

    }

    public void mapState( int code ) {
        if (code == KeyEvent.VK_M) {

            gp.gameState = gp.playState;
            gp.syncGameState();
        }

    }

    public void inputNumber(int code){
        if (gp.waitingForNumberInput) {
            if (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
                gp.inputNumber += (char) code;
                gp.ui.inputString = gp.inputNumber;  // sincronizează cu UI
            } else if (code == KeyEvent.VK_BACK_SPACE && !gp.inputNumber.isEmpty()) {
                gp.inputNumber = gp.inputNumber.substring(0, gp.inputNumber.length() - 1);
                gp.ui.inputString = gp.inputNumber;  // sincronizează cu UI
            } else if (code == KeyEvent.VK_ENTER) {
                int guessedNumber = -1;
                try {
                    guessedNumber = Integer.parseInt(gp.inputNumber);
                } catch (NumberFormatException ex) {
                    gp.inputNumber = "";
                    gp.ui.inputString = "";
                    return;
                }

                if (guessedNumber == gp.correctNumber) {
                    System.out.println("Escaped from trap room!");
                    gp.waitingForNumberInput = false;
                    gp.escapedFromTrapRoom = true;
                    gp.inputNumber = "";
                    gp.ui.inputString = "";
                    gp.trapRoomLevel1.monster.dead = true;
                    gp.monsters[gp.currentMap][0].dead = true;
                } else {
                    gp.inputNumber = "";
                    gp.ui.inputString = "";
                }
            }
        }else {
            // restul input-ului normal
            if (gp.player.isNearMonster(gp.trapRoomLevel1.monster, gp)) {
                if (code == KeyEvent.VK_F) {
                    gp.waitingForNumberInput = true;
                    gp.inputNumber = "";
                }
            }
        }

    }
}
