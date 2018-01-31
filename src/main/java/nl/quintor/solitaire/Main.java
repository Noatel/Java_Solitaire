package nl.quintor.solitaire;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.game.moves.Quit;
import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.card.Rank;
import nl.quintor.solitaire.models.card.Suit;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.deck.DeckType;
import nl.quintor.solitaire.models.state.GameState;
import nl.quintor.solitaire.ui.CMD;
import nl.quintor.solitaire.ui.UI;

import java.util.*;


/**
 * Application entry point
 */
public class Main {
    /**
     *  Application entry point. Consists of three phases: initialization, game loop and game shutdown. During the
     *  initialization phase, the UI, game state and possible moves are created. The game loop is entered, which runs as
     *  long as the game is not over. The game loop essentially consists of:
     *
     *  <p><ul>
     *      <li>visualize GameState object
     *      <li>request input
     *      <li>translate input into a Move
     *      <li>apply the Move to the GameState object
     *      <li>communicate the result to the player
     *  </ul></p>
     *
     * When the game loop exits, the result of the game must be shown to the player and the UI is refreshed one final
     * time.
     */
    public static void main(String... args){
        // initialize the GameState, UI and all possible moves
        UI ui = new CMD();
        GameState gameState = new GameState();
        List<String> keys = Arrays.asList("Q");
        List<Move> moves = Arrays.asList(new Quit());
        HashMap<String, Move> possibleMoves = new HashMap<>();
        for (int i = 0; i<keys.size(); i++) possibleMoves.put(keys.get(i), moves.get(i));


        System.out.println("gamestate");
        System.out.println(gameState);

        // game loop

        while (!gameState.isGameOver()){
            String playerInput = ui.refreshAndRequestMove(gameState, moves).toUpperCase();
        }
        /*while (!gameState.isGameOver()) {
            // show gamestate to the player and ask for next move
            String playerInput = ui.refreshAndRequestMove(gameState, moves).toUpperCase();

            Move move = possibleMoves
                .getOrDefault(playerInput.substring(0,1), null)
                .createInstance(playerInput);

            try{
                ui.setMessage(move.apply(gameState));
            } catch (MoveException e){
                ui.setErrorMessage(e.getMessage());
            }
        }

        if (gameState.isGameWon()){
            ui.setMessage("Congratulations, you beat the game!!! " + gameState.toString());
        }*/
        //ui.refresh(gameState);
        ui.refresh(gameState);
    }
}