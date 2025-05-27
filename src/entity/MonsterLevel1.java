package entity;

import main.GamePanel;

public class MonsterLevel1 extends Monster{

    public MonsterLevel1(GamePanel gp) {
        super(gp);
        getPlayerImage();
        setDialogue();
    }

    public void getPlayerImage() {
        animationSet.loadSeparate("npc", dir , "npc_walk", 23); // 23 cadre per direcție
    }

    public void setDialogue(){
        dialogues[0] = "You are trapped here\nIn order to escape, you have to tell me the right number...\n" +
                "Be careful, cause your time here is limited!";
    }

}
