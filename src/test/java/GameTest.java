import Helpers.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
  private final Player PLAYER1 = new Player(Color.BLACK, "Jimmy Boy");
  private final Player PLAYER2 = new Player(Color.WHITE, "Sunshine");
  private final Game NEW_GAME = new Game(PLAYER1,PLAYER2);

  @Test
  void getBoard() {
    assertNotEquals(NEW_GAME.getBoard(),null);
  }

  @Test
  void setBoard() {
    Board board = new Board();
    NEW_GAME.setBoard(board);
    assertEquals(NEW_GAME.getBoard(), board);
  }

  @Test
  void checkTurn() {
    if (NEW_GAME.getCurrentTurn() == PLAYER1.getCurrentColor()) {
      assertEquals(NEW_GAME.getCurrentTurn(), Color.BLACK);
    } else {
      assertEquals(NEW_GAME.getCurrentTurn(), Color.WHITE);
    }
  }

}