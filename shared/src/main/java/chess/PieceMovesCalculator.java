package chess;public class PieceMovesCalculator {

    public boolean pieceBlocks(ChessBoard board, ChessPosition myPosition, int newRow, int newCol){
        if (!(newRow <=8 && newRow >=1 && newCol <=8 && newCol >=1)){
            return false;
        }
        ChessPosition otherPosition = new ChessPosition(newRow, newCol);
        ChessPiece otherPiece = board.getPiece(otherPosition);
        if (otherPiece != null){
            return true;
        } else {
            return false;
        }
    }

    public ChessMove calculateMove(ChessBoard board, ChessPosition myPosition, int newRow, int newCol){
        if (!(newRow <=8 && newRow >=1 && newCol <=8 && newCol >=1)){
            return null;
        }
        ChessPosition otherPosition = new ChessPosition(newRow, newCol);
        ChessPiece otherPiece = board.getPiece(otherPosition);
        if (otherPiece != null){
            ChessGame.TeamColor otherColor = otherPiece.getTeamColor();
            ChessPiece myPiece = board.getPiece(myPosition);
            ChessGame.TeamColor myColor = myPiece.getTeamColor();
            if (myColor != otherColor){
                ChessMove move = new ChessMove(myPosition, otherPosition, null);
                return move;
            } else {
                return null;
            }
        } else {
            ChessMove move = new ChessMove(myPosition, otherPosition, null);
            return move;
        }
    }

    public ChessMove calculateMovePawn(ChessBoard board, ChessPosition myPosition, int newRow, int newCol){
        if (!(newRow <=8 && newRow >=1 && newCol <=8 && newCol >=1)){
            return null;
        }
        ChessPosition otherPosition = new ChessPosition(newRow, newCol);
        ChessPiece otherPiece = board.getPiece(otherPosition);
        if (otherPiece != null){
            return null;
        } else {
            if (newRow == 8 || newRow == 1){
                ChessMove move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.QUEEN);
                return move;
            }
            ChessMove move = new ChessMove(myPosition, otherPosition, null);
            return move;
        }
    }

    public ChessMove calculateMoveCapture(ChessBoard board, ChessPosition myPosition, int newRow, int newCol){
        if (!(newRow <=8 && newRow >=1 && newCol <=8 && newCol >=1)){
            return null;
        }
        ChessPosition otherPosition = new ChessPosition(newRow, newCol);
        ChessPiece otherPiece = board.getPiece(otherPosition);
        if (otherPiece != null){
            ChessGame.TeamColor otherColor = otherPiece.getTeamColor();
            ChessPiece myPiece = board.getPiece(myPosition);
            ChessGame.TeamColor myColor = myPiece.getTeamColor();
            if (myColor != otherColor){
                if (newRow == 8 || newRow == 1){
                    ChessMove move = new ChessMove(myPosition, otherPosition, ChessPiece.PieceType.QUEEN);
                    return move;
                }
                ChessMove move = new ChessMove(myPosition, otherPosition, null);
                return move;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
