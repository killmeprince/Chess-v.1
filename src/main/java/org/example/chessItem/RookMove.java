package org.example.chessItem;

public interface RookMove {
    default boolean canMoveStraight(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine || column == toColumn) {
            return true;
        }
    return false;
    }
}
