package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class WinGame {

    GamePanel gp;

    int quitX, quitY;
    int btnWidth = 200, btnHeight = 50;
    int centerX, centerY;

    BufferedImage quit;
    BufferedImage retry;

    public WinGame(GamePanel gp) {
        this.gp = gp;

        centerX = (gp.screenWidth  - 200) / 2; // 284
        centerY = (gp.screenHeight - 20) / 2; //278
        quitX = centerX;
        quitY = centerY;

        // loading the images
        try {
            retry = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Restart.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }
        try {
            quit = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Quit1.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }

    }

    public void draw(Graphics2D g2) {

        // Fundal meniu
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g2.drawString("YOU WON!", centerX + 2, centerY - 100);

        // QUIT Button
        if (gp.hoverQuit) {
            g2.drawImage(quit, quitX - 10, quitY - 5, btnWidth + 20, btnHeight + 10, null);
        } else {
            g2.drawImage(quit, quitX, quitY, btnWidth, btnHeight, null);
        }

    }

    public void update(){
        if(gp.winOption == 0)
            gp.gameState = gp.winState;
        if(gp.winOption == 1) {
            gp.gameState = gp.menuState;
            gp.menuOption = 0;
        }
    }
}
