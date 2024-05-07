package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {

    public PawnMovesCalculator(){}

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor,ChessPiece.PieceType type) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        if(pieceColor == ChessGame.TeamColor.WHITE){
            if(row != 2){
            //Move One Space:
            //First check for if there are pieces diagonally from it.
            if(column+1<=8) {
                ChessPosition otherPosition1 = new ChessPosition(row + 1, column + 1);
                ChessPiece other_piece1 = board.getPiece(otherPosition1);
                if (other_piece1 != null){
                    ChessGame.TeamColor other_piece_color = other_piece1.pieceColor;
                    if (other_piece_color != pieceColor){
                        ChessPosition endPosition = new ChessPosition(row+1, column+1);
                        if(row+1==8){
                            ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                            moves.add(chessMove);
                            chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                            moves.add(chessMove);
                            chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                            moves.add(chessMove);
                            chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                            moves.add(chessMove);
                        }
                        else {
                            ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                            moves.add(chessMove);
                        }
                    }
                }
            }
            if (column-1>=1) {
                ChessPosition otherPosition2 = new ChessPosition(row + 1, column - 1);
                ChessPiece other_piece2 = board.getPiece(otherPosition2);
                if (other_piece2 != null) {
                    ChessGame.TeamColor other_piece_color = other_piece2.pieceColor;
                    if (other_piece_color != pieceColor) {
                        ChessPosition endPosition = new ChessPosition(row + 1, column - 1);
                        if (row + 1 == 8) {
                            ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                            moves.add(chessMove);
                            chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                            moves.add(chessMove);
                            chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                            moves.add(chessMove);
                            chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                            moves.add(chessMove);
                        } else {
                            ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                            moves.add(chessMove);
                        }
                    }
                }
            }
            //Next Check for in front of it
            ChessPosition in_front = new ChessPosition(row+1, column);
            ChessPiece front_piece = board.getPiece(in_front);
            if(front_piece == null) {
                ChessPosition endPosition = new ChessPosition(row+1, column);
                if(row+1==8){
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                    moves.add(chessMove);
                    chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                    moves.add(chessMove);
                    chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                    moves.add(chessMove);
                    chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                    moves.add(chessMove);
                }
                else {
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                }
            }
            }
            else {
                //Move One Space:
                //First check for if there are pieces diagonally from it.
                if(column+1<=8) {
                    ChessPosition otherPosition1 = new ChessPosition(row + 1, column + 1);
                    ChessPiece other_piece1 = board.getPiece(otherPosition1);
                    if (other_piece1 != null){
                        ChessGame.TeamColor other_piece_color = other_piece1.pieceColor;
                        if (other_piece_color != pieceColor){
                            ChessPosition endPosition = new ChessPosition(row+1, column+1);
                            if(row+1==8){
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(chessMove);
                            }
                            else {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                                moves.add(chessMove);
                            }
                        }
                    }
                }
                if (column-1>=1) {
                    ChessPosition otherPosition2 = new ChessPosition(row + 1, column - 1);
                    ChessPiece other_piece2 = board.getPiece(otherPosition2);
                    if (other_piece2 != null) {
                        ChessGame.TeamColor other_piece_color = other_piece2.pieceColor;
                        if (other_piece_color != pieceColor) {
                            ChessPosition endPosition = new ChessPosition(row + 1, column - 1);
                            if (row + 1 == 8) {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(chessMove);
                            } else {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                                moves.add(chessMove);
                            }
                        }
                    }
                }
                //Next Check for in front of it
                ChessPosition in_front = new ChessPosition(row+1, column);
                ChessPiece front_piece = board.getPiece(in_front);
                if(front_piece == null) {
                    ChessPosition endPosition = new ChessPosition(row+1, column);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                }
                //Or Move Two Spaces:
                //Check for two spaces in front of it
                ChessPosition in_front2 = new ChessPosition(row+2, column);
                ChessPiece front_piece2 = board.getPiece(in_front2);
                if(front_piece2 == null && front_piece == null) {
                    ChessPosition endPosition = new ChessPosition(row+2, column);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                }
            }
        }

        if(pieceColor == ChessGame.TeamColor.BLACK){
            if(row != 7){
                //Move One Space:
                //First check for if there are pieces diagonally from it.
                if(column+1 <=8) {
                    ChessPosition otherPosition1 = new ChessPosition(row - 1, column + 1);
                    ChessPiece other_piece1 = board.getPiece(otherPosition1);
                    if (other_piece1 != null){
                        ChessGame.TeamColor other_piece_color = other_piece1.pieceColor;
                        if (other_piece_color != pieceColor){
                            ChessPosition endPosition = new ChessPosition(row-1, column+1);
                            if(row-1==1){
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(chessMove);
                            }
                            else {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                                moves.add(chessMove);
                            }
                        }
                    }
                }
                if (column-1 >=1) {
                    ChessPosition otherPosition2 = new ChessPosition(row - 1, column - 1);
                    ChessPiece other_piece2 = board.getPiece(otherPosition2);
                    if (other_piece2 != null) {
                        ChessGame.TeamColor other_piece_color = other_piece2.pieceColor;
                        if (other_piece_color != pieceColor) {
                            ChessPosition endPosition = new ChessPosition(row - 1, column - 1);
                            if (row - 1 == 1) {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(chessMove);
                            } else {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                                moves.add(chessMove);
                            }
                        }
                    }
                }
                //Next Check for in front of it
                ChessPosition in_front = new ChessPosition(row-1, column);
                ChessPiece front_piece = board.getPiece(in_front);
                if(front_piece == null) {
                    ChessPosition endPosition = new ChessPosition(row-1, column);
                    if(row-1==1){
                        ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                        moves.add(chessMove);
                        chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                        moves.add(chessMove);
                        chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                        moves.add(chessMove);
                        chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                        moves.add(chessMove);
                    }
                    else {
                        ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                        moves.add(chessMove);
                    }
                }
            }
            else {
                //Move One Space:
                //First check for if there are pieces diagonally from it.
                if(column+1 <=8) {
                    ChessPosition otherPosition1 = new ChessPosition(row - 1, column + 1);
                    ChessPiece other_piece1 = board.getPiece(otherPosition1);
                    if (other_piece1 != null){
                        ChessGame.TeamColor other_piece_color = other_piece1.pieceColor;
                        if (other_piece_color != pieceColor){
                            ChessPosition endPosition = new ChessPosition(row-1, column+1);
                            if(row-1==1){
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(chessMove);
                            }
                            else {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                                moves.add(chessMove);
                            }
                        }
                    }
                }
                if (column-1 >=1) {
                    ChessPosition otherPosition2 = new ChessPosition(row - 1, column - 1);
                    ChessPiece other_piece2 = board.getPiece(otherPosition2);
                    if (other_piece2 != null) {
                        ChessGame.TeamColor other_piece_color = other_piece2.pieceColor;
                        if (other_piece_color != pieceColor) {
                            ChessPosition endPosition = new ChessPosition(row - 1, column - 1);
                            if (row - 1 == 1) {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(chessMove);
                                chessMove = new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(chessMove);
                            } else {
                                ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                                moves.add(chessMove);
                            }
                        }
                    }
                }
                //Next Check for in front of it
                ChessPosition in_front = new ChessPosition(row-1, column);
                ChessPiece front_piece = board.getPiece(in_front);
                if(front_piece == null) {
                    ChessPosition endPosition = new ChessPosition(row-1, column);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                }
                //Or Move Two Spaces:
                //Check for two spaces in front of it
                ChessPosition in_front2 = new ChessPosition(row-2, column);
                ChessPiece front_piece2 = board.getPiece(in_front2);
                if(front_piece2 == null && front_piece == null) {
                    ChessPosition endPosition = new ChessPosition(row-2, column);
                    ChessMove chessMove = new ChessMove(myPosition, endPosition, null);
                    moves.add(chessMove);
                }
            }
        }
        return moves;
    }
}

