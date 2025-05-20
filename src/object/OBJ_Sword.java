package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

    public OBJ_Sword(GamePanel gp) {
        super(gp);
        name = "Sword";
        //down1 = setup("/object/sword", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "sabie";
    }
}
