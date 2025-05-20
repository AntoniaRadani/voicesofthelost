package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest2 extends SuperObject {
    GamePanel gp;
    public OBJ_Chest2(GamePanel gp) {
        this.gp = gp;
        name = "open_chest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/open_chest.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.out.println("IMAGINE CUFAR DESCHIS NU S A GASIT");
            e.printStackTrace();
        }
    }
}
