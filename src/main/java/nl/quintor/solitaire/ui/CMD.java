package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;
import nl.quintor.solitaire.game.moves.Quit;
import nl.quintor.solitaire.Main;

import java.util.ArrayList;
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

        //The first card in the waste is empty
        String waste = "empty";

        //Run the default values like waste, collections
        this.displayDefault(waste,gameState);

        //zet het aantal keer dat de loop voor de vraag blijft lopen op 1
        int totalTries = 1;
        //Count how many cards there is on the stock
        int numberDeck = 0;

        //maak een nieuwe scanner voor het checken van de input
        Scanner scanner = new Scanner(System.in);
        //zet de standaard text neer


        //de for loop blijft net zo lang loopen totdat er een valid answer is gegeven
        for (int i = 0; i < totalTries; i++) {
            char textInput = scanner.next().toLowerCase().charAt(0);
            // To put a card to the waste and change the Deck
            if (textInput == 'd') {

                //When you want a card in the deck run the function
                //Displaywaste() With the numberDeck (What card he is at in the stock)
                waste = displayWaste(numberDeck,gameState);

                i = 0;
                numberDeck++;
                totalTries++;

                if(waste.equals("empty")){
                    numberDeck = 0;
                }

                //After the function, clear screen
                clearScreen();

                //Run the default settings with the waste updated
                displayDefault(waste,gameState);
            //To quit the game
            } else if (textInput == 'q') {
                Quit quit = new Quit();
                System.out.println(quit.apply(gameState));
                quit.apply(gameState);

            //To show the controls
            } else if (textInput == 'h') {
                displayControls();

                totalTries++;
            } else {
                //voeg een extra try toe om nog een keer door de code heen te loopen
                System.out.println("Please try a valid character");
                textInput = scanner.next().charAt(0);

                totalTries++;
            }
        }

    }
    private void displayControls(){
        String controls = "D = Deck | H = Help | Q = Quit";

        System.out.println(controls);
    }
    private void displayDefault(String waste,GameState gameState){
        // display time, move, score
        System.out.println(gameState.toString());

        // display waste
        System.out.print("Waste = ");
        System.out.println(waste);

        // display columns
        displayColumns(gameState);

        // display controls
        displayControls();
    };
    private String displayWaste(int numberDeck,GameState gameState){

        System.out.println(gameState.getStock());


        if(gameState.getStock().size() == numberDeck){
            // Empty the waste
            return "empty";
        }

        System.out.println(numberDeck);
        System.out.println(gameState.getStock().size());
        System.out.println((gameState.getStock().size() - 1));
        System.out.println(gameState.getStock());
        System.out.println(gameState.getStock().get(numberDeck).toShortString());
        System.out.println(gameState.getStock().get(numberDeck).toString());

       return gameState.getStock().get(numberDeck).toShortString();
    }

    private void displayColumns(GameState gameState) {
        System.out.println();
        System.out.println("\tC1 \tC2 \tC3 \tC4 \tC5 \tC6 \tC7");

        // iterate through all rows
        int maxRows = 13;

        for (int i = 1; i <= maxRows; i++){
            // display rows
            System.out.print("R"+i);

            // iterate through columns
            for (int y = 1; y <= gameState.getColumns().size(); y++){
                Deck selectedColumn = gameState.getColumns().get(Integer.toString(y));
                Card selectedCard = null;

                // if card exists in column, get it
                if (i <= selectedColumn.size()){
                    selectedCard = selectedColumn.get(i -1);
                }

                // if card is last card in column, display card as visible otherwise invisible
                if (selectedCard != null){
                    if (!selectedCard.equals(selectedColumn.get(selectedColumn.size() -1))){
                        System.out.print(" \t??");
                    } else  {
                        System.out.print(String.format("\t%s", selectedCard.toShortString()));
                    }
                }
                else {
                    // card was not found in oolumn, display just a tab
                    System.out.print("\t");
                }
            }

            System.out.println();
        }
        System.out.println();
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
