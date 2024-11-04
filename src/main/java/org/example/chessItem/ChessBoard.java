package org.example.chessItem;

public class ChessBoard {
    public ChessPiece[][] CHESS_BOARD = new ChessPiece[8][8];
    private Color nowPlayer;
    private GameState gameState;


    public ChessBoard(Color nowPlayer, GameState gameState) {
        this.nowPlayer = nowPlayer;
        this.gameState = gameState;
    }


    public Color getNowPlayerColor() {
        return this.nowPlayer;
    }


    public GameState getGameState() {
        return this.gameState;
    }


    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {

        if (gameState != GameState.RUNNING) {
            System.out.println("GAME END");
            return false;
        }

        if (!checkPos(startLine) || !checkPos(startColumn)) {
            return false;
        }
        ChessPiece piece = CHESS_BOARD[startLine][startColumn];
        if (piece == null || !piece.getColor().equals(nowPlayer)) {
            return false;
        }
        if (piece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
            makeMove(piece, startLine, startColumn, endLine, endColumn);
            return true;
        }
        return false;
    }


    private void makeMove(ChessPiece piece, int startLine, int startColumn, int endLine, int endColumn) {
        CHESS_BOARD[endLine][endColumn] = piece;
        CHESS_BOARD[startLine][startColumn] = null;
        switchPlayer();
    }


    private void switchPlayer() {
        nowPlayer = (nowPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }


    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    //long castling (wr < 50%)
    public boolean castling0() {
        if (gameState != GameState.RUNNING) {
            return false;
        }
        ChessPiece king = CHESS_BOARD[0][4];  // К бел
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

    public void printBoard() {
        System.out.println("Turn: " + nowPlayer);
        System.out.println("Player 2 (Black)");

        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i >= 0; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = CHESS_BOARD[i][j];
                if (piece == null) {
                    System.out.print("..\t");
                } else {
                    String colorSymbol = piece.getColor() == Color.WHITE ? "w" : "b";
                    System.out.print(piece.getSymbol() + colorSymbol + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("Player 1 (White)");
    }
}
