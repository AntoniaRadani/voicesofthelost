package entity;

import main.GamePanel;

public class MonsterLevel1 extends Monster{


    public void getPlayerImage() {

        animationSet.loadSeparate("npc", dir , "npc_walk", 23); // 23 cadre per direcție
    }


    public MonsterLevel1(GamePanel gp) {
        super(gp);
        System.out.println("CREAT MONSTRU");
        life = 3;

        getPlayerImage();
        setDialogue();
    }

    @Override
    public void update() {
        super.update();
        // adaugă aici comportamentul specific (mișcare, atac)
    }

    public void setDialogue(){
        dialogues[0] = "You are trapped here\nIn order to escape, you have to tell me the right number...\n" +
                "Be careful, cause your time here is limited!";
    }

}
