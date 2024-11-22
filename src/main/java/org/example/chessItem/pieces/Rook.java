package org.example.chessItem.pieces;

import org.example.chessItem.board.ChessBoard;

import org.example.chessItem.board.Color;
import org.example.chessItem.util.RookMove;

public class Rook extends ChessPiece implements RookMove {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "R";
    }


    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверяем все базовые условия
        if (!checkAll(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }
        // Проверяем движение по прямой
        if (canMoveStraight(chessBoard, line, column, toLine, toColumn)) {
            // Убедимся, что путь свободен
            return isPathClear(chessBoard, line, column, toLine, toColumn);
        }
        return false;
    }

    // Проверяем, свободен ли путь
    private boolean isPathClear(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine) { // Горизонтальное движение
            int step = column < toColumn ? 1 : -1;
            for (int i = column + step; i != toColumn; i += step) {
                if (chessBoard.getPiece(line, i) != null) { // Проверяем фигуру
                    return false;
                }
            }
        } else if (column == toColumn) { // Вертикальное движение
            int step = line < toLine ? 1 : -1;
            for (int i = line + step; i != toLine; i += step) {
                if (chessBoard.getPiece(i, column) != null) { // Проверяем фигуру
                    return false;
                }
            }
        }
        return true;
    }


}
