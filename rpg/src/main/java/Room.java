import java.io.Serializable;


public class Room implements Serializable {
	private int doors;
	private int[] directions = new int[4];
	private String[] doorColours = new String[4];
	private NPC occupant;
    private String location;
    private int itemNo;
	private static final long serialVersionUID = 1L;

	/*requirements: {0,1,2}
	 * 0 - no requirements

	 * 5 - ID
	 * 2 - Bike Keys
	 * 4 - Debit Card
	 */

	public void Room(String location, int n, int[] directions, String[] colours,int itemNo, NPC occupant) {
		this.doors = n;
		this.location = location;
		this.itemNo = itemNo;
		this.occupant = occupant;
		for(int i = 0; i < 4; i++) {
			if(directions[i] == 1) {
				this.directions[i] = 1;
				this.doorColours[i] = colours[i];
			} else {
				this.directions[i] = 0;
				this.doorColours[i] = "N";
			}
		}
	}


	public Boolean isNPC() {
		return occupant.isNPC();
	}

	public NPC giveNPC(){
		return occupant;
	}


	public String giveLocation(){
	    return location;
    }

    public Boolean hasInventory() {
		if(itemNo != -1)
			return true;
		return false;
	}
	
	public int printInventory() {
		return itemNo;
	}
	

	
	public Boolean isDoor(int i) {
		if(directions[i] == 1) {
			return true;
		}
		return false;
	}

	public String doorColour(int i) {
		return doorColours[i];
	}

	
	public int numberDoors() {
		return doors;
	}
	
    public void removeItem(){
	    this.itemNo = -1;
    }
	
	
}
