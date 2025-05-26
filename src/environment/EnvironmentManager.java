package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {

    GamePanel gp;
    Lighting lighting;

    public EnvironmentManager( GamePanel gp ) {

        this.gp = gp;

    }

    public void setup() {
        // instantiema lighting class

        lighting = new Lighting(gp, 550);
    }

    public void draw(Graphics2D g2) {

        lighting.draw(g2);
    }
}
