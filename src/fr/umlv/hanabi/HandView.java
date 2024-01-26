package fr.umlv.hanabi;

public class HandView {
	HandData hand;
	
	/**
	 * create a hand to be seen in the terminal
	 * @param hand the card based on a HandData's instance
	 */
	public HandView(HandData hand) {
		this.hand = hand;
	}
	
	/**
	 * obtain a string from the hand of the current player
	 * @return a string with the element from the current player's hand
	 */
	public String toString(){
		var s = "Your hand : \n";
		for(int i = 0; i < this.hand.getHand().size(); i++) {
			s += "\t card " + (i+1) + " : ";
			if(this.hand.getInfoColor().get(i) == true) {
				s += "color = " + this.hand.getHand().get(i).getColor() + ", ";
			}
			else {
				s += "color = ?, ";
			}
			
			if(this.hand.getInfoNum().get(i) == true) {
				s += "num = " + this.hand.getHand().get(i).getNum() + "\n";
			}
			else {
				s += "num = ?\n";
			}
		}
		return s;
	}
	
	/**
	 * display a string from the hand of the other players
	 * @param num_player the number of the player
	 */
	public void printHand(int num_player) {
		var s = "Player n°" + num_player + " hand : \n";
		for(int i = 0; i < this.hand.getHand().size(); i++) {
			s += "\t card " + (i+1) + " : ";
			if(this.hand.getInfoColor().get(i) == true)
				s += "color = " + this.hand.getHand().get(i).getColor() + " Ok , ";
			else
				s += "color = " + this.hand.getHand().get(i).getColor() + "    , ";
			if(this.hand.getInfoNum().get(i) == true)
				s += "num = " + this.hand.getHand().get(i).getNum() + " Ok\n";
			else
				s += "num = " + this.hand.getHand().get(i).getNum()  + "\n";
		}
		System.out.println(s);
	}
}
