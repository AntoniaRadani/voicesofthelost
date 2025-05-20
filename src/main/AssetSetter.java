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

        int i = 0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 23 * gp.tileSize;
        gp.obj[i].worldY = 42 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 23 * gp.tileSize;
        gp.obj[i].worldY = 40 * gp.tileSize;
        i++;
        gp.obj[i] = new OBJ_Chest(gp);
        gp.obj[i].worldX = 4 * gp.tileSize;
        gp.obj[i].worldY = 25 * gp.tileSize;

    }

    public void setNPC() {

        // instantiem npc

        gp.npc[0] = new NPC(gp);
        gp.npc[0].worldX = gp.tileSize * 26;
        gp.npc[0].worldY = gp.tileSize * 44;

    }
}
