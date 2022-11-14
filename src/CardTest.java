import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.Random;

/**
 * Tests the functionality of the Card class
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class CardTest {

    @Test
    public void getValueReturnsCorrectValue() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int val = rand.nextInt(1,100);
            Card card = new Card(val);
            assertEquals(val, card.getValue());
        }
    }
}