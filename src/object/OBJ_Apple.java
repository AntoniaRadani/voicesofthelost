package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Apple extends SuperObject{
    GamePanel gp;

    public OBJ_Apple(GamePanel gp){
        this.gp = gp;
        name = "Apple";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/apple.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE MAR NU S A GASIT");
            e.printStackTrace();
        }
    }
}
