import Helpers.Color;
import Helpers.CaptureDirection;
import Pieces.*;

import java.util.HashSet;

public class Board {
  private final static int PAWNS = 8;
  private final static int ROWS = 8;
  private final static int COLS = 8;

  /**
   * Chess board is 8x8 matrix.
   * All chess pieces initially stored in currentPieces, when a piece is taken out of the game
   * it is removed from the currentPieces set and added to the removedPieces set.
   */
  private static Square[][] boardRepresentation = new Square[8][8];
  private static HashSet<Piece> currentPieces = new HashSet<>();
  private static HashSet<Piece> removedPieces = new HashSet<>();

  private Game currentGame;

  /**
   * Initialize an empty 8x8 board with black and white squares.
   * Note: All squares have null pieces after function call
   */
  public void setupBoard() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        Square square;
        // If both (i,j) odd or both even: white, otherwise black
        if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
          square = new Square(Color.WHITE, null, j, i);
        } else {
          square = new Square(Color.BLACK, null, j, i);
        }
        boardRepresentation[i][j] = square;
      }
    }
  }

  /**
   * Set all 16 pieces colored correctly on each side of the board
   * Note: black player on bottom rows, white player on top rows
   */
  public void setupPieces(Player forPlayer) {
    switch (forPlayer.getCurrentColor()) {
      case WHITE:
        for (int i = 0; i < PAWNS; i++) {
          Piece bPawn = new Pawn("Pawn", Color.WHITE, i, 1);
          addToBoardAtPosition(bPawn);
        }

        Piece bKing = new King("King", Color.WHITE, 4, 0);
        Piece bQueen = new Queen("Queen", Color.WHITE, 3, 0);
        Piece bRook1 = new Rook("Rook", Color.WHITE, 0, 0);
        Piece bRook2 = new Rook("Rook", Color.WHITE, 7, 0);
        Piece bBishop1 = new Bishop("Bishop", Color.WHITE, 2, 0);
        Piece bBishop2 = new Bishop("Bishop", Color.WHITE, 5, 0);
        Piece bKnight1 = new Knight("Knight", Color.WHITE, 1, 0);
        Piece bKnight2 = new Knight("Knight", Color.WHITE, 6, 0);

        addToBoardAtPosition(bKing);
        addToBoardAtPosition(bQueen);
        addToBoardAtPosition(bRook1);
        addToBoardAtPosition(bRook2);
        addToBoardAtPosition(bBishop1);
        addToBoardAtPosition(bBishop2);
        addToBoardAtPosition(bKnight1);
        addToBoardAtPosition(bKnight2);
        break;

      case BLACK:
        for (int i = 0; i < PAWNS; i++) {
          Piece wPawn = new Pawn("Pawn", Color.BLACK, i, 6);
          addToBoardAtPosition(wPawn);
        }

        Piece wKing = new King("King", Color.BLACK, 4, 7);
        Piece wQueen = new Queen("Queen", Color.BLACK, 3, 7);
        Piece wRook1 = new Rook("Rook", Color.BLACK, 0, 7);
        Piece wRook2 = new Rook("Rook", Color.BLACK, 7, 7);
        Piece wBishop1 = new Bishop("Bishop", Color.BLACK, 2, 7);
        Piece wBishop2 = new Bishop("Bishop", Color.BLACK, 5, 7);
        Piece wKnight1 = new Knight("Knight", Color.BLACK, 1, 7);
        Piece wKnight2 = new Knight("Knight", Color.BLACK, 6, 7);

        addToBoardAtPosition(wKing);
        addToBoardAtPosition(wQueen);
        addToBoardAtPosition(wRook1);
        addToBoardAtPosition(wRook2);
        addToBoardAtPosition(wBishop1);
        addToBoardAtPosition(wBishop2);
        addToBoardAtPosition(wKnight1);
        addToBoardAtPosition(wKnight2);
        break;
    }
  }

  /**
   * When a piece is added to the board, it should be added both to the currentPieces set
   * and to the boardRepresentation matrix.
   */
  private void addToBoardAtPosition(Piece piece) {
    currentPieces.add(piece);
    boardRepresentation[piece.getyPosition()][piece.getxPosition()].setPiece(piece);
  }

  public void removeFromBoard(Piece piece) {
    currentPieces.remove(piece);
  }

  /**
   * Called when the game ends and prepares board to be reset for next game
   */
  private void removeAllPieces() {
    currentPieces.clear();
    removedPieces.clear();
  }

  public void setCurrentGame(Game game) {
    currentGame = game;
  }

  public Game getCurrentGame() {
    return currentGame;
  }

  /**
   * This function will allow a player to move a piece from a specified position to a destination position
   *
   * @param player: the player making the move.
   * @param atX:    the x-position of the piece to be moved
   * @param atY:    the y-position of the piece to be moved
   * @param toX:    the desired x-position to move the piece to
   * @param toY:    the desired y-position to move the piece to
   *                <p>
   *                Note: this function throws an exception if the move cannot be made. This could be due to varius reasons such as:
   *                - it's not the players turn
   *                - there is no piece at the given location
   *                - the player is trying to move the other players piece
   *                - the move itself is not valid according to the rules of chess
   *                - the piece is blocked by another piece of the same color
   */
  public void movePiece(Player player, int atX, int atY, int toX, int toY) throws Exception {
    if (player.getCurrentColor() != currentGame.getCurrentTurn()) {
      throw new Exception("It's not " + player.getName() + "'s turn yet!");
    }
    if (!boardRepresentation[atX][atY].hasPiece()) {
      throw new Exception("This square is empty! There's no piece to move");
    }

    Piece piece = boardRepresentation[atX][atY].getPiece();
    CaptureDirection direction = null;

    if (piece.getColor() != player.getCurrentColor()) {
      throw new Exception(player.getName() + " can only move their own pieces!");
    }
    if (piece instanceof Pawn && pawnCanCapture(piece) != null) {
      direction = pawnCanCapture(piece);
    }
    if (!piece.isMoveValid(direction, atX, atY, toX, toY)) {
      throw new Exception("This move is not valid. Try again.");
    }
    if (pieceIsBlocked(piece, atX, atY, toX, toY)) {
      throw new Exception("Move not valid! There is another piece blocking this move.");
    }

    System.out.println("Board: move is valid, making movement...");

    if (boardRepresentation[toX][toY].hasPiece()) {
      removedPieces.add(boardRepresentation[toX][toY].getPiece());
      currentPieces.remove(boardRepresentation[toX][toY].getPiece());
    }

    boardRepresentation[atX][atY].setPiece(null);
    boardRepresentation[toX][toY].setPiece(piece);
    piece.updatePosition(toX, toY);

    displayBoard();
    currentGame.nextTurn();
    currentGame.checkTurn();
  }

  /**
   * Loops through the boardRepresentation printing the first letter
   * of the piece if it is valid inside the square, otherwise ' - '
   * to represent an empty square
   */
  public void displayBoard() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Square currentSquare = boardRepresentation[i][j];

        if (currentSquare.hasPiece()) {
          System.out.print(" " + currentSquare.getPiece().getName().toCharArray()[0] + " ");
        } else {
          System.out.print(" - ");
        }

      }
      System.out.println();
    }
  }

  /**
   * This function checks if the piece trying to be moved from a specifed position to
   * the destination is blocked by any other piece of the same color, immediately making
   * the move invalid.
   *
   * @param piece: the piece being moved
   * @param fromX: the starting x position
   * @param fromY: the starting y position
   * @param toX:   the desired x position
   * @param toY:   the desired y position
   *               <p>
   *               Also: the inside of this function is ugly af.
   */
  private boolean pieceIsBlocked(Piece piece, int fromX, int fromY, int toX, int toY) {

    int deltaX = toX - fromX;
    int deltaY = toY - fromY;
    int xDistance = Math.abs(deltaX);
    int yDistance = Math.abs(deltaY);
    Square toSquare = boardRepresentation[toX][toY];

    if (piece instanceof Knight) {
      //Knight is a special case since it doesn't pass any squares
      if (!piece.isMoveValid(CaptureDirection.RIGHT, fromX, fromY, toX, toY)) {
        return true;
      }
      if (toSquare.hasPiece() && toSquare.getPiece().getColor() == piece.getColor()) {
        return true;
      }
    } else if (piece instanceof Pawn) {
      if ((xDistance == 1 && yDistance == 0) && toSquare.hasPiece()) {
        // if the move being made is a single step forward, then check if there is another piece in front
        return true;
      }
    } else if(!piece.getClass().equals(Piece.class)) {
      //uses that all other pieces move in a straight line in a practically identically way
      if (!piece.isMoveValid(CaptureDirection.RIGHT, fromX, fromY, toX, toY)) {
        return true;
      }
      int stepX = Integer.compare(deltaX, 0);
      int stepY = Integer.compare(deltaY, 0);
      int currentX = fromX;
      int currentY = fromY;

      //goes through all the passed squares one by one and checks if it is blocked
      while (currentX != toX || currentY != toY) {
        currentX += stepX;
        currentY += stepY;
        Square currentSquare = boardRepresentation[currentX][currentY];
        if (currentSquare.hasPiece() && currentSquare.getPiece().getColor().equals(piece.getColor())) {
          return true;
        }
        if (currentSquare.hasPiece() && (currentX != toX || currentY != toY)) {
          return true;
        }
      }
    }
    return false;
  }

   /**
    * This function checks if a pawn has a piece of the opposite color to it's diagonal left or right
    * in which case it is also a valid move for the pawn to move a single step diagonally to the
    * left or right.
    *
    * This is done by getting the (x,y) positions of the pawns diagonal left and right and checking
    * if the squares at these positions have pieces of the opposite color in them
   */
  private CaptureDirection pawnCanCapture(Piece pawn) {
    int xPos = pawn.getxPosition();
    int yPos = pawn.getyPosition();

    int leftxPos = 0;
    int leftyPos = 0;
    int rightxPos = 0;
    int rightyPos = 0;

    Color oppositeColor = null;

    // Check if there's a piece to the left or right diagonal of the pawns current position
    switch (pawn.getColor()) {
      case WHITE:
        oppositeColor = Color.BLACK;
        leftxPos = xPos + 1;
        leftyPos = yPos - 1;
        rightxPos = xPos + 1;
        rightyPos = yPos + 1;
      case BLACK:
        oppositeColor = Color.WHITE;
        leftxPos = xPos - 1;
        leftyPos = xPos - 1;
        rightxPos = xPos - 1;
        rightyPos = yPos + 1;
    }


    if (leftxPos >= 0 && leftyPos >= 0 && rightxPos >= 0 && rightyPos >= 0) {
      if (boardRepresentation[leftxPos][leftyPos].hasPiece()) {
        if (boardRepresentation[leftxPos][leftyPos].getPiece().getColor() == oppositeColor) {
          return CaptureDirection.LEFT;
        }
      } else if (boardRepresentation[rightxPos][rightyPos].hasPiece()) {
        if (boardRepresentation[rightxPos][rightyPos].getPiece().getColor() == oppositeColor) {
          return CaptureDirection.RIGHT;
        }
      }
    }

    return null;
  }

  public HashSet<Piece> getCurrentPieces() {
    return currentPieces;
  }

  public HashSet<Piece> getRemovedPieces() {
    return removedPieces;
  }

}