import org.junit.Test;
import static org.junit.Assert.*;

public class PackTest {
    @Test
    public void getCards() {
        String packFourPlayers = "3\n2\n2\n4\n1\n5\n6\n2\n3\n4\n1\n2\n4\n5\n2\n1\n2\n3\n5\n6\n4\n3\n2\n1\n2\n3\n4\n1\n2\n3\n4\n1\n";
        Pack pack = new Pack(packFourPlayers, 4);
        assertTrue(pack.valid);
    }

    @Test
    public void invalidNumberOfCardsFails() {
        String packFourPlayers = "3\n2\n2\n4\n1\n5\n6\n2\n3\n4\n1\n2\n4\n5\n2\n1\n2\n3\n5\n6\n4\n3\n2\n1\n2\n3\n4\n1\n2\n3\n4\n1\n";
        Pack pack = new Pack(packFourPlayers, 3);
        assertFalse(pack.valid);
    }

    @Test
    public void dealCards() {
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        Player player3 = new Player(3);
        Player player4 = new Player(4);

        Player[] players = {player1, player2, player3, player4};

        CardDeck deck1 = new CardDeck(1);
        CardDeck deck2 = new CardDeck(2);
        CardDeck deck3 = new CardDeck(3);
        CardDeck deck4 = new CardDeck(4);

        CardDeck[] decks = {deck1, deck2, deck3, deck4};

        String packFourPlayers = "3\n2\n2\n4\n1\n5\n6\n2\n3\n4\n1\n2\n4\n5\n2\n1\n2\n3\n5\n6\n4\n3\n2\n1\n2\n3\n4\n1\n2\n3\n4\n1\n";

        Pack pack = new Pack(packFourPlayers, 4);
        pack.dealCards(players, decks);


        assertEquals(4, player1.getHand().size());
        assertEquals(4, player2.getHand().size());
        assertEquals(4, player3.getHand().size());
        assertEquals(4, player4.getHand().size());

        assertEquals(4, deck1.getCards().size());
        assertEquals(4, deck2.getCards().size());
        assertEquals(4, deck3.getCards().size());
        assertEquals(4, deck4.getCards().size());
    }
}