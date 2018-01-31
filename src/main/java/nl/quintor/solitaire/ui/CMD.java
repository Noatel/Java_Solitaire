package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;
import nl.quintor.solitaire.game.moves.Quit;
import nl.quintor.solitaire.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.lang.*;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMD implements UI {

    private Scanner scanner = new Scanner(System.in);
    private String info = "";

    public void setMessage(String message) {
        System.out.println(message);
    }

    public void setErrorMessage(String message) {
        System.out.println(message.toUpperCase());
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

    private void displayColumns(GameState gameState) {
        System.out.println();
        System.out.println("\tC1 \tC2 \tC3 \tC4 \tC5 \tC6 \tC7");

        // iterate through all rows
        int maxRows = 13;

        for (int i = 1; i <= maxRows; i++) {
            // display rows
            System.out.print("R" + i);

            // iterate through columns
            for (Deck column : gameState.getColumns().values()){

                // if card is last card in column, display card as visible otherwise invisible
                if (i <= column.size()){
                    Card selectedCard = column.get(i - 1);

                    if (!selectedCard.equals(column.get(column.size() - 1))) {
                        System.out.print(" \t??");
                    } else {
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

    private void displayHelp() {
        info = "\nHow do i move a Card \n" +
               "If you want to move a card, first you need to enter the m to move a card\n" +
               "After you enter the M into the commandline you need to select a card that you want to move \n" +
               "";

    }

    private void displayHeader(GameState gameState) {
        System.out.println(gameState.toString());

        System.out.print("Stock: " + gameState.getStock().size());
        System.out.println("\t \t \t S1 \t S2 \t S3 \t S4");

        Deck waste = gameState.getWaste();
        System.out.print(String.format("Waste: %s", waste.isEmpty() ? "--" : waste.get(waste.size()-1).toShortString()));
        System.out.print("\t \t \t ");

        for (Deck stackPile : gameState.getStackPiles().values()){
            Card cardOnTop = !stackPile.isEmpty() ? stackPile.get(0) : null;
            System.out.print(String.format("%s \t", cardOnTop == null ? "--" : cardOnTop.toShortString()));
        }

    }

    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        refresh(gameState);
        gameControls(gameState);

        return "test";
    }
    private void gameControls(GameState gameState) {
        String inputCommand = scanner.nextLine().toLowerCase();

        char commandType = inputCommand.charAt(0);

        switch (commandType) {
            case 'd':
                // if stock is empty, put waste back in stock
                if (gameState.getStock().isEmpty()){
                    gameState.getStock().addAll(gameState.getCardsFromDeck(gameState.getWaste(), gameState.getWaste().size()));
                }
                else {
                    // add card from stock to waste
                    gameState.getWaste().addAll(gameState.getCardsFromDeck(gameState.getStock(), 1));
                }
            break;

            case 'q':
                Quit quit = new Quit();
                System.out.println(quit.apply(gameState));
                quit.apply(gameState);
            break;

            case 'm':
                moveFunction(gameState, inputCommand);
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
    void moveFunction (GameState gameState, String input){
        this.setMessage("From:");
        String from = scanner.nextLine().toLowerCase();

        switch (from.charAt(0)){
            case 'w':

        }
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
