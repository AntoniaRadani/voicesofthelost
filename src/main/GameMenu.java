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

    public int btnWidth = 200, btnHeight = 50;

    BufferedImage background;
    BufferedImage start;
    BufferedImage quit;
    BufferedImage settings;

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
    }


    public void draw(Graphics2D g2) {

        // Fundal meniu
   //     g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);

//        g2.setColor(Color.BLACK);
//        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Buton "Start Game"
        g2.drawImage(start, startX, startY, btnWidth, btnHeight, null);

        // Buton "Exit"
        g2.drawImage(quit, exitX, exitY, btnWidth, btnHeight, null);

        g2.drawImage(settings, settingsX, settingsY, btnWidth, btnHeight, null);
    }

}
