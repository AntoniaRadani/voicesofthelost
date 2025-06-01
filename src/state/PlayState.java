package state;

import main.GamePanel;

import java.awt.Graphics2D;

public class PlayState implements GameState {
    private final GamePanel gp;

    public PlayState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {
        gp.player.update();

        for (int i = 0; i < gp.npc.length; i++) {
            if (gp.npc[i] != null) {
                gp.npc[i].update();
            }
        }

        for (int i = 0; i < gp.monsters[1].length; i++) {
            if (gp.monsters[gp.currentMap][i] != null) {
                if (gp.monsters[gp.currentMap][i].alive && !gp.monsters[gp.currentMap][i].dying)
                    gp.monsters[gp.currentMap][i].updateMonster();
                if (!gp.monsters[gp.currentMap][i].alive)
                    gp.monsters[gp.currentMap][i] = null;
            }
        }

        if (!gp.roomCleared) {
            gp.trapRoomLevel1.update();
            if (gp.escapedFromTrapRoom)
                gp.roomCleared = true;
        }

        gp.zoom();
    }

    @Override
    public void draw(Graphics2D g2) {
        gp.tiledMapViewer.draw(g2);

        for (int i = 0; i < gp.npc.length; i++) {
            if (gp.npc[i] != null) {
                gp.npc[i].draw(g2, gp);
            }
        }

        for (int i = 0; i < gp.monsters[gp.currentMap].length; i++) {
            if (gp.monsters[gp.currentMap][i] != null) {
                gp.monsters[gp.currentMap][i].draw(g2, gp);
            }
        }

        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                gp.obj[gp.currentMap][i].draw(g2, gp);
            }
        }

        gp.player.draw(g2);

        if (gp.showFullMap) {
            gp.map.drawFullMapScreen(g2);
        }

        gp.map.drawMiniMap(g2);

        if (gp.showLighting) {
            gp.eManager.draw(g2);
        }

        gp.ui.draw(g2);
    }
}
