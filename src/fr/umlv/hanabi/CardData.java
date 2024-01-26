package fr.umlv.hanabi;

public class CardData {
	private final Color color;
	private final int num;
	
	/**
	 * create a card
	 * @param num this is the value of the card
	 * @param color this is the color of the card(red,yellow,green,blue or white)
	 */
	public CardData(int num, Color color) {
		this.num = num;
		this.color = color;
	}
	
	/**
	 * get the information on the color of the card
	 * @return the color of a card
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * get the information on the number of the card
	 * @return the number of a card
	 */
	public int getNum() {
		return num;
	}
	
}
