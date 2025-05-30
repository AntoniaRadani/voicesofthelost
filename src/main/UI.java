package main;

import entity.Entity;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public int subState;
    public Font arial_20;
    Font arial_80;
    BufferedImage keyImage;
    BufferedImage heart_full;
    BufferedImage heart_half;
    BufferedImage heart_empty;
    BufferedImage stamina1;
    BufferedImage stamina2;
    BufferedImage stamina3;
    BufferedImage stamina4;
    BufferedImage stamina5;
    BufferedImage stamina6;
    BufferedImage coin;
    public int commandNum;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public String currentDialogue = "";
    public int playerslotCol = 0;
    public int playerslotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public boolean inputActive = false;
    public String inputString = "";
    public String feedbackMessage = "";
    public Entity npc;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        arial_80 = new Font("Arial", Font.BOLD, 80);

        // create hud object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;

        SuperObject stamina = new OBJ_Stamina(gp);
        stamina1 = stamina.image;
        stamina2 = stamina.image2;
        stamina3 = stamina.image3;
        stamina4 = stamina.image4;
        stamina5 = stamina.image5;
        stamina6 = stamina.image6;

        SuperObject coins = new OBJ_Coin(gp);
        coin = coins.image;
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(arial_20);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawPlayerStamina();
        }

        if(gp.gameState == gp.pauseState) {
            // do nothing, handled in GamePause
        }

        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawPlayerStamina();
            drawDialogueScreen();
        }

        if(gp.gameState == gp.characterStatus){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        if (gp.waitingForNumberInput) {
            inputMessage();
        }

        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
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

    private void drawTradeScreen() {
        switch(subState){
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
            default: break;
        }
        gp.keyH.enterPressed = false;
        
    }

    private void trade_sell() {
        // draw player inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // draw hint window
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 58);

        // draw player coin window
        x = gp.tileSize * 9;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your coins : " + gp.player.coin, x + 24, y + 58);

        // draw price window
        int itemIndex = getItemIndexOnSlot(playerslotCol, playerslotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize * 13.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 10, 30, 30, null);

            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXForAlignToRight(text, gp.tileSize * 15);
            g2.drawString(text, x, y + 32);

            // sell an item
            if(gp.keyH.enterPressed){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentShield ||
                gp.player.inventory.get(itemIndex) == gp.player.currentWeapon){
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot sell an equipped item!";
                }
                else{
                    gp.player.inventory.remove(itemIndex);
                    gp.player.coin += price;
                }
            }
        }
    }

    private void trade_buy() {
        // draw player inventory
        drawInventory(gp.player, false);
        // draw npc inventory
        drawInventory(npc, true);

        // draw hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 58);

        // draw player coin window
        x = gp.tileSize * 9;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your coins : " + gp.player.coin, x + 24, y + 58);

        // draw price window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 10, 30, 30, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForAlignToRight(text, gp.tileSize * 7) + 10;
            g2.drawString(text, x, y + 32);

            // buy an item

            if(gp.keyH.enterPressed){
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more money to buy this";
                    drawDialogueScreen();
                }
                else if(gp.player.inventory.size() == gp.player.maxInventorySize){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot carry anymore";
                    drawDialogueScreen();
                }
                else{
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                }
            }
        }


    }

    private void trade_select() {
        drawDialogueScreen();

        // draw window
        int x = gp.tileSize * 12;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        // draw texts
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed){
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed){
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Goodbye.";

            }
        }
    }

    public void drawInventory(Entity entity, boolean cursor){

        System.out.println("APEL FUNCTIE DRAW INVENTORY");
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            System.out.println("APEL FUNCTIE IF ENTITY + PLAYER");
            frameX = gp.tileSize * 9;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerslotCol;
            slotRow = playerslotRow;
        }else{
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // draw player items
        System.out.println(entity.inventory.size());
        for(int i = 0; i < entity.inventory.size(); i++){

            // equip cursor
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);}

            System.out.println(entity.inventory.size());
            BufferedImage itemImage = entity.inventory.get(i).image;
            g2.drawImage(itemImage, slotX, slotY, null);
            slotX += slotSize;

            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // cursor
        if(cursor){

            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // draw cursor
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;


            // draw description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(arial_20);

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if(itemIndex < entity.inventory.size()){
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for(String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }

        }

    }

    public int getItemIndexOnSlot(int slotCol, int slotRow){
        return slotCol + (slotRow * 5);
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
        textY += gp.tileSize + 15;
        g2.drawString("Weapon", textX, textY);
        textY += gp.tileSize;
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

        if(gp.player.currentWeapon != null)
            g2.drawImage(gp.player.currentWeapon.image, tailX - gp.tileSize, textY, null);
        textY += gp.tileSize;

        if(gp.player.currentShield != null)
            g2.drawImage(gp.player.currentShield.image, tailX - gp.tileSize, textY, null);

    }

    public void drawPlayerStamina(){

        int currentStamina = gp.player.stamina;

        int x = gp.tileSize / 2;
        int y = gp.tileSize + 30;

        BufferedImage staminaImg = stamina1;

        switch (currentStamina) {
            case 5:
                staminaImg = stamina1;
                break;
            case 4:
                staminaImg = stamina2;
                break;
            case 3:
                staminaImg = stamina3;
                break;
            case 2:
                staminaImg = stamina4;
                break;
            case 1:
                staminaImg = stamina5;
                break;
            case 0:
                staminaImg = stamina6;
                break;
        };

        g2.drawImage(staminaImg, x, y, null);

    }

    public void drawPlayerLife(){

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

    public void inputMessage(){
        int x = gp.tileSize * 2;
        int y = gp.screenHeight - gp.tileSize * 4;
        int width = gp.tileSize * 10;
        int height = gp.tileSize * 3;
        drawSubWindow(x - 10, y - 30, width, height);

        g2.setColor(Color.WHITE);
        g2.setFont(arial_20);
        g2.drawString("Introdu numar: " + inputString, x, y);

        if (!feedbackMessage.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18f));
            g2.drawString(feedbackMessage, x, y + 30);
        }

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


