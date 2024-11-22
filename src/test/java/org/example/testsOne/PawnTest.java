package org.example.testsOne;

import org.example.chessItem.board.ChessBoard;
import org.example.chessItem.board.Color;
import org.example.chessItem.board.GameState;
import org.example.chessItem.pieces.Pawn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void testPawnCanMoveForward() {
        ChessBoard board = new ChessBoard(Color.WHITE, GameState.RUNNING);
        Pawn whitePawn = new Pawn(Color.WHITE);
        board.placePiece(whitePawn, 1, 0); // Установить пешку на позицию (1, 0)

        // Пешка может пойти на одну клетку вперед
        assertTrue(whitePawn.canMoveToPosition(board, 1, 0, 2, 0));

        // Пешка может пойти на две клетки вперед с начальной позиции
        assertTrue(whitePawn.canMoveToPosition(board, 1, 0, 3, 0));

        // Пешка не может пойти назад
        assertFalse(whitePawn.canMoveToPosition(board, 1, 0, 0, 0));
    }

    @Test
    void testPawnBlockedByPiece() {
        ChessBoard board = new ChessBoard(Color.WHITE, GameState.RUNNING);
        Pawn whitePawn = new Pawn(Color.WHITE);
        Pawn blockingPiece = new Pawn(Color.WHITE);

        board.placePiece(whitePawn, 1, 0); // Установить пешку на позицию (1, 0)
        board.placePiece(blockingPiece, 2, 0); // Заблокировать движение на (2, 0)

        // Пешка не может пойти на заблокированную клетку
        assertFalse(whitePawn.canMoveToPosition(board, 1, 0, 2, 0));

        // Пешка не может перепрыгнуть фигуру
        assertFalse(whitePawn.canMoveToPosition(board, 1, 0, 3, 0));
    }

    @Test
    void testPawnCapturesDiagonally() {
        ChessBoard board = new ChessBoard(Color.WHITE, GameState.RUNNING);
        Pawn whitePawn = new Pawn(Color.WHITE);
        Pawn blackPawn = new Pawn(Color.BLACK);

        board.placePiece(whitePawn, 4, 4); // Белая пешка на (4, 4)
        board.placePiece(blackPawn, 5, 5); // Черная пешка на (5, 5)

        // Белая пешка может взять черную по диагонали
        assertTrue(whitePawn.canMoveToPosition(board, 4, 4, 5, 5));

        // Белая пешка не может идти вперед, если там стоит фигура
        board.placePiece(new Pawn(Color.BLACK), 5, 4);
        assertFalse(whitePawn.canMoveToPosition(board, 4, 4, 5, 4));
    }

    @Test
    void testPawnCannotMoveOutOfBoard() {
        ChessBoard board = new ChessBoard(Color.WHITE, GameState.RUNNING);
        Pawn whitePawn = new Pawn(Color.WHITE);

        board.placePiece(whitePawn, 7, 7); // Пешка на краю доски

        // Пешка не может выйти за границы доски
        assertFalse(whitePawn.canMoveToPosition(board, 7, 7, 8, 7));
        assertFalse(whitePawn.canMoveToPosition(board, 7, 7, 7, 8));
    }

    @Test
    void testPawnCannotMoveInInvalidState() {
        ChessBoard board = new ChessBoard(Color.WHITE, GameState.END);
        Pawn whitePawn = new Pawn(Color.WHITE);
        board.placePiece(whitePawn, 1, 0);

        // В завершенном состоянии игра не допускает ходов
        assertFalse(board.moveToPosition(1, 0, 2, 0));
    }
}
