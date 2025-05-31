package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_HealthPotion extends SuperObject{
    GamePanel gp;
    public OBJ_HealthPotion(GamePanel gp){

        this.gp = gp;
        type = 6;
        name = "HealthPotion";
        value = 1;
        price = 1;
        description = "[Health potion]\nGives back 1 life...";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/healthPotion.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE HEALTH POTION NU S A GASIT");
        }

    }

    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life  > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
            //gp.playSE(2);
    }
}
