package tile;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import main.GamePanel;
import main.KeyHandler;


import org.w3c.dom.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TiledMapViewer {


    GamePanel gp;

    public int[][][] mapData; // MapTileNum
    private int tileWidth = 16;  // Set default tile size (adjustable)
    private int tileHeight = 16;
    public int mapWidth, mapHeight;
    public int layerLength;

    private BufferedImage tilesetImage;
    private BufferedImage[] tileImages;


    private int screenX, screenY;

    public boolean[] tileCollision;


    public TiledMapViewer(String tmxFilePath, GamePanel gp) {
        loadTMX(tmxFilePath);
        this.gp = gp;
        screenX = 0;
        screenY = 0;
    }

    public void updateCamera(int playerX, int playerY, int screenWidth, int screenHeight) {
        // Setează screenX și screenY astfel încât jucătorul să fie în mijlocul ecranului
        screenX = playerX - (screenWidth / 2);  // Centrarea camerei pe jucător
        screenY = playerY - (screenHeight / 2);

        // Asigură-te că camera nu depășește limitele hărții
        if (screenX < 0) {
            screenX = 0;
        }
        if (screenY < 0) {
            screenY = 0;
        }
        if (screenX > mapWidth * tileWidth - screenWidth) {
            screenX = mapWidth * tileWidth - screenWidth;
        }
        if (screenY > mapHeight * tileHeight - screenHeight) {
            screenY = mapHeight * tileHeight - screenHeight;
        }

        System.out.println("SCREEN VALUES : " + screenX + screenY);

    }

    private void loadTMX(String filePath) {
        try {
            File tmxFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(tmxFile);
            doc.getDocumentElement().normalize();

            // MAP SETTINGS
            Element mapElement = (Element) doc.getElementsByTagName("map").item(0);
            mapWidth = Integer.parseInt(mapElement.getAttribute("width"));
            mapHeight = Integer.parseInt(mapElement.getAttribute("height"));

            tileWidth = Integer.parseInt(mapElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(mapElement.getAttribute("tileheight"));


            // TILESET (support for external TSX)
            Node tilesetNode = doc.getElementsByTagName("tileset").item(0);
            Element tilesetElement;
            if (((Element) tilesetNode).hasAttribute("source")) {
                // External TSX
                String tsxPath = ((Element) tilesetNode).getAttribute("source");
                File tsxFile = new File(new File(filePath).getParent(), tsxPath);
                Document tsxDoc = builder.parse(tsxFile);
                tsxDoc.getDocumentElement().normalize();

                tilesetElement = (Element) tsxDoc.getElementsByTagName("tileset").item(0);

                // după încărcarea tilesetElement

                int tileCount = Integer.parseInt(tilesetElement.getAttribute("tilecount"));
                tileCollision = new boolean[tileCount + 1]; // +1 pentru că tile IDs încep de la 0

                System.out.println("tileCount: " + tileCount );
// tile count = cate tile uri sunt in total: aprox 290
                NodeList tileList = tilesetElement.getElementsByTagName("tile");

              //  System.out.println("tileList.getLength: " + tileList.getLength() );
// tileList.getLength = tile urile care au proprietate(oricare cred) : aprox 85
                for (int i = 0; i < tileList.getLength(); i++) {
                    Element tileElement = (Element) tileList.item(i);
                    int id = Integer.parseInt(tileElement.getAttribute("id"));

                    NodeList propertyList = tileElement.getElementsByTagName("property");
                    for (int j = 0; j < propertyList.getLength(); j++) {
                        Element property = (Element) propertyList.item(j);
                        String propertyName = property.getAttribute("name");
                        String propertyValue = property.getAttribute("value");

                        if (propertyName.equals("collision") && propertyValue.equals("true")) {
                            tileCollision[id + 1] = true; // toate tile urile care au proprietatea collision sunt setate
                        }
                    }
                }

            } else {
                // Inline tileset
                tilesetElement = (Element) tilesetNode;
            }

            System.out.println("=== TILE COLLISIONS ===");
            for (int i = 0; i < tileCollision.length; i++) {
                if (tileCollision[i]) {
                    System.out.println("Tile " + i + " is solid");
                }
            }




            // Get tileset image
            Element imageElement = (Element) tilesetElement.getElementsByTagName("image").item(0);
            String imageSource = imageElement.getAttribute("source");

            File imageFile = new File(new File(filePath).getParent(), imageSource);
            tilesetImage = ImageIO.read(imageFile);

            sliceTileset();

            /*
            0 - ground
            1 - walls
            2 - decor objects
            3 - interact objects
             */

            // MAP DATA - Multi-layer support
            NodeList layerList = doc.getElementsByTagName("layer");
            mapData = new int[layerList.getLength()][mapHeight][mapWidth]; // Array 3D pentru date

            layerLength = layerList.getLength();

            for (int i = 0; i < layerList.getLength(); i++) {
                Element layer = (Element) layerList.item(i);
                Element data = (Element) layer.getElementsByTagName("data").item(0);
                String[] tiles = data.getTextContent().trim().split(",");

                for (int j = 0; j < tiles.length; j++) {
                    int row = j / mapWidth;
                    int col = j % mapWidth;

                    mapData[i][row][col] = Integer.parseInt(tiles[j].trim());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sliceTileset() {
        int columns = tilesetImage.getWidth() / tileWidth;
        int rows = tilesetImage.getHeight() / tileHeight;
        int tileCount = columns * rows;

        tileImages = new BufferedImage[tileCount + 1]; // ID-urile încep de la 1

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int index = y * columns + x + 1;
                tileImages[index] = tilesetImage.getSubimage(
                        x * tileWidth, y * tileHeight, tileWidth, tileHeight);

            }
        }
    }
    public void draw(Graphics2D g2) {


        // Desenarea hărții cu toate straturile
        screenX = gp.player.screenX;
        screenY = gp.player.screenY;

// Opțional: Clamp (limitează camera)
        if (gp.player.worldX < gp.screenWidth/2) {
            screenX = gp.player.worldX;
        }

        if (gp.player.worldY < gp.screenHeight/2) {
            screenY = gp.player.worldY;
        }

        int rightOffset = gp.worldWidth - gp.player.worldX;
        if (rightOffset < gp.screenWidth/2) {
            screenX = gp.screenWidth - (gp.worldWidth - gp.player.worldX);
        }

        int bottomOffset = gp.worldHeight - gp.player.worldY;
        if (bottomOffset < gp.screenHeight/2) {
            screenY = gp.screenHeight - (gp.worldHeight - gp.player.worldY);
        }

        //  Parcurge toate layerele

        int worldRow = 0;
        int worldCol = 0;



        for (int layer = 0; layer < mapData.length; layer++) {
            for (int row = 0; row < mapHeight; row++) {
                for (int col = 0; col < mapWidth; col++) {

                    int worldX = col * gp.tileSize;
                    int worldY = row * gp.tileSize;

                    int screenX = worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    int tileId = mapData[layer][row][col];

                    if (
                            worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
                    ) {
                        if (tileId > 0 && tileId < tileImages.length && tileImages[tileId] != null) {
                            g2.drawImage(tileImages[tileId], screenX, screenY, gp.tileSize, gp.tileSize, null);
                        }
                    }
                }
            }
        }
    }

}

