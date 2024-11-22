package org.example.chessItem.pieces;
import org.example.chessItem.board.ChessBoard;
import org.example.chessItem.board.Color;

public class Knight extends ChessPiece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (checkAll(chessBoard,line,column,toLine,toColumn)) {
            int dx = Math.abs(line - toLine);
            int dy = Math.abs(column - toColumn);

            return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
        }
        return false;
    }

    @Override
    public String getSymbol() {

        return "H";
    }

    @Override
    public Color getColor() {
        return color;
    }

}
