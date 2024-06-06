package client;

//import client.websocket.NotificationHandler;
//import webSocketMessages.Notification;

import java.util.Scanner;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.RESET;
import static java.awt.Color.*;
import static ui.EscapeSequences.*;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl, this);
    }

    public void run() {
        System.out.println("Welcome to 240 Chess! (Legally Distinct Chess) Type help to get started.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        if (client.state == State.LOGGED_OUT){
            System.out.print("\n" + "Logged Out" + " >>> " + GREEN);
        } else {
            System.out.print("\n" + "Logged In" + " >>> " + GREEN);
        }

    }

}
