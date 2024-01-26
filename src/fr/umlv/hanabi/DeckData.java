package fr.umlv.hanabi;

import java.util.ArrayList;

public class DeckData {
	
	final int NB_MAX = 50;
	final int NB_SWAP = 200;
	private ArrayList<CardData> deck;
	private int nbCard;
	
	/**
	 * create the deck containing all the cards of the game in a set order
	 * @param nbCard the number of card in the deck
	 */
	public DeckData(int nbCard) {
		this.deck = new ArrayList<CardData>();
		this.nbCard = nbCard;
		if(nbCard == 0)
				return;
		Color[] colors = Color.values();
		for(var color : colors){
			this.deck.add(new CardData(1, color));
			this.deck.add(new CardData(1, color));
			this.deck.add(new CardData(1, color));
			this.deck.add(new CardData(2, color));
			this.deck.add(new CardData(2, color));
			this.deck.add(new CardData(3, color));
			this.deck.add(new CardData(3, color));
			this.deck.add(new CardData(4, color));
			this.deck.add(new CardData(4, color));
			this.deck.add(new CardData(5, color));
		}
	}
	
	/**
	 * randomly mix the deck 
	 */
	public void  mix(){
		int card1;
		int card2;
		
		for(int i = 0; i < NB_SWAP; i++) {
			card1 = (int) (Math.random() * 50);
			card2 = (int) (Math.random() * 50);
			this.swap(card1, card2);
		}
		
	}
	
	/**
	 * exchange the position of two cards in the deck
	 * @param i the index of the first card to exchange
	 * @param j the index of the second card to exchange
	 */
	public void swap(int i, int j){
		CardData tmp = this.deck.get(i);
		this.deck.set(i, this.deck.get(j));
		this.deck.set(j, tmp);
	}
	
	/**
	 * change the number of card in the deck
	 * @param nbCard the new quantity of card in the deck
	 */
	public void setNbCard(int nbCard) {
		this.nbCard = nbCard;
	}

	/**
	 * obtain the deck
	 * @return the current deck
	 */
	public ArrayList<CardData> getDeck() {
		return this.deck;
	}

	/**
	 * obtain the number of card in the deck
	 * @return the number of card in the deck
	 */
	public int getNbCard() {
		return this.nbCard;
	}
	
}


 
