package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shield extends SuperObject{

    public OBJ_Shield(GamePanel gp) {
        type = 5;
        name = "Shield";
        //down1 = setup("/object/shield.png", gp.tileSize, gp.tileSize);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/shield.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("Eroare imagine scut");
        }
        defense = 1;
        description = "scut";
    }


}
