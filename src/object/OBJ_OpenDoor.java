package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_OpenDoor extends SuperObject {
    GamePanel gp;
    public OBJ_OpenDoor(GamePanel gp){

        this.gp = gp;
        name = "OpenDoor";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/open_door.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE USA DESCHISA NU S A GASIT");
            e.printStackTrace();
        }
    }
}
