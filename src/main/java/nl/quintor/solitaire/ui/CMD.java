package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.DeckMove;
import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.card.Rank;
import nl.quintor.solitaire.models.card.Suit;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.deck.DeckType;
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
    private Move deckMove = new DeckMove();

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
        System.out.println("asdf");
    }

    private void displayHeader(GameState gameState) {
        System.out.println(gameState.toString());

        System.out.print("Stock: " + gameState.getStock().size());
        System.out.println("\t \t \t S1 \t S2 \t S3 \t S4");

        Deck waste = gameState.getWaste();
        System.out.print(String.format("Waste: %s", waste.isEmpty() ? "--" : waste.get(waste.size()-1).toShortString()));
        System.out.print("\t \t \t ");

        for (Deck stackPile : gameState.getStackPiles().values()){
            Card cardOnTop = !stackPile.isEmpty() ? stackPile.get(stackPile.size()-1) : null;
            System.out.print(String.format("%s \t", cardOnTop == null ? "--" : cardOnTop.toShortString()));
        }
    }

    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        refresh(gameState);

        return scanner.nextLine();
    }

    // must be moved to according move class
    private void gameControls(GameState gameState) throws MoveException {
        String inputCommand = scanner.nextLine().toLowerCase();

        char commandType = inputCommand.charAt(0);

        switch (commandType) {
            case 'd':
                deckMove.apply(gameState);
            break;

            case 'q':
                Quit quit = new Quit();
                System.out.println(quit.apply(gameState));
                quit.apply(gameState);
            break;

            case 'm':
                moveFunction(gameState);
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

    // must be moved to move class
    void moveFunction (GameState gameState){
        this.setMessage("From:");
        String fromDeckInput = scanner.nextLine().toLowerCase();
        Deck fromDeck = getDeckByInput(gameState, fromDeckInput);

        while (fromDeck == null){
            setErrorMessage("invalid deck, try again:");
            fromDeckInput = scanner.nextLine().toLowerCase();
            fromDeck = getDeckByInput(gameState, fromDeckInput);
        }

        String rowInput = "";

        if (fromDeck.getDeckType().equals(DeckType.COLUMN)){
            setMessage("Row:");
            rowInput = scanner.nextLine().toLowerCase();

            while (rowInput.isEmpty() || rowInput.charAt(0) != 'r' || Integer.parseInt(rowInput.replace("r", "")) < 0 && Integer.parseInt(rowInput.replace("r", "")) > 13){
                setErrorMessage("invalid row number, try again");
                rowInput = scanner.nextLine().toLowerCase();
            }
        }

        setMessage("To:");

        String toDeckInput = scanner.nextLine().toLowerCase();
        Deck toDeck = getDeckByInput(gameState, toDeckInput);

        while (toDeck == null || toDeck.getDeckType().equals(DeckType.WASTE)){
            setErrorMessage("invalid target deck, try again:");
            toDeckInput = scanner.nextLine().toLowerCase();
            toDeck = getDeckByInput(gameState, toDeckInput);
        }

        if (rowInput.isEmpty()) {
            toDeck.addAll(gameState.getCardsFromDeck(fromDeck, 1));
        }
        else {
            int rowNumber = Integer.parseInt(rowInput.replace("r", "")) - 1;
            int amount = fromDeck.size() - rowNumber;
            toDeck.addAll(gameState.getCardsFromDeck(fromDeck, amount, rowNumber));
        }
    }

    private Deck getDeckByInput(GameState gameState, String input){
        if (input == null || input.isEmpty()){
            return null;
        }

        switch (input.charAt(0)){
            case 'w':
                return gameState.getWaste();
            case 's':
                return gameState.getStackPiles().get(input.replace("s",""));
            case 'c':
                return gameState.getColumns().get(input.replace("c",""));

            default:
                return null;
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
