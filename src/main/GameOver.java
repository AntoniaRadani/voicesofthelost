package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameOver {

    GamePanel gp;

    int retryX, retryY;
    int quitX, quitY;
    int btnWidth = 200, btnHeight = 50;
    int centerX, centerY;

    BufferedImage quit;
    BufferedImage retry;

    public GameOver(GamePanel gp) {
        this.gp = gp;

        centerX = (gp.screenWidth  - 200) / 2; // 284
        centerY = (gp.screenHeight - 20) / 2; //278
        retryX = centerX;
        retryY = centerY - 60;
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
        g2.drawString("GAME OVER", centerX, centerY - 100);

        // restart button
        if (gp.hoverStart) {
            g2.drawImage(retry, retryX - 10, retryY - 5, btnWidth + 20, btnHeight + 10, null);
        } else {
            g2.drawImage(retry, retryX, retryY, btnWidth, btnHeight, null);
        }

        // QUIT Button
        if (gp.hoverQuit) {
            g2.drawImage(quit, quitX - 10, quitY - 5, btnWidth + 20, btnHeight + 10, null);
        } else {
            g2.drawImage(quit, quitX, quitY, btnWidth, btnHeight, null);
        }

    }

    public void update(){
        if(gp.overOption == 0)
            gp.gameState = gp.gameOverState;
        if(gp.overOption == 1)
            gp.gameState = gp.playState;
        if(gp.overOption == 2)
            gp.gameState = gp.menuState;
    }
}
