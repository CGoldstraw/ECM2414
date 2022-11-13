/**
 * Pack contains the initial pack of cards and deals them to the players and decks
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class Pack {
    private final Card[] cards;
    public boolean valid;

    /**
     * Creates a new pack of cards.
     *
     * @param packContent The cards of the pack with each card on a new line
     * @param numPlayers The number of players in the game
     */
    public Pack(String packContent, int numPlayers) {
        this.valid = true;
        String[] lines = packContent.split("\n");

        int numLines = lines.length;
        if (numLines < 8 * numPlayers) {
            System.out.println("Not enough cards. Expected: " + 8 * numPlayers + ", received " + numLines);
            this.valid = false;
        } else if (numLines > 8 * numPlayers) {
            System.out.println("Too many cards. Expected: " + 8 * numPlayers + ", received " + numLines);
            this.valid = false;
        }

        // Read the cards from the pack
        cards = new Card[numLines];
        if (valid) {
            for (int i = 0; i < numLines; i++) {
                try {
                    // Check the card is a positive integer.
                    int cardValue = Integer.parseInt(lines[i]);
                    if (cardValue <= 0) {
                        System.out.println("Pack contains an invalid card value. Found " + lines[i] + " on line " + (i + 1) + ". Expected a positive integer.");
                        this.valid = false;
                    }
                    cards[i] = new Card(cardValue);
                } catch (NumberFormatException e) {
                    System.out.println("Pack contains an invalid card value. Found " + lines[i] + " on line " + (i + 1) + ". Expected a positive integer.");
                    this.valid = false;
                }
            }
        }
    }

    /**
     * Gets the cards in the pack
     *
     * @return The cards in the pack
     */
    public Card[] getCards() {
        return cards;
    }

    /**
     * Deals the cards in a round-robin fashion to the players and then the decks
     *
     * @param players The players to deal to
     * @param decks The decks to deal to
     */
    public void dealCards(Player[] players, CardDeck[] decks) {
        // Deal four cards to each player
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < players.length; j++) {
                players[j].dealCard(cards[i*players.length + j]);
            }
        }

        // Deal four cards to each deck
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < decks.length; j++) {
                decks[j].dealCard(cards[(4+i)*decks.length + j]);
            }
        }

        // Check for case where a player immediately wins.
        for (Player player : players) {
            int v1 = player.getCardValue(0);
            int v2 = player.getCardValue(1);
            int v3 = player.getCardValue(2);
            int v4 = player.getCardValue(3);

            if ((v1 == v2) && (v1 == v3) && (v1 == v4)) {
                Player.gameWon = true;
                Player.winningPlayer = player.getPlayerNumber();
            }
        }
    }
}
