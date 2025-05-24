package entity;

import main.GamePanel;

import java.awt.*;

public class Monster extends Entity{

    public boolean dead;
    String [] dir = {"left" , "right"};
    boolean interaction = false;

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
        // logica monștrilor (mișcare, atac, etc.)
        if (life <= 0) {
            dead = true;
        }
    }

    public void takeDamage(int amount) {
        life -= amount;
        if (life <= 0) {
            dead = true;
        }
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
