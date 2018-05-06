import java.io.Serializable;
import java.util.*;

public class NPC implements Serializable {
    private Boolean isNPC;
    private String name;
    private int visited;
    private int defence;
    private String[] moves;
    private int multipleVisits;
    private int[] moveStrength;
    private int friendly;
    transient Scanner in;
    transient String input;


    public NPC(Boolean exists, String name, int health, String[] moves, int[] moveStrength, int multipleVisits, int friendly){
        this.isNPC = exists;
        this.name = name;
        this.defence = health;
        this.visited = -1;
        this.moves = moves;
        this.multipleVisits = multipleVisits;
        this.moveStrength = moveStrength;
        this.friendly = friendly;
    }

    public Boolean isFriendly(){
        return friendly == 1;
    }

    public String giveName(){
        return name;
    }

    public Boolean isNPC(){
        return isNPC;
    }

    public Boolean isVisited(){
        if(visited >= 1 && multipleVisits == 0){
            return true;
        }
        if(multipleVisits == 1 && visited >= 2){
            return true;
        }
        return false;
    }

    public void fightSetup(Character p){
        this.visited = 1;

        if(fight(p)){
            /*well done*/
        } else {
            /*dead */
            System.out.println("Senna's alcohol levels have risen too much! She passes out!");
            System.out.println("Game Over!");
            System.exit(0);
        }

    }


    public Boolean fight(Character c){
        in = new Scanner(System.in);
        if(friendly == 1){
            System.out.println(name + "has offered Senna " + moves[0] + ", do she accept? (Y/n) (-" + moveStrength[0] + " drunkness)");
            String answer;
            answer = in.next();
            if(answer.toLowerCase().equals("y")){
                c.changeDrunkness(-moveStrength[0]);
            }
            this.visited = 1;
            return true;
        } else {
            while (true) {
                System.out.println(name + ": Health = " + defence);
                System.out.println("Senna: Alcohol Level = " + c.getDrunkness());
                System.out.println("What would you like to do?");
                for (int i = 0; i < 4; i++) {
                    System.out.println("(" + i + ") " + c.getMoves(i));
                    System.out.println("Attack = " + c.getAttack(i) + ", Uses Left = " + c.getUses(i));
                }
                int in2 = 1;
                Boolean correctInput = false;
                while(correctInput == false) {
                    input = in.next();

                    /*do this fix more places!*/
                    try {
                        in2 = Integer.parseInt(input);
                        if (in2 >= 0 && in2 < 4) {
                            correctInput = true;
                        }
                    } catch(NumberFormatException nfe) {
                        System.out.println("This is not a number!");
                    }
                }
                if (in2 >= 0 && in2 < 4) {
                    if (in2 == 3 && name.equals("Police")) {
                        System.out.println("Senna apologies to stop any trouble!");
                        System.out.println("The police accept her apology and let her carry on!");
                        return true;
                    }
                    if (in2 == 0 && name.equals("Bouncer") && c.getDrunkness() >= 60) {
                        System.out.println("Senna attempts to flirt with the bouncer!");
                        System.out.println("The bouncer seems quite happy and lets her in.");
                        return true;
                    }
                    System.out.println("Senna uses " + c.getMoves(in2));
                    System.out.println(name + " looses " + c.getAttack(in2) + " health!");
                    c.changeUses(in2);
                    this.defence -= c.getAttack(in2);
                } else {
                    System.out.println("That isn't an acceptable input, Senna pukes and skips her turn!");
                }

                if (defence <= 0) {
                    System.out.println("Congratulations, Senna beat " + name);
                    return true;
                }
                Random rand = new Random();
                int att;

                att = rand.nextInt(2);
                System.out.println(name + "attacks, using " + moves[att]);
                System.out.println("Senna's alcohol level rises by " + moveStrength[att]);
                int change =  moveStrength[att];

                c.changeDrunkness(change);
                if (c.getDrunkness() >= 100) {
                    return false;
                }
            }

        }


    }

}

