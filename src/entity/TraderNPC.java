package entity;

import main.GamePanel;
import object.OBJ_Apple;
import object.OBJ_HealthPotion;
import object.OBJ_Key;

public class TraderNPC extends Entity{

    String [] dir = {"left" , "right"};

    public void getPlayerImage() {
        animationSet.loadSeparate("npc", dir , "npc_walk", 23); // 23 cadre per direcție
    }

    public TraderNPC(GamePanel gp) {

        super(gp);

        direction = "left";

        getPlayerImage();
        setDialogue();
        setItems();
    }

    public void setDialogue(){
        dialogues[0] = "So, you've found me.";
        dialogues[1] = "I have some good stuff...";
        dialogues[2] = "Do you want to trade?";
    }

    public void speak(){
        if(dialogues[dialogueIndex] == null)
            dialogueIndex = 0;

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        gp.gameState = gp.tradeState;
        gp.ui.npc = this;

    }

    public void setItems(){
        inventory.add(new OBJ_HealthPotion(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Apple(gp));

        // poate arme noi
    }


}
