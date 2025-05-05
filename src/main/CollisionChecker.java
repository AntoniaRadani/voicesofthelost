package main;

import entity.Entity;

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

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[1][entityTopRow][entityLeftCol];
                }
                if (isInsideMap(entityTopRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[1][entityTopRow][entityRightCol];
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (isInsideMap(entityBottomRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[1][entityBottomRow][entityLeftCol];
                }
                if (isInsideMap(entityBottomRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[1][entityBottomRow][entityRightCol];
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityLeftCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[1][entityTopRow][entityLeftCol];
                }
                if (isInsideMap(entityBottomRow, entityLeftCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[1][entityBottomRow][entityLeftCol];
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (isInsideMap(entityTopRow, entityRightCol)) {
                    tileNum1 = gp.tiledMapViewer.mapData[1][entityTopRow][entityRightCol];
                }
                if (isInsideMap(entityBottomRow, entityRightCol)) {
                    tileNum2 = gp.tiledMapViewer.mapData[1][entityBottomRow][entityRightCol];
                }
                break;
        }

        if ((tileNum1 > 0 && gp.tiledMapViewer.tileCollision[tileNum1]) ||
                (tileNum2 > 0 && gp.tiledMapViewer.tileCollision[tileNum2])) {
            entity.collisionOn = true;
        }
    }

}
