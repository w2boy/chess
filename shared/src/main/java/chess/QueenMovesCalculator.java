package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator extends PieceMovesCalculator{

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        //Bishop like moves
        BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator();
        moves.addAll(bishopMovesCalculator.pieceMoves(board, myPosition));

        //Rook like moves
        RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
        moves.addAll(rookMovesCalculator.pieceMoves(board, myPosition));

        return moves;
    }
}
