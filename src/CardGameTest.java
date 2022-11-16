import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.lang.reflect.*;

/**
 * Tests the functionality of the entire game
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class CardGameTest {

    @Test
    public void getPackContentTest() {
        // Writes to a test pack file and uses reflection to read it
        // using the private getPackContent method.
        String testPackStr = "1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n";
        try {
            FileWriter testFile = new FileWriter(new File("test_pack.txt"));
            testFile.write(testPackStr);
            testFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        try {
            Method packContentGetter = CardGame.class.getDeclaredMethod("getPackContent", String.class);
            packContentGetter.setAccessible(true);
            Object returnObject = packContentGetter.invoke(null, "test_pack.txt");
            String packStr = String.valueOf(returnObject);
            assertEquals(testPackStr, packStr);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        File testPack = new File("test_pack.txt");
        testPack.delete();
    }

    @Test
    public void fullGameTest() {
        // Systems Testing for the entire game. Creates a mock pack file and ensures the program
        // runs without throwing an error.
        Random rand = new Random();
        int numPlayers = rand.nextInt(6,10);
        String packStr = "";
        for (int i = 0; i < 4*numPlayers; i++) {
            packStr += (int)(i/4)+1 + "\n";
        }
        for (int i = 0; i < 4*numPlayers; i++) {
            packStr += (int)(i/4)+1 + "\n";
        }
        Pack pack = new Pack(packStr, numPlayers);
        CardGame.play(numPlayers, pack);
    }
}