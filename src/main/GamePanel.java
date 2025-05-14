package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile.TiledMapViewer;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // screen settings

    public boolean hoverExitButton = false;
    public boolean hoverVolumeSlider = false;
    public boolean hoverSoundSlider = false;
    public boolean hoverStart = false;
    public boolean hoverQuit = false;
    public boolean hoverSettings = false;
    public boolean hoverMuteVolume = false;
    public boolean hoverMuteSound = false;

    public boolean isVolumeMuted = false;
    public boolean isSoundMuted = false;

    // the default size of the player
    public final int originalTileSize = 16; // 16 x 16 tile size
    public final int scale = 3; // to make our player and tiles bigger

    public final int tileSize = originalTileSize * scale; // 48 x 48 the actual tile size
    final int maxScreenCol = 16; // how many tiles can we see - column
    final int maxScreenRow = 12; // how many tiles can we see - row
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // volume

    public int volumeLevel = 100; // intre 0 și 100
    public int previousVolumeLevel = 100;
    public boolean draggingVolume = false;


    // sound

    public int soundLevel = 100;
    public int previousSoundLevel = 100;
    public boolean draggingSound = false;


    // WORLD PARAMETERS

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

//    TileManager tileM = new TileManager("level1/level1.tmx");

public TiledMapViewer tiledMapViewer = new TiledMapViewer("res/level1/level1.tmx", this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // keeps our program running
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    GameMenu menu = new GameMenu(this);
    MouseHandler mouseH = new MouseHandler(this);

    // for npc

    public Entity []npc = new Entity[10];

    // GAME STATES <- pentru meniu
    // 0 = MENU
    // 1 = JOC
    int gameState = 0; // the game starts at the menu

    int menuOption = 0;
    // 0 = INITIAL STATE (draw)
    // 1 = START GAME
    // 2 = QUIT GAME
    // 3 = SETTINGS
    // 4 = INSTRUCTIUNI


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

        aSetter.setNPC();
    }

    public void setupGame() {

        aSetter.setNPC();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        // menu mode
        if(gameState == 0){
            menu.update();
       }
        else if (gameState == 1) {
            // game mode
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }

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

            // npc

            for ( int i = 0; i < npc.length; i++ ) {
                if ( npc[i] != null ) {
                    npc[i].draw(g2, this);
                }
            }

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
                delta--;

            }

            if ( timer >= 1000000000) {

                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;

            }
             

            if ( timer >= 1000000000) {
                timer = 0;
            }

        }
    }
}