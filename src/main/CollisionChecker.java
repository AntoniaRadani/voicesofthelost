package main;

import entity.Entity;
import tile.TiledMapViewer;
import tile.Vector2f;

import javax.swing.plaf.PanelUI;
import java.awt.*;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp ) {
        this.gp = gp;
    }

    private boolean isInsideMap(int row, int col) {
        return row >= 0 && row < gp.tiledMapViewer.mapHeight &&
                col >= 0 && col < gp.tiledMapViewer.mapWidth;
    }
    
    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1 = 0, tileNum2 = 0;
        int layer1 = 1, layer2 = 2; // in functie de nivel, reprezinta layerele pe care sunt obiecte cu coliziune

        switch (gp.currentLevel) {
            case 1:
                layer1 = 1; // walls
                layer2 = 2; // decor
                break;
            case 2:
                layer1 = 5; // decor
                layer2 = 4; // walls
                break;
            case 3:
                layer1 = 2; // walls
                layer2 = 3; // decor
        }
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer1][entityTopRow][entityLeftCol];
                }
                if (isInsideMap(entityTopRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer1][entityTopRow][entityRightCol];
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (isInsideMap(entityBottomRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer1][entityBottomRow][entityLeftCol];
                }
                if (isInsideMap(entityBottomRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer1][entityBottomRow][entityRightCol];
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer1][entityTopRow][entityLeftCol];
                }
                if (isInsideMap(entityBottomRow, entityLeftCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer1][entityBottomRow][entityLeftCol];
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityRightCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer1][entityTopRow][entityRightCol];
                }
                if (isInsideMap(entityBottomRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer1][entityBottomRow][entityRightCol];
                }
                break;
        }

        if ((tileNum1 > 0 && gp.tiledMapViewer.tileCollision[tileNum1]) ||
                (tileNum2 > 0 && gp.tiledMapViewer.tileCollision[tileNum2])) {
            //System.out.println(" AM LOVIT CEVA PE PRIMUL LAYER");
            entity.collisionOn = true;
        }

        // pentru al 2 lea layer

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer2][entityTopRow][entityLeftCol];
                }
                if (isInsideMap(entityTopRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer2][entityTopRow][entityRightCol];
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (isInsideMap(entityBottomRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer2][entityBottomRow][entityLeftCol];
                }
                if (isInsideMap(entityBottomRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer2][entityBottomRow][entityRightCol];
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer2][entityTopRow][entityLeftCol];
                }
                if (isInsideMap(entityBottomRow, entityLeftCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer2][entityBottomRow][entityLeftCol];
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityRightCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[layer2][entityTopRow][entityRightCol];
                }
                if (isInsideMap(entityBottomRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[layer2][entityBottomRow][entityRightCol];
                }
                break;
        }

        if ((tileNum1 > 0 && gp.tiledMapViewer.tileCollision[tileNum1]) ||
                (tileNum2 > 0 && gp.tiledMapViewer.tileCollision[tileNum2])) {
            //System.out.println(" AM LOVIT CEVA PE AL 2 LEA LAYER");
            entity.collisionOn = true;
        }
    }

    public int checkObject2(Entity entity, boolean player) {

        int index = 999;
        int currentMap = gp.currentMap;

        for (int i = 0; i < gp.obj[currentMap].length; i++) {
            if (gp.obj[currentMap][i] != null) {
                // get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // get object solid area position
                gp.obj[currentMap][i].solidArea.x = gp.obj[currentMap][i].worldX + gp.obj[currentMap][i].solidArea.x;
                gp.obj[currentMap][i].solidArea.y = gp.obj[currentMap][i].worldY + gp.obj[currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[currentMap][i].solidArea)) {
                            if(gp.obj[currentMap][i].collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[currentMap][i].solidArea)) {
                            if(gp.obj[currentMap][i].collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[currentMap][i].solidArea)) {
                            if(gp.obj[currentMap][i].collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[currentMap][i].solidArea)) {
                            if(gp.obj[currentMap][i].collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[currentMap][i].solidArea.x = gp.obj[currentMap][i].solidAreaDefaultX;
                gp.obj[currentMap][i].solidArea.y = gp.obj[currentMap][i].solidAreaDefaultY;
            }

        }

        return index;
    }

    public int checkEntity( Entity entity, Entity[] target ) { // check npc or monster collision

        int index = -1;

        for ( int i = 0; i < target.length; i++ ) {

            if ( target[i] != null ) {
                // entity solid area pos
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // get target solid area pos
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if( entity.solidArea.intersects(target[i].solidArea) ) {
                                entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if( entity.solidArea.intersects(target[i].solidArea) ) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if( entity.solidArea.intersects(target[i].solidArea) ) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if( entity.solidArea.intersects(target[i].solidArea) ) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public void checkPlayer( Entity entity ) {
        // entity solid area pos
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // get target solid area pos
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                if( entity.solidArea.intersects(gp.player.solidArea) ) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if( entity.solidArea.intersects(gp.player.solidArea) ) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if( entity.solidArea.intersects(gp.player.solidArea) ) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if( entity.solidArea.intersects(gp.player.solidArea) ) {
                    entity.collisionOn = true;
                }
                break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }

}
