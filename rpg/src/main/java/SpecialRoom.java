import java.io.Serializable;

public class SpecialRoom extends Room implements Serializable {
    private int requirement;
    private static final long SerialVersionUID = 1L;



    public void Room(String location, int n, int[] directions, String[] colours,int itemNo, NPC occupant, int requirement) {
        this.requirement = requirement;
        super.Room(location, n, directions, colours, itemNo, occupant);
    }

    public int whatRequirement(){
        return requirement;
    }
}


/*Make this work*/