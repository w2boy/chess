package websocket.commands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {

    CommandType type = CommandType.MAKE_MOVE;
    ChessMove chessMove;

    public MakeMove(String authToken, int gameID, ChessMove move){
        super(authToken, gameID);
        this.chessMove = move;
    }
}
