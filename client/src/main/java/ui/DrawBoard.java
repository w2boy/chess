package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";
    private static Random rand = new Random();


    public void run(ChessPiece[][] matrix) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoardWhite(out, matrix);

        out.println();
        out.println();

        drawHeadersBlack(out);

        drawChessBoardBlack(out, matrix);

        setDarkGrey(out);
    }

    private static void drawHeaders(PrintStream out) {

        setDarkGrey(out);

        String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };

        out.print("    ");
        out.print(headers[0]);
        out.print("  ");
        out.print(headers[1]);
        out.print("  ");
        out.print(headers[2]);
        out.print("  ");
        out.print(headers[3]);
        out.print("  ");
        out.print(headers[4]);
        out.print("  ");
        out.print(headers[5]);
        out.print("  ");
        out.print(headers[6]);
        out.print("  ");
        out.print(headers[7]);

        out.println();
    }

    private static void drawHeadersBlack(PrintStream out) {

        setDarkGrey(out);

        String[] headers = { "h", "g", "f", "e", "d", "c", "b", "a" };

        out.print("    ");
        out.print(headers[0]);
        out.print("  ");
        out.print(headers[1]);
        out.print("  ");
        out.print(headers[2]);
        out.print("  ");
        out.print(headers[3]);
        out.print("  ");
        out.print(headers[4]);
        out.print("  ");
        out.print(headers[5]);
        out.print("  ");
        out.print(headers[6]);
        out.print("  ");
        out.print(headers[7]);

        out.println();
    }

    private static void drawChessBoardWhite(PrintStream out, ChessPiece[][] matrix) {
            drawSquares(out, matrix, "WHITE");
            setDarkGrey(out);
    }

    private static void drawChessBoardBlack(PrintStream out, ChessPiece[][] matrix) {
        drawSquares(out, matrix, "BLACK");
        setDarkGrey(out);
    }

    private static void drawSquares(PrintStream out, ChessPiece[][] matrix, String color) {

        setDarkGrey(out);

        for (int squareRow = 0; squareRow < 8; ++squareRow) {
            int rowNumber;
            if (color.equals("WHITE")){
                rowNumber = 8 - squareRow;
            } else {
                rowNumber = 1 + squareRow;
            }

            out.print(" ");
            out.print(Integer.toString(rowNumber));
            out.print(" ");
            for (int boardCol = 0; boardCol < 8; ++boardCol) {

                int horizonotalView;
                int verticleView;
                if (color.equals("WHITE")){
                    horizonotalView = boardCol;
                    verticleView = 7-squareRow;
                } else {
                    horizonotalView = 7-boardCol;
                    verticleView = squareRow;
                }

                //Figure out Chess Piece
                ChessPiece currentPiece = matrix[verticleView][horizonotalView];
                String type = " ";
                if (currentPiece != null){
                    ChessPiece.PieceType pieceType = currentPiece.getPieceType();
                    switch (pieceType){
                        case KNIGHT:
                            type = "N";
                            break;
                        case PAWN:
                            type = "P";
                            break;
                        case ROOK:
                            type = "R";
                            break;
                        case BISHOP:
                            type = "B";
                            break;
                        case QUEEN:
                            type = "Q";
                            break;
                        case KING:
                            type = "K";
                            break;
                    }
                    if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        setGreen(out);
                    } else {
                        setRed(out);
                    }
                }

                //Display Piece
                if (squareRow % 2 == 0){

                    if (boardCol % 2 == 0){
                        setWhite(out);
                    } else {
                        setBlack(out);
                    }

                    out.print(" ");
                    out.print(type);
                    out.print(" ");

                    setDarkGrey(out);
                } else {
                    if (boardCol % 2 == 0){
                        setBlack(out);
                    } else {
                        setWhite(out);
                    }

                    out.print(" ");
                    out.print(type);
                    out.print(" ");

                    setDarkGrey(out);
                }
            }

            out.println();
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setGreen(PrintStream out) {
        out.print(SET_TEXT_COLOR_GREEN);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
    }

    private static void setDarkGrey(PrintStream out){
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }
}
