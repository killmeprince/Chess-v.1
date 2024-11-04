package org.example.chessItem;

public interface BishopMove {
    default boolean canMoveDiagonally(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (Math.abs(line - toLine) == Math.abs(column - toColumn)) {
            int stepToLine = (toLine > line) ? 1 : -1;
            int stepToColumn = (toColumn > column) ? 1 : -1;
            int currentLine = line + stepToLine;
            int currentColumn = column + stepToColumn;

            while (currentLine != toLine && currentColumn != toColumn) {
                if (chessBoard.CHESS_BOARD[currentLine][currentColumn] != null) {
                    return false;
                }
                currentLine += stepToLine;
                currentColumn += stepToColumn;
            }
            return true;
        }
        return false;
    }
}

