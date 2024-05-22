package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        for (int up=row+1, right=column+1; up <= 8 && right <=8; up++, right++){
            ChessMove move = calculateMove(board, myPosition, up, right);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, up, right)){
                break;
            }
        }

        for (int down=row-1, right=column+1; down >= 1 && right <=8; down--, right++){
            ChessMove move = calculateMove(board, myPosition, down, right);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, down, right)){
                break;
            }
        }

        for (int down=row-1, left=column-1; down >=1 && left >= 1; down--, left--){
            ChessMove move = calculateMove(board, myPosition, down, left);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, down, left)){
                break;
            }
        }

        for (int up=row+1, left=column-1; up <=8 && left >= 1; up++, left--){
            ChessMove move = calculateMove(board, myPosition, up, left);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, up, left)){
                break;
            }
        }

        return moves;
    }
}
