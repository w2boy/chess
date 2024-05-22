package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        ChessGame.TeamColor myColor = myPiece.getTeamColor();

        if(myColor == ChessGame.TeamColor.WHITE){
            //First Check if it can do two space move
            if(row==2){
                ChessMove move = calculateMovePawn(board, myPosition, row+1, column);
                if (move != null){
                    move = calculateMovePawn(board, myPosition, row+2, column);
                    if (move != null){
                        moves.add(move);
                    }
                }
            }
            //Normal Pawn Moves
            ChessMove move = calculateMovePawn(board, myPosition, row+1, column);
            if (move != null){
                moves.add(move);
                if (row+1 ==8){
                    ChessPosition otherPosition = new ChessPosition(row+1, column);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.ROOK);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.BISHOP);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.KNIGHT);
                    moves.add(move);
                }
            }
            move = calculateMoveCapture(board, myPosition, row+1, column+1);
            if (move != null){
                moves.add(move);
                if (row+1 ==8){
                    ChessPosition otherPosition = new ChessPosition(row+1, column+1);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.ROOK);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.BISHOP);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.KNIGHT);
                    moves.add(move);
                }
            }
            move = calculateMoveCapture(board, myPosition, row+1, column-1);
            if (move != null){
                moves.add(move);
                if (row+1==8){
                    ChessPosition otherPosition = new ChessPosition(row+1, column-1);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.ROOK);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.BISHOP);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.KNIGHT);
                    moves.add(move);
                }
            }
        }

        if(myColor == ChessGame.TeamColor.BLACK){
            //First Check if it can do two space move
            if(row==7){
                ChessMove move = calculateMovePawn(board, myPosition, row-1, column);
                if (move != null){
                    move = calculateMovePawn(board, myPosition, row-2, column);
                    if (move != null){
                        moves.add(move);
                    }
                }
            }
            //Normal Pawn Moves
            ChessMove move = calculateMovePawn(board, myPosition, row-1, column);
            if (move != null){
                moves.add(move);
                if (row-1==1){
                    ChessPosition otherPosition = new ChessPosition(row-1, column);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.ROOK);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.BISHOP);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.KNIGHT);
                    moves.add(move);
                }
            }
            move = calculateMoveCapture(board, myPosition, row-1, column+1);
            if (move != null){
                moves.add(move);
                if (row-1==1){
                    ChessPosition otherPosition = new ChessPosition(row-1, column+1);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.ROOK);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.BISHOP);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.KNIGHT);
                    moves.add(move);
                }
            }
            move = calculateMoveCapture(board, myPosition, row-1, column-1);
            if (move != null){
                moves.add(move);
                if (row-1==1){
                    ChessPosition otherPosition = new ChessPosition(row-1, column-1);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.ROOK);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.BISHOP);
                    moves.add(move);
                    move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.KNIGHT);
                    moves.add(move);
                }
            }
        }
        return moves;
    }
}
