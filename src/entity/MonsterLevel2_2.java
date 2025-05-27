package entity;

import main.GamePanel;

import java.awt.*;

public class MonsterLevel2_2 extends Monster{

    public MonsterLevel2_2(GamePanel gp) {
        super(gp);
        System.out.println("AM creat monstru");
        maxLife = 20;
        life = 20;
        speed = 6;
        attack = 5;
        defense = 5;
        getPlayerImage();
        setDialogue();
    }


    public void getPlayerImage() {
        animationSet.loadSeparate("npc_fight", dir , "npc_fight", 23); // 23 cadre per direcție
    }

    public void update() {
        // logica monștrilor (mișcare, atac, etc.)
        if (life <= 0) {
            dead = true;
        }

        if (!dead) {
            followPlayer();
        }
    }

    public void die() {
        dead = true;
        gp.monsters[1][1] = null;
    }
}
