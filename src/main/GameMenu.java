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

    public int startX = 300, startY = 250;
    public int exitX = 300, exitY = 320;
    public int settingsX = 300, settingsY = 390;
    public int xX = 700, xY = 50;
    public int btnWidth = 200, btnHeight = 50;

    BufferedImage background;
    BufferedImage start;
    BufferedImage quit;
    BufferedImage settings;
    BufferedImage x_btn;
    BufferedImage muteVolumeActive;    // verde
    BufferedImage muteVolumeMuted;     // negru
    BufferedImage muteSoundActive;     // verde
    BufferedImage muteSoundMuted;      // negru

    public GameMenu(GamePanel gp) {
        this.gp = gp;

        // loading the images
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Background1.png")));
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
       if(gp.menuOption == 0)
           gp.gameState = 0;
       if(gp.menuOption == 1)
           gp.gameState = 1;
       if(gp.menuOption == 2)
           System.exit(0);
       if(gp.menuOption == 3){
           // meniu setari
       }
    }

    public void draw(Graphics2D g2) {

        // Fundal meniu
        g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);

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

        // dimensiuni slider
        int sliderWidth = 200;
        int sliderHeight = 20;

        // centrul ecranului
        int centerX = (gp.screenWidth - sliderWidth) / 2; // 284
        int centerY = (gp.screenHeight - sliderHeight) / 2; //278

        // butoane mute
        int muteVolumeX = centerX, muteVolumeY = centerY + 100;
        int muteSoundX = centerX + 100, muteSoundY = centerY + 100;

        // volume slider

        g2.drawString("VOLUME", centerX, centerY - 50);
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(centerX, centerY - 40, sliderWidth, sliderHeight);
        g2.setColor(Color.magenta);
        g2.fillRect(centerX, centerY - 40, gp.volumeLevel * 2, 20);

        // sound slider

        g2.setColor(Color.white);
        g2.drawString("SOUND", centerX, centerY + 30);
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(centerX, centerY + 40, sliderWidth, sliderHeight);
        g2.setColor(Color.magenta);
        g2.fillRect(centerX, centerY + 40, gp.soundLevel * 2, 20);

        // Mute Volume Button
        if (gp.isVolumeMuted) {
            if (gp.hoverMuteVolume) {
                g2.drawImage(muteVolumeMuted, muteVolumeX - 5, muteVolumeY - 5, 50, 50, null); // mai mare la hover
            } else {
                g2.drawImage(muteVolumeMuted, muteVolumeX, muteVolumeY, 40, 40, null);
            }
        } else {
            if (gp.hoverMuteVolume) {
                g2.drawImage(muteVolumeActive, muteVolumeX - 5, muteVolumeY - 5, 50, 50, null); // mai mare la hover
            } else {
                g2.drawImage(muteVolumeActive, muteVolumeX, muteVolumeY, 40, 40, null);
            }
        }

        // Mute Sound Button
        if (gp.isSoundMuted) {
            if (gp.hoverMuteSound) {
                g2.drawImage(muteSoundMuted, muteSoundX - 5, muteSoundY - 5, 50, 50, null); // mai mare la hover
            } else {
                g2.drawImage(muteSoundMuted, muteSoundX, muteSoundY, 40, 40, null);
            }
        } else {
            if (gp.hoverMuteSound) {
                g2.drawImage(muteSoundActive, muteSoundX - 5, muteSoundY - 5, 50, 50, null); // mai mare la hover
            } else {
                g2.drawImage(muteSoundActive, muteSoundX, muteSoundY, 40, 40, null);
            }
        }

        // exit button

        if (gp.hoverExitButton) {
            g2.drawImage(x_btn, xX - 5, xY - 5, 40, 40, null);
        } else {
            g2.drawImage(x_btn, xX, xY, 30, 30, null);
        }

    }
}
