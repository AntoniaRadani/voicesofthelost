package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GamePause {

    GamePanel gp;

    int centerX, centerY;
    int btnWidth = 200, btnHeight = 50;
    int quitX, quitY;

    BufferedImage quit;

    public GamePause(GamePanel gp){
        this.gp = gp;

        centerX = (gp.screenWidth - 200) / 2; // 284
        centerY = (gp.screenHeight - 20) / 2; //278

        quitX = centerX;
        quitY = centerY;

        try {
            quit = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/menu/Quit1.png")));
        }catch (IOException e){
            System.err.println("ERROR: Image not found");
        }

    }

    public void update(){
        if (gp.pauseOption == 0) {
            gp.gameState = gp.pauseState;
            gp.syncGameState();
        }
        if (gp.menuOption == 1) {
            gp.pauseOption = 1;
        }
    }

    public void draw(Graphics2D g2){
        // fundal paused
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g2.drawString("PAUSED", centerX + 37, centerY - 30);

        if (gp.hoverQuit) {
            g2.drawImage(quit, quitX - 10, quitY - 5, btnWidth + 20, btnHeight + 10, null);
        } else {
            g2.drawImage(quit, quitX, quitY, btnWidth, btnHeight, null);
        }
    }
}
