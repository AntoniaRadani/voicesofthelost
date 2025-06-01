package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.awt.image.BufferedImage;

public class OBJ_ClosedDoor5 extends SuperObject {
    GamePanel gp;
    public OBJ_ClosedDoor5(GamePanel gp){
        this.gp = gp;
        name = "ClosedDoor5";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/closed_door5.png")));
            image = uTool.scaleImage(image, gp.tileSize, 2 * gp.tileSize);
            image = uTool.rescale(image, gp.tileSize, 2 * gp.tileSize);
            solidArea = new Rectangle(0, 0, gp.tileSize, 2 * gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE USA INCHISA NU S A GASIT");
            e.printStackTrace();
        }
    }
}
