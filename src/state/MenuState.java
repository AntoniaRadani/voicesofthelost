package state;

import main.GamePanel;
import java.awt.Graphics2D;

public class MenuState implements GameState {
    private final GamePanel gp;

    public MenuState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {
        gp.menu.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        gp.menu.draw(g2);
    }
}
