public class Player extends Thread {
    private final int playerNumber;

    private Card[] hand;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.hand = new Card[4];
    }

    public void dealCard(Card card, int cardNumber) {
        hand[cardNumber] = card;
    }

    public Card[] getHand() {
        return hand;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
