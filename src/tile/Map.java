package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TiledMapViewer {

    GamePanel gp;
    BufferedImage worldMap[];
    public boolean miniMapOn = false;

    public Map(String tmxFilePath, GamePanel gp) {
        super(tmxFilePath, gp);
        this.gp = gp;
        createMiniMap();
    }

    public void createMiniMap() {

        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;

        for (int i = 0; i < gp.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)  worldMap[i].createGraphics();

            drawFullMap(g2);
        }
    }

    public void drawFullMapScreen(Graphics2D g2) {

        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // draw map
        int width = 600;
        int height = 600 ;
        int x = gp.screenWidth/2 - width/2; // middle of the screen
        int y = gp.screenHeight/2 - height/2;

        g2.drawImage(worldMap[gp.currentLevel], x, y, width, height, null );

        // draw player on the map
        double scale = (double) (gp.tileSize * gp.maxWorldCol) / width;
        int playerX = (int) (x + gp.player.worldX/scale);
        int playerY = (int) ( y + gp.player.worldY/scale);
        int playerSize = (int) (gp.tileSize/scale);

        g2.drawImage(gp.player.getCurrentSprite(),playerX, playerY, playerSize, playerSize, null);

        // hint
        g2.setFont(gp.ui.arial_20.deriveFont(32f));
        g2.setColor(Color.BLACK);
        g2.drawString("Press M to close", 750, 550);

    }

    public void drawMiniMap( Graphics2D g2 ) {

        if ( miniMapOn == true ) {
            // draw map
            int width = 200;
            int height = 200 ;
            int x = gp.screenWidth2 - width - 5;
            int y = 5;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.drawImage(worldMap[gp.currentLevel], x, y, width, height, null);

            // draw player on the map
            double scale = (double) (gp.tileSize * gp.maxWorldCol) / width;
            int playerX = (int) (x + gp.player.worldX/scale);
            int playerY = (int) ( y + gp.player.worldY/scale);
            int playerSize = (int) (gp.tileSize/4);

            g2.drawImage(gp.player.getCurrentSprite(),playerX - 8, playerY - 8, playerSize, playerSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));

        }
    }

}
