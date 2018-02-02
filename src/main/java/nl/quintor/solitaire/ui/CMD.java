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
