package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board = new ChessBoard();
    private TeamColor currentTurnColor;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTurnColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTurnColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    //My own helper function to just see if a piece can attack the king
    public Collection<ChessPosition> spacesInDanger(ChessPosition startPosition) {
        Collection<ChessPosition> endPositions = new ArrayList<>();
        ChessPiece myPiece = this.board.getPiece(startPosition);

        Collection<ChessMove> moves = myPiece.pieceMoves(this.board,startPosition);
        for(ChessMove move : moves) {
            ChessPosition endPosition = move.getEndPosition();
            endPositions.add(endPosition);
        }

        return endPositions;
    }


    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();
        ChessPiece myPiece = this.board.getPiece(startPosition);
        TeamColor myColor = myPiece.getTeamColor();
        if (myPiece == null) {
            return null;
        }
        if (currentTurnColor != myColor) {
            return moves;
        }
        moves = myPiece.pieceMoves(this.board, startPosition);

        //The cloned board will remove the piece where it originally is and try to
        //move the piece to its end position then check if their king is in check.
        for(ChessMove move : moves){
            ChessGame clonedGame = new ChessGame();
            clonedGame.board = this.board.copiedBoard();
            clonedGame.currentTurnColor = myColor;
            clonedGame.board.addPiece(move.startPosition, null);
            clonedGame.board.addPiece(move.endPosition, myPiece);
            boolean inCheck = clonedGame.isInCheck(myColor);
            if (inCheck){
                moves.remove(move);
            }
        }
        return moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //Find Team's King piece and add spaces the other team can attack to a collection.
        Collection<ChessPosition> spacesInDanger = new ArrayList<>();
        ChessPosition kingPosition = null;
        for (int i=1; i<=8; i++) {
            for (int j=1; j<=8; j++) {
                ChessPiece piece = this.board.getPiece(new ChessPosition(i,j));
                if (piece != null){
                    ChessPiece.PieceType pieceType = piece.getPieceType();
                    if ((pieceType == ChessPiece.PieceType.KING) && (piece.pieceColor == teamColor)){
                        //Found current team's king location
                        kingPosition = new ChessPosition(i,j);
                    }
                    if (piece.getTeamColor() != teamColor) {
                        spacesInDanger.addAll(spacesInDanger(new ChessPosition(i,j)));
                    }
                }
            }
        }
        //Check if the kingPosition is in any spaces that the other team could attack.
        if (spacesInDanger.contains(kingPosition)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean inCheck = isInCheck(teamColor);
        Collection<ChessMove> moves = new ArrayList<ChessMove>();
        if (inCheck){
            for (int i=1; i<=8; i++) {
                for (int j=1; j<=8; j++) {
                    ChessPiece piece = this.board.getPiece(new ChessPosition(i,j));
                    if (piece != null && (piece.pieceColor == teamColor)){
                        ChessPosition piecePosition = new ChessPosition(i,j);
                        moves = validMoves(piecePosition);
                    }
                }
            }
        }
        if (moves.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean inCheck = isInCheck(teamColor);
        Collection<ChessMove> moves = new ArrayList<ChessMove>();
        if (!inCheck){
            for (int i=1; i<=8; i++) {
                for (int j=1; j<=8; j++) {
                    ChessPiece piece = this.board.getPiece(new ChessPosition(i,j));
                    if (piece != null && (piece.pieceColor == teamColor)){
                        ChessPosition piecePosition = new ChessPosition(i,j);
                        moves = validMoves(piecePosition);
                    }
                }
            }
        }
        if (moves.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
