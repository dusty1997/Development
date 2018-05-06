
import java.io.*;
import java.util.Scanner;

public class Storage{
    private Character p;
    private SpecialRoom[][] map;
    private String savePath;

    private static final long serialVersionUID = 42L;

    public Storage(Character p, SpecialRoom[][] rooms, String savePath){
        this.p = p;
        this.map = rooms;
        this.savePath = savePath;
    }

    public Character getCharacter(){
        return p;
    }

    public SpecialRoom[][] getSpecialRoom(){
        return map;
    }
    // This function saves either the current game with the name "QuickSave" or with a name chosen by the player.
    public void save(String filePath){
        Scanner in = new Scanner(System.in);
        File saveFile = new File(filePath);
        if (saveFile.exists()){
            System.out.println("Do you really, really want to overwrite your previous game?");
            System.out.println("Yes: '1', No: '0'.");
            int input = in.nextInt();
            if (input!=1){
                return;
            }
        }
        File saveDir = new File(this.savePath);
        if (!saveDir.exists()){
            saveDir.mkdir();
        }
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(saveFile);
            out = new ObjectOutputStream(fos);
            out.writeObject(this.serialVersionUID);
            out.writeObject(p);
            out.writeObject(map);
            out.close();
        } catch (Exception ex) {
            System.out.println("Make sure you don't use spaces in your folder names.");
            ex.printStackTrace();
        }
    }

    // This function loads either the quickSave game or the saved game of the player's choice.
    public void load(String filePath){
        File loadFile = new File(filePath);
        if (!loadFile.exists()){
            System.out.println("I failed. There are no saved games.");
            return;
        }
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(filePath);
            in = new ObjectInputStream(fis);
            long versionUID = (long) in.readObject();
            if (versionUID != this.serialVersionUID) {
                throw new UnsupportedClassVersionError("Version mismatch for save game!");
            }
            this.p = (Character) in.readObject();
            this.map = (SpecialRoom[][]) in.readObject();

        } catch (FileNotFoundException ex){
            System.out.println("The saved game was not found!");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("There was an error reading your save game :(");
            ex.printStackTrace();
            System.out.println(")");
        } catch (ClassNotFoundException ex) {
            System.out.println("The version of the save game is not compatible with this game!");
            ex.printStackTrace();
        } catch (UnsupportedClassVersionError ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("An unknown error occurred!");
        }

    }

    // This function prints the saved games a player has made in the past.
    public File[] showFiles(String filePath) throws FileNotFoundException {
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            throw new FileNotFoundException("No files found in directory");
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("("+i+") " + listOfFiles[i].getName());
            }
        }
        return listOfFiles;
    }

    // The player types here how he/she wants to name his/her file
    public String getFilename(){
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        return input;
    }

    // The player wants to stop his/her game, so we will return to the main and quit the game.
    public void giveUp(){
        System.out.println("You are such a loser. Glad that you give up!");
        System.exit(0);
    }

    // This function let the player do a quick save.
    public void saveQuickly(){
        String quickSavePath = this.savePath  + File.separator + "QuickSave.ser";
        this.save(quickSavePath);
    }

    // This function let the player do a quick load.
    public void loadQuickly(){
        System.out.println("I am going to try to load your games.");
        String quickLoadPath = this.savePath + File.separator + "QuickSave.ser";
        this.load(quickLoadPath);
    }

    // This function let the player save his/her game with a name given by the player.
    public void saveGame(){
        Scanner in = new Scanner(System.in);
        Boolean nameIsFine = false;
        String filename;
        int input;
        do {
            System.out.println("Filename?");
            filename = this.savePath + File.separator + this.getFilename() + ".ser";
            File newFile = new File(filename);
            if(newFile.exists()) {
                System.out.println("This file already exists.");
                System.out.println("Do you want to change the file name?");
                System.out.println("Yes: '1', No: '0'.");
                input = in.nextInt();
                if (input == 0) {
                    nameIsFine = true;
                } else {
                    if (input != 1){
                        System.out.println("This is not an option.");
                    }
                    System.out.println("Give a different filename.");
                }
            } else {
                nameIsFine = true;
            }
        } while(!nameIsFine);
        this.save(filename);
    }

    // This function let the player load a chosen game.
    public void loadGame(){
        Scanner in = new Scanner(System.in);
        System.out.println("Choose a saved game. ( -1 : none )");
        try {
            File[] listOfFiles = this.showFiles(this.savePath); //Show the files that have been saved earlier
            int i = in.nextInt();
            if(i==-1){
                return;
            }
            String filePath = listOfFiles[i].getPath(); //Get the path of the chosen file
            this.load(filePath);
        } catch (FileNotFoundException ex) {
            System.out.println("There are no saved games.");
        }
    }

}
