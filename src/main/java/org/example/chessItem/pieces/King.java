package org.example.chessItem.pieces;

import org.example.chessItem.board.ChessBoard;

import org.example.chessItem.board.Color;

public class King extends ChessPiece {
    public King(Color color) {
        super(color);
    }

    @Override
    public Color getColor() {
        return super.getColor();
    }



    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (checkAll(chessBoard,line,column,toLine,toColumn)) {
            if (Math.abs(toLine - line) <= 1 && Math.abs(toColumn - column) <= 1) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String getSymbol() {
        return "K";
    }
    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        for (int i = 0; i < chessBoard.CHESS_BOARD.length; i++) {
            for (int j = 0; j < chessBoard.CHESS_BOARD[i].length; j++) {
                ChessPiece piece = chessBoard.CHESS_BOARD[i][j];
                if (piece != null && piece.getColor() != this.getColor()) {
                    if (piece.canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
