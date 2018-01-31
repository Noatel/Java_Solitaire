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

    private Scanner scanner = new Scanner(System.in);
    private String waste = "--";
    private int numberDeck = 0;
    private String info = "";

    public void setMessage(String message) {

    }

    public void setErrorMessage(String message) {

    }


    public void refresh(GameState gameState) {
        clearScreen();
        displayHeader(gameState);
        displayColumns(gameState);
        displayControls();
        System.out.println(info);
    }

    private void displayControls() {
        String controls = "M = Move | D = Draw | H = Help | Q = Quit";

        System.out.println(controls);
    }

    private String displayWaste(int numberDeck, GameState gameState) {

        if (gameState.getStock().size() == numberDeck) {
            // Empty the waste
            return "empty";
        }

        return gameState.getStock().get(numberDeck).toShortString();
    }

    private void displayColumns(GameState gameState) {
        System.out.println();
        System.out.println("\tC1 \tC2 \tC3 \tC4 \tC5 \tC6 \tC7");

        // iterate through all rows
        int maxRows = 13;

        for (int i = 1; i <= maxRows; i++) {
            // display rows
            System.out.print("R" + i);

            // iterate through columns
            for (int y = 1; y <= gameState.getColumns().size(); y++) {
                Deck selectedColumn = gameState.getColumns().get(Integer.toString(y));
                Card selectedCard = null;

                // if card exists in column, get it
                if (i <= selectedColumn.size()) {
                    selectedCard = selectedColumn.get(i - 1);
                }

                // if card is last card in column, display card as visible otherwise invisible
                if (selectedCard != null) {
                    if (!selectedCard.equals(selectedColumn.get(selectedColumn.size() - 1))) {
                        System.out.print(" \t??");
                    } else {
                        System.out.print(String.format("\t%s", selectedCard.toShortString()));
                    }
                } else {
                    // card was not found in oolumn, display just a tab
                    System.out.print("\t");
                }
            }

            System.out.println();
        }
        System.out.println();
    }

    private void displayHelp() {
        info = "\nHow do i move a Card \n" +
               "If you want to move a card, first you need to enter the m to move a card\n" +
               "After you enter the M into the commandline you need to select a card that you want to move \n" +
               "";

    }

    private void displayHeader(GameState gameState) {
        int stockSize = gameState.getStock().size();

        //If the numberdeck is lower than the deck
        if (numberDeck < 25) {
            stockSize = gameState.getStock().size() - numberDeck;
        } else {
            //reset the deck
            numberDeck = 0;
        }


        System.out.println(gameState.toString());

        System.out.print("Stock: " + stockSize);
        System.out.println("\t \t \t SA \t SB \t SC \t SD");
        System.out.print("Waste: " + waste);
        System.out.println("\t \t \t -- \t -- \t -- \t --\n");

    }

    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        refresh(gameState);
        gameControls(gameState);

        return "test";
    }

    private void gameControls(GameState gameState) {
        String inputCommand = scanner.next().toLowerCase();
        info = "";

        char commandType = inputCommand.charAt(0);

        switch (commandType) {
            case 'd':
                //Displaywaste() With the numberDeck (What card he is at in the stock)
                waste = displayWaste(numberDeck, gameState);
                numberDeck++;

                if (waste.equals("--")) {
                    numberDeck = 0;
                }
                break;
            case 'q':
                Quit quit = new Quit();
                System.out.println(quit.apply(gameState));
                quit.apply(gameState);
                break;
            case 'm':
                moveFunction();
                break;
            case 'h':
                displayHelp();
                break;

            default:
                System.out.println("Please try a valid command, press H for help.");
                gameControls(gameState);
                break;
        }
    }

    void moveFunction() {

    }


    private static void clearScreen() {
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
