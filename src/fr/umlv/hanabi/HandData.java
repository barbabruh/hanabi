package fr.umlv.hanabi;

import java.util.ArrayList;

public class HandData {
	
	private ArrayList<CardData> hand;
	private ArrayList<Boolean> infoColor;
	private ArrayList<Boolean> infoNum;
	private int nbCard;
	
	/**
	 * create the hand of a player. The number of card depend on the number of player.
	 * @param d the deck you pick the cards from
	 * @param nb_player the number of player
	 */
	public HandData(DeckData d,int nb_player){
		if(nb_player == 2 || nb_player == 3)
			nbCard = 5;
		else
			nbCard = 4;
		this.hand = new ArrayList<CardData>();
		this.infoColor = new ArrayList<Boolean>();
		this.infoNum = new ArrayList<Boolean>();
		
		for(int i = 0; i < nbCard; i++) {
			this.hand.add(d.getDeck().get(d.getNbCard() - i - 1));
			this.infoColor.add(false);
			this.infoNum.add(false);
		}
		d.setNbCard(d.getNbCard() - nbCard);
	}
	
	/**
	 * obtain the hand of a player
	 * @return the hand
	 */
	public ArrayList<CardData> getHand() {
		return hand;
	}
	
	/**
	 * obtain the number of card in the hand
	 * @return the number of cards
	 */
	public int getNbCard() {
		return this.nbCard;
	}
	
	/**
	 * tell if the given card have a clue on its color
	 * @return true if a clue on its color has been given or false otherwise
	 */
	public ArrayList<Boolean> getInfoColor() {
		return infoColor;
	}
	
	/**
	 * tell if the given card have a clue on its number
	 * @return true if a clue on its number has been given or false otherwise
	 */
	public ArrayList<Boolean> getInfoNum() {
		return infoNum;
	}
	
}
