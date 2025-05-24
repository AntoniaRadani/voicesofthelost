package entity;

import main.GamePanel;

import java.util.Random;

public class NPC extends Entity {

    String [] dir = {"left" , "right"};

    public void getPlayerImage() {
        animationSet.loadSeparate("npc", dir , "npc_walk", 23); // 23 cadre per direcție
    }

    public NPC(GamePanel gp) {

        super(gp);

        direction = "right";
        speed = 3; // faster than player

        getPlayerImage();
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "Dialog 1";
        dialogues[2] = "Dialog 2";
        dialogues[3] = "Dialog 3";
    }

    public void setAction() {

        actionLockCounter++;
        if ( actionLockCounter == 120 || collisionOn) {
            Random random = new Random();

            int i = random.nextInt(100) + 1; // random num 0-100

            if ( i <= 50 ) {
                direction = "left";
            }
            if ( i > 50 & i <= 100 ) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak(){
        if(dialogues[dialogueIndex] == null)
            dialogueIndex = 0;

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }



}
