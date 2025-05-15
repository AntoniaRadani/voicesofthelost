package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest2 extends SuperObject {
    public OBJ_Chest2() {
        name = "open_chest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/open_chest.png")));
        } catch (IOException e) {
            System.out.println("IMAGINE CUFAR DESCHIS NU S A GASIT");
            e.printStackTrace();
        }
    }
}
