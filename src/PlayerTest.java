import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Tests the functionality of the Player class
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class PlayerTest {

    private Method findDisposableCard;

    @Before
    public void playerSetup() {
        try {
            Player player = new Player(1);
            findDisposableCard = player.getClass().getDeclaredMethod("findDisposableCard");
            findDisposableCard.setAccessible(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void findingDisposableCard() {
        try {
            Player player = new Player(1);
            Object returnedCard = findDisposableCard.invoke(player);
            assertTrue(returnedCard instanceof Card);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}