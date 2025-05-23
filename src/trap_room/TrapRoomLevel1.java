package trap_room;

import entity.Monster;
import entity.MonsterLevel1;
import main.GamePanel;
import entity.Player;
import object.OBJ_LevelKey;

import java.awt.*;


public class TrapRoomLevel1 {

    private GamePanel gp;
    private Rectangle roomArea;
    private boolean active = false;
    private boolean roomLocked = false;
    private long lastDamageTime;
    public Monster monster;
    private int exitTileX, exitTileY;
    private int lockedTileIndexUp;
    private int lockedTileIndexDown;
    private int normalTileIndex;

    public TrapRoomLevel1(GamePanel gp) {
        this.gp = gp;

        // Inițializezi aici parametrii specifici camerei, de ex:
        roomArea = new Rectangle(
                31 * gp.tileSize,    // x start
                19 * gp.tileSize,    // y start
                13 * gp.tileSize,    // lățimea
                8 * gp.tileSize      // înălțimea
        );

        // Alege un monstru din gp, sau instanțiază-l direct:
         // monster = gp.monsters[0];

        // Ieșirea și tile-urile de blocare
        exitTileX = 33;
        exitTileY = 28;
        lockedTileIndexUp = 25;  // index-ul tile-ului blocat
        lockedTileIndexDown = 50;
        normalTileIndex = 2;   // index-ul tile-ului normal (deschis)
    }

    public void blockExit(){
        gp.tiledMapViewer.mapData[1][exitTileY][exitTileX] = lockedTileIndexUp;
        gp.tiledMapViewer.mapData[1][exitTileY][exitTileX + 1] = lockedTileIndexUp;
        gp.tiledMapViewer.mapData[1][exitTileY][exitTileX + 2] = lockedTileIndexUp;
        gp.tiledMapViewer.mapData[1][exitTileY + 1][exitTileX] = lockedTileIndexDown;
        gp.tiledMapViewer.mapData[1][exitTileY + 1][exitTileX + 1] = lockedTileIndexDown;
        gp.tiledMapViewer.mapData[1][exitTileY + 1][exitTileX + 2] = lockedTileIndexDown;
    }

    public void update() {
        Player player = gp.player;

        Rectangle playerBox = new Rectangle(player.worldX + player.solidArea.x,
                player.worldY + player.solidArea.y,
                player.solidArea.width,
                player.solidArea.height);

        if(monster == null)
            monster = gp.monsters[0];

        if (roomArea.intersects(playerBox) && gp.player.doorOpen1) {
            // daca traproom ul nu e activ si player ul a deschis usa
            if (!active) {
                active = true;
                roomLocked = true;

                gp.gameState = gp.dialogueState;
                monster.speak();

                lastDamageTime = System.currentTimeMillis();

                // Blocăm ieșirea
                blockExit();

            }

            // Damage la fiecare 20 secunde
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDamageTime >= 20000) {
                player.life--;
                lastDamageTime = currentTime;
            }

            // verifica dacă monstrul a fost invins
            if (roomLocked && gp.escapedFromTrapRoom) {
                System.out.println("SUNT AICI");
                roomLocked = false;
                monster = null;
                gp.monsters[0] = null;

                // adaugare in inventar cheie nivel urmator
                gp.player.inventory.add(new OBJ_LevelKey(gp));

                // redesenare mapa
                gp.tiledMapViewer.mapData[1][exitTileY][exitTileX] = normalTileIndex;
                gp.tiledMapViewer.mapData[1][exitTileY][exitTileX + 1] = normalTileIndex;
                gp.tiledMapViewer.mapData[1][exitTileY][exitTileX + 2] = normalTileIndex;
                gp.tiledMapViewer.mapData[1][exitTileY + 1][exitTileX] = normalTileIndex;
                gp.tiledMapViewer.mapData[1][exitTileY + 1][exitTileX + 1] = normalTileIndex;
                gp.tiledMapViewer.mapData[1][exitTileY + 1][exitTileX + 2] = normalTileIndex;
            }

        } else if (!roomLocked) {
            active = false;
        }
    }

}
