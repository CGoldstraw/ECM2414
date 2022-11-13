import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a deck of cards.
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class CardDeck {
    private final int deckNumber;
    private ArrayList<Card> cards;

    /**
     * Creates a new card deck with the given deck number.
     *
     * @param deckNumber The deck number
     */
    public CardDeck(int deckNumber) {
        this.deckNumber = deckNumber;
        // As decks are not guaranteed to take the same number of turns, we
        // use an arraylist which can handle a variable length list of cards.
        this.cards = new ArrayList<Card>();
    }

    /**
     * Deals a card from the pack to the deck
     *
     * @param card The card to deal
     */
    public synchronized void dealCard(Card card) {
        // Cards should be dealt to the top
        cards.add(card);
    }

    /**
     * Remove card from the deck at index
     *
     * @param index The index of the card to remove
     * @return The card removed
     */
    public synchronized Card removeCard(int index) {
        return cards.remove(index);
    }

    /**
     * Gets the deck number
     *
     * @return The deck number
     */
    public int getDeckNumber() {
        return deckNumber;
    }

    /**
     * Gets the cards in the deck
     *
     * @return The cards in the deck
     */
    public synchronized ArrayList<Card> getCards() {
        // Multiple threads can change the deck, so to prevent a thread caching the result
        // and obtaining stale data, the method must be synchronized.
        return cards;
    }

    /**
     * Gets the value of a card in the deck at the given index
     * @param index The index of the card
     * @return The value of the card
     */
    public synchronized int getCardValue(int index) {
        return cards.get(index).getValue();
    }

    /**
     * Logs the state of the deck at the end of the game to a file
     */
    public void logDeck() {
        try {
            String filename = "deck" + this.deckNumber + "_output.txt";
            // check logs directory exists
            File logsDir = new File("logs");
            logsDir.mkdir();
            FileWriter deckLogFile = new FileWriter(new File("logs", filename));
            String deckCards = "";
            for (int i = 0; i < this.cards.size(); i++) {
                deckCards += this.getCardValue(i) + " ";
            }
            if (deckCards.equals("")) {
                deckCards = "empty";
            }
            deckLogFile.write("deck " + this.deckNumber + " contents: " + deckCards);
            deckLogFile.close();
        } catch (IOException e) {
            System.out.println("Log file creation failed for deck " + deckNumber);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
