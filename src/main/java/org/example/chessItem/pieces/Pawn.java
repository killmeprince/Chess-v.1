package org.example.chessItem.pieces;

import org.example.chessItem.board.ChessBoard;

import org.example.chessItem.board.Color;

public class Pawn extends ChessPiece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "P";

    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!chessBoard.checkPos(line) || !chessBoard.checkPos(column) ||
                !chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }
        int direction = (this.getColor() == Color.WHITE) ? 1 : -1;

        if (column == toColumn) {
            if (line + direction == toLine && chessBoard.CHESS_BOARD[toLine][toColumn] == null) {
                return true;
            }
            if ((this.getColor() == Color.WHITE && line == 1 || this.getColor() == Color.BLACK && line == 6) &&
                    line + 2 * direction == toLine &&
                    chessBoard.CHESS_BOARD[toLine][toColumn] == null &&
                    chessBoard.CHESS_BOARD[line + direction][column] == null) {
                return true;
            }
        }
        if (Math.abs(column - toColumn) == 1 && line + direction == toLine &&
                chessBoard.CHESS_BOARD[toLine][toColumn] != null &&
                chessBoard.CHESS_BOARD[toLine][toColumn].getColor() != this.getColor()) {
            return true;
        }
        return false;
    }

    @Override
    public Color getColor() {
        return super.getColor();
    }


}
