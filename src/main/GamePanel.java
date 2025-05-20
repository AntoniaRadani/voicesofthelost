package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import tile.TiledMapViewer;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
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

    // OBJECTS


    // FPS
    int FPS = 60;

    // SYSTEM
    public TiledMapViewer tiledMapViewer = new TiledMapViewer("res/level1/level1.tmx", this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread; // keeps our program running
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    Sound sound = new Sound();
    GameMenu menu = new GameMenu(this);
    MouseHandler mouseH = new MouseHandler(this);
    GamePause pause = new GamePause(this);
    public UI ui = new UI(this);
    public SuperObject obj[] = new SuperObject[10];
    // for npc

    public Entity[] npc = new Entity[10];

    // GAME STATES <- pentru meniu
    // 0 = MENU
    // 1 = JOC
    // 2 = PAUSED
    // 3 = CHARACTER STATUS
    public int gameState; // the game starts at the menu
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int characterStatus = 3;
    public final int dialogueState = 4;

    int menuOption = 0;
    // 0 = INITIAL STATE (draw)
    // 1 = START GAME
    // 2 = QUIT GAME
    // 3 = SETTINGS
    // 4 = INSTRUCTIUNI

    int pauseOption = 0;
    // 0 = PAUSED
    // 1 = RELOADED

    public GamePanel() {
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

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setUpGame() {
        aSetter.setObject();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }

    public void update() {

        if (gameState == 0)
            // menu mode
            menu.update();

        else if (gameState == 1)
            // game mode
            player.update();
        else if (gameState == 2)
            // pause mode
            pause.update();
        else if (gameState == 1)
            // game mode
            player.update();
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].update();
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (gameState == menuState) {
            // drawing the menu
            menu.draw(g2);
        } else if (gameState == playState || gameState == dialogueState || gameState == characterStatus) {

            // tile
            tiledMapViewer.draw(g2);

            // npc
            for ( int i = 0; i < npc.length; i++ ) {
                if ( npc[i] != null ) {
                    npc[i].draw(g2, this);
                }
            }

            // object
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }

            // player
            player.draw(g2);

            // ui
            ui.draw(g2);
        }
        else if (gameState == pauseState) {
            pause.draw(g2);
        }

        // to dispose of this graphic context and release
        // any system resources that its using
        g2.dispose();
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }

    public void playSE(int i){
        sound.setFile(i);
        sound.play();
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