package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject{
    GamePanel gp;

    public OBJ_Door(GamePanel gp){

        this.gp = gp;
        name = "Door";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/door.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image = uTool.rescale(image, 3 * gp.tileSize, 2 * gp.tileSize);
            solidArea = new Rectangle(0, 0, 3 * gp.tileSize, 2 * gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE USA NU S A GASIT");
            e.printStackTrace();
        }
    }

}
