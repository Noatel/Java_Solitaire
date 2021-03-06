package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class CMD implements UI {

    private Scanner scanner = new Scanner(System.in);
    private String message = "Welcome to Solitaire \n\n" +
        "Made by: Ali Safdari, Jordie Verbakel, Tim Wapenaar en Noah Telussa \n \n" +
        "=================================================================== \n \n" +
        " How do i move a Card \n \n" +
        " If you want to move a card, first you need to enter the m to move a card + \n" +
        " After you enter the M into the commandline you need to select a card that you want to move, \n" +
        " The cmd is asking: From, meaning from column do you want to move? \n" +
        " After entering the column for example C1 you need to select the row.  \n" +
        " The command line is asking you for the row, on the left side you see all rows that are available (R1 to R13) \n" +
        " If you entered your row number you need to move the cards to a specific column, \n" +
        " The cmd is asking where do you want to move the columns, you enter here the column  \n" +
        " where you want to move the card to \n" +
        "  \n" +
        " How do i draw a card from the deck?  \n" +
        " You draw a card from the stock to the waste by pressing the D button.\n" +
        " After pressing the D key its cycle through all the cards from the stock until its empty \n \n"+
        "=================================================================== \n \n" ;

    private String errorMessage = "";

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }


    public void refresh(GameState gameState) {
        clearScreen();
        displayHeader(gameState);
        displayColumns(gameState);
        checkWon(gameState);
        displayControls();
        System.out.println(this.message);
        System.out.println(errorMessage.toUpperCase());
    }

    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        refresh(gameState);
        message = "";
        errorMessage = "";
        return scanner.nextLine();
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
            for (Deck column : gameState.getColumns().values()) {

                // if card is last card in column, display card as visible otherwise invisible
                if (i <= column.size()) {
                    Card selectedCard = column.get(i - 1);

                    if (!selectedCard.equals(column.get(column.size() - 1))) {
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

    private void displayHeader(GameState gameState) {
        System.out.println(gameState.toString() + "\n");

        System.out.print("Stock: " + gameState.getStock().size());
        System.out.println("\t \t \t S1 \t S2 \t S3 \t S4");

        Deck waste = gameState.getWaste();

        System.out.print(String.format("Waste: %s", waste.isEmpty() ? "--" : waste.get(waste.size() - 1).toShortString()));
        System.out.print("\t \t \t ");

        for (Deck stackPile : gameState.getStackPiles().values()) {
            Card cardOnTop = !stackPile.isEmpty() ? stackPile.get(stackPile.size() - 1) : null;
            System.out.print(String.format("%s \t", cardOnTop == null ? "--" : cardOnTop.toShortString()));
        }
        System.out.print("\n");
    }

    private void checkWon(GameState gameState) {
        if (gameState.getStock().isEmpty() && gameState.getWaste().isEmpty() && gameState.getColumns().isEmpty()) {
            gameState.setGameWon(true);
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
