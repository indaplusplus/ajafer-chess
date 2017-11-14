import Helpers.Color;
import Pieces.Pawn;
import Pieces.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  private final Board BOARD = new Board();
  private final Player PLAYER1 = new Player(Color.BLACK, "Jimmy Boy");
  private final Player PLAYER2 = new Player(Color.WHITE, "Sunshine");

  @Test
  void setupPieces() {
    BOARD.setupBoard();
    BOARD.setupPieces(PLAYER1);
    assertEquals(BOARD.getCurrentPieces().size(), 144);
    BOARD.setupPieces(PLAYER2);
    assertEquals(BOARD.getCurrentPieces().size(), 16);
  }

  @Test
  void removeFromBoard() {
    Pawn pawn = new Pawn("Pawn",Color.BLACK, 1,0);
    BOARD.removeFromBoard(pawn);
    assert BOARD.getRemovedPieces().contains(pawn);
  }

  @Test
  void movePiece() {
  }

  @Test
  void displayBoard() {
  }

  @Test
  void getCurrentPieces() {
    
  }

  @Test
  void getRemovedPieces() {
  }

}
