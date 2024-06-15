package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";


    public void run(ChessPiece[][] matrix, String color, Collection<ChessMove> validMoves) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        out.println();

        if (color.equals("WHITE")){

            drawHeaders(out, new String[] { "a", "b", "c", "d", "e", "f", "g", "h" });

            drawChessBoardWhite(out, matrix, validMoves);
        } else if (color.equals("BLACK")){

            drawHeaders(out, new String[] { "h", "g", "f", "e", "d", "c", "b", "a" });

            drawChessBoardBlack(out, matrix, validMoves);
        }

        setDefault(out);
    }

    private static void drawHeaders(PrintStream out, String[] headers) {

        setDarkGrey(out);

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

    private static void drawChessBoardWhite(PrintStream out, ChessPiece[][] matrix, Collection<ChessMove> validMoves) {
            drawSquares(out, matrix, "WHITE", validMoves);
            setDarkGrey(out);
    }

    private static void drawChessBoardBlack(PrintStream out, ChessPiece[][] matrix, Collection<ChessMove> validMoves) {
        drawSquares(out, matrix, "BLACK", validMoves);
        setDarkGrey(out);
    }

    private static void drawSquares(PrintStream out, ChessPiece[][] matrix, String color, Collection<ChessMove> validMoves) {

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

                    if (validMoves != null && validMoveSpace(new ChessPosition(verticleView+1, horizonotalView+1), validMoves)){
                        setBlue(out);
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

                    if (validMoves != null && validMoveSpace(new ChessPosition(verticleView+1, horizonotalView+1), validMoves)){setBlue(out);}

                    out.print(" ");
                    out.print(type);
                    out.print(" ");

                    setDarkGrey(out);
                }
            }

            out.println();
        }
    }

    public static boolean validMoveSpace(ChessPosition spacePosition, Collection<ChessMove> validMoves){
        for (ChessMove validMove : validMoves){
            if (validMove.getEndPosition().getRow() == spacePosition.getRow()){
                if (validMove.getEndPosition().getColumn() == spacePosition.getColumn()){
                    return true;
                }
            }
        }
        return false;
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

    private static void setBlue(PrintStream out) {
        out.print(SET_BG_COLOR_BLUE);
    }

    private static void setDarkGrey(PrintStream out){
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void setDefault(PrintStream out){
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }
}
