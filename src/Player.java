import java.util.ArrayList;

public class Player extends Thread {
    private final int playerNumber;
    public static boolean gameWon = false;
    private int cardCycleCount = 0;

    private ArrayList<Card> hand;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.hand = new ArrayList<Card>();
    }

    public void run() {
        checkWon();
        while (!gameWon) {
            CardDeck leftDeck = CardGame.decks[this.playerNumber-1];
            CardDeck rightDeck = CardGame.decks[this.playerNumber%4];
            synchronized (leftDeck) {
                // Top of deck considered to be last card as decks operate in a stack.
                int lastIndex = leftDeck.getCards().size()-1;
                Card newCard = leftDeck.getCards().remove(lastIndex);
                this.hand.add(newCard);
            }
            Card disposedCard = findDisposableCard();
            synchronized (rightDeck) {
                // Bottom card disposed
                Card oldCard = this.hand.remove(0);
                rightDeck.dealCard(oldCard);
            }
            System.out.println("Player " + this.playerNumber + " checked");
            checkWon();
        }
    }

    private Card findDisposableCard() {
        // Cycle through cards to prevent stale cards.
        cardCycleCount++;
        while (this.hand.get(cardCycleCount).getValue() == this.playerNumber) {
            cardCycleCount++;
        }
        return this.hand.get(cardCycleCount);
    }

    private boolean checkWon() {
        int v1 = this.hand.get(0).getValue();
        int v2 = this.hand.get(1).getValue();
        int v3 = this.hand.get(2).getValue();
        int v4 = this.hand.get(3).getValue();

        if ((v1 == v2) && (v1 == v3) && (v1 == v4)) {
            System.out.println("Player " + this.playerNumber + " wins");
            Player.gameWon = true;
            return true;
        }
        return false;
    }

    public void dealCard(Card card) {
        this.hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
