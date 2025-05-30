package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;
import tile.Vector2f;
import game1.MatchCards;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Player extends Entity{

    KeyHandler keyH;
    UtilityTool uTool = new UtilityTool();

    public int screenX;
    public int screenY;

    public int hasKey = 1;
    public int hasSpecialKey = 1; // number of special keys to open really important rooms
    public int hasApple = 0;
    public int hasHealthPotion = 0;
    public int hasCard = 0;
    public int hasLevelKey = 1;
    public boolean doorOpen1 = false;
    boolean openChest = false;

    public boolean isRunning = false;
    public long lastDamageTime = 0;
    public long lastRunTime = 0;
    private int runSeconds = 0;
    private boolean damagePenaltyApplied = false;

    int counter2 = 0;
    public int coin = 0;


    // invetory
   // public ArrayList<SuperObject> inventory = new ArrayList<>();
    //public final int maxInventorySize = 20;


    // constructor
    public Player(GamePanel gp, KeyHandler keyH){

        super(gp); // calling the constructor of the superclass ( entity )
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;  // the center of the screen
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        // making the "body" of the player smaller for better collision handling
        //solidArea = new Rectangle(12, 24, 24, 24); // posibil sa schimbam in frunctie de sprite dr zenn
        solidArea = new Rectangle(12, 24, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        setItems();
        getPlayerImage();
    }

    public  void setDefaultValues(){
       // worldX = gp.tileSize * 24; // players position in the world map
        //worldY = gp.tileSize * 49; // where the player starts the game   gp.tileSize * coordonata( linis/coloana din matrice)
        setPlayerStartPosition(1);
        speed = 5;
        direction = "down";

        // player status

        maxLife = 6;
        life = 6;
        maxStamina = 5;
        stamina = 5;
        level = 1;
        strength = 1;
        dexterity = 1;
        cards = 0;
        coin = 500;
        currentWeapon = new OBJ_Sword(gp);
        currentShield = new OBJ_Shield(gp);
        attack = getAttack(); // total attack value is decided by strength and weapon
        defense = getDefense(); // total defense value is decided by dexterity and shield
    }

    public void setItems(){
        // initializarea obiectelor din inventar cu armele basic
        inventory.add(currentWeapon);
        inventory.add(currentShield);

    }

    public void takeDamage(int amount) {
        life -= amount;
        if (life <= 0) {
            // logică pentru moartea playerului
        }
    }

    public void attackMonster(int i) {
        if (i != -1) {
            Monster monster = gp.monsters[gp.currentMap][i];

            int damage = this.getAttack() - monster.getDefense();
            if (damage < 1) damage = 1;

            monster.takeDamage(damage);
        }
    }

    public int getAttack(){
        if(currentWeapon != null)
            return attack = strength * currentWeapon.attack;
        else return strength;
    }

    public int getDefense(){
        if(currentShield != null)
            return defense = dexterity * currentShield.defense;
        else return dexterity;
    }

    public void getPlayerImage() {
            animationSet.loadSeparate("player", Entity.DIRECTIONS, "player", 8); // 4 cadre per direcție
    }

    public void update() {

       // System.out.println("STAMINA:" + stamina);

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

            // pentru fight uri
            if (gp.keyH.qPressed) {
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters[gp.currentMap]);
                attackMonster(monsterIndex);
                gp.keyH.enterPressed = false; // resetăm după atac
            }

            // pentru fugit
            if (gp.keyH.ctrlPressed && stamina > 0) {
                System.out.println("Is running");
                isRunning = true;
                speed = 10;
            } else {
                isRunning = false;
                speed = 5;
            }

            // pentru stamina

            // SCĂDERE STAMINA DACĂ FUGI
            if (isRunning) {
                if (System.currentTimeMillis() - lastRunTime >= 1000) { // o dată pe secundă
                    lastRunTime = System.currentTimeMillis();
                    runSeconds++;

                    if (runSeconds >= 30) {
                        runSeconds = 0;
                        if (stamina > 0) stamina--;
                        if (stamina <= 0) life--;
                    }

                }
            } else {
                runSeconds = 0;
            }

            // SCĂDERE STAMINA DACĂ AI LUAT DAMAGE ÎN ULTIMELE 30 SECUNDE
            if (System.currentTimeMillis() - lastDamageTime <= 30000) {
                if (!damagePenaltyApplied) {
                    stamina = Math.max(stamina - 1, 0);
                    damagePenaltyApplied = true;
                }
            } else {
                damagePenaltyApplied = false;
            }

            // verificare coliziune
            this.collisionOn = false;
            gp.cChecker.checkTile(this);

            // verificare coliziune object v2
            int objIndex = gp.cChecker.checkObject2(this, true);
                pickUpObject2(objIndex);

            // npc collision verificare

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc[gp.currentMap] );
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

            // verificam daca este pe pozitia unde trebuie trecut la next level

            int playerX = worldX / gp.tileSize;
            int playerY = worldY / gp.tileSize;

            System.out.println("playerX: " + playerX + " playerY: " + playerY );
            if (gp.currentLevel == 1 && playerX == 5 && playerY == 2 ) { // pt trecerea la nivelul 2
                gp.loadLevel(2);
            }
            if (gp.currentLevel == 2 && playerY == 1 && (playerX == 7 || playerX == 12) ) {
                gp.loadLevel(3);
            }
        }

        // gp.tiledMapViewer.updateCamera(worldX, worldY, gp.screenWidth, gp.screenHeight);

    }

    public void pickUpObject(int index, Vector2f ob) {

        if ( index != -1 ) { // adica am atins obiecte
            gp.tiledMapViewer.mapData[3][(int)ob.x][(int)ob.y] = 0;
        }
    }

    public void pickUpObject2(int i){
        int mapNum = gp.currentMap;
        if(i != 999){
            String objectName = gp.obj[mapNum][i].name;

            if(inventory.size() != maxInventorySize) {
                switch (objectName) {
                    case "Key":
                        System.out.println("AM LUAT CHEIE");
                        hasKey++;
                        gp.playSE(2);
                        inventory.add(gp.obj[mapNum][i]);
                        gp.obj[mapNum][i] = null;
                        gp.ui.showMessage("You got a key!");
                        break;
                    case "SpecialKey":
                        hasSpecialKey++;
                        gp.playSE(2);
                        inventory.add(gp.obj[mapNum][i]);
                        gp.obj[mapNum][i] = null;
                        gp.ui.showMessage("You got a special key!");
                        break;
                    case "Apple":
                        hasApple++;
                        gp.playSE(2);
                        inventory.add(gp.obj[mapNum][i]);
                        gp.obj[mapNum][i] = null;
                        break;
                    case "HealthPotion":
                        hasHealthPotion++;
                        gp.playSE(2);
                        inventory.add(gp.obj[mapNum][i]);
                        gp.obj[mapNum][i] = null;
                        break;
                    case "ChestLevel1":
                        if (hasKey > 0) {
                            gp.playSE(1);
                            for(int j = 0; j < inventory.size(); j++)
                                if(Objects.equals(inventory.get(j).name, "Key")) {
                                    inventory.remove(j);
                                    break;
                                }
                            inventory.add(new OBJ_HealthPotion(gp));
                            inventory.add(new OBJ_Sword(gp));
                            inventory.add(new OBJ_Shield(gp));
                            // cheia care deschide usa unde se afla butoaiele care trebuie numarate
                            inventory.add(new OBJ_SpecialKey(gp));
                            int worldX = gp.obj[mapNum][i].worldX;
                            int worldY = gp.obj[mapNum][i].worldY;
                            gp.obj[mapNum][i] = new OBJ_Chest2(gp);
                            gp.obj[mapNum][i].worldX = worldX;
                            gp.obj[mapNum][i].worldY = worldY;
                            hasKey--;
                            gp.ui.showMessage("You opened a chest!");
                        } else {
                            gp.ui.showMessage("You need a key!");
                        }
                        break;
                    case "ChestLevel2":
                        if (hasKey > 0) {
                            gp.playSE(1);
                            for(int j = 0; j < inventory.size(); j++)
                                if(Objects.equals(inventory.get(j).name, "Key")) {
                                    inventory.remove(j);
                                    break;
                                }
                            inventory.add(new OBJ_HealthPotion(gp));
                            inventory.add(new OBJ_RedSword(gp));
                            inventory.add(new OBJ_Shield(gp));
                            inventory.add(new OBJ_SpecialKey(gp));
                            int worldX = gp.obj[mapNum][i].worldX;
                            int worldY = gp.obj[mapNum][i].worldY;
                            gp.obj[mapNum][i] = new OBJ_Chest2(gp);
                            gp.obj[mapNum][i].worldX = worldX;
                            gp.obj[mapNum][i].worldY = worldY;
                            hasKey--;
                            gp.ui.showMessage("You opened a chest!");
                        } else {
                            gp.ui.showMessage("You need a key!");
                        }
                        break;
                    case "Chest":
                        if (hasKey > 0) {
                            gp.playSE(1);
                            for(int j = 0; j < inventory.size(); j++)
                                if(Objects.equals(inventory.get(j).name, "Key")) {
                                    inventory.remove(j);
                                    break;
                                }
                            inventory.add(new OBJ_Apple(gp));
                            // ceva de stamina idk
                            inventory.add(new OBJ_Apple(gp));
                            coin++;
                            int worldX = gp.obj[mapNum][i].worldX;
                            int worldY = gp.obj[mapNum][i].worldY;
                            gp.obj[mapNum][i] = new OBJ_Chest2(gp);
                            gp.obj[mapNum][i].worldX = worldX;
                            gp.obj[mapNum][i].worldY = worldY;
                            hasKey--;
                            gp.ui.showMessage("You opened a chest!");
                        } else {
                            gp.ui.showMessage("You need a key!");
                        }
                        break;
                    case "ClosedDoor":
                        // usa trap room
                        if (hasSpecialKey > 0) {
                            gp.playSE(1);
                            for(int j = 0; j < inventory.size(); j++)
                                if(Objects.equals(inventory.get(j).name, "Special")) {
                                    inventory.remove(j);
                                    break;
                                }
                            doorOpen1 = true;
                            gp.obj[mapNum][i] = null;
                            hasSpecialKey--;
                            gp.ui.showMessage("You opened the right door!");
                        } else {
                            collisionOn = true;
                            gp.ui.showMessage("You nseed a special key!");
                        }
                        break;
                    case "Door":
                        // usa basic in care poate sa gaseasca obiecte
                        System.out.println("HITTING DOORs");
                        if (hasKey > 0) {
                            gp.playSE(1);
                            for(int j = 0; j < inventory.size(); j++)
                                if(Objects.equals(inventory.get(j).name, "Key")) {
                                    inventory.remove(j);
                                    break;
                                }
                            gp.obj[mapNum][i] = null;
                            hasKey--;
                            gp.ui.showMessage("You opened a door...");
                        } else {
                            collisionOn = true;
                            gp.ui.showMessage("You need a key!");
                        }
                        break;
                    case "Table":
                        gp.playSE(2);
                        if(gp.currentMap == 0 && hasCard == 0)
                            new MatchCards(gp);
                        // facem masa sa dispara ca sa nu poata lua mai multe carti de la un singur nivel
                        // poate daca avem timp implementam si varianta in care ramane masa idk
                        break;
                    case "ClosedDoor2":
                        // usa catre nivelul urmator pentru care are nevoie de o cheie speciala
                        // o castiga in urma fightului din camera cu butoaie
                        if (hasLevelKey > 0) {
                            gp.playSE(1);
                            for(int j = 0; j < inventory.size(); j++)
                                if(Objects.equals(inventory.get(j).name, "LevelKey")) {
                                    inventory.remove(j);
                                    break;
                                }
                            gp.obj[mapNum][i] = null;
                            hasLevelKey--;
                            gp.ui.showMessage("You opened the right door!");
                        } else {
                            collisionOn = true;
                            gp.ui.showMessage("You need a special key!");
                        }
                }
            }
            else{
                gp.ui.showMessage("Inventory full");
            }
        }
    }

    public boolean isNearMonster(Monster monster, GamePanel gp) {
        if (monster == null) return false;

        Rectangle playerRect = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
        Rectangle monsterRect = new Rectangle(monster.getWorldX() + monster.solidArea.x, monster.getWorldY() + monster.solidArea.y,
                monster.solidArea.width, monster.solidArea.height);

        // extindem zona monsterului pentru a verifica "aproape"
        Rectangle interactionZone = new Rectangle(
                monsterRect.x - gp.tileSize,
                monsterRect.y - gp.tileSize,
                monsterRect.width + gp.tileSize * 2,
                monsterRect.height + gp.tileSize * 2);

        return playerRect.intersects(interactionZone);
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

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerslotCol, gp.ui.playerslotRow);
        if(itemIndex < inventory.size()){
            SuperObject selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword) {
                currentWeapon =  selectedItem;
                attack = getAttack();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }

    }

    public void interactNPC(int index ) {
        if ( index != -1 ) {
            if(gp.keyH.fPressed) {
                System.out.println(" you are hitting an npc ");
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][index].speak();
            }
        }
        gp.keyH.fPressed = false;
    }

    public void calculateScreenPosition() {
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
    }

    public void setPlayerStartPosition(int level) {
        switch (level) {
            case 1:
                worldX = 24 * gp.tileSize;
                worldY = 42 * gp.tileSize;
                break;
            case 2:
                worldX = 6 * gp.tileSize;
                worldY = 31 * gp.tileSize;
                break;
            case 3:
                worldX = 7 * gp.tileSize;
                worldY = 5 * gp.tileSize;
        }
        System.out.println("Player start position: " + worldX + ", " + worldY);

    }


}
