package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseHandler implements MouseListener, MouseMotionListener {

    GamePanel gp;

    // buttons positions
    int startX, startY;
    int exitX, exitY;
    int settingsX, settingsY;
    int xX, xY;
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


    private int scaledMouseX(MouseEvent e) {
        int panelWidth = gp.getWidth();
        int panelHeight = gp.getHeight();
        double scaleX = (double) panelWidth / gp.screenWidth;
        double scaleY = (double) panelHeight / gp.screenHeight;
        double scale = Math.min(scaleX, scaleY); // păstrează proporția
        int drawWidth = (int)(gp.screenWidth * scale);
        int drawX = (panelWidth - drawWidth) / 2;

        return (int)((e.getX() - drawX) / scale);
    }

    private int scaledMouseY(MouseEvent e) {
        int panelWidth = gp.getWidth();
        int panelHeight = gp.getHeight();
        double scaleX = (double) panelWidth / gp.screenWidth;
        double scaleY = (double) panelHeight / gp.screenHeight;
        double scale = Math.min(scaleX, scaleY); // păstrează proporția
        int drawHeight = (int)(gp.screenHeight * scale);
        int drawY = (panelHeight - drawHeight) / 2;
        return (int)((e.getY() - drawY) / scale);
    }

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
        centerX = (gp.screenWidth - 200) / 2; // 284
        centerY = (gp.screenHeight - 20) / 2; //278
        startX = centerX;
        startY = centerY - 40;
        exitX = centerX;
        exitY = centerY;
        settingsX = centerX;
        settingsY = centerY + 40;
        xX = 2 * centerX + 130;
        xY = centerY / 10;
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
        int mx = scaledMouseX(e);
        int my = scaledMouseY(e);
        if (gp.gameState == gp.menuState && gp.menuOption != 3) {

            // Start Game (when the start button is clicked)
            if (mx >= startX && mx <= startX + btnWidth &&
                    my >= startY && my <= startY + btnHeight) {
                gp.menuOption = 1;
            }

            // Exit (when the exit button is clicked)
            if (mx >= exitX && mx <= exitX + btnWidth &&
                my >= exitY && my <= exitY + btnHeight)
                gp.menuOption = 2;

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
            else if(gp.gameState == gp.pauseState){
            if (mx >= exitX && mx <= exitX + btnWidth &&
                    my >= exitY && my <= exitY + btnWidth) {
                gp.gameState = gp.menuState;
                gp.menuOption = 0;
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gp.menuOption == 3) {
            int mx = scaledMouseX(e);
            int my = scaledMouseY(e);

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
            int mx = scaledMouseX(e);
            gp.volumeLevel = Math.max(0, Math.min(100, (mx - 200) * 100 / 200));
        }

        if(gp.menuOption == 3 && gp.draggingSound){
            int mx = scaledMouseX(e);
            gp.soundLevel = Math.max(0, Math.min(100, (mx - 200) * 100 / 200));
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int mx = scaledMouseX(e);
        int my = scaledMouseY(e);

        if (gp.gameState == gp.menuState) {
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

        if(gp.gameState == 2){
            if (mx >= exitX && mx <= exitX + btnWidth &&
                    my >= exitY && my <= exitY + btnHeight) {
                gp.hoverQuit = true;
            } else {
                gp.hoverQuit = false;
            }
        }


    }
}
