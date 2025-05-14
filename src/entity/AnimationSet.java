package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AnimationSet {

        private final HashMap<String, BufferedImage[]> frames = new HashMap<>();

    public void loadSeparate(String folderName, String[] directions, String sprite, int frameCount) {
        for (String dir : directions) {
            BufferedImage[] dirFrames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                String path = "/" + folderName + "/" + sprite + "_" + dir + "_" + i + ".png";
                InputStream is = getClass().getResourceAsStream(path);
                if (is == null) {
                    System.err.println("⚠️ Sprite lipsă: " + path);
                    continue; // sau returnează/întrerupe complet dacă e critic
                }
                try {
                    dirFrames[i] = ImageIO.read(is);
                } catch (IOException e) {
                    System.err.println("❌ Eroare citire imagine la: " + path);
                    e.printStackTrace();
                }
            }
            frames.put(dir, dirFrames);
        }
    }


    public BufferedImage getFrame(String direction, int index) {
            BufferedImage[] dirFrames = frames.get(direction);
            if (dirFrames != null && index < dirFrames.length) {
                return dirFrames[index];
            }
            return null;
        }

        public int getFrameCount(String direction) {
            return frames.get(direction).length;
        }


}
