package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Card extends SuperObject{
    GamePanel gp;
    public OBJ_Card(GamePanel gp){
        this.gp = gp;
        name = "Card";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/card.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE CHEIE 1 NU S A GASIT");
        }
        description = "[Card]\n?";

    }
}
