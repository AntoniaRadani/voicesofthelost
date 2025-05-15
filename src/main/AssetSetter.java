package main;

import entity.NPC;
import object.OBJ_Apple;
import object.OBJ_Chest;
import object.OBJ_Heart;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_Apple();
        gp.obj[2].worldX = 10 * gp.tileSize;
        gp.obj[2].worldY = 10 * gp.tileSize;

        gp.obj[3] = new OBJ_Apple();
        gp.obj[3].worldX = 0;
        gp.obj[3].worldY = 0;

        gp.obj[4] = new OBJ_Chest();
        gp.obj[4].worldX = 4 * gp.tileSize;
        gp.obj[4].worldY = 25 * gp.tileSize;

        gp.obj[5] = new OBJ_Heart();
        gp.obj[5].worldX = 0;
        gp.obj[5].worldY = 0;

        gp.obj[6] = new OBJ_Heart();
        gp.obj[6].worldX = 0;
        gp.obj[6].worldY = 0;
    }

    public void setNPC() {

        // instantiem npc

        gp.npc[0] = new NPC(gp);
        gp.npc[0].worldX = gp.tileSize * 26;
        gp.npc[0].worldY = gp.tileSize * 44;

    }
}
