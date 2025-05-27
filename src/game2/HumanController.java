package game2;

import java.awt.event.KeyEvent;

public class HumanController implements PlayerController {

    private int upKey, downKey;

    public HumanController(int upKey, int downKey) {
        this.upKey = upKey;
        this.downKey = downKey;
    }

    @Override
    public void update(Paddle paddle, Ball ball) {
        // Mișcarea e controlată prin key events
        paddle.move();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == upKey) {
            paddle.setYDirection(-paddle.speed);
        } else if (e.getKeyCode() == downKey) {
            paddle.setYDirection(paddle.speed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == upKey || e.getKeyCode() == downKey) {
            paddle.setYDirection(0);
        }
    }

    private Paddle paddle; // atașăm paddle-ul când îl folosim

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
}
