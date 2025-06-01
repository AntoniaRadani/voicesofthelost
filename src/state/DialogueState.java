package state;

import main.GamePanel;
import java.awt.Graphics2D;

public class DialogueState implements GameState {
    private final GamePanel gp;

    public DialogueState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {
        // Nu ai nevoie de update logic aici, doar UI
    }

    @Override
    public void draw(Graphics2D g2) {
        gp.tiledMapViewer.draw(g2);
        gp.player.draw(g2);
        gp.ui.draw(g2); // va desena dialogul
    }
}
