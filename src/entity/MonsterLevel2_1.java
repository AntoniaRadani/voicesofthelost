package entity;

import main.GamePanel;
import object.OBJ_SpecialKey;

import java.awt.*;

public class MonsterLevel2_1 extends Monster{

    public MonsterLevel2_1(GamePanel gp) {
        super(gp);
        System.out.println("AM creat monstru");
        maxLife = 12;
        life = 12;
        speed = 4;
        attack = 3;
        defense = 2;
        getPlayerImage();
        setDialogue();
    }


    public void getPlayerImage() {
        animationSet.loadSeparate("monster", dir , "monster", 3); // 23 cadre per direcție
    }

    public void update() {
        // logica monștrilor (mișcare, atac, etc.)
        if (life <= 0) {
            dead = true;
            gp.player.inventory.add(new OBJ_SpecialKey(gp));
        }

        if (!dead) {
            followPlayer();
        }
    }

    public void die() {
        dead = true;
        gp.monsters[1][0] = null;
    }
}
