package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceType == that.pieceType && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, pieceColor);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPiece.PieceType myPieceType = getPieceType();

        switch (myPieceType) {
            case KING:
                KingMovesCalculator kingMovesCalculator = new KingMovesCalculator();
                moves = kingMovesCalculator.pieceMoves(board, myPosition);
                break;
            case QUEEN:
                QueenMovesCalculator queenMovesCalculator = new QueenMovesCalculator();
                moves = queenMovesCalculator.pieceMoves(board, myPosition);
                break;
            case ROOK:
                RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
                moves = rookMovesCalculator.pieceMoves(board, myPosition);
                break;
            case BISHOP:
                BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator();
                moves = bishopMovesCalculator.pieceMoves(board, myPosition);
                break;
            case KNIGHT:
                KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator();
                moves = knightMovesCalculator.pieceMoves(board,myPosition);
                break;
            case PAWN:
                PawnMovesCalculator pawnMovesCalculator = new PawnMovesCalculator();
                moves = pawnMovesCalculator.pieceMoves(board, myPosition);
                break;
        }

        return moves;}

    @Override
    public String toString(){
        return this.pieceType.toString() + " " + this.pieceColor.toString();
    }
}
