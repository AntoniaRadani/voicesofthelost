package object;

import main.GamePanel;

public class ObjectFactory implements SuperObjectFactory {

    @Override
    public SuperObject createObject(String objectType, GamePanel gp) {
        return switch (objectType) {
            case "Apple" -> new OBJ_Apple(gp);
            case "Card" -> new OBJ_Card(gp);
            case "Chest" -> new OBJ_Chest(gp);
            case "Chest2" -> new OBJ_Chest2(gp);
            case "ChestLevel1" -> new OBJ_ChestLevel1(gp);
            case "ChestLevel2" -> new OBJ_ChestLevel2(gp);
            case "ClosedDoor" -> new OBJ_ClosedDoor(gp);
            case "ClosedDoor2" -> new OBJ_ClosedDoor2(gp);
            case "Door" -> new OBJ_Door(gp);
            case "HealthPotion" -> new OBJ_HealthPotion(gp);
            case "Table" -> new OBJ_Table(gp);
            default -> {
                System.err.println("Unknown object type: " + objectType);
                yield null; // Or throw an IllegalArgumentException
            }
        };
    }
}

// concrete class