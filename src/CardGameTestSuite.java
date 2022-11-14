import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs every test
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CardTest.class, CardDeckTest.class,
        PackTest.class, CardGameTest.class,
        PlayerTest.class
})
public class CardGameTestSuite {
}