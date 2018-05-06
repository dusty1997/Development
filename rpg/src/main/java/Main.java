
import java.io.*;
import java.util.*;


public class Main {
    public Character p;
    public SpecialRoom[][] map;
    public String savePath = "Save/";

    private static final long serialVersionUID = 42L;


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Door d = new Door();
        int oldX = 0;
        int oldY = 0;
        Character p = new Character("Senna");
        NPC npc;
        SpecialRoom r;


        /*Last coord*/
        int FINALX = 4;
        int FINALY = 4;
        String input;

        /*Setting up the map*/
        SpecialRoom[][] map = new SpecialRoom[5][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = SetUp(i, j);
            }
        }

        Storage save = new Storage(p,map,"Saves/");





        openingText();


        while (true) {
            p= save.getCharacter();
            map = save.getSpecialRoom();

            System.out.println();
            System.out.println();


            r = map[p.getX()][p.getY()];
            npc = r.giveNPC();
            System.out.println("You are currently at " + r.giveLocation());
            /*Win or end conditions*/
            if (p.getX() == 4 && p.getY() == 4) {
                System.out.println("You win!");
                System.exit(0);
            }
            if (p.getDrunkness() >= 100) {
                System.out.println("You have fed " + p.getName() + " too much alcohol and she has passed out!");
                System.out.println("Game Over!");
                System.exit(0);
            }
            if (p.getHunger() >= 100) {
                System.out.println("You haven't fed " + p.getName() + " enough food, she has passed out!");
                System.out.println("Game Over!");
                System.exit(0);
            }

            /*Hunger Watch*/
            if (p.getHunger() >= 80) {
                System.out.println(p.getName() + " is getting hungry!");
            }
            p.changeHunger(1);

            /*If there is a character in the room*/
            if (npc.isNPC() && !npc.isFriendly() && !npc.isVisited()) {
                System.out.println("You are approached by a " + npc.giveName() + "!");
                System.out.println("Do you want to battle? (Y/n)");
                input = in.next();

                if (input.toLowerCase().equals("y")) {
                    npc.fightSetup(p);
                } else {
                    System.out.println("You go back to your previous location!");
                    p.newX(oldX);
                    p.newY(oldY);
                    r = map[p.getX()][p.getY()];
                }
            }

            System.out.println("What do you want to do?");
            System.out.println(" (Q) Look around");
            System.out.println(" (W) Look for a new location?");
            System.out.println(" (E) See Senna's Alcohol Stats");
            System.out.println(" (R) See Senna's Inventory");
            System.out.println(" (T) Look for NPC");
            System.out.println(" (A) Quick Save");
            System.out.println(" (S) Quick Load");
            System.out.println(" (D) Save");
            System.out.println(" (Z) Load");
            System.out.println(" (X) Exit");

            input = in.next();
            switch (input) {
                case "Q":
                case "q":

                    if (r.hasInventory()) {
                        itemSorter(p, r);
                    } else {
                        System.out.println("There is nothing useful!");
                    }


                    break;
                case "W":
                case "w":


                    System.out.println("You ask around for directions. You can go to:");
                    for (int i = 0; i < 4; i++) {
                        if (r.isDoor(i)) {
                            System.out.println(r.doorColour(i));
                        }
                    }
                    if (r.numberDoors() != 0) {
                        System.out.println("Do you want to a new location?");
                        System.out.println("(0) - No");
                        int j;
                        for (int i = 0; i < 4; i++) {
                            j = i + 1;
                            if (r.isDoor(i)) {
                                System.out.println("(" + j + ") -  " + r.doorColour(i));
                            }
                        }
                        input = in.next();
                        oldX = p.getX();
                        oldY = p.getY();
                        switch (input) {
                            case "0":
                                break;
                            case "1":
                                if (r.isDoor(0)) {
                                    p.changeX(-1);
                                }
                                break;
                            case "2":
                                if (r.isDoor(1)) {
                                    p.changeY(1);
                                }
                                break;
                            case "3":
                                if (r.isDoor(2)) {
                                    p.changeX(1);
                                }
                                break;
                            case "4":
                                if (r.isDoor(3)) {
                                    p.changeY(-1);
                                }
                                break;
                            default:
                                break;
                        }
                        if (p.getX() == FINALX && p.getY() == FINALY) {
                            victorySequence(p);
                            System.exit(0);
                        }

                        SpecialRoom newRoom;
                        newRoom = map[p.getX()][p.getY()];
                        if (!d.canEnter(p, newRoom)) {
                            p.newX(oldX);
                            p.newY(oldY);
                        }


                    }
                    break;

                case "E":
                case "e":
                    System.out.println("Senna's drunkness level is " + p.getDrunkness());

                    System.out.println("Your bank balance is: " + p.getBankBalance());


                    System.out.println("Senna's hunger level is: " + p.hungerLevel());
                    break;


                case "R":
                case "r":
                    p.printInventory();
                    if (p.hasInventory()) {
                        System.out.println("Do you want to use an item? (Y/n)");
                        input = in.next();
                        if (input.equals("Y") || input.equals("y")) {
                            p.useItem();
                        }
                    }

                    break;

                case "T":
                case "t":
                    if (r.isNPC() && npc.isFriendly() && !npc.isVisited()) {

                            System.out.println("You see a " + npc.giveName() + ", do yo want to approach? (Y/n)");
                            input = in.next();
                            if (input.toLowerCase().equals("y")) {
                                npc.fightSetup( p);
                            }

                    } else {
                        System.out.println("There is no one important in the location");
                    }
                    break;
                case "A":
                case "a":
                    save.saveQuickly();
                    break;
                case "S":
                case "s":
                    save.loadQuickly();
                    break;

                case "D":
                case "d":
                   save.saveGame();
                    break;
                case "Z":
                case "z":
                    save.loadGame();
                    break;

                case "X":
                case "x":
                    save.giveUp();
                    break;

                case "C":
                case "c":
                    //load();
                    break;


            }
        }


    }

    /*requirements: {0,1,2}
     * 0 - no requirements
     * 1 - Alcohol level < 80
     * 2 - Alcohol Level > 80
     * 3 - ID
     * 4 - Bike Keys
     * 5 - Debit Card
     */
    private static SpecialRoom SetUp(int i, int j) {
        /*Default no NPC*/
        String[] defaultMoves = {"None"};
        int[] defaultAttack = {0};
        NPC defaultNPC =new  NPC(false, "none",0,defaultMoves,defaultAttack,0,0);
        SpecialRoom r = new SpecialRoom();
        String location;

        String[] drugMoves = {"Persuade", "Swear"};
        int[] drugAttacks = {1,1};
        String[] policeMoves = {"Arrest", "Fine"};
        int[] policeAttack = {2, 1};

        String[] chrisMoves = {"Flirt", "Buy Drinks"};
        int[] chrisAttack = {1, 5};

        String[] chrisMoves2 = {"Ask on Date", "Grab Bum"};
        int[] chrisAttack2 = {2, 10};

        String[] stolenBike = {"Cycle Away", "Attempt to sell bike", "Swear"};
        int[] stolenBikeAttack = {1, 1, 2};

        String[] bouncerMoves = {"Refuse Entry", "Flirt"};
        int[] bouncerAttack = {3, 2};

        /*Top Left*/
        if (i == 0 && j == 0) {
            String[] firstRoomColours = {"N", "Van Brakelplein", "N", "N"};
            int firstRoomDoors = 1;
            int[] firstRoomDoorDirections = {0, 1, 0, 0};
            int[] friendlyAttack ={10};
            String[] moves = {"Water"};
            NPC friend = new NPC(true,"Friend",0,moves,friendlyAttack,0,1);
            int itemNo = -1;
            location = "House Party (Start)";
            r.Room(location, firstRoomDoors, firstRoomDoorDirections, firstRoomColours, itemNo, friend);
            return r;
        }


        if (i == 0 && j == 1) {
            /*1st Row, 2nd Column*/
            String[] secondRoomColours = {"N", "Hoendiepskade", "N", "House Party (Start)"};
            int secondRoomDoors = 2;
            int[] secondRoomDoorDirections = {0, 1, 0, 1};

            int itemNo = 0;
            location = "Van Brakelplein";
            r.Room(location, secondRoomDoors, secondRoomDoorDirections, secondRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 0 && j == 2) {
            /*1st Row, 3rd Column*/
            String[] thirdRoomColours = {"N", "N", "Westerhave", "Van Brakelplein"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 0, 1, 1};
            NPC police = new NPC(true,"Police",5, policeMoves,policeAttack,0,0);

            int itemNo = -1;
            location = "Hoendiepskade";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, police);
            return r;
        }

        if (i == 0 && j == 3) {
            /*1st Row, 4rd Column*/
            String[] thirdRoomColours = {"N", "Mc Donalds", "Westersingle", "N"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 1, 1, 0};

            int itemNo = -1;
            location = "Westerkade";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 0 && j == 4) {
            /*1st Row, 5th Column*/
            String[] thirdRoomColours = {"N", "N", "N", "Westerkade"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 0, 0, 1};

            int itemNo = 1;
            location = "Mc Donalds";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 1 && j == 0) {
            /*2st Row, 0th Column*/
            String[] thirdRoomColours = {"N", "Mic Podium", "Gelkingestraat", "N"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 1, 1, 0};
            NPC bouncer = new NPC(true,"Bouncer",10,bouncerMoves,bouncerAttack,0,0);
            int itemNo = 0;
            location = "Karaoke Bar";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, bouncer);
            return r;
        }

        if (i == 1 && j == 1) {
            /*2st Row, 2th Column*/
            String[] thirdRoomColours = {"N", "N", "N", "Karaoke Bar"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 0, 0, 1};

            NPC chris = new NPC(true,"Chris",10,chrisMoves2,chrisAttack2,0,0 );
            int itemNo = 4;
            location = "Mic Podium";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, chris);
            return r;
        }

        if (i == 1 && j == 2) {
            /*2st Row, 3th Column*/
            String[] thirdRoomColours = {"Hoendiepskade", "Westersingle", "Vismarkt", "N"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {1, 1, 1, 0};

            int itemNo = -1;
            r.Room("Westerhaven", thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 1 && j == 3) {
            /*2st Row, 4th Column*/
            String[] thirdRoomColours = {"Westerkade", "All Night Shop", "N", "Westerhaven"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {1, 1, 0, 1};

            int itemNo = -1;
            location = "Westersingle";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 1 && j == 4) {
            /*2st Row, 5th Column*/
            String[] thirdRoomColours = {"N", "N", "N", "Westersingle"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 0, 0, 1};

            int itemNo = 3;
            location = "All Night Shop";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 2 && j == 0) {
            /*3st Row, 1th Column*/
            String[] thirdRoomColours = {"Karaoke Bar", "Grote Markt", "Gelkingestraat South", "N"};
            int thirdRoomDoors = 3;
            int[] thirdRoomDoorDirections = {1, 1, 1, 0};

            int itemNo = 1;
            location = "Gelkingestraat";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 2 && j == 1) {
            /*3st Row, 2th Column*/
            String[] thirdRoomColours = {"N", "Vismarkt", "N", "Gelkingestraat"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 1, 0, 1};

            int itemNo = 1;
            location = "Grote Markt";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 2 && j == 2) {
            /*3st Row, 3th Column*/
            String[] thirdRoomColours = {"Westerhaven", "N", "N", "Grote Markt"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {1, 0, 0, 1};
            NPC DrugDealer = new NPC(true,"Drug Dealer", 5, drugMoves, drugAttacks,0,0);
            int itemNo = 5;
            location = "Vismarkt";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, DrugDealer);
            return r;
        }

        if (i == 2 && j == 3) {
            /*3st Row, 4th Column*/
            String[] thirdRoomColours = {"N", "Oosterhamrikkade", "Dem Dems", "N"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 1, 1, 0};


            int itemNo = 2;
            location = "Bloemsingle";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo,defaultNPC);
            return r;
        }

        if (i == 2 && j == 4) {
            /*3st Row, 5th Column*/

            String[] thirdRoomColours = {"N", "N", "Diephuisstraat", "Bloemsingel"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 0, 1, 1};
            NPC bikeThief = new NPC(true,"Bike Thief",20, stolenBike, stolenBikeAttack, 0, 0);
            int itemNo = -1;
            location = "Oosterhamirkkade";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, bikeThief,2);
            return r;
        }


        if (i == 3 && j == 0) {
            /*4st Row, 1th Column*/
            String[] thirdRoomColours = {"Gelkingestraat", "Warhol", "N", "N"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {1, 1, 0, 0};

            int itemNo = 0;
            location = "Gelkingestraat South";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }

        if (i == 3 && j == 1) {

            /*4st Row, 2th Column*/
            String[] thirdRoomColours = {"N", "Chupitos", "N", "Gelkingestraat South"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 1, 0, 1};
            int[] strength = {15};
            String[] offer = {"Red Bull"};
            NPC barTender = new NPC(true,"Bar Tender", 0,offer, strength, 0,1);

            int itemNo = 0;
            location = "Warhol";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, barTender,5);
            return r;
        }

        if (i == 3 && j == 2) {
            /*4st Row, 2th Column*/

            String[] thirdRoomColours = {"N", "Dem Dems", "N", "Warhol"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {0, 1, 0, 1};

            NPC chris = new NPC(true,"Chris",15,chrisMoves,chrisAttack,0,0);
            int itemNo = 0;
            location = "Chupitos";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, chris);
            return r;
        }

        if (i == 3 && j == 3) {
            /*4st Row, 4th Column*/
            String[] thirdRoomColours = {"Bloemsingle", "N", "N", "Chupitos"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {1, 0, 0, 1};

            int itemNo = 1;
            location = "Dem Dems";

            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo,  defaultNPC,4);
            return r;
        }

        if (i == 3 && j == 4) {
            /*4st Row, 4th Column*/
            String[] thirdRoomColours = {"Oosterhamrikade", "N", "Home", "N"};
            int thirdRoomDoors = 2;
            int[] thirdRoomDoorDirections = {1, 0, 1, 0};

            int itemNo = -1;
            location = "Diephuisstraat";
            r.Room(location, thirdRoomDoors, thirdRoomDoorDirections, thirdRoomColours, itemNo, defaultNPC);
            return r;
        }
        return r;
    }

    private static void itemSorter(Character p, Room r) {
        String input;
        Scanner in = new Scanner(System.in);
        switch (r.printInventory()) {
            case 0:
                System.out.println("There is Alcohol available!");
                System.out.println("Cost: +10 Alcohol Levels, -10 from Bank Balance!");
                System.out.println("Do you want to give Senna alchohol? (Y/N)");
                input = in.next();
                if ((input.equals("Y") || input.equals("y") && p.getDrunkness() < 100 && p.getBankBalance() >= 10)) {
                    p.changeDrunkness(10);
                    p.changeBalance(-10);
                    System.out.println("Sluuurrrrp");
                } else if (p.getBankBalance() < 10) {
                    System.out.println("Sorry, you can not afford this!");
                }
                break;
            case 1:
                System.out.println("There is Food available!");
                System.out.println("Cost: -20 Hunger, -15 from Bank Balance, -5 Drunkness!");
                System.out.println("Should Senna have some food? (Y/N");
                input = in.next();
                if ((input.equals("Y") || input.equals("y") && p.getHunger() >= 20 && p.getBankBalance() >= 15)) {
                    p.changeDrunkness(-5);
                    p.changeBalance(-15);
                    p.changeHunger(-20);
                    System.out.println("Nom Nom Nom Nom");
                } else if (p.getBankBalance() < 15) {
                    System.out.println("Sorry, you can not afford this!");
                } else if (p.getHunger() < 20) {
                    System.out.println("Senna is not hungry!");
                }
                break;
            case 2:
                System.out.println("You see Senna's keys on the ground, you pick them up!");
                r.removeItem();
                p.giveItem(2);
                break;
            case 3:
                System.out.println("There is water available here, should Senna drink some? (-10 Drunkness)?");
                input = in.next();
                if (input.equals("Y") || input.equals("y") && p.getDrunkness() >= 10) {
                    p.changeDrunkness(-10);


                }
                break;
            case 4:
                System.out.println("You see Senna's pin card, you pick it up (+20 bank balance!)");
                r.removeItem();
                p.giveItem(4);
                p.changeBalance(20);
                break;

            case 5:
                System.out.println("You have found Sennas ID Card!");
                r.removeItem();
                p.giveItem(5);
                break;

        }

    }

    private static void openingText() {
        System.out.println("Hi, You have just arrived at a house party and found your friend Senna very drunk!");
        System.out.println("Your aim is to guide Senna home!");
        System.out.println("When she gets home she needs her keys and her pin card");
        System.out.println("You have a bank balance starting at 50, Senna has an alcoholic and hunger level starting at 50/100");
        System.out.println("If she gets too hungry or drunk she will pass out and you will have failed!");
        System.out.println("She will follow your every advice, her life is in your hands...");
        System.out.println("The game starts now...");
    }

    private static void victorySequence(Character c) {
        System.out.println("Congratulations, you got Senna home!");
        System.out.println("Her alcohol level was: " + c.getDrunkness());
        System.out.println("Her hunger level was: " + c.getHunger());
        System.out.println("Your bank balance is: " + c.getBankBalance());
        System.out.println("In her inventory there is:");
        c.printInventory();
        System.out.println("GoodBye!");

    }



}






