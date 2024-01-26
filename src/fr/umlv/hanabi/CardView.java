package fr.umlv.hanabi;

public class CardView{
	CardData card;
	
	/**
	 * create a card to be seen in the terminal
	 * @param card the card based on a CardData's instance
	 */
	public CardView(CardData card) {
		this.card = card;
	}
	
	
	/**
	 * obtain a string from an instance of CardView
	 * @return a string with the element from a card
	 */
	public String toString(){
		return this.card.getColor().toString() + " " + this.card.getNum() ;
	}
	
}



