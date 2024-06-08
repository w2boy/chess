package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable {

    public ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard() {
        
    }

    public ChessBoard copiedBoard(){
        ChessBoard clonedBoard = new ChessBoard();

        //Clone pieces on board
        for (int i=1; i<=8; i++) {
            for (int j=1; j<=8; j++) {
                ChessPiece clonedPiece = this.getPiece(new ChessPosition(i,j));
                if (clonedPiece != null){
                    clonedBoard.addPiece(new ChessPosition(i, j), clonedPiece);
                }
            }
        }

        return clonedBoard;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //Place white pawns
        for (int i=0; i <=7; i++){
            ChessPiece p = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            ChessPosition pPos = new ChessPosition(2, i+1);
            this.addPiece(pPos, p);
        }
        //Place black pawns
        for (int i=0; i <=7; i++){
            ChessPiece p = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            ChessPosition pPos = new ChessPosition(7, i+1);
            this.addPiece(pPos, p);
        }
        //Place rest of white pieces
        //Rooks
        ChessPiece whiteRook1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPosition whiteRook1Position = new ChessPosition(1, 1);
        this.addPiece(whiteRook1Position, whiteRook1);
        ChessPiece whiteRook2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPosition whiteRook2Position = new ChessPosition(1, 8);
        this.addPiece(whiteRook2Position, whiteRook2);
        //Knights
        ChessPiece whiteKnight1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPosition whiteKnight1Position = new ChessPosition(1, 2);
        this.addPiece(whiteKnight1Position, whiteKnight1);
        ChessPiece whiteKnight2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPosition whiteKnight2Position = new ChessPosition(1, 7);
        this.addPiece(whiteKnight2Position, whiteKnight2);
        //Bishops
        ChessPiece whiteBishop1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPosition whiteBishop1Position = new ChessPosition(1, 3);
        this.addPiece(whiteBishop1Position, whiteBishop1);
        ChessPiece whiteBishop2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPosition whiteBishop2Position = new ChessPosition(1, 6);
        this.addPiece(whiteBishop2Position, whiteBishop2);
        //Queen
        ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPosition whiteQueenPosition = new ChessPosition(1, 4);
        this.addPiece(whiteQueenPosition, whiteQueen);
        //King
        ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPosition whiteKingPosition = new ChessPosition(1, 5);
        this.addPiece(whiteKingPosition, whiteKing);

        //Place rest of black pieces
        //Rooks
        ChessPiece blackRook1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPosition blackRook1Position = new ChessPosition(8, 1);
        this.addPiece(blackRook1Position, blackRook1);
        ChessPiece blackRook2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPosition blackRook2Position = new ChessPosition(8, 8);
        this.addPiece(blackRook2Position, blackRook2);
        //Knights
        ChessPiece blackKnight1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPosition blackKnight1Position = new ChessPosition(8, 2);
        this.addPiece(blackKnight1Position, blackKnight1);
        ChessPiece blackKnight2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPosition blackKnight2Position = new ChessPosition(8, 7);
        this.addPiece(blackKnight2Position, blackKnight2);
        //Bishops
        ChessPiece blackBishop1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPosition blackBishop1Position = new ChessPosition(8, 3);
        this.addPiece(blackBishop1Position, blackBishop1);
        ChessPiece blackBishop2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPosition blackBishop2Position = new ChessPosition(8, 6);
        this.addPiece(blackBishop2Position, blackBishop2);
        //Queen
        ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPosition blackQueenPosition = new ChessPosition(8, 4);
        this.addPiece(blackQueenPosition, blackQueen);
        //King
        ChessPiece blackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPosition blackKingPosition = new ChessPosition(8, 5);
        this.addPiece(blackKingPosition, blackKing);
    }
}
