import org.junit.Test;
import static org.junit.Assert.*;

public class CardDeckTest {

    @Test
    public void dealCardsAddsCardsToDeck() {
        CardDeck deck = new CardDeck(1);
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);
        deck.dealCard(card1);
        deck.dealCard(card2);
        deck.dealCard(card3);
        deck.dealCard(card4);
        assertEquals(4, deck.getCards().size());
    }

    @Test
    public void dealtCardsAreCorrectValues() {
        CardDeck deck = new CardDeck(1);
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);
        deck.dealCard(card1);
        deck.dealCard(card2);
        deck.dealCard(card3);
        deck.dealCard(card4);
        assertEquals(4, deck.getCards().get(3).getValue());
        assertEquals(3, deck.getCards().get(2).getValue());
        assertEquals(2, deck.getCards().get(1).getValue());
        assertEquals(1, deck.getCards().get(0).getValue());
    }

    @Test
    public void getDeckNumber() {
        CardDeck deck1 = new CardDeck(1);
        CardDeck deck2 = new CardDeck(2);
        CardDeck deck3 = new CardDeck(3);
        CardDeck deck4 = new CardDeck(4);
        assertEquals(1, deck1.getDeckNumber());
        assertEquals(2, deck2.getDeckNumber());
        assertEquals(3, deck3.getDeckNumber());
        assertEquals(4, deck4.getDeckNumber());
    }
}