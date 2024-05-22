package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        //Bishop like moves
        for (int up=row+1, right=column+1; up <= 8 && right <=8; up++, right++){
            ChessMove move = calculateMove(board, myPosition, up, right);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int down=row-1, right=column+1; down >= 1 && right <=8; down--, right++){
            ChessMove move = calculateMove(board, myPosition, down, right);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int down=row-1, left=column-1; down >=1 && left >= 1; down--, left--){
            ChessMove move = calculateMove(board, myPosition, down, left);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int up=row+1, left=column-1; up <=8 && left >= 1; up++, left--){
            ChessMove move = calculateMove(board, myPosition, up, left);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        //Rook like moves
        for (int up=row+1; up <= 8; up++){
            ChessMove move = calculateMove(board, myPosition, up, column);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int down=row-1; down >= 1; down--){
            ChessMove move = calculateMove(board, myPosition, down, column);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int right=column+1; right <= 8; right++){
            ChessMove move = calculateMove(board, myPosition, row, right);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        for (int left=column-1; left >= 1; left--){
            ChessMove move = calculateMove(board, myPosition, row, left);
            if (move != null){
                moves.add(move);
            }
            break;
        }

        return moves;
    }
}
