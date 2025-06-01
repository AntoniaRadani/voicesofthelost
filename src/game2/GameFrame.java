package game2;

import java.awt.*;

import javax.swing.*;

public class GameFrame extends JFrame{

    GamePanel panel;

    public GameFrame(){

        this.panel = new GamePanel(this);
        this.add(panel);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}