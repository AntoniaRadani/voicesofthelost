package main;

import javax.swing.*;

public class Main {

    private static void createWindow(){

    }

    public static void main(String[] args) {

        // create a new window
        JFrame window = new JFrame();
        // ends the app when the window is closed
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // not resizable
        window.setResizable(false);
        window.setTitle("Voices of Lost");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // causes this window to be sized to fit the preferred size and layouts
        // of its subcomponents ( = GamePanel)
        window.pack();
        // puts the window in the center of the screen
        window.setLocationRelativeTo(null);
        // make the window visible on screen
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}