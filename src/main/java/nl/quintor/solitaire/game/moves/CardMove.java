package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.deck.DeckType;
import nl.quintor.solitaire.models.state.GameState;

import java.util.Scanner;

public class CardMove implements Move {
    @Override
    public String apply(GameState gameState) throws MoveException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("From:");
        String fromDeckInput = scanner.nextLine().toLowerCase();
        Deck fromDeck = getDeckByInput(gameState, fromDeckInput);

        if (fromDeck == null){
            throw new MoveException("invalid deck, try again:");
        }

        /*while (fromDeck == null){
            System.out.println("invalid deck, try again:");
            fromDeckInput = scanner.nextLine().toLowerCase();
            fromDeck = getDeckByInput(gameState, fromDeckInput);
        }*/

        String rowInput = "";

        if (fromDeck.getDeckType().equals(DeckType.COLUMN)){
            System.out.println("Row:");
            rowInput = scanner.nextLine().toLowerCase();

            if (rowInput.isEmpty() || rowInput.charAt(0) != 'r' || Integer.parseInt(rowInput.replace("r", "")) < 0 && Integer.parseInt(rowInput.replace("r", "")) > 13){
                throw new MoveException("invalid row number, try again");
            }

            /*while (rowInput.isEmpty() || rowInput.charAt(0) != 'r' || Integer.parseInt(rowInput.replace("r", "")) < 0 && Integer.parseInt(rowInput.replace("r", "")) > 13){
                System.out.println("invalid row number, try again");
                rowInput = scanner.nextLine().toLowerCase();
            }*/
        }

        System.out.println("To:");

        String toDeckInput = scanner.nextLine().toLowerCase();
        Deck toDeck = getDeckByInput(gameState, toDeckInput);

        if (toDeck == null){
            throw new MoveException("invalid target deck, try again:");
        }
        /*while (toDeck == null || toDeck.getDeckType().equals(DeckType.WASTE)){
            System.out.println("invalid target deck, try again:");
            toDeckInput = scanner.nextLine().toLowerCase();
            toDeck = getDeckByInput(gameState, toDeckInput);
        }*/

        if (rowInput.isEmpty()) {
            toDeck.addAll(gameState.getCardsFromDeck(fromDeck, 1));
        }
        else {
            int rowNumber = Integer.parseInt(rowInput.replace("r", "")) - 1;
            int amount = fromDeck.size() - rowNumber;
            toDeck.addAll(gameState.getCardsFromDeck(fromDeck, amount, rowNumber));
        }

        return "Moved card(s)";
    }

    @Override
    public Move createInstance(String playerInput) {
        return this;
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
}
