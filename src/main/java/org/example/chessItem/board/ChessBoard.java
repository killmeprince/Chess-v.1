package org.example.chessItem.board;

import org.example.chessItem.pieces.ChessPiece;
import org.example.chessItem.pieces.King;
import org.example.chessItem.pieces.Rook;

public class ChessBoard {
    public ChessPiece[][] CHESS_BOARD = new ChessPiece[8][8];
    private Color nowPlayer;
    private GameState gameState;

    public ChessBoard(Color nowPlayer, GameState gameState) {
        this.nowPlayer = nowPlayer;
        this.gameState = gameState;
    }

    public boolean isCheck(Color color) {
        int[] kingPosition = findKing(color);
        if (kingPosition == null) {
            return false;
        }
        return isUnderAttack(kingPosition[0], kingPosition[1], color);
    }
    public ChessPiece getPiece(int line, int column) {
        if (checkPos(line) && checkPos(column)) {
            return CHESS_BOARD[line][column];
        }
        return null;
    }


    private int[] findKing(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = CHESS_BOARD[i][j];
                if (piece instanceof King && piece.getColor() == color) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private boolean isUnderAttack(int line, int column, Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = CHESS_BOARD[i][j];
                if (piece != null && piece.getColor() != color) {
                    if (piece.canMoveToPosition(this, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasAnyValidMove(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = CHESS_BOARD[i][j];
                if (piece != null && piece.getColor() == color) {
                    for (int toLine = 0; toLine < 8; toLine++) {
                        for (int toColumn = 0; toColumn < 8; toColumn++) {
                            if (piece.canMoveToPosition(this, i, j, toLine, toColumn)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(Color color) {
        return isCheck(color) && !hasAnyValidMove(color);
    }

    public boolean isStalemate(Color color) {
        return !isCheck(color) && !hasAnyValidMove(color);
    }

    public boolean isDraw() {
        return isInsufficientMaterial(); // Add additional draw rules as needed
    }

    private boolean isInsufficientMaterial() {
        int whitePieces = 0, blackPieces = 0;
        for (ChessPiece[] row : CHESS_BOARD) {
            for (ChessPiece piece : row) {
                if (piece != null) {
                    if (piece.getColor() == Color.WHITE) whitePieces++;
                    else blackPieces++;
                }
            }
        }
        return (whitePieces <= 2 && blackPieces <= 2);
    }

    public void updateGameState() {
        if (isCheckmate(nowPlayer)) {
            gameState = GameState.CHECKMATE;
            System.out.println("Checkmate! " + (nowPlayer == Color.WHITE ? "Black" : "White") + " wins.");
        } else if (isStalemate(nowPlayer)) {
            gameState = GameState.STALEMATE;
            System.out.println("Stalemate! It's a draw.");
        } else if (isDraw()) {
            gameState = GameState.DRAW;
            System.out.println("Draw!");
        }
    }

    public void placePiece(ChessPiece piece, int line, int column) {
        if (checkPos(line) && checkPos(column)) {
            CHESS_BOARD[line][column] = piece;
        }
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (gameState != GameState.RUNNING) {
            System.out.println("The game has ended.");
            return false;
        }

        if (!checkPos(startLine) || !checkPos(startColumn) || !checkPos(endLine) || !checkPos(endColumn)) {
            return false;
        }

        ChessPiece piece = CHESS_BOARD[startLine][startColumn];
        if (piece == null || piece.getColor() != nowPlayer) {
            return false;
        }

        if (piece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
            CHESS_BOARD[endLine][endColumn] = piece;
            CHESS_BOARD[startLine][startColumn] = null;
            switchPlayer();
            updateGameState();
            return true;
        }
        return false;
    }

    private void switchPlayer() {
        nowPlayer = (nowPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public void printBoard() {
        System.out.println("Turn: " + nowPlayer);
        System.out.println("    0   1   2   3   4   5   6   7");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        for (int i = 7; i >= 0; i--) {
            System.out.print(i  + " |"); // Ряды с 1 до 8
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = CHESS_BOARD[i][j];
                System.out.print(" " + (piece == null ? " " : piece.getSymbol()) + " |");
            }
            System.out.println();
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }
    }


    public boolean checkPos(int pos) {
        return pos >= 0 && pos < 8;
    }
    //long castling (wr < 50%)
    public boolean castling0() {
        if (gameState != GameState.RUNNING) {
            return false;
        }
        ChessPiece king = CHESS_BOARD[0][4];
        ChessPiece rook = CHESS_BOARD[0][0];

        if (king instanceof King && rook instanceof Rook) {
            King whiteKing = (King) king;
            Rook whiteRook = (Rook) rook;


            if (!whiteKing.check || !whiteRook.check) {
                return false;
            }


            for (int i = 1; i < 4; i++) {
                if (CHESS_BOARD[0][i] != null) {
                    return false;
                }
            }


            if (whiteKing.isUnderAttack(this, 0, 4) ||
                    whiteKing.isUnderAttack(this, 0, 3) ||
                    whiteKing.isUnderAttack(this, 0, 2)) {
                return false;
            }


            CHESS_BOARD[0][2] = king;
            CHESS_BOARD[0][3] = rook;
            CHESS_BOARD[0][4] = null;
            CHESS_BOARD[0][0] = null;


            whiteKing.check = false;
            whiteRook.check = false;


            switchPlayer();
            return true;
        }
        return false;
    }


    //short castling (wr > 50%)
    public boolean castling7() {
        if (gameState != GameState.RUNNING) {
            return false;
        }
        ChessPiece king = CHESS_BOARD[0][4];
        ChessPiece rook = CHESS_BOARD[0][7];

        if (king instanceof King && rook instanceof Rook) {
            King whiteKing = (King) king;
            Rook whiteRook = (Rook) rook;

            if (!whiteKing.check || !whiteRook.check) {
                return false;
            }

            if (CHESS_BOARD[0][5] != null || CHESS_BOARD[0][6] != null) {
                return false;
            }


            if (whiteKing.isUnderAttack(this, 0, 4) ||
                    whiteKing.isUnderAttack(this, 0, 5) ||
                    whiteKing.isUnderAttack(this, 0, 6)) {
                return false;
            }

            CHESS_BOARD[0][6] = king;
            CHESS_BOARD[0][5] = rook;
            CHESS_BOARD[0][4] = null;
            CHESS_BOARD[0][7] = null;

            whiteKing.check = false;
            whiteRook.check = false;

            switchPlayer();
            return true;
        }
        return false;
    }


    public GameState getGameState() {
        return this.gameState;
    }
}
