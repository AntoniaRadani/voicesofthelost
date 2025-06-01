package main;

// pozitiile butoanelor
// startX, startY
// exitX, exitY
// settingsX, settingsY

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

//dimensiunile butoanelor
//btnWidth
//btnHeight
public class GameMenu {

    GamePanel gp;

    // buttons positions
//    int startX = 300, startY = 250;
//    int exitX = 300, exitY = 320;
//    int settingsX = 300, settingsY = 390;
//    int xX = 700, xY = 50;
    int startX, startY;
    int exitX, exitY;
    int settingsX, settingsY;
    int resumeX, resumeY;
    int xX, xY;
    int smallBtnWidth = 40, smallBtnHeight = 40;
    int btnWidth = 200, btnHeight = 50;
    // dimensiuni slider
    int sliderWidth = 200;
    int sliderHeight = 20;
    // centrul ecranului
    int centerX, centerY; // 284, 278
    // slider
    int volumeSliderX, volumeSliderY;
    int soundSliderX, soundSliderY;
    // butoane mute
    int muteVolumeX, muteVolumeY;
    int muteSoundX, muteSoundY;

    BufferedImage background;
    BufferedImage start;
    BufferedImage resume;
    BufferedImage quit;
    BufferedImage settings;
    BufferedImage x_btn;
    BufferedImage muteVolumeActive;    // verde
    BufferedImage muteVolumeMuted;     // negru
    BufferedImage muteSoundActive;     // verde
    BufferedImage muteSoundMuted;      // negru

    public GameMenu(GamePanel gp) {
        this.gp = gp;

        centerX = (gp.screenWidth  - 200) / 2; // 284
        centerY = (gp.screenHeight - 20) / 2; //278
        resumeX = centerX;
        resumeY = centerY - 120;
        startX = centerX;
        startY = centerY - 60;
        exitX = centerX;
        exitY = centerY;
        settingsX = centerX;
        settingsY = centerY + 60;
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

        // loading the images
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Background1.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try{
            resume = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Restart.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try {
            start = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Start1.png")));
        }catch(IOException e){
            System.err.println("ERROR: Image not found");
        }
        try {
            quit = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Quit1.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try {
            settings = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Settings1.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try{
            x_btn = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/X_btn.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try{
            muteVolumeActive = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/ActiveMusic.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try{
            muteVolumeMuted = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/InactiveMusic.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try{
            muteSoundActive = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/ActiveSound.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try{
            muteSoundMuted = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/InactiveSound.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
    }

    public void update(){

        if (gp.menuOption == 0) {
            gp.gameState = gp.menuState;
            gp.syncGameState();
        }
        if (gp.menuOption == 1) {
            gp.gameState = gp.playState;
            gp.syncGameState();
        }
        if (gp.menuOption == 2) {
            System.exit(0);
        }

    }

    public void draw(Graphics2D g2) {

        // Fundal meniu
        g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);

        if(gp.hoverResume){
            g2.drawImage(resume, resumeX - 10, resumeY - 5, btnWidth + 20, btnHeight + 10, null);
        }
        else{
            g2.drawImage(resume, resumeX, resumeY, btnWidth, btnHeight, null);
        }

        if (gp.hoverStart) {
            g2.drawImage(start, startX - 10, startY - 5, btnWidth + 20, btnHeight + 10, null);
        } else {
            g2.drawImage(start, startX, startY, btnWidth, btnHeight, null);
        }

        // QUIT Button
        if (gp.hoverQuit) {
            g2.drawImage(quit, exitX - 10, exitY - 5, btnWidth + 20, btnHeight + 10, null);
        } else {
            g2.drawImage(quit, exitX, exitY, btnWidth, btnHeight, null);
        }

        // SETTINGS Button
        if (gp.hoverSettings) {
            g2.drawImage(settings, settingsX - 10, settingsY - 5, btnWidth + 20, btnHeight + 10, null);
        } else {
            g2.drawImage(settings, settingsX, settingsY, btnWidth, btnHeight, null);
        }


        if (gp.menuOption == 3) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Times New Roman", Font.BOLD, 24));

            drawSettings(g2);
        }
    }

    public void drawSettings(Graphics2D g2){

        // volume slider
        g2.setColor(Color.white);
        g2.drawString("VOLUME", centerX, centerY - 50);
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(volumeSliderX, volumeSliderY, sliderWidth, sliderHeight);
        g2.setColor(Color.magenta);
        g2.fillRect(volumeSliderX, volumeSliderY, gp.volumeLevel * 2, 20);

        // sound slider

        g2.setColor(Color.white);
        g2.drawString("SOUND", centerX, centerY + 30);
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(soundSliderX, soundSliderY, sliderWidth, sliderHeight);
        g2.setColor(Color.magenta);
        g2.fillRect(soundSliderX, soundSliderY, gp.soundLevel * 2, 20);

        // Mute Volume Button
        if (gp.isVolumeMuted) {
            if (gp.hoverMuteVolume) {
                g2.drawImage(muteVolumeMuted, muteVolumeX - 5, muteVolumeY - 5, smallBtnWidth + 10, smallBtnHeight + 10, null); // mai mare la hover
            } else {
                g2.drawImage(muteVolumeMuted, muteVolumeX, muteVolumeY, smallBtnWidth, smallBtnHeight, null);
            }
        } else {
            if (gp.hoverMuteVolume) {
                g2.drawImage(muteVolumeActive, muteVolumeX - 5, muteVolumeY - 5, smallBtnWidth + 10, smallBtnHeight + 10, null); // mai mare la hover
            } else {
                g2.drawImage(muteVolumeActive, muteVolumeX, muteVolumeY, smallBtnWidth, smallBtnHeight, null);
            }
        }

        // Mute Sound Button
        if (gp.isSoundMuted) {
            if (gp.hoverMuteSound) {
                g2.drawImage(muteSoundMuted, muteSoundX - 5, muteSoundY - 5, smallBtnWidth + 10, smallBtnHeight + 10, null); // mai mare la hover
            } else {
                g2.drawImage(muteSoundMuted, muteSoundX, muteSoundY, smallBtnWidth, smallBtnHeight, null);
            }
        } else {
            if (gp.hoverMuteSound) {
                g2.drawImage(muteSoundActive, muteSoundX - 5, muteSoundY - 5, smallBtnWidth + 10, smallBtnHeight + 10, null); // mai mare la hover
            } else {
                g2.drawImage(muteSoundActive, muteSoundX, muteSoundY, smallBtnWidth, smallBtnHeight, null);
            }
        }

        // exit button

        if (gp.hoverExitButton) {
            g2.drawImage(x_btn, xX - 5, xY - 5, smallBtnWidth + 10, smallBtnHeight + 10, null);
        } else {
            g2.drawImage(x_btn, xX, xY, smallBtnWidth, smallBtnHeight, null);
        }

    }
}
