package org.example.chessItem.pieces;

import org.example.chessItem.board.ChessBoard;
import org.example.chessItem.board.Color;

public abstract class ChessPiece {

    protected Color color;
    public boolean check = true;

    ChessPiece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean moveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (checkAll(chessBoard, line, column, toLine, toColumn) && (canMoveToPosition(chessBoard, line, column, toLine, toColumn))) {
            chessBoard.CHESS_BOARD[toLine][toColumn] = chessBoard.CHESS_BOARD[line][column];
            chessBoard.CHESS_BOARD[line][column] = null;
            return true;
        }
        return false;
    }


    public boolean checkAll(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!chessBoard.checkPos(line) || !chessBoard.checkPos(column) ||
                !chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }
        if (line == toLine && column == toColumn) {
            return false;
        }
        if (chessBoard.CHESS_BOARD[line][column] == null) {
            return false;
        }
        if ((chessBoard.CHESS_BOARD[toLine][toColumn] != null) &&
                (chessBoard.CHESS_BOARD[line][column].getColor() == chessBoard.CHESS_BOARD[toLine][toColumn].getColor())) {
            return false;
        }
        return true;
    }

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    public abstract String getSymbol();
}
