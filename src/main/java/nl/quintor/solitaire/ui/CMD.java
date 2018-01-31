package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;
import nl.quintor.solitaire.game.moves.Quit;
import nl.quintor.solitaire.Main;
import java.util.Scanner;
import java.io.IOException;
import java.lang.*;

import java.util.Collection;

public class CMD implements UI {

    private Scanner scanner = new Scanner(System.in);

    public void setMessage(String message) {

    }

    public void setErrorMessage(String message) {

    }


    public void refresh(GameState gameState) {
        clearScreen();

        System.out.println(gameState.toString());

        System.out.println("1 \t 2 \t 3 \t 4 \t 5 \t 6 \t 7");
    }

    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        gameControls();

        refresh(gameState);
        return "test";
    }

    private void gameControls(){
        String inputCommand = scanner.next().toLowerCase();

        char commandType = inputCommand.charAt(0);

        switch (commandType){
            case 'd':
                break;
            case 'q':
                GameState gameState = new GameState();
                Quit quit = new Quit();
                System.out.println(quit.apply(gameState));
                quit.apply(gameState);
                break;
            case 'm':
                System.out.println("You pressed M");
                moveFunction();
                break;

            default:
                System.out.println("Please try a valid command, press H for help.");
                gameControls();
                break;
        }
    }

    void moveFunction (){


    }

    private static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033\143");
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Screen clearing error");
        }
    }
}
