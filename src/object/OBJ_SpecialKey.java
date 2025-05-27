package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_SpecialKey extends SuperObject{
    GamePanel gp;
    public OBJ_SpecialKey(GamePanel gp){

        this.gp = gp;
        name = "SpecialKey";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/key1.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE CHEIE 1 NU S A GASIT");
        }
        description = "[Key]\nOpens the door to\n the answer...";

    }
}
