package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import server.Server;

import java.util.Scanner;

import static java.awt.Color.*;

public class Repl {
    private final ChessClient client;
    private final DrawBoard drawBoard = new DrawBoard();
    private ChessBoard chessBoard = new ChessBoard();


    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl, this);
    }

    public void run() {
        chessBoard.resetBoard();
        drawBoard.run(chessBoard.squares);
        System.out.println("Welcome to 240 Chess! (Legally Distinct Chess) Type an option.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {

                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();

    }

    private void printPrompt() {
        if (client.state == State.LOGGED_OUT){
            System.out.print("\n" + "Logged Out" + " >>> ");
        } else {
            System.out.print("\n" + "Logged In" + " >>> ");
        }

    }

}
