import java.io.*;

public class Pack {
    private Card[] cards;
    public Pack(String packLocation, int numPlayers) throws IOException {
        File packFile = new File(packLocation);
        BufferedReader reader = new BufferedReader(new FileReader(packFile));

        // Check there are a valid number of cards in the packS
        int numLines = 0;
        while (reader.readLine() != null) numLines++;

        if (numLines < 8 * numPlayers) {
            throw new IOException("Pack does not have enough cards. Found " + numLines + " cards, expected " + 8 * numPlayers + ".");
        } else if (numLines > 8 * numPlayers) {
            throw new IOException("Pack has too many cards. Found " + numLines + " cards, expected " + 8 * numPlayers + ".");
        }
        reader.close();

        // Read the cards from the pack
        cards = new Card[numLines];
        reader = new BufferedReader(new FileReader(packFile));
        for (int i = 0; i < numLines; i++) {
            String line = reader.readLine();
            try {
                // Check the card is a positive int
                int cardValue = Integer.parseInt(line);
                if (cardValue <= 0) {
                    throw new IOException("Pack contains invalid card value. Found " + cardValue + " on line " + (i + 1) + ". Expected a positive integer.");
                }
                cards[i] = new Card(cardValue);
            } catch (NumberFormatException e) {
                throw new IOException("Pack contains invalid card value. Found " + line + " on line " + (i + 1) + ". Expected a positive integer.");
            }
        }
        reader.close();
    }

    public Card[] getCards() {
        return cards;
    }
}
