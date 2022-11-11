import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;

public class Player extends Thread {
    private final int playerNumber;
    public static boolean gameWon = false;
    public static int winningPlayer = 0;
    public static int numPlayers = 0;
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
            CardDeck leftDeck = CardGame.decks[this.playerNumber - 1];
            CardDeck rightDeck = CardGame.decks[this.playerNumber % Player.numPlayers];
            // Decks are locked in ascending order so that deadlock doesn't occur.
            // Both decks must be locked at once in order to ensure fully atomic player actions.
            CardDeck firstLockDeck = this.playerNumber-1 < this.playerNumber % Player.numPlayers ? leftDeck : rightDeck;
            CardDeck lastLockDeck = this.playerNumber-1 < this.playerNumber % Player.numPlayers ? rightDeck : leftDeck;
            synchronized (firstLockDeck) {
                synchronized (lastLockDeck) {
                    // Top of deck considered to be last card as decks operate in a stack.
                    int lastIndex = leftDeck.getCards().size()-1;
                    // Ensure deck has at least 1 card.
                    if (lastIndex != -1) {
                        Card newCard = leftDeck.getCards().remove(lastIndex);
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
        this.logInform();
        this.safeLog("player " + this.playerNumber + " exits");
        this.logHand("final");
        this.closeLog();
        // Each player creates a log file for the deck to their left at the end.
        this.logDeck();
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
        System.out.println("player " + this.playerNumber + " wins");
        this.safeLog("player " + this.playerNumber + " wins");
    }

    private void logInform() {
        if (this.playerNumber != Player.winningPlayer) {
            String winner = "player " + Player.winningPlayer;
            String loser = "player " + this.playerNumber;
            this.safeLog(winner + " has informed " + loser + " that " + winner + " has won");
        }
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

    private void logDeck() {
        try {
            String filename = "deck" + this.playerNumber + "_output.txt";
            FileWriter deckLogFile = new FileWriter(filename);
            String deckCards = "";
            for (int i = 0; i < CardGame.decks[this.playerNumber-1].getCards().size(); i++) {
                deckCards += CardGame.decks[this.playerNumber-1].getCards().get(i).getValue() + " ";
            }
            if (deckCards.equals("")) {
                deckCards = "empty";
            }
            deckLogFile.write("deck " + this.playerNumber + " contents: " + deckCards);
            deckLogFile.close();
        } catch (IOException e) {
            System.out.println("Log file creation failed for deck " + playerNumber);
        }
    }
}
