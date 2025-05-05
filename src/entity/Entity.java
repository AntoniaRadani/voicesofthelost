package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.HashMap;


public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;


    public int spriteCounter = 0;
    int spriteIndex = 0;

    public static final String[] DIRECTIONS = { "up", "down", "left", "right" };

    protected AnimationSet animationSet = new AnimationSet(); // vector pt toate imaginile sprite urilor

    // pentru coliziuni
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public void updateAnimation() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteIndex = (spriteIndex + 1) % animationSet.animations.get(direction).size();
            spriteCounter = 0;
        }
    }

    public BufferedImage getCurrentSprite() {
        return animationSet.getFrame(direction, spriteIndex);
    }

}
