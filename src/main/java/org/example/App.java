package org.example;

import org.example.chessItem.*;

import java.util.Scanner;

public class App {
    public static ChessBoard buildBoard() {
        ChessBoard chessBoard = new ChessBoard(Color.WHITE, GameState.RUNNING);

        chessBoard.CHESS_BOARD[0][0] = new Rook(Color.WHITE);
        chessBoard.CHESS_BOARD[0][1] = new Knight(Color.WHITE);
        chessBoard.CHESS_BOARD[0][2] = new Bishop(Color.WHITE);
        chessBoard.CHESS_BOARD[0][3] = new Queen(Color.WHITE);
        chessBoard.CHESS_BOARD[0][4] = new King(Color.WHITE);
        chessBoard.CHESS_BOARD[0][5] = new Bishop(Color.WHITE);
        chessBoard.CHESS_BOARD[0][6] = new Knight(Color.WHITE);
        chessBoard.CHESS_BOARD[0][7] = new Rook(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            chessBoard.CHESS_BOARD[1][i] = new Pawn(Color.WHITE);
        }


        chessBoard.CHESS_BOARD[7][0] = new Rook(Color.BLACK);
        chessBoard.CHESS_BOARD[7][1] = new Knight(Color.BLACK);
        chessBoard.CHESS_BOARD[7][2] = new Bishop(Color.BLACK);
        chessBoard.CHESS_BOARD[7][3] = new Queen(Color.BLACK);
        chessBoard.CHESS_BOARD[7][4] = new King(Color.BLACK);
        chessBoard.CHESS_BOARD[7][5] = new Bishop(Color.BLACK);
        chessBoard.CHESS_BOARD[7][6] = new Knight(Color.BLACK);
        chessBoard.CHESS_BOARD[7][7] = new Rook(Color.BLACK);
        for (int i = 0; i < 8; i++) {
            chessBoard.CHESS_BOARD[6][i] = new Pawn(Color.BLACK);
        }

        return chessBoard;
    }

    public static void main(String[] args) {
        ChessBoard board = buildBoard();
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
               Чтобы проверить игру, вводите команды:
               'exit' - для выхода
               'replay' - для перезапуска игры
               'castling0' или 'castling7' - для рокировки по соответствующей стороне
               'move 1 1 2 3' - для перемещения фигуры с позиции 1 1 на 2 3 (поле — это двумерный массив от 0 до 7)
               Проверьте: могут ли фигуры ходить сквозь друг друга, корректно ли они съедают другие фигуры, можно ли сделать рокировку и т.д.""");
        System.out.println();
        board.printBoard();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            } else if (input.equals("replay")) {
                System.out.println("Перезапуск игры");
                board = buildBoard();
                board.printBoard();
            } else if (input.equals("castling0")) {
                if (board.castling0()) {
                    System.out.println("Рокировка по стороне 0 удалась");
                    board.printBoard();
                } else {
                    System.out.println("Рокировка по стороне 0 не удалась");
                }
            } else if (input.equals("castling7")) {
                if (board.castling7()) {
                    System.out.println("Рокировка по стороне 7 удалась");
                    board.printBoard();
                } else {
                    System.out.println("Рокировка по стороне 7 не удалась");
                }
            } else if (input.startsWith("move")) {
                String[] parts = input.split(" ");
                try {
                    int startLine = Integer.parseInt(parts[1]);
                    int startColumn = Integer.parseInt(parts[2]);
                    int endLine = Integer.parseInt(parts[3]);
                    int endColumn = Integer.parseInt(parts[4]);

                    if (board.moveToPosition(startLine, startColumn, endLine, endColumn)) {
                        System.out.println("Фигура успешно перемещена");
                        board.printBoard();
                    } else {
                        System.out.println("Невозможно выполнить ход");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка ввода. Попробуйте снова.");
                }
            }
        }
    }
}
