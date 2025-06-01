package state;

import main.GamePanel;

import java.awt.Graphics2D;

public class PauseState implements GameState {
    private final GamePanel gp;

    public PauseState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {
        gp.pause.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        gp.pause.draw(g2);
    }
}
