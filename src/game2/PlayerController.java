package game2;

import java.awt.event.KeyEvent;

public interface PlayerController {

    void update(Paddle paddle, Ball ball);
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
}
