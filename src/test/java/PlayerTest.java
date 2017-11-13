import Helpers.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
  private final Player PLAYER1 = new Player(Color.BLACK, "Jimmy Boy");
  private final Player PLAYER2 = new Player(Color.WHITE, "Sunshine");

  @Test
  void getCurrentGame() {
    Game newGame = new Game(PLAYER1, PLAYER2);
    PLAYER1.setCurrentGame(newGame);
    assertEquals(PLAYER1.getCurrentGame(),PLAYER2.getCurrentGame());
  }

  @Test
  void getName() {
    assertEquals(PLAYER1.getName(),"Jimmy Boy");
    assertEquals(PLAYER2.getName(), "Sunshine");
  }

  @Test
  void getCurrentColor() {
    assertEquals(PLAYER1.getCurrentColor(), Color.BLACK);
    assertEquals(PLAYER2.getCurrentColor(), Color.WHITE);

    assertNotEquals(PLAYER1.getCurrentColor(), PLAYER2.getCurrentColor());
  }

}