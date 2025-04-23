package main;

import entity.Player;
import tile.TileManager;
import tile.TiledMapViewer;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // screen settings

    // the default size of the player
    final int originalTileSize = 16; // 16 x 16 tile size
    final int scale = 3; // to make our player and tiles bigger

    public final int tileSize = originalTileSize * scale; // 48 x 48 the actual tile size
    final int maxScreenCol = 16; // how many tiles can we see - column
    final int maxScreenRow = 12; // how many tiles can we see - row
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

//    TileManager tileM = new TileManager("level1/level1.tmx");
    TiledMapViewer tiledMapViewer = new TiledMapViewer("res/level1/level1.tmx");
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // keeps our program running
    Player player = new Player(this, keyH);
    GameMenu menu = new GameMenu(this);
    MouseHandler mouseH = new MouseHandler(this);

    // GAME STATES <- pentru meniu
    // 0 = MENU
    // 1 = JOC
    int gameState = 0; // the game starts at the menu
    int menuOption = 0; // 0 = START GAME, 1 = EXIT
    // player position on map
   /* int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4; */ // NU MAI AVEM NEVOIE.. LE STERGEM LA FINAL I GUESS

    public GamePanel(){
        // how big is the window
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        // the background color
        this.setBackground(Color.BLACK);
        // makes the drawing smoother, without flickering
        this.setDoubleBuffered(true);
        // to receive key input
        this.addKeyListener(keyH);
        // to receive mouse input
        this.addMouseListener(mouseH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        // menu mode
        if (keyH.upPressed || keyH.downPressed) {
            menuOption = (menuOption + 1) % 2; // toggle between 0 and 1 (start game and exit)
//                keyH.upPressed = true; // ?? ceva nu e ok
//                keyH.downPressed = true;
        }

        if (keyH.enterPressed) {
            if (menuOption == 0) {
                gameState = 1; // Start Game
            } else if (menuOption == 1) {
                System.exit(0); // Exit
            }
            keyH.enterPressed = false;
        }
     else if (gameState == 1)
        // game mode
        player.update();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (gameState == 0) {
            // drawing the menu
            menu.draw(g2);

        } else if (gameState == 1) {
            // updating the player

            tiledMapViewer.draw(g2);

            player.draw(g2);
        }
        // to dispose of this graphic context and release
        // any system resources that its using
        g2.dispose();
    }
    // our game loop
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval; // how much time has passed / drawInterval
            timer += (currentTime - lastTime);
            lastTime = currentTime; // updating the last time

            if (delta >= 1) {
                update();
                repaint();
                --delta;
            }

            if ( timer >= 1000000000) {

                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;

            }
        }
    }
}