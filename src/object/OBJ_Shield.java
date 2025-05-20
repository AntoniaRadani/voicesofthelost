package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shield extends Entity{

    public OBJ_Shield(GamePanel gp) {
        super(gp);
        name = "Shield";
        //down1 = setup("object/shield", gp.tileSize, gp.tileSize);
        defenseValue = 1;
    }
}
