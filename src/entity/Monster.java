package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster extends Entity{

    public boolean dead;
    public int attackCooldown = 60;
    public int attackTimer = 0;
    public int attack;
    String [] dir = {"left" , "right"};

    public Monster(GamePanel gp) {
        super(gp);
        direction = "left";
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        dead = false;

        getPlayerImage();
        setDialogue();
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null)
            dialogueIndex = 0;

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }

    public void getPlayerImage() {
    }

    public boolean isDead(){
        return dead;
    }

    public void update() {
        if (life <= 0) {
            dead = true;
        }
    }

    public void takeDamage(int damage) {
        life -= damage;
        System.out.println("Monster took " + damage + " damage! Remaining HP: " + life);

        if (life <= 0) {
            die();
        }
    }

    public void die() {
        dead = true;
        //gp.monsters[gp.currentMap][0] = null;
        System.out.println("Monster died!");
    }

    public int getDefense(){
        return defense;
    }

    public void followPlayer() {
        int playerX = gp.player.worldX;
        int playerY = gp.player.worldY;

        int dx = playerX - worldX;
        int dy = playerY - worldY;

        // Alegerea direcției în funcție de cea mai mare distanță
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                direction = "right";
                worldX += speed;
            } else {
                direction = "left";
                worldX -= speed;
            }

            // Atac cu cooldown dacă e aproape de jucător
            if (attackTimer <= 0 && Math.abs(dx) < gp.tileSize && Math.abs(dy) < gp.tileSize) {
                gp.player.takeDamage(1);
                attackTimer = attackCooldown;
            }

            if (attackTimer > 0) {
                attackTimer--;
            }
        }
    }

    public void attackPlayer() {
        int damage = this.attack - gp.player.getDefense();

        if (damage < 1) {
            damage = 1; // Damage minim
        }

        gp.player.takeDamage(damage);
        System.out.println("Player took " + damage + " damage! Life left: " + gp.player.life);
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setDialogue(){
    }

}
