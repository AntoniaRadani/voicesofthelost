package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int counter2 = 0;

    // constructor
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;  // the center of the screen
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        // making the "body" of the player smaller for better collision handling
        solidArea = new Rectangle(8, 40, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public  void setDefaultValues(){
        worldX = gp.tileSize * 23; // players position in the world map
        worldY = gp.tileSize * 21; // where the player starts the game   gp.tileSize * coordonata( linis/coloana din matrice)
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/boy_right_2.png")));

        }catch(IOException e){
            System.err.println("ERROR: Image not found");
            e.printStackTrace();
        }
    }

    public void update() {
        // Actualizarea coordonatelor lumii în funcție de direcția de mișcare
        if (keyH.downPressed == true || keyH.upPressed == true || keyH.rightPressed == true || keyH.leftPressed == true ) {

            if (keyH.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                worldX += speed;
            }

            spriteCounter++;

            if ( spriteCounter > 12 ) {
                if ( spriteNum == 1 ) {
                    spriteNum = 2;
                }
                else if ( spriteNum == 2 ) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }


     /*   collisionOn = false;  // Resetăm flag-ul de coliziune

        if(!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed; // Mișcare în sus
                    break;
                case "down":
                    worldY += speed; // Mișcare în jos
                    break;
                case "left":
                    worldX -= speed; // Mișcare la stânga
                    break;
                case "right":
                    worldX += speed; // Mișcare la dreapta
                    break;
            }
        }

        gp.tiledMapViewer.updateCamera(worldX, worldY, gp.screenWidth, gp.screenHeight);

      */
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.black);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if ( spriteNum == 1 ) {
                    image = up1;
                }
                if ( spriteNum == 2 ) {
                    image = up2;
                }
                break;
            case "down":
                if ( spriteNum == 1 ) {
                    image = down1;
                }
                if ( spriteNum == 2 ) {
                    image = down2;
                }
                break;
            case "left":
                if ( spriteNum == 1 ) {
                    image = left1;
                }
                if ( spriteNum == 2 ) {
                    image = left2;
                }
                break;
            case "right":
                if ( spriteNum == 1 ) {
                    image = right1;
                }
                if ( spriteNum == 2 ) {
                    image = right2;
                }
                break;
        };

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
