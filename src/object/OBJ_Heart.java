package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{
    public OBJ_Heart(){
        name = "Heart";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/heart.png")));
        }catch(IOException e){
            System.out.println("IMAGINE INIMA NU S A GASIT");
            e.printStackTrace();
        }
    }
}
