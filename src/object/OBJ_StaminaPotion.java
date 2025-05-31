package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_StaminaPotion extends SuperObject{
    GamePanel gp;
    public OBJ_StaminaPotion(GamePanel gp){

        this.gp = gp;
        type = 6;
        name = "StaminaPotion";
        value = 1;
        price = 1;
        description = "[Health potion]\nGives back 1 stamina...";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/staminaPotion.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE STAMINA POTION NU S A GASIT");
        }

    }

    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your stamina has been recovered by " + value + ".";
        entity.stamina += value;
        if(gp.player.stamina  > gp.player.maxStamina){
            gp.player.stamina = gp.player.maxStamina;
        }
        //gp.playSE(2);
    }
}
