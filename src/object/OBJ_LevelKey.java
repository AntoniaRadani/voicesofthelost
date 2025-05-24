package object;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_LevelKey extends SuperObject {
    GamePanel gp;
    public OBJ_LevelKey(GamePanel gp){
        this.gp = gp;
        name = "LevelKey";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/level_key.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE CHEIE NU S A GASIT");
        }
        description = "[Key]\nOpens the door to\n the the unknown...";

    }
}