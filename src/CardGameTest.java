import org.junit.Test;

public class CardGameTest {

    @Test
    public void fullGameTest() {
        CardGame cardGame = new CardGame();
        int numPlayers = 4;
        String packStr = "3\n2\n2\n4\n1\n5\n6\n2\n3\n4\n1\n2\n4\n5\n2\n1\n2\n3\n5\n6\n4\n3\n2\n1\n2\n3\n4\n1\n2\n3\n4\n1\n";
        Pack pack = new Pack(packStr, numPlayers);
        CardGame.play(numPlayers, pack);
    }
}