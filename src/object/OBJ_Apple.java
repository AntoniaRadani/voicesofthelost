package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Apple extends SuperObject{
    GamePanel gp;

    public OBJ_Apple(GamePanel gp){

        this.gp = gp;
        name = "Apple";
        type = 6;
        value = 3;
        price = 2;
        description = "[Apple]\nGives back 3 life...";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/apple.png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image = uTool.rescale(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println("IMAGINE MAR NU S A GASIT");
        }
    }

    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You ate the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life  > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        //gp.playSE(2);
    }
}
