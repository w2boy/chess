package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {

    public RookMovesCalculator(){
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor,ChessPiece.PieceType type) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        for(int up = row+1; up <= 8; up++){

        }

        return moves;
    }
}
