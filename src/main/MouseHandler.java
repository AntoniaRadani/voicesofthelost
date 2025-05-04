package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseHandler implements MouseListener, MouseMotionListener {

    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
        gp.addMouseMotionListener(this);
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
            int settingsX = 300, settingsY = 390;

            // dimensions of the buttons
            int btnWidth = 200, btnHeight = 50;

            // Start Game (when the start button is clicked)
            if (mx >= startX && mx <= startX + btnWidth &&
                    my >= startY && my <= startY + btnHeight) {
                //gp.gameState = 1;
                gp.menuOption = 1;
            }

            // Exit (when the exit button is clicked)
            if (mx >= exitX && mx <= exitX + btnWidth &&
                    my >= exitY && my <= exitY + btnHeight) {
               // System.exit(0);
                gp.menuOption = 2;
            }

            // Settings (when the settings button is clicked)
            if(mx >= settingsX && mx <= settingsX + btnWidth &&
                my >= settingsY && my <= settingsY + btnHeight){
                gp.menuOption = 3;
            }
            // pt inchidere meniu setari
            if (mx >= 550 && mx <= 580 && my >= 50 && my <= 80 && gp.menuOption == 3) {
                gp.menuOption = 0;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gp.menuOption == 3) {
            int mx = e.getX();
            int my = e.getY();

            // zona sliderului
            if (mx >= 200 && mx <= 400 && my >= 130 && my <= 150) {
                gp.draggingVolume = true;
                gp.volumeLevel = Math.max(0, Math.min(100, (mx - 200) * 100 / 200));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.draggingVolume = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (gp.menuOption == 3 && gp.draggingVolume) {
            int mx = e.getX();
            gp.volumeLevel = Math.max(0, Math.min(100, (mx - 200) * 100 / 200));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
