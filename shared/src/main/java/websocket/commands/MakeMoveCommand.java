package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    ChessMove move;

    public MakeMoveCommand(String authToken, int gameID, ChessMove chessMove){
        super(authToken, gameID);
        this.move = chessMove;
        commandType = CommandType.MAKE_MOVE;
    }

    public ChessMove getChessMove(){
        return move;
    }
}
