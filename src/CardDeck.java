import java.util.ArrayList;

public class CardDeck {
    private final int deckNumber;

    private ArrayList<Card> cards;

    public CardDeck(int deckNumber) {
        this.deckNumber = deckNumber;
        this.cards = new ArrayList<Card>();
    }

    public void dealCard(Card card) {
        // Cards should be dealt to the top
        cards.add(card);
    }

    public int getDeckNumber() {
        return deckNumber;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
