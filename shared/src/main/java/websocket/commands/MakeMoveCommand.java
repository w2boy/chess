package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    CommandType type = CommandType.MAKE_MOVE;
    ChessMove chessMove;

    public MakeMoveCommand(String authToken, int gameID, ChessMove move){
        super(authToken, gameID);
        this.chessMove = move;
    }
}
