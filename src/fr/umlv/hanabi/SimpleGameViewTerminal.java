package fr.umlv.hanabi;

public class SimpleGameViewTerminal {
		
	private SimpleGameData game;
	
	/**
	 * create a copy of the game's data to be seen in the terminal
	 * @param game the informations of the game
	 */
	public SimpleGameViewTerminal(SimpleGameData game){
		this.game = game;
	}
	
	/**
	 * display all the players' hand, depending on the current player
	 * @param index_hand the index of the player
	 */
	public void printPlayer(int index_hand){
		for(int i = 0; i < game.getHands().size(); i++) {
			if(i == index_hand)
				System.out.println(new HandView(game.getHands().get(i)));
			else {
				new HandView(game.getHands().get(i)).printHand(i+1);
			}
		}
		System.out.println("discarded pile : " + new DeckView(game.getDiscardedPile()) + "\nlife : " + game.getLife() + "   number of clue : " + game.getClue());
		System.out.print("Board : [ ");
		for(int i = 0; i < 5; i++) {
			System.out.print("(" + new CardView(game.getBoard()[i]) + ") ");
		}
		System.out.println("]");
	}

}
