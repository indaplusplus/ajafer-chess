package Pieces;

import Helpers.CaptureDirection;
import Helpers.Color;

public class Bishop extends Piece {

  private String name;
  private Color color;
  private int xPosition;
  private int yPosition;

  public Bishop(String name, Color color, int xPosition, int yPosition) {
    super(name, color, xPosition, yPosition);
    this.name = name;
    this.color = color;
    this.xPosition = xPosition;
    this.yPosition = yPosition;
  }

  // We don't need to check for color here since a bishop anywhere on the board moves in a similar manner regardless
  // if it's black or white.
  @Override
  public boolean isMoveValid(CaptureDirection captureDirection, int startX, int startY, int endX, int endY) {
    System.out.println("Preparing to move... " + name);

    if (startX >= 0 && startX <= 7 && startY >= 0 && startY <= 7 && endX >= 0 && endX <= 7 && endY >= 0 && endY <= 7) {
      int deltaX = endX - startX;
      int deltaY = endY - startY;

      if (deltaX < 0 && deltaY > 0 && (deltaY / deltaX) == -1) {
        return true;
      } else if (deltaX < 0 && deltaY < 0 && (deltaY / deltaX) == 1) {
        return true;
      } else if (deltaX > 0 && deltaY > 0 && (deltaY / deltaX) == 1) {
        return true;
      } else if (deltaX > 0 && deltaY < 0 && (deltaY / deltaX) == -1) {
        return true;
      }
    }

    return false;
  }
}
