package main;

import entity.NPC;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        int i = 0;
        gp.obj[i] = new OBJ_ClosedDoor(gp);
        gp.obj[i].worldX = 37 * gp.tileSize;
        gp.obj[i].worldY = 42 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = 33 * gp.tileSize;
        gp.obj[i].worldY = 28 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_ClosedDoor(gp);
        gp.obj[i].worldX = 19 * gp.tileSize;
        gp.obj[i].worldY = 15 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 44 * gp.tileSize;
        gp.obj[i].worldY = 33 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 5 * gp.tileSize;
        gp.obj[i].worldY = 42 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_ChestLevel1(gp);
        gp.obj[i].worldX = 4 * gp.tileSize;
        gp.obj[i].worldY = 21 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_HealthPotion(gp);
        gp.obj[i].worldX = 4 * gp.tileSize;
        gp.obj[i].worldY = 27 * gp.tileSize;
        i++;
//        gp.obj[i] = new OBJ_RedSword(gp);
//        gp.obj[i].worldX = 23 * gp.tileSize;
//        gp.obj[i].worldY = 37 * gp.tileSize;
//        i++;
    }

    public void setNPC() {

        // instantiem npc

        gp.npc[0] = new NPC(gp);
        gp.npc[0].worldX = gp.tileSize * 26;
        gp.npc[0].worldY = gp.tileSize * 44;

    }
}
