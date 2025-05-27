package game2;

import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {

    int id;
    int yVelocity;
    int speed = 10;
    PlayerController controller;

    public Paddle(int x, int y, int width, int height, int id, PlayerController controller) {
        super(x, y, width, height);
        this.id = id;
        this.controller = controller;

        if (controller instanceof HumanController) {
            ((HumanController) controller).setPaddle(this);
        }
    }

    public void move() {
        y += yVelocity;
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void update(Ball ball) {
        controller.update(this, ball);
    }

    public void keyPressed(KeyEvent e) {
        controller.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        controller.keyReleased(e);
    }

    public void draw(Graphics g) {
        g.setColor(id == 1 ? Color.blue : Color.red);
        g.fillRect(x, y, width, height);
    }
}
