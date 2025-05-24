package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Table extends SuperObject{
    GamePanel gp;

    public OBJ_Table(GamePanel gp){

        this.gp = gp;
        name = "Table";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/table.png")));
            image = uTool.scaleImage(image, 2 * gp.tileSize, 2 * gp.tileSize);
            image = uTool.rescale(image, 2 * gp.tileSize, 2 * gp.tileSize);
            solidArea = new Rectangle(0, 0, 2 * gp.tileSize, 2 * gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE MASA NU S A GASIT");
            e.printStackTrace();
        }
    }
}
