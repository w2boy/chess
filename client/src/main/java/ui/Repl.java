package ui;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Repl {
    private final ChessClient client;


    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl, this);
    }

    public void run() {
        System.out.println("Welcome to 240 Chess! (Legally Distinct Chess) Type an option.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        try {
            var result = "";
            while (!result.equals("quit")) {
                sleep(400);
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
        } catch (Exception e) {

        }


    }

    private void printPrompt() {
        if (client.state == State.LOGGED_OUT){
            System.out.print("\n" + "Logged Out" + " >>> ");
        } else if (client.state == State.LOGGED_IN){
            System.out.print("\n" + "Logged In" + " >>> ");
        } else if (client.state == State.PLAYING_GAME){
            System.out.print("\n" + "Playing Game" + " >>> ");
        } else if (client.state == State.OBSERVING_GAME){
            System.out.print("\n" + "Observing Game" + " >>> ");
        } else {
            System.out.print("\n" + "Game Ended (No more moves can be made.)" + " >>> ");
        }

    }

}
