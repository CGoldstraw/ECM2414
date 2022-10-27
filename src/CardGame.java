import java.io.IOException;
import java.util.*;

class CardGame {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numPlayers = 0;
        while (numPlayers <= 0) {
            System.out.println("Please enter the number of players.");
            String numPlayerInput = scan.nextLine();
            try {
                numPlayers = Integer.parseInt(numPlayerInput);
                if (numPlayers > 0) {
                    break;
                }
                System.out.println("Please enter a positive number of players.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number of players.");
            }
        }

        String packLocation = "";
        while (packLocation.equals("")) {
            System.out.println("Please enter the location of pack to load:");
            packLocation = scan.nextLine();
            try {
                Pack pack = new Pack(packLocation, numPlayers);
                Card[] cards = pack.getCards();
                for (int i = 0; i < cards.length; i++) {
                    System.out.println("Card " + (i + 1) + ": " + cards[i].getValue());
                }
            } catch (IOException e) {
                packLocation = "";
                System.out.println("Error: " + e);
            }
        }
        scan.close();
    }
}