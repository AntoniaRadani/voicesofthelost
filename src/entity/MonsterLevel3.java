package entity;

import main.GamePanel;
import object.OBJ_Card;
import object.OBJ_LevelKey;

import java.awt.*;

public class MonsterLevel3 extends Monster{

    public MonsterLevel3(GamePanel gp) {
        super(gp);
        maxLife = 20;
        life = 20;
        speed = 6;
        attack = 5;
        defense = 5;
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
            gp.player.inventory.add(new OBJ_LevelKey(gp));
            gp.player.inventory.add(new OBJ_Card(gp));
            gp.player.hasCard++;
            gp.player.hasLevelKey++;
        }

        if (!dead) {
            followPlayer();
        }
    }

    public void die() {
        dead = true;
        gp.monsters[2][0] = null;
    }
}
