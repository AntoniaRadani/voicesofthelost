package tile;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TiledMapViewer {
    private int[][] mapData;
    private int tileWidth = 16;  // Set default tile size (adjustable)
    private int tileHeight = 16;
    private int mapWidth, mapHeight;

    private BufferedImage tilesetImage;
    private BufferedImage[] tileImages;

    public TiledMapViewer(String tmxFilePath) {
        loadTMX(tmxFilePath);
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
            } else {
                // Inline tileset
                tilesetElement = (Element) tilesetNode;
            }

            // Get tileset image
            Element imageElement = (Element) tilesetElement.getElementsByTagName("image").item(0);
            String imageSource = imageElement.getAttribute("source");

            File imageFile = new File(new File(filePath).getParent(), imageSource);



            tilesetImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resurse/level1.png"));


            sliceTileset();

            // MAP DATA - Multi-layer support
            NodeList layerList = doc.getElementsByTagName("layer");
            mapData = new int[mapHeight][mapWidth];

            for (int i = 0; i < layerList.getLength(); i++) {
                Element layer = (Element) layerList.item(i);
                Element data = (Element) layer.getElementsByTagName("data").item(0);
                String[] tiles = data.getTextContent().trim().split(",");

                for (int j = 0; j < tiles.length; j++) {
                    int row = j / mapWidth;
                    int col = j % mapWidth;
                    mapData[row][col] = Integer.parseInt(tiles[j].trim());
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
                        x * tileWidth, y * tileHeight, tileWidth, tileHeight
                );
            }
        }
    }

    public void draw(Graphics2D g2) {
        // Desenarea hărții cu toate straturile
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                int tileId = mapData[row][col];
                if (tileId > 0 && tileId < tileImages.length && tileImages[tileId] != null) {
                    g2.drawImage(tileImages[tileId], col * tileWidth, row * tileHeight, null);
                }
            }
        }
    }
}
