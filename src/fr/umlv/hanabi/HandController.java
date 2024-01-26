package fr.umlv.hanabi;

public class HandController {
	HandData hand;
	
	/**
	 * create a hand with the data of a hand
	 * @param hand data of a hand
	 */
	public HandController(HandData hand) {
		this.hand = hand;
	}
	
	/**
	 * remove a given card of the hand and pick a new one on the top of the deck
	 * @param d the deck the new card is picked from
	 * @param i the index of the card in the hand that need to be removed 
	 */
	public void pick(DeckData d, int i) {
		if(d.getNbCard() <= 0) {
			this.hand.getHand().remove(i);
		}
		else {
			this.hand.getHand().remove(i);
			this.hand.getHand().add(d.getDeck().get(d.getNbCard() - 1));
			this.hand.getInfoColor().remove(i);
			this.hand.getInfoColor().add(false);
			this.hand.getInfoNum().remove(i);
			this.hand.getInfoNum().add(false);
			d.setNbCard(d.getNbCard() - 1);
		}
	}
	
	
	/**
	 * add a card from the hand to the discarded pile
	 * @param index the index of the card to discard
	 * @param discarded_pile the discarded pile
	 */
	public void discard(int index, DeckData discarded_pile){
		discarded_pile.getDeck().add(this.hand.getHand().get(index));
		discarded_pile.setNbCard(discarded_pile.getNbCard() + 1);
	}
}
