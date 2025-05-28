package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Stamina extends SuperObject{
    GamePanel gp;
    public OBJ_Stamina(GamePanel gp){

        this.gp = gp;
        name = "Stamina";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/stamina1.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/stamina2.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/stamina3.png")));
            image4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/stamina4.png")));
            image5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/stamina5.png")));
            image6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/stamina6.png")));

            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image = uTool.rescale(image, 3 * gp.tileSize, gp.tileSize / 2);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image2 = uTool.rescale(image2, 3 * gp.tileSize, gp.tileSize / 2);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
            image3 = uTool.rescale(image3, 3 * gp.tileSize, gp.tileSize / 2);
            image4 = uTool.scaleImage(image4, gp.tileSize, gp.tileSize);
            image4 = uTool.rescale(image4, 3 * gp.tileSize, gp.tileSize / 2);
            image5 = uTool.scaleImage(image5, gp.tileSize, gp.tileSize);
            image5 = uTool.rescale(image5, 3 * gp.tileSize, gp.tileSize / 2);
            image6 = uTool.scaleImage(image6, gp.tileSize, gp.tileSize);
            image6 = uTool.rescale(image6, 3 * gp.tileSize, gp.tileSize / 2);

        }catch(IOException e){
            System.out.println("IMAGINE STAMINA NU S A GASIT");
            e.printStackTrace();
        }
    }
}
