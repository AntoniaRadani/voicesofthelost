package tile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class TileManager {

    public static ArrayList<TileMap> tileMap;

    public TileManager() {

        tileMap = new ArrayList<TileMap>();

    }

    public TileManager(String path) {
        tileMap = new ArrayList<TileMap>();
        addTileMap(path,64,64);
    }

    private void addTileMap(String path, int blockWidth, int blockHeight) {

        String imagePath;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount; // how many tiles
        int tileColumns;
        int layer = 0;
        Sprite sprite;

        String[] data = new String[10];

        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder= builderFactory.newDocumentBuilder();
            URL resource = getClass().getClassLoader().getResource(path);
            if (resource == null) {
                throw new IllegalArgumentException("File not found: " + path);
            }

            File xmlFile = new File(resource.toURI());
            Document doc = builder.parse(xmlFile);

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element element = (Element) node;

            imagePath = element.getAttribute("name");
            tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(element.getAttribute("tileheight"));

            sprite = new Sprite(imagePath + "/" + imagePath + ".png", tileWidth, tileHeight);
            // MEREU NUMELE DIRECTORULUI CU RES SA FIE ACELASI CU PNG  si xml PT A FI BUN PT ORICE NIVEL
            list = doc.getElementsByTagName("layer");
            int layers = list.getLength();

            for ( int i = 0; i < layers; i++ ) {
                node = list.item(i);
                element = (Element) node;
                if ( i <= 0 ) {
                    width = Integer.parseInt(element.getAttribute("width"));
                    height = Integer.parseInt(element.getAttribute("height"));
                }
                // i este numarul layer ului
                data[i] = element.getElementsByTagName("data").item(0).getTextContent();

                /*
                i = 0 -> ground
                i = 1 -> walls, trebuie coliziuni
                i = 2 -> obiecte decor, trebuie coliziuni
                i = 3 ->obiecte interactive
                 */
             /*   if ( i <= 2 ) {
                    tileMap.add( new TileMapNorm() );
                } else {
                    tileMap.add( new TileMapObj() );
                } */
            }

            System.out.println("XML parsed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void render(Graphics2D g) {

    }

}
