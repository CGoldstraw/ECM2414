import org.junit.Test;
import static org.junit.Assert.*;

public class CardDeckTest {

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
            assertEquals(i, deck.getCards().get(i-1).getValue());
        }
    }

    @Test
    public void getDeckNumber() {
        for (int i = 1; i <= 4; i++) {
            CardDeck deck1 = new CardDeck(i);
            assertEquals(i, deck1.getDeckNumber());
        }
    }
}
