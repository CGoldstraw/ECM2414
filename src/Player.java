import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Player extends Thread {
    private final int playerNumber;
    public static boolean gameWon = false;
    public static int winningPlayer = 0;
    private int cardCycleCount = 0;
    private FileWriter logFile;
    private ArrayList<Card> hand;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.hand = new ArrayList<Card>();
        try {
            String filename = "player" + playerNumber + "_output.txt";
            logFile = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("Log file creation failed for player " + playerNumber);
        }
    }

    public void run() {
        checkWon();
        while (!gameWon) {
            boolean pickedUp = false;
            CardDeck leftDeck = CardGame.decks[this.playerNumber-1];
            CardDeck rightDeck = CardGame.decks[this.playerNumber%4];
            synchronized (leftDeck) {
                // Top of deck considered to be last card as decks operate in a stack.
                int lastIndex = leftDeck.getCards().size()-1;
                if (lastIndex != -1) {
                    pickedUp = true;
                    Card newCard = leftDeck.getCards().remove(lastIndex);
                    this.hand.add(newCard);
                    this.logDraw(newCard.getValue());
                }
            }
            
            synchronized (rightDeck) {
                if (pickedUp) {
                    Card disposedCard = findDisposableCard();
                    this.hand.remove(disposedCard);
                    rightDeck.dealCard(disposedCard);
                    this.logDiscard(disposedCard.getValue());
                }
            }
            if (pickedUp) {
                this.logHand("current");
            }
            checkWon();
        }
        this.logHand("final");
        this.safeLog("player " + this.playerNumber + " exits");
        this.closeLog();
    }

    private Card findDisposableCard() {
        // Cycle through cards to prevent stale cards.
        cardCycleCount = (cardCycleCount+1) % 4;
        while (this.hand.get(cardCycleCount).getValue() == this.playerNumber) {
            cardCycleCount = (cardCycleCount+1) % 4;
        }
        return this.hand.get(cardCycleCount);
    }

    private boolean checkWon() {
        int v1 = this.hand.get(0).getValue();
        int v2 = this.hand.get(1).getValue();
        int v3 = this.hand.get(2).getValue();
        int v4 = this.hand.get(3).getValue();

        if ((v1 == v2) && (v1 == v3) && (v1 == v4)) {
            Player.gameWon = true;
            Player.winningPlayer = this.playerNumber;
            this.logWin();
            return true;
        }
        return false;
    }

    public void dealCard(Card card) {
        this.hand.add(card);
        if (this.hand.size() == 4) {
            this.logHand("initial");
        }
    }

    public ArrayList<Card> getHand() {
        // The players do not directly interact with eachother, so this will never
        // return stale data, making it thread safe.
        return this.hand;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    private void logHand(String handType) {
        String handStr = "";
        for (int i = 0; i < 4; i++) {
            handStr += this.hand.get(i).getValue() + " ";
        }
        this.safeLog("player " + this.playerNumber + " " + handType + " hand: " + handStr);
    }

    private void logDraw(int cardVal) {
        this.safeLog("player " + this.playerNumber + " draws a " + cardVal +
            " from deck " + this.playerNumber);
    }

    private void logDiscard(int cardVal) {
        this.safeLog("player " + this.playerNumber + " discards a " + cardVal +
            " to deck " + (this.playerNumber%4+1));
    }

    private void logWin() {
        this.safeLog("player " + this.playerNumber + " wins");
    }

    private void safeLog(String msg) {
        try {
            this.logFile.write(msg + "\n");
        } catch (IOException e) {
            System.out.println("Log writing failed.");
        }
    }

    private void closeLog() {
        try {
            this.logFile.close();
        } catch (IOException e) {
            System.out.println("Log closing failed.");
        }
    }
}
