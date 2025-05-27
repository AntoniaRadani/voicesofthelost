package game2;

import java.awt.event.KeyEvent;

public class AIController implements PlayerController {

    @Override
    public void update(Paddle paddle, Ball ball) {
        if (ball.y + ball.height / 2 < paddle.y + paddle.height / 2) {
            paddle.setYDirection(-paddle.speed);
        } else if (ball.y + ball.height / 2 > paddle.y + paddle.height / 2) {
            paddle.setYDirection(paddle.speed);
        } else {
            paddle.setYDirection(0);
        }
        paddle.move();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
