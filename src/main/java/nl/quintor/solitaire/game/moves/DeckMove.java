package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.state.GameState;

public class DeckMove implements Move {
    @Override
    public String apply(GameState gameState) throws MoveException {
        if (gameState.getStock().isEmpty()){
            gameState.getStock().addAll(gameState.getCardsFromDeck(gameState.getWaste(), gameState.getWaste().size()));
        }
        else {
            // add card from stock to waste
            gameState.getWaste().addAll(gameState.getCardsFromDeck(gameState.getStock(), 1));
        }

        return "Card retrieved from stock";
    }

    @Override
    public Move createInstance(String playerInput) {
        return this;
    }
}
