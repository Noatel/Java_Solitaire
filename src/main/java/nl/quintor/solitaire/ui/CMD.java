package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
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
               "After you enter the M into the commandline you need to select a card that you want to move,\n" +
               "The cmd is asking: From, meaning from column do you want to move? \n" +
               "After entering the column for example C1 you need to select the row. \n" +
               "The command line is asking you for the row, on the left side you see all rows that are available (R1 to R13) \n" +
               "If you entered your row number you need to move the cards to a specific column, \n" +
               "The cmd is asking where do you want to move the columns, you enter here the column \n" +
               "where you want to move the card to\n \n \n" +
               "" +
               "How do i draw a card from the deck? \n" +
               "You draw a card from the stock to the waste by pressing the D button.\n" +
               "After pressing the D key its cycle through all the cards from the stock until its empty \n \n";
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
