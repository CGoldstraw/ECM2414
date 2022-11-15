import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Test;
import java.io.File;
import java.lang.reflect.*;

/**
 * Tests the functionality of the Player class
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class PlayerTest {

    @After
    public void clearLogs() {
        File logsDir = new File("logs");
        logsDir.mkdir();
        for (File file : logsDir.listFiles()) {
            file.delete();
        }
    }

    @Test
    public void dealCardAddsCardToHand() {
        Player player = new Player(1);
        Card card = new Card(1);
        player.dealCard(card);
        assertEquals(1, player.getHand().size());
    }

    @Test
    public void dealCardAddsCardToHandWithCorrectValue() {
        Player player = new Player(1);
        Card card = new Card(1);
        player.dealCard(card);
        assertEquals(1, player.getCardValue(0));
    }

    @Test
    public void getPlayerNumberTest() {
        Player player = new Player(1);
        assertEquals(1, player.getPlayerNumber());
    }

    @Test
    public void getHandReturnsCorrectValues() {
        Player player = new Player(1);
        for (int i = 0; i < 4; i++) {
            player.dealCard(new Card(i));
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(i, player.getCardValue(i));
        }
    }

    @Test
    public void desiredCardsNotDisposed() {
        // Uses reflection to test a private method.
        Player examplePlayer = new Player(1);
        Card[] hand = {new Card(1), new Card(1), new Card(2), new Card(1)};
        for (int i = 0; i < 4; i++) {
            examplePlayer.dealCard(hand[i]);
        }
        try {
            Method disposedCardFinder = examplePlayer.getClass().getDeclaredMethod("findDisposableCard");
            disposedCardFinder.setAccessible(true);
            Object returnObject = disposedCardFinder.invoke(examplePlayer);
            Card card = (Card)returnObject;
            // The 2 card is the only card without the desired value, so it must be disposed.
            assertEquals(2, card.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void playerCanWin() {
        // Uses reflection to test whether a player with a winning hand
        // ends the game successfully.
        Player examplePlayer = new Player(1);
        for (int i = 0; i < 4; i++) {
            examplePlayer.dealCard(new Card(1));
        }
        try {
            Method winChecker = examplePlayer.getClass().getDeclaredMethod("checkWon");
            winChecker.setAccessible(true);
            winChecker.invoke(examplePlayer);
            assertEquals(1, Player.winningPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void playerWinsWithAbnormalNumbers() {
        // Uses reflection to test whether a player with a winning hand
        // ends the game successfully despite holding numbers other
        // than their preferred value.
        Player examplePlayer = new Player(1);
        for (int i = 0; i < 4; i++) {
            examplePlayer.dealCard(new Card(123));
        }
        try {
            Method winChecker = examplePlayer.getClass().getDeclaredMethod("checkWon");
            winChecker.setAccessible(true);
            winChecker.invoke(examplePlayer);
            assertEquals(1, Player.winningPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}