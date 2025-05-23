package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_ClosedDoor extends SuperObject {
    GamePanel gp;
    public OBJ_ClosedDoor(GamePanel gp){

        this.gp = gp;
        name = "ClosedDoor";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/closed_door.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE USA INCHISA NU S A GASIT");
            e.printStackTrace();
        }
    }
}
