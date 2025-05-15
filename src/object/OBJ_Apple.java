package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Apple extends SuperObject{
    public OBJ_Apple(){
        name = "Apple";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/apple.png")));
        }catch(IOException e){
            System.out.println("IMAGINE MAR NU S A GASIT");
            e.printStackTrace();
        }
    }
}
