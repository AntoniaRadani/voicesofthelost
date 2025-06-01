// New TrapRoomState.java
package state;

import main.GamePanel;
import java.awt.Graphics2D;

public class TrapRoomState implements GameState {
    private final GamePanel gp;
    private long dialogueStartTime;
    private boolean inDialogue = true;

    public TrapRoomState(GamePanel gp) {
        this.gp = gp;
        this.dialogueStartTime = System.currentTimeMillis();
        this.gp.trapRoomLevel1.monster.speak();
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (inDialogue && currentTime - dialogueStartTime > 2000) { // 2 seconds
            inDialogue = false;
            gp.setGameState(gp.playStateObj);
            gp.gameState = gp.playState;
            gp.syncGameState();
        }
        if (!inDialogue) {
            gp.trapRoomLevel1.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!inDialogue) {
            gp.playStateObj.draw(g2); // fallback to play state drawing
        } else {
            gp.dialogueStateObj.draw(g2);
        }
    }
}
