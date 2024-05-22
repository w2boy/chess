package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        //Bishop like moves
        for (int up=row+2, right=column+1; up <= 8 && right <=8; up++, right++){
            ChessMove move = calculateMove(board, myPosition, up, right);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int up=row+1, right=column+2; up <= 8 && right <=8; up++, right++){
            ChessMove move = calculateMove(board, myPosition, up, right);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int down=row-1, right=column+2; down >= 1 && right <=8; down--, right++){
            ChessMove move = calculateMove(board, myPosition, down, right);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int down=row-2, right=column+1; down >= 1 && right <=8; down--, right++){
            ChessMove move = calculateMove(board, myPosition, down, right);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int down=row-2, left=column-1; down >=1 && left >= 1; down--, left--){
            ChessMove move = calculateMove(board, myPosition, down, left);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int down=row-1, left=column-2; down >=1 && left >= 1; down--, left--){
            ChessMove move = calculateMove(board, myPosition, down, left);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int up=row+1, left=column-2; up <=8 && left >= 1; up++, left--){
            ChessMove move = calculateMove(board, myPosition, up, left);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int up=row+2, left=column-1; up <=8 && left >= 1; up++, left--){
            ChessMove move = calculateMove(board, myPosition, up, left);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        return moves;
    }
}
