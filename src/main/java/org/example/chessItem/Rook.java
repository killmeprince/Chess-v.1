package org.example.chessItem;

public class Rook extends ChessPiece implements RookMove {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public Color getColor() {
        return super.getColor();
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (checkAll(chessBoard,line,column,toLine,toColumn)) {
            canMoveStraight(chessBoard,line,column,toLine,toColumn);
        }
        return false;
    }


}
