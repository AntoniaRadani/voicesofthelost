package entity;

import main.GamePanel;

import java.util.Random;

public class Troll extends Entity {

    GamePanel gp;
    String [] dir = {"left" , "right"};

    public Troll( GamePanel gp ) {

        super(gp);

        this.gp = gp;

       // type = type_monster;
        name = "Troll-FinalBoss";
        speed = 3;
        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 2;
        type = 2;
        

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 30;
        solidArea.height = 40;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getTrollWalkImage();

    }

    public void getTrollWalkImage() {

        animationSet.loadSeparate("troll", dir , "troll_walk", 10); // 23 cadre per direcție
    }

    public void getTrollAttackImage() {
        animationSet.loadSeparate("troll", dir , "troll_attack", 10); // 23 cadre per direcție
    }

    public void setAction() {
        actionLockCounter++;
        if ( actionLockCounter == 300 || collisionOn) {
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

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    
}
