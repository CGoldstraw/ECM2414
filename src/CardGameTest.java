import org.junit.Test;
import java.util.Random;

public class CardGameTest {

    @Test
    public void fullGameTest() {
        // Systems Testing for the entire game. Creates a mock pack file and ensures the program
        // runs without throwing an error.
        Random rand = new Random();
        int numPlayers = rand.nextInt(10);
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