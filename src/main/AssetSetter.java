package main;

import entity.*;
import object.*;

public class AssetSetter {

    GamePanel  gp;
    private ObjectFactory objectFactory; // field for the factory

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
        this.objectFactory = new ObjectFactory(); // initializare
    }

    public void setObject() {
        // instantiere obiecte
        int mapNum = gp.currentMap;
        if(mapNum == 0) {
            if (gp.obj[mapNum] == null) {
                gp.obj[mapNum] = new SuperObject[100];
            }
            int i = 0;
            gp.obj[mapNum][i] = objectFactory.createObject("Door", gp);
            gp.obj[mapNum][i].worldX = 37 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 42 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("ClosedDoor", gp);
            gp.obj[mapNum][i].worldX = 33 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 28 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("Door", gp);
            gp.obj[mapNum][i].worldX = 19 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 15 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("ClosedDoor2", gp);
            gp.obj[mapNum][i].worldX = 8 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 14 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("Chest", gp);
            gp.obj[mapNum][i].worldX = 42 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 32 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("Key", gp);
            gp.obj[mapNum][i].worldX = 5 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 42 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("Key", gp);
            gp.obj[mapNum][i].worldX = 47 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 5 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("ChestLevel2", gp);
            gp.obj[mapNum][i].worldX = 3 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("HealthPotion", gp);
            gp.obj[mapNum][i].worldX = 14 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("Table", gp);
            gp.obj[mapNum][i].worldX = 16 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
        }
        else if(mapNum == 1) {
            int i = 0;
            if (gp.obj[mapNum] == null) {
                gp.obj[mapNum] = new SuperObject[100];
            }
            gp.obj[mapNum][i] = objectFactory.createObject("Table", gp);
            gp.obj[mapNum][i].worldX = 10 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 10 * gp.tileSize;
            i++;

            gp.obj[mapNum][i] = objectFactory.createObject("ChestLevel2", gp);
            gp.obj[mapNum][i].worldX = 29 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 18 * gp.tileSize;
            // adaugat elemente mobila in camera
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("Chest", gp);
            gp.obj[mapNum][i].worldX = 44 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 32 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = objectFactory.createObject("HealthPotion", gp);

            gp.obj[mapNum][i] = new OBJ_ChestLevel2(gp);
            gp.obj[mapNum][i].worldX = 27 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 18 * gp.tileSize;
            // adaugat elemente mobila in camera
            i++;
            gp.obj[mapNum][i] = new OBJ_Chest(gp);
            gp.obj[mapNum][i].worldX = 14 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 20 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Chest(gp);
            gp.obj[mapNum][i].worldX = 44 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 32 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Chest(gp);
            gp.obj[mapNum][i].worldX = 29 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
            i++;

            gp.obj[mapNum][i] = objectFactory.createObject("Key", gp);
            gp.obj[mapNum][i].worldX = 45 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 45 * gp.tileSize;

            gp.obj[mapNum][i] = new OBJ_Key(gp);
            gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 31 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_ClosedDoor4(gp);
            gp.obj[mapNum][i].worldX = 6 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 18 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_ClosedDoor3(gp);
            gp.obj[mapNum][i].worldX = 41 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 6 * gp.tileSize;



        }

    }

    public void setNPC() {

        // instantiem npc
        int mapNum = gp.currentMap;
        if(mapNum == 0) {
            gp.npc[mapNum][0] = new NPC(gp);
            gp.npc[mapNum][0].worldX = gp.tileSize * 26;
            gp.npc[mapNum][0].worldY = gp.tileSize * 44;
        }
        else if(mapNum == 1){

            gp.npc[mapNum][0] = new TraderNPC(gp);
            gp.npc[mapNum][0].worldX = 46 * gp.tileSize;
            gp.npc[mapNum][0].worldY = 46 * gp.tileSize;
        }

    }

    public void setMonster(){
        // instantiem monstrii
        int mapNum = gp.currentMap;
        if(mapNum == 0) {
            gp.monsters[0][0] = new MonsterLevel1(gp);
            gp.monsters[0][0].worldX = 40 * gp.tileSize;
            gp.monsters[0][0].worldY = 21 * gp.tileSize;
         /*   gp.monsters[0][1] = new Troll(gp);
            gp.monsters[0][1].worldX = 26 * gp.tileSize;
            gp.monsters[0][1].worldY = 40 * gp.tileSize; */
        }
        else if(mapNum == 1){

        }
    }

}
