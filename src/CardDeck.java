public class CardDeck {
    private final int deckNumber;

    private Card[] cards;

    public CardDeck(int deckNumber) {
        this.deckNumber = deckNumber;
        this.cards = new Card[4];
    }

    public void dealCard(Card card, int cardNumber) {
        cards[cardNumber] = card;
    }

    public int getDeckNumber() {
        return deckNumber;
    }

    public Card[] getCards() {
        return cards;
    }
}
