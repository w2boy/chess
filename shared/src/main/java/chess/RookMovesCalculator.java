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
            //Checks it there is a piece
            ChessPosition otherPosition = new ChessPosition(up, column);
            ChessPiece other_piece = board.getPiece(otherPosition);
            if (other_piece != null){
                ChessGame.TeamColor other_piece_color = other_piece.pieceColor;
                //If not the same team's color, it can move to that space but needs exit loop after.
                // Otherwise, the piece blocks the path. (exit loop without adding move)
                if (other_piece_color != pieceColor){
                    ChessPosition endPosition = new ChessPosition(up, column);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                    break;
                }
                else {
                    break;
                }
            }
            //If there is no piece add the position
            else {
                ChessPosition endPosition = new ChessPosition(up, column);
                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                moves.add(chessMove);
            }
        }

        for(int down = row-1; down >= 1; down--){
            //Checks it there is a piece
            ChessPosition otherPosition = new ChessPosition(down, column);
            ChessPiece other_piece = board.getPiece(otherPosition);
            if (other_piece != null){
                ChessGame.TeamColor other_piece_color = other_piece.pieceColor;
                //If not the same team's color, it can move to that space but needs exit loop after.
                // Otherwise, the piece blocks the path. (exit loop without adding move)
                if (other_piece_color != pieceColor){
                    ChessPosition endPosition = new ChessPosition(down, column);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                    break;
                }
                else {
                    break;
                }
            }
            //If there is no piece add the position
            else {
                ChessPosition endPosition = new ChessPosition(down, column);
                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                moves.add(chessMove);
            }
        }

        for(int right = column+1; right <= 8; right++){
            //Checks it there is a piece
            ChessPosition otherPosition = new ChessPosition(row, right);
            ChessPiece other_piece = board.getPiece(otherPosition);
            if (other_piece != null){
                ChessGame.TeamColor other_piece_color = other_piece.pieceColor;
                //If not the same team's color, it can move to that space but needs exit loop after.
                // Otherwise, the piece blocks the path. (exit loop without adding move)
                if (other_piece_color != pieceColor){
                    ChessPosition endPosition = new ChessPosition(row, right);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                    break;
                }
                else {
                    break;
                }
            }
            //If there is no piece add the position
            else {
                ChessPosition endPosition = new ChessPosition(row, right);
                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                moves.add(chessMove);
            }
        }

        for(int left = column-1; left >= 1; left--){
            //Checks it there is a piece
            ChessPosition otherPosition = new ChessPosition(row, left);
            ChessPiece other_piece = board.getPiece(otherPosition);
            if (other_piece != null){
                ChessGame.TeamColor other_piece_color = other_piece.pieceColor;
                //If not the same team's color, it can move to that space but needs exit loop after.
                // Otherwise, the piece blocks the path. (exit loop without adding move)
                if (other_piece_color != pieceColor){
                    ChessPosition endPosition = new ChessPosition(row, left);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                    break;
                }
                else {
                    break;
                }
            }
            //If there is no piece add the position
            else {
                ChessPosition endPosition = new ChessPosition(row, left);
                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                moves.add(chessMove);
            }
        }

        return moves;
    }
}
