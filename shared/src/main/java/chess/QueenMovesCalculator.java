package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator extends PieceMovesCalculator{

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

        //Rook like moves
        for (int up=row+1; up <= 8; up++){
            ChessMove move = calculateMove(board, myPosition, up, column);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, up, column)){
                break;
            }
        }

        for (int down=row-1; down >= 1; down--){
            ChessMove move = calculateMove(board, myPosition, down, column);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, down, column)){
                break;
            }
        }

        for (int right=column+1; right <= 8; right++){
            ChessMove move = calculateMove(board, myPosition, row, right);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, row, right)){
                break;
            }
        }

        for (int left=column-1; left >= 1; left--){
            ChessMove move = calculateMove(board, myPosition, row, left);
            if (move != null){
                moves.add(move);
            }
            if (pieceBlocks(board, myPosition, row, left)){
                break;
            }
        }

        return moves;
    }
}
