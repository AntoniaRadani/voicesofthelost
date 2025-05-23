package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_ChestLevel1 extends SuperObject{
    GamePanel gp;
    public OBJ_ChestLevel1(GamePanel gp){
        this.gp = gp;
        name = "ChestLevel1";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/closed_chest.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE CUFAR NIVEL 1 NU S A GASIT");
        }
    }
}
