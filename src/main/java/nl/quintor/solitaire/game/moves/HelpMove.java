package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.state.GameState;

public class HelpMove implements Move {
    @Override
    public String apply(GameState gameState) throws MoveException {
        return   "=================================================================== \n \n" +
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
    }

    @Override
    public Move createInstance(String playerInput) {
        return this;
    }
}
