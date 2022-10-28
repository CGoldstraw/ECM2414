public class Pack {
    private Card[] cards;
    public boolean valid;
    public Pack(String packContent, int numPlayers) {
        this.valid = true;
        String[] lines = packContent.split("\n");
        
        int numLines = lines.length;
        if (numLines < 8 * numPlayers) {
            System.out.println("Not enough cards. Expected: "+ 8 * numPlayers + ", received " + numLines);
            this.valid = false;
        } else if (numLines > 8 * numPlayers) {
            System.out.println("Too many cards. Expected: "+ 8 * numPlayers + ", received " + numLines);
            this.valid = false;
        }

        // Read the cards from the pack
        cards = new Card[numLines];
        for (int i = 0; i < numLines; i++) {
            try {
                // Check the card is a positive int
                int cardValue = Integer.parseInt(lines[i]);
                if (cardValue <= 0) {
                    System.out.println("Pack contains an invalid card value. Found " + lines[i] + " on line " + (i+1) + ". Expected a positive integer.");
                    this.valid = false;
                }
                cards[i] = new Card(cardValue);
            } catch (NumberFormatException e) {
                System.out.println("Pack contains an invalid card value. Found " + lines[i] + " on line " + (i+1) + ". Expected a positive integer.");
                this.valid = false;
            }
        }
    }

    public Card[] getCards() {
        return cards;
    }
}
