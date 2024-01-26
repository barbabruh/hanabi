package fr.umlv.hanabi;

public class DeckView {
	DeckData Deck;
	
	/**
	 * create a deck to be seen
	 * @param Deck the deck based on a DeckData's instance
	 */
	public DeckView(DeckData Deck) {
		this.Deck = Deck;
	}
	
	/**
	 * obtain a string from an instance of DeckView
	 * @return "empty deck" if the number of card in the deck is 0. Else return a string with the data of all the cards in the deck.
	 */
	public String toString() {
		if(this.Deck.getNbCard() == 0) {
			return "empty deck";
		}
		if(Deck.getNbCard() == 1) {
			return "[" + new CardView(this.Deck.getDeck().get(0)).toString() + "]";
		}
		String s = "[ " + new CardView(this.Deck.getDeck().get(0)).toString();
		for(int i = 1; i < Deck.getNbCard(); i++) {
			s += ", " + new CardView(this.Deck.getDeck().get(i)).toString();
		}
		return s + "]";
	}
}
