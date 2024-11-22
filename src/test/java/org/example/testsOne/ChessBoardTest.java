package org.example.testsOne;

import org.example.chessItem.board.ChessBoard;
import org.example.chessItem.board.Color;
import org.example.chessItem.board.GameState;
import org.example.chessItem.pieces.ChessPiece;
import org.example.chessItem.pieces.King;
import org.example.chessItem.pieces.Rook;
import org.example.chessItem.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    private ChessBoard chessBoard;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard(Color.WHITE, GameState.RUNNING);
        chessBoard.placePiece(new King(Color.WHITE), 0, 4);
        chessBoard.placePiece(new Rook(Color.WHITE), 0, 0);
        chessBoard.placePiece(new Rook(Color.WHITE), 0, 7);
        chessBoard.placePiece(new King(Color.BLACK), 7, 4);
    }

    @Test
    void testPlacePiece() {
        ChessPiece pawn = new Pawn(Color.WHITE);
        chessBoard.placePiece(pawn, 1, 0);
        assertEquals(pawn, chessBoard.CHESS_BOARD[1][0], "Пешка должна быть на позиции 1,0");
    }

    @Test
    void testMoveToPositionValidMove() {
        chessBoard.placePiece(new Pawn(Color.WHITE), 1, 0);
        boolean moved = chessBoard.moveToPosition(1, 0, 2, 0);
        assertTrue(moved, "Фигура должна успешно переместиться");
        assertNull(chessBoard.CHESS_BOARD[1][0], "Начальная позиция должна быть пустой");
        assertNotNull(chessBoard.CHESS_BOARD[2][0], "Конечная позиция должна быть занята");
    }

    @Test
    void testMoveToPositionInvalidMove() {
        chessBoard.placePiece(new Pawn(Color.WHITE), 1, 0);
        boolean moved = chessBoard.moveToPosition(1, 0, 3, 0); // Пешка не может ходить на 2 клетки, если не изначальная позиция
        assertFalse(moved, "Ход должен быть запрещён");
    }

    @Test
    void testIsCheck() {
        chessBoard.placePiece(new Rook(Color.BLACK), 6, 4);
        boolean isCheck = chessBoard.isCheck(Color.WHITE);
        assertTrue(isCheck, "Должен быть шах белому королю");
    }

    @Test
    void testIsNotCheck() {
        chessBoard.placePiece(new Rook(Color.BLACK), 6, 0);
        boolean isCheck = chessBoard.isCheck(Color.WHITE);
        assertFalse(isCheck, "Не должно быть шаха белому королю");
    }

    @Test
    void testIsCheckmate() {
        chessBoard.placePiece(new Rook(Color.BLACK), 6, 3);
        chessBoard.placePiece(new Rook(Color.BLACK), 6, 5);
        boolean isCheckmate = chessBoard.isCheckmate(Color.WHITE);
        assertTrue(isCheckmate, "Должен быть мат белому королю");
    }

    @Test
    void testCastlingShort() {
        boolean castled = chessBoard.castling7();
        assertTrue(castled, "Короткая рокировка должна быть успешной");
        assertNotNull(chessBoard.CHESS_BOARD[0][6], "Король должен находиться на позиции 0,6");
        assertNotNull(chessBoard.CHESS_BOARD[0][5], "Ладья должна находиться на позиции 0,5");
    }

    @Test
    void testCastlingLong() {
        boolean castled = chessBoard.castling0();
        assertTrue(castled, "Длинная рокировка должна быть успешной");
        assertNotNull(chessBoard.CHESS_BOARD[0][2], "Король должен находиться на позиции 0,2");
        assertNotNull(chessBoard.CHESS_BOARD[0][3], "Ладья должна находиться на позиции 0,3");
    }

    @Test
    void testInvalidCastlingWhenUnderCheck() {
        chessBoard.placePiece(new Rook(Color.BLACK), 7, 4);
        boolean castled = chessBoard.castling7();
        assertFalse(castled, "Рокировка не должна быть возможна, если король под шахом");
    }

    @Test
    void testGameStateCheckmate() {
        chessBoard.placePiece(new Rook(Color.BLACK), 6, 3);
        chessBoard.placePiece(new Rook(Color.BLACK), 6, 5);
        chessBoard.updateGameState();
        assertEquals(GameState.CHECKMATE, chessBoard.getGameState(), "Состояние игры должно быть 'Мат'");
    }

    @Test
    void testGameStateStalemate() {
        chessBoard.placePiece(new Pawn(Color.BLACK), 6, 4);
        chessBoard.placePiece(new Rook(Color.BLACK), 5, 7);
        chessBoard.updateGameState();
        assertEquals(GameState.STALEMATE, chessBoard.getGameState(), "Состояние игры должно быть 'Пат'");
    }
}
