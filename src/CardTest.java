import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the Card class
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class CardTest {

    @Test
    public void getValueReturnsCorrectValue() {
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);
        assertEquals(1, card1.getValue());
        assertEquals(2, card2.getValue());
        assertEquals(3, card3.getValue());
        assertEquals(4, card4.getValue());
    }
}