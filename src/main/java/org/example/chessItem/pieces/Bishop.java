package org.example.chessItem.pieces;

import org.example.chessItem.board.ChessBoard;

import org.example.chessItem.board.Color;
import org.example.chessItem.util.BishopMove;

public class Bishop extends ChessPiece implements BishopMove {

    
    public Bishop(Color color) {
        super(color);
    }
    @Override
    public Color getColor() {
        return super.getColor();
    }
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (checkAll(chessBoard,line,column,toLine,toColumn)) {
            return canMoveDiagonally(chessBoard, line, column, toLine, toColumn);
        }
        return false;
    }
    @Override
    public String getSymbol() {
        return "B";
    }
}
