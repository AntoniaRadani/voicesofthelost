package main;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_20;
    Font arial_80;
    BufferedImage keyImage;
    BufferedImage heart_full;
    BufferedImage heart_half;
    BufferedImage heart_empty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public String currentDialogue = "";

    public UI(GamePanel gp){
        this.gp = gp;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        arial_80 = new Font("Arial", Font.BOLD, 80);

        // create hud object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(arial_20);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            drawPlayerLife();
        }

        if(gp.gameState == gp.pauseState) {
            // do nothing, handled in GamePause
        }

        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

        if(gp.gameState == gp.characterStatus){
            drawCharacterScreen();
        }

//        if(gameFinished){
//            g2.setFont(arial_20);
//            g2.setColor(Color.WHITE);
//            String text;
//            int textLength;
//            int x;
//            int y;
//            text = "YOU FINISHED";
//            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = (gp.screenWidth  - textLength)/ 2;
//            y = (gp.screenHeight  - textLength) / 2;
//            g2.drawString(text, x, y);
//        }
//        else {
//            g2.setFont(arial_20);
//            g2.setColor(Color.WHITE);
//            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize / 2, null);
//            g2.drawString("Key = " + gp.player.hasKey, 50, 50);
//
//            // player time
//
//            playTime += (double)1/60;
//            g2.drawString( "PLAY TIME : " + dFormat.format(playTime), gp.tileSize * 12, 50);
//            // message
//            if (messageOn) {
//                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
//                messageCounter++;
//                if (messageCounter > 120) {
//                    // the text disappears after 2 seconds
//                    messageCounter = 0;
//                    messageOn = false;
//                }
//           }
//        }
    }

    public void drawCharacterStatus(){

    }

    public void drawCharacterScreen(){

        // creating a frame
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight  = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // text
        g2.setColor(Color.white);
        g2.setFont(arial_20);
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        // names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Cards", textX, textY);
        textY += lineHeight;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        // values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;


        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.cards);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.currentWeapon.attack);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.currentShield.defense);
        textX = getXForAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
    }

    public void drawPlayerLife(){

        gp.player.life = 2;

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // draw max life
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawDialogueScreen(){
        // window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 5;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;

        // to show the dialogue on lines
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXForAlignToRight(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}


