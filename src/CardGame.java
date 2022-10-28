import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


class CardGame {

    public static int getNumPlayers(Scanner scan) {
        int numPlayers = 0;
        while (true) {
            System.out.println("Please enter the number of players:");
            String numPlayerInput = scan.nextLine();
            try {
                numPlayers = Integer.parseInt(numPlayerInput);
                if (numPlayers >= 2) {
                    break;
                }
                System.out.println("Please enter a positive number of players.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number of players.");
            }
        }
        return numPlayers;
    }

    public static String getPackFile(Scanner scan) {
        String packContent = "";
        while (true) {
            try {
                System.out.println("Please enter the location of pack to load:");
                String packLocation = scan.nextLine();
                File packFile = new File(packLocation);
                Scanner packReader = new Scanner(packFile);
                while(packReader.hasNextLine()){
                    String line = packReader.nextLine();
                    packContent += line + "\n";
                }
                packReader.close();
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found, please enter a valid file location.");
            }
        }
        return packContent;
    }

    public static void main(String[] args) {        
        Scanner scan = new Scanner(System.in);
        int numPlayers = getNumPlayers(scan);
        Pack pack;

        do {
            String packLocation = getPackFile(scan);
            pack = new Pack(packLocation, numPlayers);
        } while (!pack.valid);
        scan.close();

        Card[] cards = pack.getCards();
        System.out.println(cards.length);
    }
}