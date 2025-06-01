package main;

import entity.Entity;
import entity.Monster;
import entity.Player;
import environment.EnvironmentManager;
import object.SuperObject;
import tile.Map;

import tile.TiledMapViewer;

import entity.Monster;
import trap_room.TrapRoomLevel1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
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
    public boolean hoverResume = false;

    public boolean isVolumeMuted = false;
    public boolean isSoundMuted = false;

    // the default size of the player
    public int originalTileSize = 16; // 16 x 16 tile size
    public final int scale = 3; // to make our player and tiles bigger

    public int tileSize = originalTileSize * scale; // 48 x 48 the actual tile size
    public int maxScreenCol = 16; // how many tiles can we see - column
    public int maxScreenRow = 12; // how many tiles can we see - row
    public int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // for camera limits

    // volume

    // for maps
    public final int maxMap = 3; // the max number of maps that we can have
    public int currentMap = 0;
    public boolean showFullMap = false;
    public boolean showLighting = false;

    // for full screen
    public int screenWidth2 = screenWidth;
    public int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2temp;

    public boolean isFullscreen = true;

    // volume

    public int volumeLevel = 100; // intre 0 și 100
    public int previousVolumeLevel = 100;
    public boolean draggingVolume = false;

    // sound

    public int soundLevel = 100;
    public int previousSoundLevel = 100;
    public boolean draggingSound = false;

    // WORLD PARAMETERS

    public int maxWorldCol = 100;
    public int maxWorldRow = 100;
    public int worldWidth = tileSize * maxWorldCol;
    public int worldHeight = tileSize * maxWorldRow;

    // OBJECTS

    // for levels
    public int currentLevel = 1; // start


    // FPS
    int FPS = 60;

    // SYSTEM
    public TiledMapViewer tiledMapViewer = new TiledMapViewer(getPathForLevel(1), this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread; // keeps our program running
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    Sound sound = new Sound();
    GameMenu menu = new GameMenu(this);
    MouseHandler mouseH = new MouseHandler(this);
    GamePause pause = new GamePause(this);
    GameOver gameOver = new GameOver(this);
    WinGame winGame = new WinGame(this);
    public UI ui = new UI(this);
    public SuperObject[][] obj = new SuperObject[3][10];
    public Monster[][] monsters = new Monster[3][10];
    public TrapRoomLevel1 trapRoomLevel1 = new TrapRoomLevel1(this);
    EnvironmentManager eManager = new EnvironmentManager(this);

    // for trap room 1
    public boolean waitingForNumberInput = false;
    public String inputNumber = "";
    public boolean escapedFromTrapRoom = false;
    public final int correctNumber = 14;
    public boolean roomCleared = false;
    public Map map = new Map("res/level1/level1.tmx", this);

    // for npc

    public Entity[][] npc = new Entity[3][10];

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
    public final int mapState = 10;
    public final int tradeState = 5;
    public final int gameOverState = 6;
    public final int winState = 7;

    int menuOption = 0;
    // 0 = INITIAL STATE (draw)
    // 1 = START GAME
    // 2 = QUIT GAME
    // 3 = SETTINGS
    // 4 = INSTRUCTIUNI

    int pauseOption = 0;
    // 0 = PAUSED
    // 1 = RELOADED

    public int overOption = 0;
    // 0 = INITIAL STATE
    // 1 = RESTART
    // 2 = QUIT

    public int winOption = 0;
    // 0 = INITIAL STATE
    // 1 = QUIT

    // for zoom
    int targetTileSize = tileSize;
    boolean zooming = false;
    int zoomSpeed = 2; // cât de repede se face zoom-ul


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
    // pe g2temp vom desena tot jocul, apoi scalam in paintcomponewnt
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2temp = (Graphics2D) tempScreen.getGraphics();

        aSetter.setNPC();
    }

    public void winGame(){
        gameState = winState;
        winOption = 0;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void retry(){
        player.setDefaultPosition();
        player.setDefaultValues();
        player.restoreLifeAndMana();
        roomCleared = false;
        trapRoomLevel1.active = false;
        escapedFromTrapRoom = false;
        loadLevel(1);
    }

    public void restart(){
        player.setDefaultValues();
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        roomCleared = false;
        trapRoomLevel1.active = false;
        escapedFromTrapRoom = false;
        loadLevel(1);
    }

    public void setUpGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        eManager.setup();
        sound.setVolume(volumeLevel);
        playMusic(0);
    }

    public void update() {

        sound.setVolume(volumeLevel);

        if (gameState == menuState)
            // menu mode
            menu.update();

        else if (gameState == playState) {
            // game mode
            player.update();

            for (int i = 0; i < npc[currentMap].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            for (int i = 0; i < monsters[currentMap].length; i++) {
                if (monsters[currentMap][i] != null) {
                    monsters[currentMap][i].update();
                }
            }

            if(!roomCleared) {
                trapRoomLevel1.update();
                if (escapedFromTrapRoom == true)
                    roomCleared = true;
            }
        }
        else if (gameState == pauseState)
            // pause mode
            pause.update();
        else if(gameState == gameOverState)
            // game over
            gameOver.update();
        else if(gameState == winState)
            winGame.update();

        if(!roomCleared) {
            trapRoomLevel1.update();
            if (escapedFromTrapRoom == true)
                roomCleared = true;
        }

    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        // curatam/ eliberam canvas ul intern, ca sa nu se suprapuna imaginile
        g2temp.setColor(Color.BLACK);
        g2temp.fillRect(0, 0, screenWidth, screenHeight);

        if (gameState == menuState) {
            // drawing the menu
            menu.draw(g2temp);
        } else if (gameState == playState || gameState == dialogueState
                || gameState == characterStatus || gameState == tradeState) {

            // tile
            tiledMapViewer.draw(g2temp);

            // npc
            for ( int i = 0; i < npc[currentMap].length; i++ ) {
                if ( npc[currentMap][i] != null ) {
                    npc[currentMap][i].draw(g2temp, this);
                }
            }

            // monster
            for ( int i = 0; i < monsters[currentMap].length; i++ ) {
                if ( monsters[currentMap][i] != null ) {
                    monsters[currentMap][i].draw(g2temp, this);
                }
            }

            // object
            for(int i = 0; i < obj[currentMap].length; i++){
                if(obj[currentMap][i] != null){
                    obj[currentMap][i].draw(g2temp, this);
                }
            }

            // player
            player.draw(g2temp);

            // mini map
            if (showFullMap == true) {
                 map.drawFullMapScreen(g2temp);
            }

            // minimap
            map.drawMiniMap(g2temp);

            // lighting
            if (showLighting) {
                eManager.draw(g2temp);
            }
            // ui
            ui.draw(g2temp);
        }
        else if (gameState == pauseState) {
            pause.draw(g2temp);
        }
        else if(gameState == gameOverState){
            gameOver.draw(g2temp);
        }
        else if(gameState == winState)
            winGame.draw(g2temp);
        // calculam cu cat trebuie scalat pt full screen

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        double scaleX = (double) panelWidth / screenWidth;
        double scaleY = (double) panelHeight / screenHeight;
        double scale = Math.min(scaleX, scaleY); // păstrează proporția

        int drawWidth = (int)(screenWidth * scale);
        int drawHeight = (int)(screenHeight * scale);

        int drawX = (panelWidth - drawWidth) / 2;
        int drawY = (panelHeight - drawHeight) / 2;

        // to dispose of this graphic context and release
        // any system resources that its using
        // g2temp este un canvas intern pe care desenam jocul, iar g2 este graficul real al ferestrei
        g2.drawImage(tempScreen, drawX, drawY, drawWidth, drawHeight, null);
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
        sound.setVolume(soundLevel);
        sound.play();
    }


    public void toggleFullscreen() {
        GraphicsDevice device = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        if (isFullscreen) {
            // ieșire din fullscreen, fereastră normală CU decorații
            device.setFullScreenWindow(null);

            Main.window.dispose(); // refacem frame-ul
            Main.window.setUndecorated(false); // adaugam bara de titlu cu X
            Main.window.setVisible(true);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Main.window.setSize(screenSize);
            Main.window.setLocationRelativeTo(null);

            isFullscreen = false;
        } else {
            // intrare în fullscreen: fără decorații
            Main.window.dispose();
            Main.window.setUndecorated(true);
            Main.window.setVisible(true);

            device.setFullScreenWindow(Main.window); // FULL fullscreen (fără bară/dock)
            isFullscreen = true;
        }

        this.requestFocusInWindow(); // readuce focusul tastelor
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
                drawCount = 0;
                timer = 0;
            }
            if ( timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    public void loadLevel(int level ) {

        currentLevel = level; // update

        switch (level) {
            case 1:
                currentMap = 0;
                tiledMapViewer.loadTMX("res/level1/level1.tmx");
                tiledMapViewer.loadMap("res/level1/level1.tmx");
                // reincarcam pe noua mapa
                aSetter.setObject();
                aSetter.setNPC();
                aSetter.setMonster();
                player.setPlayerStartPosition(level);
                break;
            case 2:
                System.out.println("soon level 2");
                currentMap = 1;
                tiledMapViewer.resetMap();
                tiledMapViewer.loadTMX("res/level2/level2.tmx");
                tiledMapViewer.loadMap("res/level2/level2.tmx");
                for (int i = 0; i < obj[0].length; i++) {
                    obj[0][i] = null;
                }

                for (int i = 0; i < npc[0].length; i++) {
                    npc[0][i] = null;

                }
                // reincarcam pe noua mapa
                aSetter.setObject();
                aSetter.setNPC();
                aSetter.setMonster();
                player.setPlayerStartPosition(level);
                break;
            case 3:
                System.out.println(" soon level 3");
                currentMap = 2;
                tiledMapViewer.resetMap();
                tiledMapViewer.loadTMX("res/level3/level3.tmx");
                tiledMapViewer.loadMap("res/level3/level3.tmx");
                for (int i = 0; i < obj[1].length; i++) {
                    obj[1][i] = null;
                }

                for (int i = 0; i < npc[1].length; i++) {
                    npc[1][i] = null;

                }
                // reincarcam pe noua mapa
                aSetter.setObject();
                aSetter.setNPC();
                aSetter.setMonster();
                player.setPlayerStartPosition(level);
                break;
                // trebuie puse si obj, npc, si alte detalii la fiecare. cel mai bine ar fi in functiile de set sa facem cu case
        }

        tileSize = tiledMapViewer.tileWidth;
        System.out.println(tiledMapViewer.mapWidth);
        System.out.println(tiledMapViewer.mapHeight);
        maxWorldCol = tiledMapViewer.mapWidth;
        maxWorldRow = tiledMapViewer.mapHeight;

        tiledMapViewer.updateCamera(player.worldX, player.worldY);

        map = new Map(getPathForLevel(level), this);

        System.out.println(" LEVEL CHANGED TO: " + level );

    }

    public void adjustZoom(boolean zoomIn) {

        int zoomStep = 4;
        if (zoomIn) {
            targetTileSize = tileSize + zoomStep; // zoom mai mare
        } else {
            targetTileSize = Math.max(4, tileSize - zoomStep); // zoom out
        }

        zooming = true; // pornește animarea
    }

    public void zoom() {
        if (zooming) {
            // Salvează poziția vizuală a playerului
            int playerScreenXBefore = player.worldX - tiledMapViewer.screenX;
            int playerScreenYBefore = player.worldY - tiledMapViewer.screenY;

            // Modificăm tileSize treptat
            if (tileSize < targetTileSize) {
                tileSize += zoomSpeed;
                if (tileSize > targetTileSize) tileSize = targetTileSize;
            } else if (tileSize > targetTileSize) {
                tileSize -= zoomSpeed;
                if (tileSize < targetTileSize) tileSize = targetTileSize;
            }

            // Când am ajuns la destinație, oprim animarea
            if (tileSize == targetTileSize) {
                zooming = false;
            }

            // Actualizăm dimensiunile ecranului
            screenWidth2 = tileSize * maxScreenCol;
            screenHeight2 = tileSize * maxScreenRow;

            // Refacem tempScreen
            tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            g2temp = (Graphics2D) tempScreen.getGraphics();

            // Repoziționăm camera pentru a menține poziția vizuală a jucătorului
            tiledMapViewer.screenX = player.worldX - playerScreenXBefore;
            tiledMapViewer.screenY = player.worldY - playerScreenYBefore;

            // Clamp și recalculare
            tiledMapViewer.clampCamera();
            player.calculateScreenPosition();
        }



    }

    public String getPathForLevel(int level) {
        switch (level) {
            case 1: return "res/level1/level1.tmx";
            case 2: return "res/level2/level2.tmx";
            case 3: return "res/level3/level3.tmx";
        }
        return "res/level1/level1.tmx";
    }








}