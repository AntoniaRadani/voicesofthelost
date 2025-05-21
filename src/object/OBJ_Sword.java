package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Sword extends SuperObject {

    public OBJ_Sword(GamePanel gp) {

        System.out.println("AM CREAT OBIECT SABIE");
        type = 3;
        name = "Sword";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/sword.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("Eroare imagine sabie");
        }
        attack = 1;
        description = "sabie";
    }
}
