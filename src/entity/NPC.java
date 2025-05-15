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
        speed = 4; // faster than player

        getPlayerImage();
    }

    public void setAction() {

        actionLockCounter++;
        if ( actionLockCounter == 120 || collisionOn == true) {
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



}
