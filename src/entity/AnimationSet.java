package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimationSet {

    public HashMap<String, ArrayList<BufferedImage>> animations = new HashMap<>();

    public void load(String namePrefix, String[] directions, int frameCount, String folder) {
        try {
            for (String dir : directions) {
                ArrayList<BufferedImage> frames = new ArrayList<>();
                for (int i = 1; i < frameCount; i++) {
                    String path = "/" + folder + "/" + namePrefix + "_" + dir + "_" + i + ".png";
                   // String path = "/player/boy_down_1.png";
                    InputStream is = getClass().getResourceAsStream(path);

                    if (is == null) {
                        System.err.println("NU S-A GASIT: " + path);
                    } else {
                        frames.add(ImageIO.read(is));
                    }
                }
                animations.put(dir, frames);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getFrame(String direction, int index) {
        return animations.get(direction).get(index % animations.get(direction).size());
    }

}
