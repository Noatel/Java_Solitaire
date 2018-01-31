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

    public void setMessage(String message) {

    }

    public void setErrorMessage(String message) {

    }

    public void refresh(GameState gameState) {
        clearScreen();

        System.out.println(gameState.toString());

        System.out.println("1 \t 2 \t 3 \t 4 \t 5 \t 6 \t 7");




        //zet het aantal keer dat de loop voor de vraag blijft lopen op 1
        int totalTries = 1;
        //maak een nieuwe scanner voor het checken van de input
        Scanner scanner = new Scanner(System.in);
        //zet de standaard text neer
        String controls = "D = Deck | H = Help | Q = Quit";
        System.out.println(controls);

        //de for loop blijft net zo lang loopen totdat er een valid answer is gegeven
        for (int i = 0; i < totalTries; i++) {
            char textInput = scanner.next().toLowerCase().charAt(0);

            // To show the deck?
            if (textInput == 'd') {
                System.out.println("go fuck yourself");
                i = 0;
                textInput = 'N';
                Main main = new Main();

            //To quit the game
            } else if (textInput == 'q') {
                Quit quit = new Quit();
                System.out.println(quit.apply(gameState));
                quit.apply(gameState);

            //To show the controls
            } else if (textInput == 'h') {
                System.out.println(controls);
            } else {
                //voeg een extra try toe om nog een keer door de code heen te loopen
                totalTries++;
                System.out.println("Please try a valid character");
                textInput = scanner.next().charAt(0);
            }
        }

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

    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        return "test";
    }
}
