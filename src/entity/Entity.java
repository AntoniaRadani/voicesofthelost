package entity;

import main.GamePanel;
import object.SuperObject;
import tile.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;


public class Entity{

    GamePanel gp;
    public int worldX, worldY;

    public String direction;

    public int spriteCounter = 0;
    int spriteIndex = 0;

    // dialog
    String dialogues[] = new String[20];
    int dialogueIndex;

    // character attributes
    public String name;
    public BufferedImage image;
    public int speed;
    public int maxLife;
    public int life;
    public int maxStamina;
    public int stamina;
    public int level;
    public int attack;
    public int defense;
    public int cards;
    public int strength;
    public int dexterity;
    public SuperObject currentWeapon;
    public SuperObject currentShield;

    // type
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;

    // items attributes
    public BufferedImage down1;
    public int attackValue;
    public int defenseValue;
    public String description = "";


    // invetory
    public ArrayList<SuperObject> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public static final String[] DIRECTIONS = { "up", "down", "left", "right" };

    public AnimationSet animationSet = new AnimationSet(); // vector pt toate imaginile sprite urilor

    // pentru coliziuni
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    // pentru object interaction
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void updateAnimation() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteIndex = (spriteIndex + 1) % 8;
            spriteCounter = 0;
        }
    }

    public BufferedImage getCurrentSprite() {

        return animationSet.getFrame(direction, spriteIndex);
    }

    public void draw(Graphics2D g2, GamePanel gp ) {

        BufferedImage image = getCurrentSprite();

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if ( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
             worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) { // coordonatele pt camera frame

            g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);

        }
    }

    public void setAction() {

    }

    public void speak(){

    }

    public void use(Entity entity){

    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);

        gp.cChecker.checkPlayer(this);

        if(!this.collisionOn) {
            switch (direction) {
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

}
