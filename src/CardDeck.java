import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CardDeck {
    private final int deckNumber;
    private ArrayList<Card> cards;

    public CardDeck(int deckNumber) {
        this.deckNumber = deckNumber;
        this.cards = new ArrayList<Card>();
    }

    public synchronized void dealCard(Card card) {
        // Cards should be dealt to the top
        cards.add(card);
    }

    public synchronized Card removeCard(int index) {
        return cards.remove(index);
    }

    public int getDeckNumber() {
        return deckNumber;
    }

    public synchronized ArrayList<Card> getCards() {
        // Multiple threads can change the deck, so to prevent a thread caching the result
        // and obtaining stale data, the method must be synchronized.
        return cards;
    }

    public synchronized int getCardValue(int index) {
        return cards.get(index).getValue();
    }

    public void logDeck() {
        try {
            String filename = "deck" + this.deckNumber + "_output.txt";
            FileWriter deckLogFile = new FileWriter(filename);
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
