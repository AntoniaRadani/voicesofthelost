package object;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Coin extends SuperObject {
    GamePanel gp;
    public OBJ_Coin(GamePanel gp){
        this.gp = gp;
        name = "Coin";
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/coin.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image = uTool.rescale(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE COIN NU S A GASIT");
        }
    }
}
