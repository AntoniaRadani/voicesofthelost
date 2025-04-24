package tile;

import tile.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

public class Sprite {

    private BufferedImage SPRITE_SHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32; // posibil sa trebuiasca sa schimbam, dar detalii
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;


    public Sprite(String file) {

        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading: " + file + "..." );
        SPRITE_SHEET = loadSprite(file);

        wSprite = SPRITE_SHEET.getWidth() / w;
        hSprite = SPRITE_SHEET.getHeight() / h;

        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {

        this.w = w;
        this.h = h;

        System.out.println("Loading: " + file + "..." );
        SPRITE_SHEET = loadSprite(file);

        wSprite = SPRITE_SHEET.getWidth() / w;
        hSprite = SPRITE_SHEET.getHeight() / h;

        loadSpriteArray();

    }

    public void setSize( int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth( int w ) {
        this.w = w;
        wSprite = SPRITE_SHEET.getWidth() / w;
    }

    public void setHeight( int h ) {
        this.h = h;
        hSprite = SPRITE_SHEET.getHeight() / h;
    }

    public int getWidth() {
        return this.w;
    }

    public int getHeight() {
        return this.h;
    }

    private BufferedImage loadSprite( String file ) {

        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file) );

        } catch (Exception e) {
            System.out.println(" ERROR: could not load file: " + file);
        }
        return sprite;
    }

    public void loadSpriteArray() {

        spriteArray = new BufferedImage[wSprite][hSprite]; // daca nu merge schimba h cu w

        for( int x = 0; x < wSprite; x++ ) {
            for ( int y = 0; y < hSprite; y++ ) {
                spriteArray[x][y] = getSprite( x, y );
            }
        }
    }

    public BufferedImage getSpriteSheet() {
        return SPRITE_SHEET;
    }

    public BufferedImage getSprite(int x, int y) {
        return SPRITE_SHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage[] getSpriteArray( int i ) {
        return spriteArray[i];
    }

    public BufferedImage[][] getSpriteArray2( int i ) {
        return spriteArray;
    }

    public static  void drawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2f pos, int width, int height, int xOffSet, int yOffSet) {
        float x = pos.x;
        float y = pos.y;

        for ( int i = 0; i < img.size(); i++ ) {
            if( img.get(i) != null ) {
                g.drawImage( img.get(i), (int) x, (int) y, width, height, null);
            }

            x += xOffSet;
            y += yOffSet;
        }
    }


}
