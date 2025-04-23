package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseHandler implements MouseListener {

    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gp.gameState == 0) {
            // getting the position of the mouse
            int mx = e.getX();
            int my = e.getY();

            // the positions of the buttons
            int startX = 300, startY = 250;
            int exitX = 300, exitY = 320;

            // dimensions of the buttons
            int btnWidth = 200, btnHeight = 50;

            // Start Game (when the start button is clicked)
            if (mx >= startX && mx <= startX + btnWidth &&
                    my >= startY && my <= startY + btnHeight) {
                gp.gameState = 1;
            }

            // Exit (when the exit button is clicked)
            if (mx >= exitX && mx <= exitX + btnWidth &&
                    my >= exitY && my <= exitY + btnHeight) {
                System.exit(0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
