package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity{

    KeyHandler keyH;


    public final int screenX;
    public final int screenY;

    int hasKey = 0;
    int hasApple = 0;
    boolean openChest = false;

    int counter2 = 0;

    // constructor
    public Player(GamePanel gp, KeyHandler keyH){

        super(gp); // calling the constructor of the superclass ( entity )
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;  // the center of the screen
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        // making the "body" of the player smaller for better collision handling
        solidArea = new Rectangle(12, 24, 24, 24); // posibil sa schimbam in frunctie de sprite dr zenn

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();

    }

    public  void setDefaultValues(){
        worldX = gp.tileSize * 24; // players position in the world map
        worldY = gp.tileSize * 49; // where the player starts the game   gp.tileSize * coordonata( linis/coloana din matrice)
        speed = 4;
        direction = "down";
    }


    public void getPlayerImage() {

            animationSet.loadSeparate("player", Entity.DIRECTIONS, "player", 8); // 4 cadre per direcție


    }



    public void update() {

        // Limite pe worldX și worldY
        if (worldX < 0) {
            worldX = 0;
        }
        if (worldY < 0) {
            worldY = 0;
        }
        if (worldX > gp.worldWidth - gp.tileSize) {
            worldX = gp.worldWidth - gp.tileSize;
        }
        if (worldY > gp.worldHeight - gp.tileSize) {
            worldY = gp.worldHeight - gp.tileSize;
        }

        // Actualizarea coordonatelor lumii în funcție de direcția de mișcare
        if (keyH.downPressed == true || keyH.upPressed == true || keyH.rightPressed == true || keyH.leftPressed == true ) {

            if (keyH.upPressed) {
                direction = "up";
              //  worldY -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
               // worldY += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
               // worldX -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                //worldX += speed;
            }

            // verificare coliziune
            this.collisionOn = false;
            gp.cChecker.checkTile(this);

            // verificare coliziune object v2
            int objIndex = gp.cChecker.checkObject2(this, true);
            pickUpObject2(objIndex);

            // npc collision verificare

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc );
            interactNPC(npcIndex);

            // daca collisionOn este false, player se misca

            if(!this.collisionOn) {
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

            updateAnimation();

        }

        // gp.tiledMapViewer.updateCamera(worldX, worldY, gp.screenWidth, gp.screenHeight);

    }

    public void pickUpObject(int index, Vector2f ob) {

        if ( index != -1 ) { // adica am atins obiecte
            gp.tiledMapViewer.mapData[3][(int)ob.x][(int)ob.y] = 0;
        }
    }

    public void pickUpObject2(int i){
        if(i != 999){
            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Key":
                    hasKey++;
                    gp.playSE(2);
                    gp.obj[i] = null;
                    System.out.println("Key : " + hasKey);
                    break;
                case "Apple":
                    hasApple++;
                    gp.playSE(2);
                    gp.obj[i] = null;
                    break;
                case "Chest":
                    if(hasKey > 0){
                        gp.playSE(1);
                        gp.obj[i] = null;
                        hasKey--;
                        System.out.println("Key : " + hasKey);
                    }
            }
        }
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.black);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = getCurrentSprite();

        g2.drawImage(image, screenX, screenY, gp.tileSize + 20, gp.tileSize + 20, null);


    }

    public void interactNPC(int index ) {
        if ( index != -1 ) {
            System.out.println(" you are hitting an npc ");
        }
    }
}
