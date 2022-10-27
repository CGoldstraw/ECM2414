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
        scan.close();
    }
}