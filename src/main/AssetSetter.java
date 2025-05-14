package main;

import entity.NPC;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

    }

    public void setNPC() {

        // instantiem npc

        gp.npc[0] = new NPC(gp);
        gp.npc[0].worldX = gp.tileSize * 26;
        gp.npc[0].worldY = gp.tileSize * 44;


    }
}
