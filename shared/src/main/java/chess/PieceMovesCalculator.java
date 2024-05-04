package chess;

import java.util.Collection;

public class PieceMovesCalculator {

    public PieceMovesCalculator() {
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor,ChessPiece.PieceType type) {
        Collection<ChessMove> moves = null;
        switch (type) {
            case KING:
                KingMovesCalculator kingMovesCalculator = new KingMovesCalculator();
                moves = kingMovesCalculator.pieceMoves(board, myPosition, pieceColor, type);
                break;
            case QUEEN:
                QueenMovesCalculator queenMovesCalculator = new QueenMovesCalculator();
                moves = queenMovesCalculator.pieceMoves(board, myPosition, pieceColor, type);
                break;
            case BISHOP:
                BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator();
                moves = bishopMovesCalculator.pieceMoves(board, myPosition, pieceColor, type);
                break;
            case KNIGHT:
                KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator();
                moves = knightMovesCalculator.pieceMoves(board, myPosition, pieceColor, type);
                break;
            case ROOK:
                RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
                moves = rookMovesCalculator.pieceMoves(board, myPosition, pieceColor, type);
                break;
            case PAWN:
                PawnMovesCalculator pawnMovesCalculator = new PawnMovesCalculator();
                moves = pawnMovesCalculator.pieceMoves(board, myPosition, pieceColor, type);
                break;
        }
        return moves;
    }

}
