package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_RedSword extends SuperObject {

    public OBJ_RedSword(GamePanel gp) {

        System.out.println("AM CREAT OBIECT SABIE ROSIE");
        type = 3;
        name = "RedSword";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/red_sword.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("Eroare imagine sabie rosie");
        }
        attack = 5;
        description = "sabie rosie";
    }
}
