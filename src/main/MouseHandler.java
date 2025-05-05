package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseHandler implements MouseListener, MouseMotionListener {

    GamePanel gp;

    // buttons positions
    int startX = 300, startY = 250;
    int exitX = 300, exitY = 320;
    int settingsX = 300, settingsY = 390;
    int xX = 700, xY = 50;
    int smallBtnWidth = 40, smallBtnHeight = 40;
    int btnWidth = 200, btnHeight = 50;
    // dimensiuni slider
    int sliderWidth = 200;
    int sliderHeight = 20;
    // centrul ecranului
    int centerX;  // 284 asta e si volumeSliderX
    int centerY; //278
    // slider
    int volumeSliderX;
    int volumeSliderY;
    int soundSliderX;
    int soundSliderY;
    // butoane mute
    int muteVolumeX, muteVolumeY;
    int muteSoundX, muteSoundY;


    public MouseHandler(GamePanel gp) {
        this.gp = gp;
        centerX = (gp.screenWidth - sliderWidth) / 2; // 284 asta e si volumeSliderX
        centerY = (gp.screenHeight - sliderHeight) / 2; //278
        centerX = (gp.screenWidth - sliderWidth) / 2; // 284 asta e si volumeSliderX
        centerY = (gp.screenHeight - sliderHeight) / 2; //278
        volumeSliderX = centerX;
        volumeSliderY = centerY - 40;
        soundSliderX = centerX;
        soundSliderY = centerY + 40;
        // butoane mute
        muteVolumeX = centerX;
        muteVolumeY = centerY + 100;
        muteSoundX = centerX + 100;
        muteSoundY = centerY + 100;
        gp.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // getting the position of the mouse
        int mx = e.getX();
        int my = e.getY();
        if (gp.gameState == 0 && gp.menuOption != 3) {

            // Start Game (when the start button is clicked)
            if (mx >= startX && mx <= startX + btnWidth &&
                    my >= startY && my <= startY + btnHeight) {
                //gp.gameState = 1;
                gp.menuOption = 1;
            }

            // Exit (when the exit button is clicked)
            if (mx >= exitX && mx <= exitX + btnWidth &&
                    my >= exitY && my <= exitY + btnHeight) {
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
                if (mx >= xX && mx <= xX + smallBtnWidth && my >= xY && my <= xY + smallBtnHeight) {
                    gp.menuOption = 0;
                }

                if (mx >= muteVolumeX && mx <= muteVolumeX + smallBtnWidth &&
                        my >= muteVolumeY && my <= muteVolumeY + smallBtnHeight) {
                    gp.isVolumeMuted = !gp.isVolumeMuted;  // change state
                    if (gp.isVolumeMuted) {
                        gp.previousVolumeLevel = gp.volumeLevel;  // save current level
                        gp.volumeLevel = 0;                    // mute
                    } else {
                        gp.volumeLevel = gp.previousVolumeLevel;  // restore
                    }
                }

                if (mx >= muteSoundX && mx <= muteSoundX + smallBtnWidth &&
                        my >= muteSoundY && my <= muteSoundY + smallBtnHeight) {
                    gp.isSoundMuted = !gp.isSoundMuted;
                    if (gp.isSoundMuted) {
                        gp.previousSoundLevel = gp.soundLevel;
                        gp.soundLevel = 0;
                    } else {
                        gp.soundLevel = gp.previousSoundLevel;
                    }
                }
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gp.menuOption == 3) {
            int mx = e.getX();
            int my = e.getY();

            // zona sliderului volum
            if (mx >= volumeSliderX && mx <= volumeSliderX + sliderWidth &&
                    my >= volumeSliderY && my <= volumeSliderY + sliderHeight) {
                gp.draggingVolume = true;
                gp.draggingSound = false;
                gp.volumeLevel = Math.max(0, Math.min(100, (mx - centerX) * 100 / sliderWidth));
            }

            // zona sliderului sound
            if (mx >= soundSliderX && mx <= soundSliderX + sliderWidth &&
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
            if (mx >= startX && mx <= startX + btnWidth &&
                    my >= startY && my <= startY + btnHeight) {
                gp.hoverStart = true;
            } else {
                gp.hoverStart = false;
            }

            // Hover pentru Quit
            if (mx >= exitX && mx <= exitX + btnWidth &&
                    my >= exitY && my <= exitY + btnHeight) {
                gp.hoverQuit = true;
            } else {
                gp.hoverQuit = false;
            }

            // Hover pentru Settings
            if (mx >= settingsX && mx <= settingsX + btnWidth &&
                    my >= settingsY && my <= settingsY + btnHeight) {
                gp.hoverSettings = true;
            } else {
                gp.hoverSettings = false;
            }
        }

        if (gp.menuOption == 3) {
            if (mx >= xX && mx <= xX + smallBtnWidth && my >= xY && my <= xY + smallBtnHeight) {
                gp.hoverExitButton = true;
            } else {
                gp.hoverExitButton = false;
            }

            // hover slider volume
            if (mx >= volumeSliderX && mx <= volumeSliderX + sliderWidth &&
                    my >= volumeSliderY && my <= volumeSliderY + sliderHeight) {
                gp.hoverVolumeSlider = true; // Mouse-ul este deasupra slider-ului de volum
            } else {
                gp.hoverVolumeSlider = false;
            }

            // hover slider sound
            if (mx >= soundSliderX && mx <= soundSliderX + sliderWidth &&
                    my >= soundSliderY && my <= soundSliderY + sliderHeight) {
                gp.hoverSoundSlider = true; // Mouse-ul este deasupra slider-ului de sound
            } else {
                gp.hoverSoundSlider = false;
            }

            // Hover pe butonul mute volume
            if (mx >= muteVolumeX && mx <= muteVolumeX + smallBtnWidth &&
                    my >= muteVolumeY && my <= muteVolumeY + smallBtnHeight) {
                gp.hoverMuteVolume = true;
            } else {
                gp.hoverMuteVolume = false;
            }

            // Hover pe butonul mute sound
            if (mx >= muteSoundX && mx <= muteSoundX + smallBtnWidth &&
                    my >= muteSoundY  && my <= muteSoundY + smallBtnHeight) {
                gp.hoverMuteSound = true;
            } else {
                gp.hoverMuteSound = false;
            }
        }
    }
}
