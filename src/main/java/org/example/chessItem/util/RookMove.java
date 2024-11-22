package org.example.chessItem.util;

import org.example.chessItem.board.ChessBoard;

public interface RookMove {
    default boolean canMoveStraight(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        return line == toLine || column == toColumn;
    }
}
