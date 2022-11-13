import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a player in the game.
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class Player extends Thread {
    public static boolean gameWon = false;
    public static int winningPlayer = 0;
    public static int numPlayers = 0;
    private final int playerNumber;
    private int cardCycleCount = 0;
    private FileWriter logFile;
    private ArrayList<Card> hand;

    /**
     * Creates a new player with the given player number.
     *
     * @param playerNumber The player number
     */
    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        // As players are not guaranteed to take the same number of turns,
        // we use an arraylist which can handle a variable length list of cards.
        this.hand = new ArrayList<>();

        // Creates player log file and creates logs directory if it doesn't exist.
        try {
            File logsDir = new File("logs");
            logsDir.mkdir();
            String filename = "player" + playerNumber + "_output.txt";
            logFile = new FileWriter(new File("logs", filename));
        } catch (IOException e) {
            System.out.println("Log file creation failed for player " + playerNumber);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates a player thread and starts it.
     */
    public void run() {
        // No turns can be taken if a player is dealt a winning hand
        while (!gameWon) {
            CardDeck leftDeck = CardGame.decks[this.playerNumber - 1];
            CardDeck rightDeck = CardGame.decks[this.playerNumber % Player.numPlayers];
            // Decks are locked in ascending order so that deadlock doesn't occur.
            // Both decks must be locked in order to ensure fully atomic player actions.
            CardDeck firstLockDeck = this.playerNumber-1 < this.playerNumber % Player.numPlayers ? leftDeck : rightDeck;
            CardDeck lastLockDeck = this.playerNumber-1 < this.playerNumber % Player.numPlayers ? rightDeck : leftDeck;
            synchronized (firstLockDeck) {
                synchronized (lastLockDeck) {
                    // Top of deck considered to be last card as decks operate in a stack.
                    int lastIndex = leftDeck.getCards().size()-1;
                    // Ensure deck has at least 1 card.
                    if (lastIndex != -1) {
                        Card newCard = leftDeck.removeCard(lastIndex);
                        this.hand.add(newCard);
                        Card disposedCard = findDisposableCard();
                        this.hand.remove(disposedCard);
                        rightDeck.dealCard(disposedCard);

                        this.logDraw(newCard.getValue());
                        this.logDiscard(disposedCard.getValue());
                        this.logHand("current");
                    }
                }
            }
            checkWon();
        }
        this.logEnding();
        this.log("player " + this.playerNumber + " exits");
        this.logHand("final");
        this.closeLog();
        // Each player calls the deck to their left's logging function at the end.
        CardGame.decks[this.playerNumber-1].logDeck();
    }


    /**
     * Finds a card in the player's hand that can be discarded.
     * Prevents stagnation by cycling through the preferred disposed card.
     * Keeps the desired cards
     *
     * @return The card to be discarded
     */
    private Card findDisposableCard() {
        // Cycle through disposed index to prevent stale cards.
        cardCycleCount = (cardCycleCount+1) % 5;
        while (this.hand.get(cardCycleCount).getValue() == this.playerNumber) {
            cardCycleCount = (cardCycleCount+1) % 5;
        }
        return this.hand.get(cardCycleCount);
    }

    /**
     * Checks if the player has won the game by checking if all the cards have the same value as the player number.
     * If the player has won, the gameWon flag is set to true and the winningPlayer is set to the player number.
     */
    private void checkWon() {
        int v1 = this.hand.get(0).getValue();
        int v2 = this.hand.get(1).getValue();
        int v3 = this.hand.get(2).getValue();
        int v4 = this.hand.get(3).getValue();

        if ((v1 == v2) && (v1 == v3) && (v1 == v4)) {
            Player.gameWon = true;
            Player.winningPlayer = this.playerNumber;
        }
    }

    /**
     * Deals a card to the player.
     * @param card The card to be dealt
     */
    public void dealCard(Card card) {
        this.hand.add(card);
        if (this.hand.size() == 4) {
            this.logHand("initial");
        }
    }

    /**
     * Gets the number of the player
     * @return The player number
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Gets the cards in the player's hand
     * @return The cards in the player's hand
     */
    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**
     * Get the value of the card at the given index in the player's hand.
     * @param index The index of the card
     * @return The value of the card
     */
    public int getCardValue(int index) {
        return this.hand.get(index).getValue();
    }

    /**
     * Logs the player's hand to the player's log file
     * @param handType The type of hand to be logged e.g. initial, current or final
     */
    private void logHand(String handType) {
        String handStr = "";
        for (int i = 0; i < 4; i++) {
            handStr += this.hand.get(i).getValue() + " ";
        }
        this.log("player " + this.playerNumber + " " + handType + " hand: " + handStr);
    }

    /**
     * Logs the player's draw to the player's log file
     * @param cardVal The value of the card drawn
     */
    private void logDraw(int cardVal) {
        this.log("player " + this.playerNumber + " draws a " + cardVal +
            " from deck " + this.playerNumber);
    }

    /**
     * Logs the player's discard to the player's log file
     * @param cardVal The value of the card discarded
     */
    private void logDiscard(int cardVal) {
        this.log("player " + this.playerNumber + " discards a " + cardVal +
            " to deck " + (this.playerNumber%Player.numPlayers+1));
    }

    /**
     * Logs the end of a game to the player's log file
     */
    private void logEnding() {
        if (this.playerNumber != Player.winningPlayer) {
            String winner = "player " + Player.winningPlayer;
            String loser = "player " + this.playerNumber;
            this.log(winner + " has informed " + loser + " that " + winner + " has won");
        } else {
            System.out.println("player " + this.playerNumber + " wins");
            this.log("player " + this.playerNumber + " wins");
        }
    }

    /**
     * Logs a message to the player's log file
     * @param msg The message to be logged
     */
    private void log(String msg) {
        try {
            this.logFile.write(msg + "\n");
        } catch (IOException e) {
            System.out.println("Log writing failed.");
            e.printStackTrace();
        }
    }

    /**
     * Closes the player's log file
     */
    private void closeLog() {
        try {
            this.logFile.close();
        } catch (IOException e) {
            System.out.println("Log closing failed.");
            e.printStackTrace();
        }
    }


}
