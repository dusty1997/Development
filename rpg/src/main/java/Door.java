
public class Door {
	
	public String colour;
	
	public Door() {
		
	}
	
	public void inspected() {
		System.out.println("Your door options are:");
	}
	
	public void doorColour(String colour) {
		this.colour = colour;
	}
	
	public String whatColour() { return colour;
	}

	public Boolean canEnter(Character c, SpecialRoom r){
		if(r.whatRequirement() == 0){
			return true;
		}

		if(r.whatRequirement() == 5 && c.hasItem(5)){
			return true;
		}
		if(r.whatRequirement() == 2 && c.hasItem(2)){
			return true;
		}
		if(r.whatRequirement() == 4 && c.hasItem(4)) {
			return true;
		}


		System.out.println("You can not enter " + r.giveLocation() + ", without " + c.printItem(r.whatRequirement()) + "!");
		return false;


	}
}
