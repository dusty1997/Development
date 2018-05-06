import java.util.Scanner;
import java.io.Serializable;

public class Character implements Serializable{
    transient Scanner in = new Scanner(System.in);
	private int drunkness;
	private int[] inventory = {2,2,-1,2,-1,-1};
	private int inventoryQuantity;
	private String name;
	private String[] items = {"Alcohol","Food","Keys","Water","Card","ID"};
	private int hunger;
	private int bankBalance;
	private int x;
	private int y;


	private String[] playerMoves = {"Flirt","Refuse","Ask for Help","Apologise"};
	private int[] playerAttack = {2, 5,15,1};
	private int[] playerUses = {10,10,2,99};




	public Character(String name){
		this.drunkness = 50;
		this.hunger = 50;
		this.bankBalance = 50;
		this.inventoryQuantity = 6;
		this.name = name;
		this.x = 0;
		this.y = 0;
	}

	public void changeX(int n){
		this.x+=n;
	}

	public void changeY(int n){
		this.y+=n;
	}

	public void newX(int n){
		this.x = n;
	}

	public void newY(int n){
		this.y = n;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getDrunkness() {
		return drunkness;
	}
	public int getHunger(){return hunger;}
	
	public String getName() {
		return name;
	}
	
	public boolean hasInventory() {
        return inventoryQuantity != 0;
    }

	public String printItem(int n){
	    return items[n];
    }
	
	public void printInventory() {
	    if(inventoryQuantity == 0){
	        System.out.println("Empty");
        }
		for(int i = 0; i < 6; i++){
			if(inventory[i] != -1){
				System.out.println("(" + i + ")"+ items[i] + " * " + inventory[i]);
			}
		}
	}
	
	public void giveItem(int item){
		this.inventory[item] = 1;
		inventoryQuantity++;
	}


	public int hungerLevel(){
	    return hunger;
    }

    public int getBankBalance(){
	    return bankBalance;
    }

    public void changeDrunkness(int n){
	    this.drunkness += n;
    }

    public void changeBalance(int n){
	    this.bankBalance += n;
    }

    public void changeHunger(int n){
	    this.hunger += n;
    }

    public void changeUses(int n){
		playerUses[n] -= 1;
	}

	public int getAttack(int n){
	    return playerAttack[n];
    }

    public String getMoves(int n){
	    return playerMoves[n];
    }

    public int getUses(int n){
	    return playerUses[n];
    }

    public Boolean hasItem(int n){
	    return inventory[n] > 0;
    }

    public void useItem(){
        Scanner in = new Scanner(System.in);
	    System.out.println("Which item do you want to use?");
	    printInventory();

	    int n = in.nextInt();

	    switch(n){
            case 0:
                System.out.println("Senna drinks some alcohol (+10 Alcohol Level) ");
                inventory[n] -=1;
                this.drunkness += 10;
                break;
            case 1:
                System.out.println("Senna eats some food! (-10 Hunger)");
                inventory[n] -=1;
                this.hunger -= 10;
                break;
            case 2:
                System.out.println("Keys are not useful right now!");
                break;
            case 3:
                System.out.println("Senna drinks some water (-10 Alcohol Level) ");
                inventory[3] -=1;
                this.drunkness -=10;
                break;
            case 4:
            case 5:
                System.out.println(items[n]+ " is not useful right now!");
                break;




        }
    }
}



