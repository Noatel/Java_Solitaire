# Product Backlog

This is your **Product Backlog**, a list of desirable features for your project. It is split into two categories:

The first category consists of "MVP"-features. MVP means Minimal Viable Product, and this set of features is what you 
have to implement at minimum to launch your software (to "go live"). **You have to implement the MVP at minimum to pass the 
project.**

The second set of features consists of optional features. You get **bonus points for implementing optional features**,
but you don't have to. However, the **best version of the game will win a prize**. You might try and rush as many
features as possible into your implementation, but keep an eye on the quality of your work: **bugs & stacktraces will count
against you**.

All features have been assigned a number of points. **These points indicate our estimation of the relative difficulty 
and/or time commitment of implementing that feature.** You, as a team including your Product Owner, will decide what 
features you are going to pursue.

### MVP Features
-  **1: MVP User Interface**  <p>
   _Type_  
   Epic  
   _Total Points_  
   9  <p>
   _Description_  
   Your application has to include a UI. How you implement it is up to you. You can choose to write a 
   command line UI or a Swing / JavaFX interface.  
   <p>If you choose a command line UI, this will involve parsing user input text to card moves, for example. You will have to 
   manipulate quite a lot of Strings to generate the UI. The upside is that you don't need anything else, there is no 
   knowledge of any frameworks required. To help you on your way if you choose to implement a command line UI, here is 
   some code to clear the screen in between refreshes:

   ```java
   private static void clrscr(){
       try {
           if (System.getProperty("os.name").contains("Windows"))
               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
           else
               System.out.print("\033\143");
       } catch (IOException | InterruptedException ex) {
           throw new RuntimeException("Screen clearing error");
       }
   }
   ```     
   <p>If you choose a Swing or JavaFX interface, some things may be easier. You can create buttons, for example, so the 
   bonus repeatable-moves feature is easy to implement. However, you are on your own in working with the framework you pick, 
   and it cannot be guaranteed that your teachers can help you if you are stuck. If you are unfamiliar with such 
   a framework, it's probably best not to use one because there's enough new stuff to learn without the added burden.

   Whichever you choose, your code has to implement the [UI-interface](src/main/java/nl/quintor/solitaire/ui/UI.java),
   so please study it carefully.
   
   -  **1.1: Game time, number of moves played and score**  <p>
     _Type_  
     MVP UI Feature     
     _Points_  
     0.5  <p>
     _Description_  
     Your UI must show the total game time, the number of moves played (as in, the number of moves in [GameState](src/main/java/nl/quintor/solitaire/models/state/GameState.java).moves),
     and the current total score (as in, the result of GameState.getScore()).  

   -  **1.2: Columns with invisible cards**  <p>
     _Type_  
     MVP UI Feature     
     _Points_  
     2  <p>
     _Description_  
     There are 7 columns in a standard Solitaire game. They start of with increasing lengths (1-7 cards), 
     and all cards are visually present, but all but the last card are turned upside-down. Your UI should 
     show these columns as described. The [Deck](src/main/java/nl/quintor/solitaire/models/deck/Deck.java) class parameter 
     "invisibleCards" facilitates implementation of this feature.    

   -  **1.3: Stock with top card visible**  <p>
     _Type_  
     MVP UI Feature     
     _Points_  
     1  <p>
     _Description_  
     The stock contains 24 reserve cards, which are turned upside-down. The top card is turned open. The player can
     choose to move it to a stack or column, or put it at the bottom of the stock and turn open the next card, which is
     called "cycling the stock".

   -  **1.4: Four stacks with top card visible**  <p>
     _Type_  
     MVP UI Feature  
     _Points_  
     1  <p>
     _Description_  
     The Stack Piles are A-2-...-Q-K sorted, with the only top card visible.

   -  **1.5: Deck & card headers**  <p>
     _Type_  
     MVP UI Feature  
     _Points_  
     1  <p>
     _Description_  
     Stock, stacks and columns all need labels or headers to be identifiable. This is especially important when 
     you choose to create a command line interface, where the headers help the user to indicate which card they want to 
     move to where, for example "move A5 SA" for move card 5 from column A to Stack Pile A.
   
   -  **1.6: Available moves**  <p>
     _Type_  
     MVP UI Feature  
     _Points_  
     0.5  <p>
     _Description_  
     It must be clear to the player from the UI which actions or moves are available to him/her. Think carefully about 
     which actions your UI will support, and try to create a flexible structure that allows adding more moves later.
     
   -  **1.7: Responding to player input**  <p>
     _Type_  
     MVP UI Feature  
     _Points_  
     2  <p>
     _Description_  
     The player has to be able to perform actions somehow. In a command line UI, the user will enter text, which has to be
     parsed into actions. A GUI will probably have to respond to mouse events / button clicks. It is NOT the job of the 
     UI to actually perform the chosen action. The UI either hands of the input of the player to the game logic code to 
     process as it pleases, or at most parses the input into a specific action, like calling a "quit()" function if the 
     player inputs a quit-command in some form.
     
   -  **1.8: Messages & error messages**  <p>
     _Type_  
     MVP UI Feature  
     _Points_  
     1  <p>
     _Description_  
     The user has to be notified of the result of his/her actions. The messages are generated outside of the UI, by the
     game logic, but it's the UI's job to communicate such messages to the user. For the MVP, messages and error messages
     do not have to be visually distinct. An example of such a message is "You can't move a card to the stock".

-  **2: MVP Game logic**  <p>
   _Type_  
   Epic  
   _Total Points_  
   22  <p>
   _Description_  
   The game logic is the core of the application. It will handle all action processing, check if user actions are 
   actually allowed under the game rules and make sure the game state remains valid (for example, that there are always 
   52 cards in play). It also initializes the [GameState](src/main/java/nl/quintor/solitaire/models/state/GameState.java) object and monitors it, for example in order to detect the game 
   has been won.
   The game logic is the code that is most critical and has the biggest potential for hidden bugs like unexpected
   NullPointerExceptions or ArrayIndexOutOfBoundExceptions. Be careful and think things through.  
   
   To help you and provide some structure, there are two interfaces that you can use to create Move-classes, which
   represent player actions: [Move](src/main/java/nl/quintor/solitaire/game/moves/Move.java) and 
   [RevertibleMove](src/main/java/nl/quintor/solitaire/game/moves/RevertibleMove.java). Start off with Move, and upgrade
   to RevertibleMove when the need arises.
   
   -  **2.1: Initialize the GameState**  <p>
      _Type_  
      MVP Logic Feature  
      _Points_  
      3  <p>
      _Description_  
      The [GameState](src/main/java/nl/quintor/solitaire/models/state/GameState.java) is the most important object in the game. The UI visualizes it, and the game logic manipulates it. At 
      the start of a game, a new GameState instance must be created, and it various components must be initialized as well.
      For example, 52 cards have to be dealt. Make sure no null values remain.
     
   -  **2.2: Stock cycling**  <p>
      _Type_  
      MVP Logic Feature  
      _Points_  
      4  <p>
      _Description_  
      The stock contains a 24-card reserve. The player can cycle through it at will, but can also remove 
      cards from the stock. Create a robust cycling mechanism, that doesn't crash because it assumes there's a card left 
      in the stock that really isn't there.
      You can choose to use a single deck to hold up-faced and down-faced cards (the stock deck itself), and cycle
      cards through it. You can also use a separate deck for down-faced cards (the waste). That's why the waste deck is 
      present in the [GameState](src/main/java/nl/quintor/solitaire/models/state/GameState.java) class. It's up to you.
     
   -  **2.3: Move card(s)**  <p>
      _Type_  
      MVP Logic Feature  
      _Points_  
      4  <p>
      _Description_  
      Moving cards around is what Solitaire is all about. This feature is not about checking the legality of the moves, 
      it's just moving the cards around. Remember that some moves can consist of multiple cards.
      <p>While this seems simple, it involves divining the player's intentions somehow. If your UI has been correctly 
      implemented, the player has a possibility to feed information into the system (card x to stack pile a, please). You 
      have to find the source deck, source card(s) and destination deck before you can execute the move.
      
   -  **2.4: Flip invisible cards upon exposure**  <p>
      _Type_  
      MVP Logic Feature  
      _Points_  
      2  <p>
      _Description_  
      The seven columns in Solitaire have invisible cards. When an invisible card is exposed, that is, not covered by 
      another card anymore, it should be flipped open. You need to decrease the column's invisibleCards value.

   -  **2.5: Help & player instructions**  <p>
      _Type_  
      MVP Logic Feature  
      _Points_  
      1  <p>
      _Description_  
      There must be a possibility for the player to review how the game works. You don't have to explain the rules of 
      Solitaire to the player, but he/she might want some pointers to navigate your game, especially if it has a
      command line interface.
      
   -  **2.6: Illegal card move detection**  <p>
      _Type_  
      MVP Logic Feature  
      _Points_  
      8  <p>
      _Description_  
      Detecting an illegal move is basically the same as implementing the game rules. For example, it is not allowed to 
      move a card to the stock. Detecting an attempt to do so and blocking it brings you a step closer to implementing 
      Solitaire. The checks you have to do can basically be split into three categories: checking if the player input is 
      valid without looking at decks or cards, checking if the requested move is legal on the deck level without looking 
      at the cards themselves, and finally checking if the requested move is legal based on the specific cards. These 
      checks have to be woven into your implementation of the 2.3 feature.
     
      -  **2.6.1: Player input verification**  <p>
         _Type_  
         MVP Logic Subfeature  
         _Points_  
         2  <p>
         _Description_  
         You have to verify that the player does a theoretically legal request. Your Solitaire game will probably not be 
         able to order pizza, so if the player input reads "2 pizza salami" the game should not crash but kindly inform 
         the player that his request can't be processed. The game also should not crash when the player enters an invalid 
         card location or deck location, for example trying to move a card from column 8, which doesn't exist. In a GUI, 
         where the player clicks buttons and/or drags cards, this may be less of an issue, depending on your implementation.
     
      -  **2.6.2: Deck-level legality checks**  <p>
         _Type_  
         MVP Logic Subfeature  
         _Points_  
         2  <p>
         _Description_  
         Once you are sure that the player request is not nonsense, you can try to find the source deck, the index of 
         the source card in the source deck, and the destination deck. With this information in hand, you can then 
         do some further checks. Like if the destination deck happens to be the source deck, for example.
      
      -  **2.6.3: Card-level legality checks**  <p>
         _Type_  
         MVP Logic Subfeature  
         _Points_  
         4  <p>
         _Description_  
         After the deck-level decks, you can grab the card(s) that have to be transferred and take a look at them. For
         example, the first card on a stack pile must be an Ace, and the next card on a column must be of a different 
         color (red and black) as the previous card.        

### Optional features
-  **3: Optional User Interface**  <p>
   _Type_  
   Epic  
   _Total Points_  
   5  <p>
   _Description_  
   The basic user interface can be improved in various ways.
   
   -  **3.1: Stock header shows card count**  <p>
      _Type_  
      Optional UI Feature  
      _Points_  
      0.5  <p>
      _Description_  
      The stock header should show how many cards there are left in the stock.
   
   -  **3.2: Visually distinct error messages**  <p>
      _Type_  
      Optional UI Feature  
      _Points_  
      1  <p>
      _Description_  
      For the MVP it is enough if normal messages and error messages are displayed in the same way. It would be better, 
      however, if they were visually different. How exactly the looks are different is left up to your discretion.
      
    -  **3.3: Help text displayed automatically at game start**  <p>
       _Type_  
       Optional UI Feature  
       _Points_  
       0.5  <p>
       _Description_  
       Show the game's instructions when the game is started, so that users don't have to go search for them.
       
   -  **3.4: Previous move is repeatable**  <p>
      _Type_  
      Optional UI Feature  
      _Points_  
      3  <p>
      _Description_  
      If a user wishes to "redo" their last move, it should be enough to hit a single button, for example Return.
        
-  **4: Optional Game Logic**  <p>
   _Type_  
   Epic  
   _Total Points_  
   13  <p>
   _Description_  
   The MVP game logic is not quite complete.
   
   -  **4.1: Revertible moves**  <p>
      _Type_  
      Optional Logic Feature  
      _Points_  
      4  <p>
      _Description_  
      Moves that influence the game state or the score should be revertible. To implement this feature, make those moves 
      implement [RevertibleMove](src/main/java/nl/quintor/solitaire/game/moves/RevertibleMove.java) instead of 
      [Move](src/main/java/nl/quintor/solitaire/game/moves/Move.java). This will add a method revert() to their class 
      definition, into which you can put the logic of the revert operation. In order to save your moves for reverting, you 
      can use GameState's moves list, which can only contain RevertibleMove objects. Conveniently, GameState has two 
      methods for you called forget(RevertibleMove) and remember(RevertibleMove) which you can use.
      
   -  **4.2: Win detection**  <p>
      _Type_  
      Optional Logic Feature  
      _Points_  
      2  <p>
      _Description_  
      The game should detect when the player has won. It can be quite tedious to move cards around after a winning 
      position has already been achieved. The GameState object contains all the necessary information. Think about it: 
      when exactly has a game been won?

   -  **4.3: Base score calculation**  <p>
      _Type_  
      Optional Logic Feature  
      _Points_  
      5  <p>
      _Description_  
      The GameState holds two score values, baseScore and timeScore, which together form the total score, which can 
      never be negative.
      The base score is the score without any time penalties or time bonuses. The base score is calculated as follows:
      
        | Source           | Destination | Score |
        |------------------|-------------|-------|
        | Stock            | Column      | 5     |
        | Stock            | Stack       | 10    |
        | Column           | Stack       | 10    |
        | Stack            | Column      | -15   |
        | Invisible column | Column      | 5     |  
        
      Additionally, you **lose** 100 points every time the stock is recycled. This is what makes calculating the score 
      difficult, because how do you count how often this has happened? If you count the times the first or last stock card is 
      cycled, you run into problems if that card is moved away from the stock. Think this through very carefully. 
      Also, if your moves are revertible, their effect on the base score must be revertible as well.
      
   -  **4.4: Time score calculation**  <p>
      _Type_  
      Optional Logic Feature  
      _Points_  
      2  <p>
      _Description_  
      The GameState holds two score values, baseScore and timeScore, which together form the total score, which can 
      never be negative. To easily calculate the game time, you can use 
      [Duration](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html).between().
      The time score consists of two parts. Initially, 2 points are subtracted for every 10 seconds of play. However, if
      the game is won in more than 30 seconds, 700.000 / duration-in-seconds bonus points are awarded. If the game is 
      shorter than 30 seconds (which is probably only theoretically possible), no bonus points are awarded. 