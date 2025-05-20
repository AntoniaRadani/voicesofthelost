package entity;

import jdk.jshell.execution.Util;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Chest2;
import object.OBJ_Key;
import object.OBJ_Shield;
import object.SuperObject;
import tile.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Player extends Entity{

    KeyHandler keyH;
    UtilityTool uTool = new UtilityTool();

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;
    public int hasApple = 0;
    boolean openChest = false;

    int counter2 = 0;

    // invetory
    public ArrayList<SuperObject> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

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
        setItems();
        getPlayerImage();

    }

    public  void setDefaultValues(){
        worldX = gp.tileSize * 24; // players position in the world map
        worldY = gp.tileSize * 49; // where the player starts the game   gp.tileSize * coordonata( linis/coloana din matrice)
        speed = 4;
        direction = "down";

        // player status

        maxLife = 6;
        life = maxLife;
        level = 1;
        strength = 1;
        dexterity = 1;
        cards = 0;
        currentWeapon = new OBJ_Shield(gp);
        currentShield = new OBJ_Shield(gp);
        attack = getAttack(); // total attack value is decided by strength and weapon
        defense = getDefense(); // total defense value is decided by dexterity and shield

    }

    public void setItems(){
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }

    public int getAttack(){
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
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
            // verificare coliziune object
//            Vector2f obj = new Vector2f();
//            int objIndex = gp.cChecker.checkObject(this, true, obj);
//            pickUpObject(objIndex, obj);

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

            if(inventory.size() != maxInventorySize) {

                if(!Objects.equals(objectName, "Chest")) {
                    inventory.add(gp.obj[i]);
                    gp.playSE(1); // de cautat un sunet
                }
                else{
                    if(hasKey > 0)
                        for(int j = 0; j < inventory.size(); j++)
                            if(inventory.get(j).name == "Key") {
                                hasKey--;
                                inventory.remove(j);
                                gp.obj[i] = new OBJ_Chest2(gp);
                                break;
                            }
                    else{
                        gp.ui.showMessage("YOU NEED A KEY");
                    }
                }

                switch (objectName) {
                    case "Key":
                        hasKey++;
                        gp.playSE(2);
                        gp.obj[i] = null;
                        gp.ui.showMessage("You got a key!");
                        break;
                    case "Apple":
                        hasApple++;
                        gp.playSE(2);
                        gp.obj[i] = null;
                        break;
                    case "Chest":
                        if (hasKey > 0) {
                            gp.playSE(1);
                            gp.obj[i] = null;
                            hasKey--;
                            gp.ui.showMessage("You opened a chest!");
                        } else {
                            gp.ui.showMessage("You need a key!");
                        }
                    case "Door":
                        if (hasKey > 0) {
                            gp.playSE(1);
                            gp.obj[i] = null;
                            hasKey--;
                            gp.ui.showMessage("You opened a door!");
                        } else {
                            gp.ui.showMessage("You need a key!");
                        }
                }
            }
            else{
                gp.ui.showMessage("Inventory full");
            }
        }
    }

    public BufferedImage setup(String imageName){
        UtilityTool tool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/" + imageName +".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("NOT FOUND");
        }

        return image;
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.black);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = getCurrentSprite();

        g2.drawImage(image, screenX, screenY, gp.tileSize + 20, gp.tileSize + 20, null);


    }

    public void interactNPC(int index ) {
        if ( index != -1 ) {
            if(gp.keyH.fPressed) {
                System.out.println(" you are hitting an npc ");
                gp.gameState = gp.dialogueState;
                gp.npc[index].speak();
            }
        }
        gp.keyH.fPressed = false;
    }
}
