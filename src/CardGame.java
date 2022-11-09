import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


class CardGame {

    public final static Object lock = new Object();
    public static Player[] players;
    public static CardDeck[] decks;

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
                while (packReader.hasNextLine()) {
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

        // Create the players and decks
        players = new Player[numPlayers];
        decks = new CardDeck[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i + 1);
            decks[i] = new CardDeck(i + 1);
        }

        pack.dealCards(players, decks);

        // Print out player hands
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + players[i].getPlayerNumber() + " has " + players[i].getHand().size() + " cards.");
            players[i].start();
        }

        // Print out deck hands
        // for (int i = 0; i < numPlayers; i++) {
        //     System.out.println("Deck " + decks[i].getDeckNumber() + " has " + decks[i].getCards().size() + " cards.");
        // }
    }
}