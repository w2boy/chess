package chess;

import java.util.Collection;

public class PieceMovesCalculator {

    public PieceMovesCalculator() {
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor,ChessPiece.PieceType type) {
        Collection<ChessMove> moves = null;
        switch (type) {
            case KING:
                break;
            case QUEEN:
                break;
            case BISHOP:
                break;
            case KNIGHT:
                break;
            case ROOK:
                RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
                moves = rookMovesCalculator.pieceMoves(board, myPosition, pieceColor, type);
                break;
            case PAWN:
                break;
        }
        return moves;
    }

}
