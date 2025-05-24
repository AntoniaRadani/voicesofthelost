package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.awt.image.BufferedImage;

public class OBJ_ClosedDoor extends SuperObject {
    GamePanel gp;
    public OBJ_ClosedDoor(GamePanel gp){

        this.gp = gp;
        name = "ClosedDoor";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/closed_door.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image = uTool.rescale(image, 3 * gp.tileSize, 2 * gp.tileSize);
            solidArea = new Rectangle(0, 0, 3 * gp.tileSize, 2 * gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE USA INCHISA NU S A GASIT");
            e.printStackTrace();
        }
    }
}
