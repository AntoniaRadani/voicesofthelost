package main;

import entity.NPC;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // instantiere obiecte
        int mapNum = 0;
        if (gp.obj[mapNum] == null) {
            gp.obj[mapNum] = new SuperObject[100];
        }
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = 37 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 42 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_ClosedDoor(gp);
        gp.obj[mapNum][i].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 28 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = 19 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 15 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_ClosedDoor2(gp);
        gp.obj[mapNum][i].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 14 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = 44 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 33 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = 5 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 42 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_ChestLevel1(gp);
        gp.obj[mapNum][i].worldX = 3 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
        i++;
        gp.obj[mapNum][i] = new OBJ_HealthPotion(gp);
        gp.obj[mapNum][i].worldX = 4 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 27 * gp.tileSize;
        mapNum++;
        i = 0;
        if (gp.obj[mapNum] == null) {
            gp.obj[mapNum] = new SuperObject[100];
        }
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = 2 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 2 * gp.tileSize;
        i++;
    }

    public void setNPC() {

        // instantiem npc

        gp.npc[0] = new NPC(gp);
        gp.npc[0].worldX = gp.tileSize * 26;
        gp.npc[0].worldY = gp.tileSize * 44;

    }
}
