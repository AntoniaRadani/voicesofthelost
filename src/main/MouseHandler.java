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
        // getting the position of the mouse
        int mx = e.getX();
        int my = e.getY();
        if (gp.gameState == 0 && gp.menuOption != 3) {

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

                gp.menuOption = 2;
            }

            // Settings (when the settings button is clicked)

            if (mx >= settingsX && mx <= settingsX + btnWidth &&
                    my >= settingsY && my <= settingsY + btnHeight) {
                gp.menuOption = 3;

            }
        }
            else if(gp.menuOption == 3) {
                // pt inchidere meniu setari
                if (mx >= 700 && mx <= 730 && my >= 50 && my <= 80) {
                    gp.menuOption = 0;
                }

                if (mx >= 284 && mx <= 284+40 && my >= 378 && my <= 378+40) {
                    gp.isVolumeMuted = !gp.isVolumeMuted;  // change state
                }

                if (mx >= 384 && mx <= 384+40 && my >= 378 && my <= 378+40) {
                    gp.isSoundMuted = !gp.isSoundMuted;
                }
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gp.menuOption == 3) {
            int mx = e.getX();
            int my = e.getY();


            int sliderWidth = 200;
            int sliderHeight = 20;
            int centerX = (gp.screenWidth - sliderWidth) / 2;
            int centerY = (gp.screenHeight - sliderHeight) / 2;

            // zona sliderului volum
            int volumeSliderY = centerY - 40;
            if (mx >= centerX && mx <= centerX + sliderWidth &&
                    my >= volumeSliderY && my <= volumeSliderY + sliderHeight) {
                gp.draggingVolume = true;
                gp.draggingSound = false;
                gp.volumeLevel = Math.max(0, Math.min(100, (mx - centerX) * 100 / sliderWidth));
            }

            // zona sliderului sound
            int soundSliderY = centerY + 40;
            if (mx >= centerX && mx <= centerX + sliderWidth &&
                    my >= soundSliderY && my <= soundSliderY + sliderHeight) {
                gp.draggingSound = true;
                gp.draggingVolume = false;
                gp.soundLevel = Math.max(0, Math.min(100, (mx - centerX) * 100 / sliderWidth));

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

        if(gp.menuOption == 3 && gp.draggingSound){
            int mx = e.getX();
            gp.soundLevel = Math.max(0, Math.min(100, (mx - 200) * 100 / 200));
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        if (gp.gameState == 0) {
            // Hover pentru Start
            if (mx >= 300 && mx <= 500 && my >= 250 && my <= 300) {
                gp.hoverStart = true;
            } else {
                gp.hoverStart = false;
            }

            // Hover pentru Quit
            if (mx >= 300 && mx <= 500 && my >= 320 && my <= 370) {
                gp.hoverQuit = true;
            } else {
                gp.hoverQuit = false;
            }

            // Hover pentru Settings
            if (mx >= 300 && mx <= 500 && my >= 390 && my <= 440) {
                gp.hoverSettings = true;
            } else {
                gp.hoverSettings = false;
            }
        }

        if (gp.menuOption == 3) {
            if (mx >= 700 && mx <= 730 && my >= 50 && my <= 80) {
                gp.hoverExitButton = true;
            } else {
                gp.hoverExitButton = false;
            }


            int sliderWidth = 200;
            int sliderHeight = 20;
            int centerX = (gp.screenWidth - sliderWidth) / 2;
            int centerY = (gp.screenHeight - sliderHeight) / 2;

            // hover slider volume
            int volumeSliderY = centerY - 40;
            if (mx >= centerX && mx <= centerX + sliderWidth &&
                    my >= volumeSliderY && my <= volumeSliderY + sliderHeight) {
                gp.hoverVolumeSlider = true; // Mouse-ul este deasupra slider-ului de volum
            } else {
                gp.hoverVolumeSlider = false;
            }

            // hover slider sound

            int soundSliderY = centerY + 40;
            if (mx >= centerX && mx <= centerX + sliderWidth &&
                    my >= soundSliderY && my <= soundSliderY + sliderHeight) {
                gp.hoverSoundSlider = true; // Mouse-ul este deasupra slider-ului de sound
            } else {
                gp.hoverSoundSlider = false;
            }

            // Hover pe butonul mute volume
            if (mx >= 284 && mx <= 284+40 && my >= 378 && my <= 378+40) {
                gp.hoverMuteVolume = true;
            } else {
                gp.hoverMuteVolume = false;
            }

            // Hover pe butonul mute sound
            if (mx >= 384 && mx <= 384+40 && my >= 378 && my <= 378+40) {
                gp.hoverMuteSound = true;
            } else {
                gp.hoverMuteSound = false;
            }
        }

    }
}
