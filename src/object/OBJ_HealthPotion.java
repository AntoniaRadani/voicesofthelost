package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_HealthPotion extends SuperObject{
    GamePanel gp;
    public OBJ_HealthPotion(GamePanel gp){
        this.gp = gp;
        name = "HealthPotion";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/health_potion.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE HEALTH POTION NU S A GASIT");
            e.printStackTrace();
        }
        description = "[Health potion]\nGives back 1 life...";

    }
}
