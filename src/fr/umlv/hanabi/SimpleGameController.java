package fr.umlv.hanabi;

import java.awt.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.umlv.hanabi.SimpleGameView.Area;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;

public class SimpleGameController {
	
	/**
	 * this function is the main loop of the game when played in terminal mode
	 * @param game contains the informations of the current game
	 * @param entry allow the player to enter their choice in the terminal
	 * @param end a boolean that become "true" when one of the required conditions are met
	 * @param choice represents the different choices made by the players during the game
	 * @param nb_player the number of player
	 * @param i the number of the player who has to play
	 * @return the number of the current player when the end parameter becomes "true"
	 * @throws IOException when the entered value causes an error
	 */
	public static int mainLoopTerminal(SimpleGameData game, BufferedReader entry, boolean end, int choice, int nb_player, int i) throws IOException{
		while(!end) {
			new SimpleGameViewTerminal(game).printPlayer(i);
			choice = SimpleGameData.actionChoice(entry);
			if(choice == 1)
				if(!game.playCard(game.cardChoice(entry), i)) i = (i - 1) % nb_player;
			if(choice == 2)
				if(!game.throwCard(game.cardChoice(entry), i)) i = (i - 1) % nb_player;
			if(choice == 3)
				if(!game.giveClueTerminal(i, entry, nb_player)) i = (i - 1) % nb_player;
			i = (i+1) % nb_player;
			if(game.getLife() == 0 || game.getDeck().getNbCard() == 0 || game.scoreCalc() == 25)
				end = true;
			else {
				System.out.println("\n\n\n\n\nPress enter to pass next player...\n\n\n\n\n");
				SimpleGameData.readJump(entry);
			}
		}
		return i;
	}
	
	/**
	 * this function is the main loop of the game when played in graphic mode. It calls for an annex function.
	 * @param context contains the informations of the current graphic window
	 * @param game contains the informations of the current game
	 * @param end a boolean that become "true" when one of the required conditions are met
	 * @param choice represents the different choices made by the players during the game
	 * @param nb_player the number of player
	 * @param i the number of the player who has to play
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param area the instance that allow to draw
	 * @param num_discard the number of the card to draw in the discarded pile
	 * @return the number of the current player when the end parameter becomes "true"
	 */
	public static int mainLoopGraph(ApplicationContext context, SimpleGameData game, boolean end, int choice, int nb_player, int i, float width, float height, Area area, int num_discard){
		int[] result;
		while(!end) {
				choice = area.drawGame(context, game, width, height, i, num_discard, 1, nb_player);
				if(choice == 7 && game.getDiscardedPile().getNbCard() == 0)
					continue;
				while(choice == 7 && num_discard != -1){
					num_discard = (num_discard + 1) % game.getDiscardedPile().getNbCard();
					choice = area.drawGame(context, game, width, height, i, num_discard, 1, nb_player);
				}
				result = mainLoopGraphAnnex(context, game, end, choice, nb_player, i, width, height, area, num_discard);
				choice = result[0]; num_discard = result[1];
				if(choice == 0) {i = (i - 1) % nb_player;}
				i = (i+1) % nb_player;
				if(game.getLife() == 0 || game.getDeck().getNbCard() == 0 || game.scoreCalc() == 25) 
					end = true;
				else {area.waitNextPlayer(context, width, height, choice, (i+1)%(nb_player + 1));}
		}
		return i;
	}
	
	/**
	 * this function is responsible for the last turn of the game in graphic mode. It also calls an annex function.
	 * @param context contains the informations of the current graphic window
	 * @param game contains the informations of the current game
	 * @param end a boolean that become "true" when one of the required conditions are met
	 * @param choice represents the different choices made by the players during the game
	 * @param nb_player the number of player
	 * @param i the number of the player who has to play
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param area the instance that allow to draw
	 * @param num_discard the number of the card to draw in the discarded pile
	 * @return the number of the current player when the end parameter becomes "true"
	 */
	public static int lastTurnGraph(ApplicationContext context, SimpleGameData game, boolean end, int choice, int nb_player, int i, float width, float height, Area area, int num_discard){
		int finaly = 0; int[] result;
		while(!end) {
			choice = area.drawGame(context, game, width, height, i, num_discard, 1, nb_player);
			if(choice == 7 && game.getDiscardedPile().getNbCard() == 0) continue;
			while(choice == 7 && num_discard != -1){
				num_discard = (num_discard + 1) % game.getDiscardedPile().getNbCard();
				choice = area.drawGame(context, game, width, height, i, num_discard, 1, nb_player);
			}
			result = mainLoopGraphAnnex(context, game, end, choice, nb_player, i, width, height, area, num_discard);
			choice = result[0]; num_discard = result[1];
			if(choice == 0) {i = (i - 1) % nb_player;}
			i = (i+1) % nb_player;
			if(choice != 0) {finaly +=1;}
			if(game.getLife() == 0 || finaly == nb_player || game.scoreCalc() == 25) 
				end = true;
			else {area.waitNextPlayer(context, width, height, choice, (i+1)%(nb_player + 1));}
		}
		return i;
	}
	
	/**
	 * This deals with the choices made by players during the game and calls the corresponding functions
	 * @param context contains the informations of the current graphic window
	 * @param game contains the informations of the current game
	 * @param end a boolean that become "true" when one of the required conditions are met
	 * @param choice represents the different choices made by the players during the game
	 * @param nb_player the number of player
	 * @param i the number of the player who has to play
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param area the instance that allow to draw
	 * @param num_discard the number of the card to draw in the discarded pile
	 * @return an array containing the choice made by the player and num_discard
	 */
	public static int[] mainLoopGraphAnnex(ApplicationContext context, SimpleGameData game, boolean end, int choice, int nb_player, int i, float width, float height, Area area, int num_discard){
		if(choice == 1)	
			if(!game.playCard(area.drawGame(context, game, width, height, i, num_discard, 2, nb_player) - 1, i))
				choice = 0;
			else if(num_discard != game.getDiscardedPile().getNbCard() - 1)
				num_discard = game.getDiscardedPile().getNbCard() - 1;
		if(choice == 2)
			if(!game.throwCard(area.drawGame(context, game, width, height, i, num_discard, 2, nb_player) - 1, i)) 
				choice = 0;
			else 
				num_discard = game.getDiscardedPile().getNbCard() - 1;
		if(choice == 3)
			if(!game.giveClueGraph(context, i, area.drawGame(context, game, width, height, i, num_discard, 6, nb_player), area, width, height, game.getDiscardedPile().getNbCard() - 1, nb_player)) 
				choice = 0;
		int [] result  = {choice, num_discard};
		return result;
	}
	
	/**
	 * this is the main function for the graphic mode 
	 * @param end a boolean that become "true" when one of the required conditions are met
	 * @param choice represents the different choices made by the players during the game
	 * @param i the number of the player who has to play
	 */
	public static void mainGraph(boolean end, int choice, int i){
		Application.run(Color.BLACK, context -> {
			  int nb_player = 0, j = i;
			  Area area = new Area();
			  ScreenInfo screenInfo = context.getScreenInfo();
		      float width = (float) (screenInfo.getWidth());
		      float height = (float)(screenInfo.getHeight());
			  nb_player = area.drawNbPlayer(context, width, height);
			  SimpleGameData game = null;
			  try {game = new SimpleGameData(nb_player);} catch (IOException e) { e.printStackTrace();}
			  j = mainLoopGraph(context, game, end, choice, nb_player, j, width, height, area, -1);
			  if(game.getDeck().getNbCard() == 0) {
				  	area.waitNextPlayer(context, width, height, -1,(j+1)%(nb_player + 1));
					lastTurnGraph(context, game, end, choice, nb_player, j, width, height, area, game.getDiscardedPile().getNbCard() - 1);}
			  if(game.getLife() != 0) area.waitScore(context, width, height, game.scoreCalc());
			  else  area.waitScore(context, width, height, -1);
		      context.exit(0);
		      return;
		      });
		}
	
	/**
	 * this function is responsible for the last turn in terminal mode.
	 * @param game contains the informations of the current game
	 * @param entry allow the player to enter their choice in the terminal
	 * @param end a boolean that become "true" when one of the required conditions are met
	 * @param choice represents the different choices made by the players during the game
	 * @param nb_player the number of player
	 * @param i the number of the player who has to play
	 * @throws IOException when the entered value causes an error
	 */
	public static void lastTurnTerminal(SimpleGameData game, BufferedReader entry, boolean end, int choice, int nb_player, int i) throws IOException{
		int finaly = 0;
		while(!end) {
			new SimpleGameViewTerminal(game).printPlayer(i);
			choice = SimpleGameData.actionChoice(entry);
			if(choice == 1)
				if(!game.playCard(game.cardChoice(entry), i)) i = (i - 1) % nb_player;
			if(choice == 2)
				if(!game.throwCard(game.cardChoice(entry), i)) i = (i - 1) % nb_player;
			if(choice == 3)
				if(!game.giveClueTerminal(i, entry, nb_player)) i = (i - 1) % nb_player;
			i = (i+1) % nb_player;
			finaly += 1;
			if(game.getLife() == 0 || finaly == nb_player || game.scoreCalc() == 25)
				end = true;
			else { 
				System.out.println("\n\n\n\n\nPress enter to pass next player...\n\n\n\n\n");
				SimpleGameData.readJump(entry);}
			}
		}
	
	/**
	 * This is the main function of the terminal mode
	 * @throws IOException when the entered value causes an error
	 */
	public static void mainTerminal() throws IOException {
		boolean end = false; int i = 0; int choice = 0;
		BufferedReader entry = new BufferedReader(new InputStreamReader(System.in));
		int nb_player = SimpleGameData.nbPlayer(entry);
		SimpleGameData game = new SimpleGameData(nb_player);
		i = mainLoopTerminal(game, entry, end, choice, nb_player, i);
		if(game.getDeck().getNbCard() == 0) {
			System.out.println("\n\n\n\n\n\tLast turn(Press enter to pass next player)\n\n\n\n\n");
			SimpleGameData.readJump(entry);
			lastTurnTerminal(game, entry, false, choice, nb_player, i);
		}
		System.out.print("Board : [ ");
		for(i = 0; i < 5; i++) {
			System.out.print("(" + game.getBoard()[i].getColor() +" "+ game.getBoard()[i].getNum()+ ") ");
		}
		System.out.println("]\n\tGame Over , your score : " + game.scoreCalc());
		entry.close();
	}
	
	/**
	 * This is the main function. Make the player choose between the graphic and terminal version
	 * @param args the parameter of the main function
	 * @throws IOException when the entered value causes an error
	 */
	public static void main(String[] args) throws IOException {
		boolean end = false; int i = 0; int choice = -1;
		BufferedReader entry = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose 1 to play with the graphic version or 2 to play with the terminal version : ");
		while(choice != 1 && choice!= 2) {
			choice = SimpleGameData.readInt(entry);
		}
		if(choice == 1)
			mainGraph(end, choice, i);
		else
			mainTerminal();
	}
	
	
	
	
}
