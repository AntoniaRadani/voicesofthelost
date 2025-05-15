package main;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static JFrame window;

   /* public static void main(String[] args) {

            window = new JFrame();
            GamePanel gamePanel = new GamePanel();

            window.setUndecorated(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Voices of Lost");
            window.add(gamePanel);
            window.pack();
            window.setLocationRelativeTo(null);

            // ⚠️ NU apelăm nimic încă

            SwingUtilities.invokeLater(() -> {
                gamePanel.setupGame();         // ✅ Apelăm când totul e safe
                window.setVisible(true);       // ✅ Abia acum
                gamePanel.startGameThread();   // ✅ Loopul după visible
            });
       } */

    public static void main(String[] args) {
        window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.setUndecorated(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Test Game");
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ❗ ACUM putem accesa dimensiunea reală
        gamePanel.screenWidth = window.getWidth();
        gamePanel.screenHeight = window.getHeight();
        gamePanel.screenWidth2 = window.getWidth();
        gamePanel.screenHeight2 = window.getHeight();
        System.out.println("[MAIN] Fullscreen: " + gamePanel.screenWidth2 + "x" + gamePanel.screenHeight2);

        // APELĂM TOT DUPĂ setVisible
        window.setVisible(true);

        gamePanel.setupGame();         // se poate apela acum
        gamePanel.startGameThread();   // loop pornește
    }

}
