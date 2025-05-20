package entity;

import main.GamePanel;
import tile.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;


public class Entity {

    GamePanel gp;
    public int worldX, worldY;

    public String direction;

    public int spriteCounter = 0;
    int spriteIndex = 0;

    // dialog
    String dialogues[] = new String[20];
    int dialogueIndex;

    // character attributes
    public int type; // 0 = player, 1 = npc, 2 = monster
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int level;
    public int attack;
    public int defense;
    public int cards;
    public int strength;
    public int dexterity;
    public Entity currentWeapon;
    public Entity currentShield;

    // items attributes
    public BufferedImage down1;
    public int attackValue;
    public int defenseValue;

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
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        Vector2f obj = new Vector2f();
        int objIndex = gp.cChecker.checkObject(this, false, obj);
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
