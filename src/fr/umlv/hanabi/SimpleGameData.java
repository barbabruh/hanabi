package fr.umlv.hanabi;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.umlv.hanabi.SimpleGameView.Area;
import fr.umlv.zen5.ApplicationContext;


public class SimpleGameData {
	
	final private int MAX_PLAYER = 5;
	final private int MIN_PLAYER = 2;
	
	private DeckData deck;
	private DeckData discardedPile;
	private CardData[] board;
	private ArrayList<HandData> hands;
	private int life;
	private int clue;

	/**
	 * create all the data needed to play the game
	 * @param nb_player the number of player
	 * @throws IOException when the entered value causes an error
	 */
	public SimpleGameData(int nb_player) throws IOException {
		this.deck = new DeckData(50);
		this.deck.mix();
		this.discardedPile = new DeckData(0);
		this.board = new CardData[5];
		Color[] colors = Color.values();
		for(int i = 0; i < 5; i++) {
			this.board[i] = new CardData(0, colors[i]);
		}
		if(nb_player >= MIN_PLAYER || nb_player >= MAX_PLAYER)
			this.hands = new ArrayList<>();
		else
			throw new IOException("The number of player entered isn't valid!!!");
		
		for(int i = 0; i < nb_player; i++) {
			this.hands.add(new HandData(this.deck, nb_player));
		}
		this.life = 3;
		this.clue = 8;
	}
	
	/**
	 * obtain the amount of life of the players
	 * @return the remaining life(s) of the group
	 */
	public int getLife() {
		return this.life;
	}
	
	/**
	 * obtain the current deck of the game
	 * @return the current deck
	 */
	public DeckData getDeck() {
		return this.deck;
	}
	
	/**
	 * obtain the amount of clue left of the group
	 * @return the remaining clue of the group 
	 */
	public int getClue() {
		return this.clue;
	}
	
	/**
	 * obtain the informations of the fireworks' board
	 * @return the board in its current state
	 */
	public CardData[] getBoard() {
		return this.board;
	}
	
	/**
	 * get the hands of all players
	 * @return the arrayList containing all the hands of the player 
	 */
	public ArrayList<HandData> getHands(){
		return this.hands;
	}
	
	/**
	 * get the discarded pile
	 * @return the current discarded pile of the game
	 */
	public DeckData getDiscardedPile() {
		return this.discardedPile;
	}
	
	/**
	 * make the player choose a card in the terminal while the number chosen is valid
	 * @param entry allow the player to enter their choice in the terminal 
	 * @return the entered number (corresponding  to the choice) 
	 * @throws IOException when the entered value causes an error
	 */
	public int cardChoice(BufferedReader entry) throws IOException{
		int c;
		System.out.println("Choose one of your card : \n");
		do {
			c = readInt(entry);
			if(c < 0 || c > this.hands.get(0).getHand().size()) {
				System.out.println("The entered number isn't valid!!!");
			}
		}while(c < 0 || c > this.hands.get(0).getHand().size());
		return c -1;
	}
	
	/**
	 * play a card and remove it from the player's hand.
	 * <p>if the played card can enter the fireworks board, then the card is added to that board. One clue is restored if a color is completed</p>
	 * <p>otherwise, the card is send to the discarded pile and the group loses a life</p>
	 * @param index_card the index of the card to play
	 * @param index_hand the index of the player who is playing the card
	 * @return false if index_card is equal to 0 and true otherwise 
	 */
	public boolean playCard(int index_card, int index_hand){
		if(index_card == -1) {return false;}
		for(int i = 0; i < 5; i++) {
			if(this.hands.get(index_hand).getHand().get(index_card).getNum() == board[i].getNum() + 1 && this.hands.get(index_hand).getHand().get(index_card).getColor() == board[i].getColor()) {
				if(this.hands.get(index_hand).getHand().get(index_card).getNum() == 5 && this.clue < 8) this.clue +=1;
				board[i] = this.hands.get(index_hand).getHand().get(index_card);
				if(this.deck.getNbCard() > 0) new HandController(this.hands.get(index_hand)).pick(this.deck, index_card);
				return true;
			}
		}
		new HandController(this.hands.get(index_hand)).discard(index_card, this.discardedPile);
		new HandController(this.hands.get(index_hand)).pick(this.deck, index_card);
		this.life -= 1 ;
		return true;
	}
	
	/**
	 * throw a card in the discarded pile and, if the current number of clue is inferior to 8, add a clue
	 * @param index_card the index of the card to throw
	 * @param index_hand the index of the player who is throwing the card
	 * @return false if index_card is equal to -1 and true otherwise 
	 */
	public boolean throwCard(int index_card, int index_hand){
		if(index_card == -1) {return false;}
		new HandController(this.hands.get(index_hand)).discard(index_card, this.discardedPile);
		new HandController(this.hands.get(index_hand)).pick(this.deck, index_card);
		
		if(this.clue < 8) {
			this.clue += 1 ;
		}
		
		return true;
	}
	
	/**
	 * make the player choose a player to give a clue to in the terminal while the number chosen is valid
	 * @param player the number of the player giving the clue
	 * @param entry allow the player to enter their choice in the terminal
	 * @return the entered number, corresponding to the number of the chosen player
	 * @throws IOException when the entered value causes an error
	 */
	public int playerChoice(int player, BufferedReader entry) throws IOException{
		int c;
		System.out.println("Choose a player to give a clue or '0' to go back :\n");
		for(int i = 1; i < this.hands.size() + 1; i++){
			System.out.println("\t" + i + ": player" + i);
		}
		do {
			c = readInt(entry);
			if(c-1 == player || c > this.hands.size() || c < 0) {
				System.out.println("The entered number isn't valid!!!");
			}
		}while(c-1 == player || c > this.hands.size() || c < 0);
		
		return c;
	}
	
	/**
	 * make the player choose the type of clue they want to give in the terminal while the number chosen is valid
	 * @param entry allow the player to enter their choice in the terminal
	 * @return the entered number, corresponding to the type of clue (number or color)
	 * @throws IOException when the entered value causes an error
	 */
	public static int typeOfClue(BufferedReader entry) throws IOException{
		int c;
		System.out.println("Do you want to give a clue about the color or the number (press '0' to go back) :\n");
		System.out.println("1. Color\t 2. Num\n");
		do {
			c = readInt(entry);
			if(c < 0 || c > 2) {
				System.out.println("The entered number isn't valid!!!");
			}
		}while(c < 0 || c > 2);
		return c;
	}
	
	/**
	 * make the player choose the number/color (depending on the choice made) 
	 * <p>they want to give in the terminal while the number chosen is valid</p>
	 * @param choice correspond to 1 if the player chose to give a number or 2 if they chose to give a color
	 * @param entry allow the player to enter their choice in the terminal
	 * @return choice if choice is equal to 0 or the number/color the player entered otherwise
	 * @throws IOException when the entered value causes an error
	 */
	public static int clueChoice(int choice, BufferedReader entry) throws IOException{
		int c; Color[] v = Color.values();
		if(choice == 0) return choice;
		else if(choice == 1) {
			System.out.println("Choose a Color or '0' to go back :\n");
			for(int i = 1; i < v.length + 1; i++)
				System.out.println("\t" + v[i - 1] + " : " + i + "\t");
		}
		else if(choice == 2) {
			System.out.println("Choose a Num or '0' to go back :\n");
			for(int i = 1; i < 6; i++)
				System.out.println("\tNum " + i + " : " + i);
		}
		do {
			c = readInt(entry);
			if(c < 0 || c > 5)
				System.out.println("The entered number isn't valid!!!");
		}while(c < 0 || c > 5);
		return c - 1;	
	}
	
	/**
	 * read the first entered character
	 * @param entry allow the player to enter their choice in the terminal
	 * @return the entered character
	 * @throws IOException when the entered value causes an error
	 */
	public static int readInt(BufferedReader entry) throws IOException{
		int test = 0;
		test = entry.read() - '0';
		entry.readLine();
		
		return test;
	}
	
	/**
	 * allow to skip the rest of a line of the terminal
	 * @param entry allow the player to enter their choice in the terminal
	 * @throws IOException when the entered value causes an error
	 */
	public static void readJump(BufferedReader entry) throws IOException{
		BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
		entree.readLine();
	}
	
	/**
	 * the primary function to give a clue. It calls all the other function to give a clue
	 * @param index_hand the index of the hand giving a clue
	 * @param entry allow the player to enter their choice in the terminal
	 * @param nb_player the number of player
	 * @return false if the called functions return 0 or -1 and true otherwise
	 * @throws IOException when the entered value causes an error
	 */
	public boolean giveClueTerminal(int index_hand, BufferedReader entry, int nb_player) throws IOException{
		if(this.clue <= 0) return false;
		int choice = typeOfClue(entry);
		if(choice == 0)return false;
		int deep_choice = clueChoice(choice, entry);
		if(deep_choice == -1)return false;
		int choice_player = playerChoice(index_hand, entry);
		if(choice_player == 0)return false;
		this.giveClueAnnex(choice, choice_player, deep_choice + 1, index_hand, nb_player);
		this.clue -= 1;
		return true;
	}
	
	/**
	 * the primary function to give a clue in graphic. It calls all the other function to give a clue
	 * @param context contains the informations of the current graphic window
	 * @param index_hand the index of the hand giving a clue
	 * @param choice the player's choice of action
	 * @param area the instance that allow to draw
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param num_discard the number of the card to draw in the discarded pile
	 * @param nb_player the number of player
	 * @return false if one of the choices is 0
	 */
	public boolean giveClueGraph(ApplicationContext context, int index_hand, int choice, Area area, float width, float height, int num_discard, int nb_player) {
		if(this.clue <= 0 || choice == 0) return false;
		int deep_choice = 0, choice_player = 0;
		if(choice == 1)
			deep_choice = area.drawGame(context, this, width, height, index_hand, num_discard,0 , nb_player);
		if(choice == 2)
			deep_choice = area.drawGame(context, this, width, height, index_hand, num_discard,3 , nb_player);
		if(deep_choice == 0) return false;
		choice_player = area.drawGame(context, this, width, height, index_hand, num_discard,4 , nb_player);
		if(choice_player == 0) return false;
		else if(choice_player == index_hand + 1) {return false;}
		this.giveClueAnnex(choice, choice_player, deep_choice, index_hand, nb_player);
		this.clue -= 1;
		return true;
		
	}
	
	/**
	 * give a clue to the chosen player
	 * @param choice the player's choice of action
	 * @param choice_player the number of the player that is given a clue 
	 * @param deep_choice 1 for a clue on the color and 2 for a clue on the number
	 * @param index_hand the index of the hand giving a clue
	 * @param nb_player the number of player
	 */
	public void giveClueAnnex(int choice, int choice_player, int deep_choice, int index_hand, int nb_player) {
		int j;
		Color[] v = Color.values();
		if(nb_player >= 3) {j = 4;}
		else {j = 5;}
		if(choice == 1) {
			for(int i = 0; i < j; i++) {
				if(this.hands.get(choice_player - 1).getHand().get(i).getColor() == v[deep_choice - 1])
					this.hands.get(choice_player - 1).getInfoColor().set(i, true);
			}
		}
		else{
			for(int i = 0; i < j; i++) {
				if(this.hands.get(choice_player - 1).getHand().get(i).getNum() == deep_choice)
					this.hands.get(choice_player - 1).getInfoNum().set(i, true);
			}
		}
	}
	
	/**
	 * make the player choose an action while the entered number isn't valid
	 * @param entry allow the player to enter their choice in the terminal
	 * @return the entered number
	 * @throws IOException when the entered value causes an error
	 */
	public static int actionChoice(BufferedReader entry) throws IOException{
		int c;
		System.out.println("\nChoose the action you want to do : \n\t");
		System.out.println("1.Play a Card\t2.Throw a Card\t3.Give a Clue\n");
		do {
			c = readInt(entry);
			if(c < 1 || c > 3)
				System.out.println("The entered number isn't valid!!!");
		}while(c < 1 || c > 3);
		return c;
	}
	
	/**
	 * calculate the score of the group
	 * @return the score of the group
	 */
	public int scoreCalc() {
		int result = 0;
		for(int i = 0; i < this.board.length; i++)
			result += this.board[i].getNum();
		return result;
	}
	
	/**
	 * make the player choose the number of player while the entered number isn't valid
	 * @param entry allow the player to enter their choice in the terminal
	 * @return the entered number
	 * @throws IOException when the entered value causes an error
	 */
	public static int nbPlayer(BufferedReader entry) throws IOException{
		int c;
		System.out.println("How many player? (between 2 and 5) : ");
		do {
			c = readInt(entry);
			if(c < 2 || c > 5)
				System.out.println("The entered number isn't valid!!!");
		}while(c < 2 || c > 5);
		return c;
	}
	
}

