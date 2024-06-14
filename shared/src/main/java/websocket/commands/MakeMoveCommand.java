package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    ChessMove chessMove;

    public MakeMoveCommand(String authToken, int gameID, ChessMove move){
        super(authToken, gameID);
        this.chessMove = move;
        commandType = CommandType.MAKE_MOVE;
    }

    public ChessMove getChessMove(){
        return chessMove;
    }
}
