import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

/**
 * Tests the functionality of the Pack class
 */
public class PackTest {
    @Test
    public void getCards() {
        Random rand = new Random();
        int numPlayers = rand.nextInt(2,10);
        String packStr = "";
        for (int i = 0; i < numPlayers*8; i++) {
            packStr += rand.nextInt(1,100) + "\n";
        }
        Pack pack = new Pack(packStr, numPlayers);
        assertTrue(pack.valid);
    }

    @Test
    public void invalidNumberOfCardsFails() {
        Random rand = new Random();
        int numPlayers = rand.nextInt(3,10);
        String packStr = "";
        for (int i = 0; i < numPlayers*8; i++) {
            packStr += rand.nextInt(1,100) + "\n";
        }
        Pack pack = new Pack(packStr, numPlayers-1);
        assertFalse(pack.valid);
    }

    @Test
    public void negativeCardRejected() {
        Random rand = new Random();
        int numPlayers = rand.nextInt(5,10);
        String packStr = "";
        for (int i = 0; i < numPlayers*8-1; i++) {
            packStr += rand.nextInt(1,100) + "\n";
        }
        packStr += "-1\n";
        Pack pack = new Pack(packStr, numPlayers);
        assertFalse(pack.valid);
    }

    @Test
    public void invalidCardRejected() {
        Random rand = new Random();
        int numPlayers = rand.nextInt(5,10);
        String packStr = "";
        for (int i = 0; i < numPlayers*8-1; i++) {
            packStr += rand.nextInt(1,100) + "\n";
        }
        packStr += "A\n";
        Pack pack = new Pack(packStr, numPlayers);
        assertFalse(pack.valid);
    }

    @Test
    public void dealCards() {
        Player[] players = new Player[4];
        CardDeck[] decks = new CardDeck[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i+1);
            decks[i] = new CardDeck(i+1);
        }

        Random rand = new Random();
        String packStr = "";
        for (int i = 0; i < 32; i++) {
            packStr += rand.nextInt(1,100) + "\n";
        }
        Pack pack = new Pack(packStr, 4);

        pack.dealCards(players, decks);

        for (int i = 0; i < 4; i++) {
            assertEquals(4, players[i].getHand().size());
            assertEquals(4, decks[i].getCards().size());
        }
    }
}