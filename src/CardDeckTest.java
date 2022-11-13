import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Tests the functionality of the CardDeck class
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class CardDeckTest {

    @Before
    public void clearLogs() {
        File logsDir = new File("logs");
        for (File file : logsDir.listFiles()) {
            file.delete();
        }
    }

    @Test
    public void dealCardsAddsCardsToDeck() {
        CardDeck deck = new CardDeck(1);
        for (int i = 1; i <= 4; i++) {
            deck.dealCard(new Card(i));
        }
        assertEquals(4, deck.getCards().size());
    }

    @Test
    public void dealtCardsAreCorrectValues() {
        CardDeck deck = new CardDeck(1);
        for (int i = 1; i <= 4; i++) {
            deck.dealCard(new Card(i));
            assertEquals(i, deck.getCards().get(i - 1).getValue());
        }
    }

    @Test
    public void getDeckNumber() {
        for (int i = 1; i <= 4; i++) {
            CardDeck deck1 = new CardDeck(i);
            assertEquals(i, deck1.getDeckNumber());
        }
    }

    @Test
    public void logDeckOutputsToFile() {
        CardDeck deck = new CardDeck(1);
        for (int i = 1; i <= 4; i++) {
            deck.dealCard(new Card(i));
        }
        deck.logDeck();

        // check that file was created
        File logsFile = new File("logs", "deck1_output.txt");
        assertTrue(logsFile.exists());
    }

    @Test
    public void logDeckOutputsCorrectContents() {
        CardDeck deck = new CardDeck(1);
        for (int i = 1; i <= 4; i++) {
            deck.dealCard(new Card(i));
        }
        deck.logDeck();

        File logsFile = new File("logs", "deck1_output.txt");
        String logsContent = "";
        try {
            Scanner logsReader = new Scanner(logsFile);
            while (logsReader.hasNextLine()) {
                String line = logsReader.nextLine();
                logsContent += line + "\n";
            }
            logsReader.close();
        } catch (Exception e) {
            fail("Error reading file");
        }

        assertEquals("deck 1 contents: 1 2 3 4 \n", logsContent);
    }

    @Test
    public void logDeckOuputsCorrectContentsWhenDeckIsEmpty() {
        CardDeck deck = new CardDeck(1);
        deck.logDeck();

        File logsFile = new File("logs", "deck1_output.txt");
        String logsContent = "";
        try {
            Scanner logsReader = new Scanner(logsFile);
            while (logsReader.hasNextLine()) {
                String line = logsReader.nextLine();
                logsContent += line + "\n";
            }
            logsReader.close();
        } catch (Exception e) {
            fail("Error reading file");
        }

        assertEquals("deck 1 contents: empty\n", logsContent);
    }
}
