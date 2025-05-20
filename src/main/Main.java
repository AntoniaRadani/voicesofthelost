package main;

import javax.swing.*;

public class Main {

    public static JFrame window;  // 🔹 static pentru a fi accesibil global

    public static void main(String[] args) {

        window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true); // fullscreen la start
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
        gamePanel.setUpGame();
    }
}
