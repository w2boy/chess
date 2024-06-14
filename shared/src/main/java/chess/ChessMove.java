package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private ChessPosition startPosition;
    private ChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(startPosition, chessMove.startPosition) && Objects.equals(endPosition, chessMove.endPosition) && promotionPiece == chessMove.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotionPiece);
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return this.promotionPiece;
    }

    @Override
    public String toString() {
        String startCol = null;
        String endCol = null;
        switch (startPosition.getColumn()) {
            case 1:
                startCol = "a";
                break;
            case 2:
                startCol = "b";
                break;
            case 3:
                startCol = "c";
                break;
            case 4:
                startCol = "d";
                break;
            case 5:
                startCol = "e";
                break;
            case 6:
                startCol = "f";
                break;
            case 7:
                startCol = "g";
                break;
            case 8:
                startCol = "h";
                break;
        }
        switch (endPosition.getColumn()) {
            case 1:
                endCol = "a";
                break;
            case 2:
                endCol = "b";
                break;
            case 3:
                endCol = "c";
                break;
            case 4:
                endCol = "d";
                break;
            case 5:
                endCol = "e";
                break;
            case 6:
                endCol = "f";
                break;
            case 7:
                endCol = "g";
                break;
            case 8:
                endCol = "h";
                break;
        }
        return "{" + startCol + Integer.toString(startPosition.getRow()) + ", " + endCol + Integer.toString(endPosition.getRow()) + "}";
    }
}
