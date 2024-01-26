package fr.umlv.hanabi;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.*;
import fr.umlv.zen5.*;
import fr.umlv.zen5.Event.*;

public class SimpleGameView {
	
	 static class Area {
		
		public enum Message {
			color, action, card, number, player
		}
		
		private Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, 0, 0);
	    private Rectangle2D.Float square = new Rectangle2D.Float(0, 0, 0, 0);
	    
	    /**
	     * Convert the color of the card for the game to understand it
	     * @param card a Card with a Color (in a enum) and a number
	     * @return Return a Color for understanding the drawing
	     */
	    public Color colorCard(CardData card) {
	    	if(card.getColor() == fr.umlv.hanabi.Color.red) {
	    		return Color.red;
	    	}
	    	else if(card.getColor() == fr.umlv.hanabi.Color.blue) {
	    		return Color.cyan;
	    	}
	    	else if(card.getColor() == fr.umlv.hanabi.Color.yellow) {
	    		return Color.yellow;
	    	}
	    	else if(card.getColor() == fr.umlv.hanabi.Color.green) {
	    		return Color.green;
	    	}
	    	return Color.white;
	    }
	    
	    /**
	     * Draw cards without hide anything
	     * @param context contains the informations of the current graphic window
	     * @param card a card with a Color and a number
	     * @param x reference for the horizontal position of the left top corner boxes 
	     * @param y reference for the vertical position of the left top corner boxes 
	     * @param color a boolean to say if the color of the card is known
	     * @param num a boolean to say if the number of the card is known
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawCard(ApplicationContext context, CardData card, float x, float y, boolean color, boolean num, float width, float height) {
	    	context.renderFrame(graphics -> {
		        graphics.setColor(Color.WHITE);
		        square = new Rectangle2D.Float((float)(x - 0.5*width/192),(float) (y - 0.5*height/105), 6*width/192 + width/192, 9*height/105 + height/105);
	    		graphics.fill(square);
		        graphics.setColor(colorCard(card));
		        square = new Rectangle2D.Float(x, y, 6*width/192, 9*height/105);
		        graphics.fill(square);
		        graphics.setColor(Color.BLACK);
		        graphics.setFont(new Font("Arial", Font.BOLD,(int) (4*(width+height)/(192 + 105))));
		        graphics.drawString(card.getNum() + "",(float)( x - 1.2*width/192 + (6*width/192)/2), y  + height/105 + (9*height/105)/2);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) ((width+height)/(192 + 105))));
		        if(color == true)
		        	graphics.drawString("color : " + Ok(color) , x + 3 , y - height/105 + 9*height/105);
		        if(num == true)
		        	graphics.drawString("num : " + Ok(num), x + 3 , y - 2 + 9*height/105);
	    	});
	    }
	    
	    /**
	     * Give the String "ok" to display if its true
	     * @param bool true or false
	     * @return String "ok" or ""
	     */
	    public String Ok(boolean bool) {
	    	if(bool)
	    		return "ok";
	    	else
	    		return "";
	    }
	    
	    /**
	     * draw Cards with the information the player has 
	     * @param context contains the informations of the current graphic window
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     * @param color a boolean to verify if the color of the card is known
	     * @param num a boolean to verify if the number of the card is known
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawCardUnknown(ApplicationContext context, float x, float y, Color color, String num, float width, float height) {
	    	context.renderFrame(graphics -> {	
		        graphics.setColor(Color.WHITE);
		        square = new Rectangle2D.Float((float)(x - 0.5*width/192), (float) (y - 0.5*height/105), 6*width/192 + width/192, 9*height/105 + height/105);
	    		graphics.fill(square);
		        graphics.setColor(color);
		        square = new Rectangle2D.Float(x, y, 6*width/192, 9*height/105);
		        graphics.fill(square);
		        graphics.setColor(Color.BLACK);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) (4*(width+height)/(192 + 105))));
		        graphics.drawString(num ,(float) (x - 1.2 * width/192 + (6*width/192)/2), y  + height/105 + (9*height/105)/2);
	    	});
	    }
	    
	    /**
	     * draw the Hands of the current player with the information he has
	     * @param context contains the informations of the current graphic window
	     * @param hand the player's hand to draw 
	     * @param i give the location to draw the hand
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     */
	    public void drawHandUnknown(ApplicationContext context, HandData hand, int i, float width, float height, float x, float y) {
	    	int j = 0;
	    	String num = "?";
	    	Color color = Color.GRAY;
	    	for(CardData card : hand.getHand()) {
	    		if(hand.getInfoColor().get(j) == true)	color = colorCard(card);
        		if(hand.getInfoNum().get(j) == true)	num = card.getNum() + "";
		    	if(i == 0 || i == 3 || i == 4) {   
		    		if(i == 4)	drawCardUnknown(context, x, (float) (height/10 + (i*(height* (8./10))/4)), color, num, width, height);
		    		else		drawCardUnknown(context, x, (float) (height/10 + (i*(height* (8./10))/3)), color, num, width, height);
		    		if(i == 0) 	{x += width / 10;}
	        		else		{x += width / 20;}	}
	        	else if(i == 1 || i == 2) {
		        		drawCardUnknown(context, (float) ( width/10 + (i-1)*(width* (8./10)))  , y, color, num, width, height);
		        		y += height / 8;
		        }
		    	j++;
		    	color = Color.GRAY;
		    	num = "?";	}
	    }
	    
	    /**
	     * draw Hands of the other players
	     * @param context contains the informations of the current graphic window
	     * @param hand the player's hand to draw 
	     * @param i give the location to draw the hand
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     */
	    public void drawHand(ApplicationContext context, HandData hand, int i, float width, float height, float x, float y) {
	    	int j = 0;
	    	if(i == 0 || i == 3 || i == 4) {
	        	for(CardData card : hand.getHand()) {
	        		if(i == 4)
	        			drawCard(context, card, x, (float) (height/10 + (i*(height* (8./10))/4)), hand.getInfoColor().get(j), hand.getInfoNum().get(j), width, height);
	        		else
	        			drawCard(context, card, x, (float) (height/10 + (i*(height* (8./10))/3)), hand.getInfoColor().get(j), hand.getInfoNum().get(j), width, height);
	        		if(i == 0) {x += width / 10;}
	        		else{x += width / 20;}
	        		j++;
	        	}
        	}
        	else if(i == 1 || i == 2) {
	        	for(CardData card : hand.getHand()) {
	        		drawCard(context, card, (float) (width/10 + (i-1)*(width* (8./10)))  , y, hand.getInfoColor().get(j), hand.getInfoNum().get(j), width, height);
	        		y += height / 8;
	        		j++;
	        	}}
	    }
	    
	    /**
	     * draw the board of the game
	     * @param context contains the informations of the current graphic window
	     * @param board An array of card for the objective of the game
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawBoard(ApplicationContext context, CardData[] board, float width, float height) {
	    	float j = 11*width/192;
	    	for(CardData card: board) {
	    		drawCard(context, card, j + width/3, (height/2) - (height/8), false, false, width, height);
	    		j += 10*width/192;
	    	}
	    }
	    
	    /**
	     * draw the number of life
	     * @param context contains the informations of the current graphic window
	     * @param life an int for the number of life remaining
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawLife(ApplicationContext context, int life, float width, float height) {
	    	
	    	context.renderFrame(graphics -> {	
		        graphics.setColor(Color.RED);
		        ellipse = new Ellipse2D.Float(20*width/192 + width/3, height/2 , 4* width/192, 4* height/105);
	    		graphics.fill(ellipse);
	    		graphics.setColor(Color.white);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) (4*(width+height)/(192 + 105))));
		        graphics.drawString(life + "" , 21* width/192 + width/3,(float) (height/2 + 3.5 * height/105));
	    	});
	    }
	    
	    /**
	     * draw the number of clue
	     * @param context contains the informations of the current graphic window
	     * @param clue an int for the number of clue remaining
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawClue(ApplicationContext context, int clue, float width, float height) {
	    	context.renderFrame(graphics -> {	
		        graphics.setColor(Color.BLUE);
		        ellipse = new Ellipse2D.Float(40*width/192 + width/3, height/2 , 4*width/192, 4*height/105);
	    		graphics.fill(ellipse);
	    		graphics.setColor(Color.white);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) (4*(width+height)/(192 + 105))));
		        graphics.drawString(clue + "" , 41*width/192 + width/3, (float) (height/2 + 3.5 * height/105));
	    	});
	    }
	    
	    /**
	     * draw the deck
	     * @param context contains the informations of the current graphic window
	     * @param deck The deck of the game
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawDeck(ApplicationContext context, DeckData deck, float width, float height){
	    	context.renderFrame(graphics -> {
	    		float w = width/4;
		        graphics.setColor(Color.WHITE);
		        square = new Rectangle2D.Float(w - width/192, (height/2) - 11*height/105, 6*width/192 + 6*width/192, 9*height/105 + 8*height/105);
	    		graphics.fill(square);
		        graphics.setColor(Color.GRAY);
		        square = new Rectangle2D.Float(w, (height/2) - 10*height/105, 6*width/192 + 4*width/192, 9*height/105 + 6*height/105);
		        graphics.fill(square);
		        graphics.setColor(Color.BLACK);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
		        graphics.drawString(deck.getNbCard() + "" , w + (6*width/192 + 4*width/192)/3 - 10 , (height/2 - 10*height/105) + (9*height/105 + 6*height/105)/2 + height/105);
	    	});
	    }
	    
	    /**
	     * draw the discarded pile. Calls an annex function
	     * @param context contains the informations of the current graphic window
	     * @param deck The discard of the game
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param num_discard An int for indicates the card to draw on the discard
	     */
	    public void drawDiscard(ApplicationContext context, DeckData deck, float width, float height, int num_discard) {
	    	context.renderFrame(graphics -> {
	    		float w = 3*width/4;
	    		String str = "";
		        graphics.setColor(Color.WHITE);
		        square = new Rectangle2D.Float(w - width/192, (height/2) - 11*height/105, 6*width/192 + 6*width/192, 9*height/105 + 8*height/105);
	    		graphics.fill(square);
	    		if(num_discard != -1) {
	    			graphics.setColor(colorCard(deck.getDeck().get(num_discard)));
	    			str = deck.getDeck().get(num_discard).getNum() + "";
	    			graphics.setFont(new Font("Arial", Font.BOLD, (int) ((width+height)/(192 + 105))));}
	    		else {
	    			graphics.setColor(Color.GRAY);
	    			str = deck.getNbCard() + "";
	    			graphics.setFont(new Font("Arial", Font.BOLD, 0));}
			    drawDiscardAnnex(graphics, width, height, num_discard, deck, str);
	    	});	
	    }
	    
	    /**
	     * the annex function of drawDiscard
	     * @param graphics A Graphics2D for draw
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param num_discard An int for indicates the card to draw on the discard
	     * @param deck deck The discard of the game
	     * @param str A String to write
	     */
	    public void drawDiscardAnnex(Graphics2D graphics, float width, float height, int num_discard, DeckData deck, String str){
	    	float w = 3*width/4;
	    	square = new Rectangle2D.Float(w, (height/2) - 10*height/105, 6*width/192 + 4*width/192, 9*height/105 + 6*height/105);
		    graphics.fill(square);
		    graphics.setColor(Color.BLACK);
		    graphics.drawString("num_card = " + (num_discard + 1), w + 2*width/192, (height/2 - 10*height/105) + (9*height/105 + 6*height/105) - 2*height/105);
		    graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
		    graphics.drawString(str , w + (6*width/192 + 4*width/192)/3 , (height/2 - 10*height/105) + (9*height/105 + 6*height/105)/2 + height/105);
		    graphics.setFont(new Font("Arial", Font.BOLD, (int) ((width+height)/(192 + 105))));
		    graphics.drawString("nb_card = " + deck.getNbCard(), w + 2*width/192, (height/2 - 10*height/105) + (9*height/105 + 6*height/105) - 5);
	    }
	    
	    /**
	     * draw the player's number next to its hand
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param nb_player An int to indicate the number of player
	     */
	    public void drawName(ApplicationContext context, float width, float height, int nb_player) {
	    	context.renderFrame(graphics -> {	
	    		graphics.setColor(Color.WHITE);
			    graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
			    graphics.drawString("P1" , -2*width/192  + width/2, height/10);
			    graphics.drawString("P2" , -8*width/192 + width/10, height/2);
			    if(nb_player >= 3)
			    	graphics.drawString("P3" , 10*width/192 + 9*width/10, height/2);
			    if(nb_player >= 4)
			    	graphics.drawString("P4" , 9*width/64, 5*height/105 + 9*height/10);
			    if(nb_player >= 5)
			    	graphics.drawString("P5" , 13*width/16, 5*height/105 + 9*height/10);
	    	});
	    }
	    
	    /**
	     * draw the number of a card next to it (for horizontal ones)
	     * @param context contains the informations of the current graphic window
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param nb_player An int to indicate the number of player
	     * @param i give the location to display messages
	     */
	    public void drawNameCardHorizontal(ApplicationContext context, float x, float y, float width, float height, int nb_player, int i) {
	    	context.renderFrame(graphics -> {
	    		float w;
	    		float w_add;
	    		if(i == 0) {w = width / 10; w_add = width/10;}
        		else{w = width / 20; w_add = width/20;}
	    		graphics.setColor(Color.WHITE);
			    graphics.setFont(new Font("Arial", Font.BOLD, (int) ((width+height)/(192 + 105))));
			    graphics.drawString("Card 1" , x + w_add/8, y);
			    graphics.drawString("Card 2" , x + w + w_add/8, y);
			    w += w_add;
			    graphics.drawString("Card 3" , x + w + w_add/8, y);
			    w += w_add;
			    graphics.drawString("Card 4" , x + w + w_add/8, y);
			    w += w_add;
			    if(nb_player < 4)
				    graphics.drawString("Card 5" , x + w + w_add/8, y);
	    	});
	    }
	    
	    /**
	     * draw the number of a card next to it (for vertical ones)
	     * @param context contains the informations of the current graphic window
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param nb_player An int to indicate the number of player
	     */
	    public void drawNameCardVertical(ApplicationContext context, float x, float y, float width, float height, int nb_player) {
	    	context.renderFrame(graphics -> {
	    		float h = height/8;
	    		float h_add = height/8;
	    		graphics.setColor(Color.WHITE);
			    graphics.setFont(new Font("Arial", Font.BOLD, (int) ((width+height)/(192 + 105))));
			    graphics.drawString("Card 1" , x, h_add/2 + y);
			    graphics.drawString("Card 2" , x, h + h_add/2 + y);
			    h += h_add;
			    graphics.drawString("Card 3" , x, h + h_add/2 + y);
			    h += h_add;
			    graphics.drawString("Card 4" , x, h + h_add/2 + y);
			    h += h_add;
			    if(nb_player < 4)
				    graphics.drawString("Card 5" , x, h + h_add/2 + y);
	    	});
	    }
	    
	    /**
	     * the primary function to draw cards' name by calling other functions
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param nb_player An int to indicate the number of player
	     */
	    public void drawNameCard(ApplicationContext context, float width, float height, int nb_player) {
	    	context.renderFrame(graphics -> {	
	    		for(int i = 0; i < nb_player; i++) {
	    			if(i == 0)
	    				drawNameCardHorizontal(context, width/3, (-height)/25 + height/10, width, height, nb_player, i);
	    			else if(i == 3 )
	    				drawNameCardHorizontal(context, width/5, (-height)/25 + 9*height/10, width, height, nb_player, i);
	    			else if(i == 4)
	    				drawNameCardHorizontal(context, 3*width/5, (-height)/25 + 9*height/10, width, height, nb_player, i);
	    			else if(i == 1)
	    				drawNameCardVertical(context, (-width)/20 + width/10, height/4, width, height, nb_player);
	    			else if(i == 2)
	    				drawNameCardVertical(context, width/20 + 9*width/10, height/4, width, height, nb_player);
	    		}
	    	});
	    }
	    
	    /**
	     * draw all the game's information on the window by calling other functions
	     * @param context contains the informations of the current graphic window
	     * @param game The information on the game
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param num_discard An int for indicates the card to draw on the discard
	     * @param nb_player An int to indicate the number of player
	     */
	    public void drawInfo(ApplicationContext context, SimpleGameData game, float width, float height, int num_discard, int nb_player) {
	    	drawLife(context, game.getLife(), width, height);
	    	drawClue(context, game.getClue(), width, height);
	    	drawDeck(context, game.getDeck(), width, height);
	    	drawDiscard(context, game.getDiscardedPile(), width, height, num_discard);
	    	drawName(context, width, height, nb_player);
	    	drawNameCard(context, width, height, nb_player);
	    	
	    }
	    
	    /**
	     * draw the color when making a choice about giving a clue on the color 
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawChoiceColor(ApplicationContext context, float width, float height) {
	    	context.renderFrame(graphics -> {
	    		float w = width/4;
		        graphics.setColor(Color.RED);
		        square = new Rectangle2D.Float(w + width/192, -9*height/105 + 3*height/4, (10*width/192 + width/2)/5 - width/192, 13*height/105);
	    		graphics.fill(square);
	    		graphics.setColor(Color.CYAN);
		        square = new Rectangle2D.Float(w + width/192 + (10*width/192 + width/2)/5, -9*height/105 + 3*height/4, (10*width/192 + width/2)/5 - width/192, 13*height/105);
	    		graphics.fill(square);
	    		graphics.setColor(Color.GREEN);
		        square = new Rectangle2D.Float(w + width/192 + 2*(10*width/192 + width/2)/5, -9*height/105 + 3*height/4, (10*width/192 + width/2)/5 - width/192, 13*height/105);
	    		graphics.fill(square);
	    		graphics.setColor(Color.YELLOW);
		        square = new Rectangle2D.Float(w + width/192 + 3*(10*width/192 + width/2)/5, -9*height/105 + 3*height/4, (10*width/192 + width/2)/5 - width/192, 13*height/105);
	    		graphics.fill(square);
	    		graphics.setColor(Color.WHITE);
		        square = new Rectangle2D.Float(w + width/192 + 4*(10*width/192 + width/2)/5, -9*height/105 + 3*height/4, (10*width/192 + width/2)/5 - 2*width/192, 13*height/105);
	    		graphics.fill(square);
	    	});
	    }
	    
	    /**
	     * draw a number inside a rectangle
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param num A String of the number to display
	     * @param i give the location to display messages
	     */
	    public void drawRectNum(ApplicationContext context, float width, float height, String num, int i) {
	    	context.renderFrame(graphics -> {
	    		float w = width/4;
	    		graphics.setColor(Color.BLACK);
	    		graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
	    		graphics.drawRect((int) (w + width/192 + i*(10*width/192 + width/2)/5), (int)(-9*height/105 + 3*height/4), (int)((10*width/192 + width/2)/5 - 2*width/192),(int) (13*height/105));
	    		graphics.drawString(num, w + i*(10*width/192 + width/2)/5 + (10*width/192 + width/2)/10 - 10,(float) ((-9*height/105 + 3*height/4) + 7.5*height/105));
	    	});
	    }
	    
	    /**
	     * draw a rectangle to write a number inside
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param action An int to indicates the chosen action 
	     * @param nb_player An int to indicate the number of player
	     */
	    public void drawChoiceNum(ApplicationContext context, float width, float height, int action, int nb_player) {
	    	if(action != 5)
	    		drawRectNum(context, width, height, "1", 0);
	    	if((action == 2 && nb_player <= 3) || action == 5 || action == 3 || (action == 4 && nb_player > 4))
	    		drawRectNum(context, width, height, "5", 4);
	    	if((action == 4 && nb_player > 2) || action != 4)	
	    		drawRectNum(context, width, height, "3", 2);
	    	if((action == 4 && nb_player > 3) || action != 4)	
	    		drawRectNum(context, width, height, "4", 3);
	    	drawRectNum(context, width, height, "2", 1);
	    }
	    
	    /**
	     * draw the different action a player can choose
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawChoiceAction(ApplicationContext context, float width, float height) {
	    	context.renderFrame(graphics -> {
	    		float w = width/4;
	    		graphics.setColor(Color.BLACK);
	    		graphics.setFont(new Font("Arial", Font.BOLD, (int) (4.5*(width+height)/(192 + 105))));
	    		graphics.drawRect((int)(w + width/192), (int)(-9*height/105 + 3*height/4), (int)((10*width/192 + width/2)/3 - width/192),(int) (13*height/105));
		        graphics.drawString("Play a card", w + (10*width/192 + width/2)/18 - width/192, (float)((-9*height/105 + 3*height/4) + 7.5 * height/105));
	    		graphics.drawRect((int)(w + width/192 + (10*width/192 + width/2)/3), (int)(-9*height/105 + 3*height/4), (int)((10*width/192 + width/2)/3 - width/192),(int) (13*height/105));
	    		graphics.drawString("Throw a card",(float) (w  + (10*width/192 + width/2)/3 + (10*width/192 + width/2)/18 - 3.5*width/192),(float) ((-9*height/105 + 3*height/4) + 7.5*height/105));
	    		graphics.drawRect((int)(w + width/192 + 2*(10*width/192 + width/2)/3), (int)(-9*height/105 + 3*height/4), (int)((10*width/192 + width/2)/3 - 2*width/192),(int) (13*height/105));
	    		graphics.drawString("Give a clue", w  + 2*(10*width/192 + width/2)/3 + (10*width/192 + width/2)/18 - width/192,(float) ((-9*height/105 + 3*height/4) + 7.5*height/105));
	    	});
	    }
	    
	    /**
	     * draw the rectangle and strings to choose to give a clue about color or number
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawChoiceBetween(ApplicationContext context, float width, float height) {
	    	context.renderFrame(graphics -> {
	    		float w = width/4;
	    		graphics.setColor(Color.BLACK);
	    		graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
	    		graphics.drawRect((int)(w + width/12 - width/192), (int)(-9*height/105 + 3*height/4), (int)((10*width/192 + width/2)/3 - width/192),(int) (13*height/105));
		        graphics.drawString("Color", w + 5*width/48 + (10*width/192 + width/2)/18 - width/192,(float) ((-9*height/105 + 3*height/4) + 7.5*height/105));
	    		graphics.drawRect((int)(w + width/8 + 10 + (10*width/192 + width/2)/3), (int)(-9*height/105 + 3*height/4), (int)((10*width/192 + width/2)/3 - 10),(int) (13*height/105));
	    		graphics.drawString("Num",(float) (w  + 17*width/96 + (10*width/192 + width/2)/3 + (10*width/192 + width/2)/18 - 3.5*width/192),(float) ((-9*height/105 + 3*height/4) + 7.5*height/105));
	    	});
	    }
	    
	    /**
	     * draw the different choices a player must take after choosing an action
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param action An int to indicates the chosen action 
	     * @param nb_player An int to indicate the number of player
	     */
	    public void drawAction(ApplicationContext context, float width, float height, int action, int nb_player){
	    	context.renderFrame(graphics -> {	
		        graphics.setColor(Color.GRAY);
		        square = new Rectangle2D.Float(width/4, -10*height/105 + 3*height/4, 10*width/192 + width/2, 15*height/105);
	    		graphics.fill(square);
	    		graphics.setColor(Color.BLACK);
		        square = new Rectangle2D.Float(width/4, -15*height/105 + 3*height/4, 10*width/192 + width/2, 5*height/105);
	    		graphics.fill(square);
	    		graphics.setFont(new Font("Arial", Font.BOLD, (int) (3*(width+height)/(192 + 105))));
    			graphics.setColor(Color.WHITE);
    			if(action == 5)
    				graphics.drawString("Select the number of " + Message.values()[4], -10*width/192  + width/2 , -12*height/105 + 3*height/4);
    			else if(action == 6)
    				graphics.drawString("Select between " + Message.values()[0] + " and " + Message.values()[3], -16*width/192  + width/2 , -12*height/105 + 3*height/4);
    			else
    				graphics.drawString("Select the " + Message.values()[action], -2*width/192 + width/2 , -12*height/105 + 3*height/4);
	    	});
	    	drawActionAnnex(context, width, height, action, nb_player);
	    }
	    
	    /**
	     * an annex to the function drawAction
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param action An int to indicates the chosen action 
	     * @param nb_player An int to indicate the number of player
	     */
	    public void drawActionAnnex(ApplicationContext context, float width, float height, int action, int nb_player) {
	    	if(action == 0)
        		drawChoiceColor(context, width, height);
        	if(action == 2 || action == 3 || action == 4 || action == 5)
        		drawChoiceNum(context, width, height, action, nb_player);
        	if(action == 1)
        		drawChoiceAction(context, width, height);
        	if(action == 6)
        		drawChoiceBetween(context, width, height);
        	if(action != 5)
        		drawBack(context, width, height);
	    }
	    
	    /**
	     * draw the rectangle the player can click on to restart its turn
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawBack(ApplicationContext context, float width, float height) {
	    	context.renderFrame(graphics -> {	
		        graphics.setColor(Color.ORANGE);
		        square = new Rectangle2D.Float(width - width/10, 0, width/10, height/10);
	    		graphics.fill(square);
    			graphics.setColor(Color.BLACK);
    			graphics.setFont(new Font("Arial", Font.BOLD, (int) (2*(width+height)/(192 + 105))));
    			graphics.drawString("Restart", width - width/13, height/30);
    			graphics.drawString("Your Turn", width - width/13, 2*height/30);
	    	});
	    }
	    
	    /**
	     * draw the number of player for the game
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @return Give the number of player for the game
	     */
	    public int drawNbPlayer(ApplicationContext context, float width, float height){
	    	Point2D point;
	    	drawAction(context, width, height, 5, 0);
	    	int nb_player = 0;
	    	while(nb_player < 2 || nb_player > 5) {
	    		point = waitPoint(context);
	    		nb_player = detect(context, width, height, (float) point.getX(), (float) point.getY());
	    	}
	    	context.renderFrame(graphics -> {
		    	graphics.setColor(Color.BLACK);
		        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
	    	});
	    	return nb_player;
	    }
	    
	    /**
	     * this function calls all the other display function
	     * @param context contains the informations of the current graphic window
	     * @param game contains the informations of the current game
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param player An int to indicate the current player
	     * @param num_discard An int for indicates the card to draw on the discard
	     * @param action An int to indicates the chosen action 
	     * @param nb_player An int to indicate the number of player
	     * @return Give the player's choice
	     */
	    public int drawGame(ApplicationContext context, SimpleGameData game, float width, float height, int player, int num_discard, int action, int nb_player) {
    		int i = 0;
    		float x = width/3;
    		float y = height/4;
    		drawBoard(context, game.getBoard(), width, height);
        	drawInfo(context, game, width, height, num_discard, nb_player);
        	drawAction(context, width, height, action, nb_player);
	        for(HandData hand : game.getHands()) {
	        	if(i == 3) {x = width/5;}
	        	if(i == 4) {x = 3*width/5;}
	        	if(i == player)
	        		drawHandUnknown(context, hand, i, width, height, x, y);
	        	else
	        		drawHand(context, hand, i, width, height, x, y);
	        	x = width/3;
	        	y = height/4;
	        	i++;
	        }
	        return clickChoice(context, width, height, action, nb_player);
	    }
	    
	    /**
	     * draw the score of the group
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param score An int to indicate the score to display
	     */
	    public void drawScore(ApplicationContext context, float width, float height, int score) {
	    	context.renderFrame(graphics -> {
		        graphics.setColor(Color.BLACK);
		        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
		        graphics.setColor(Color.WHITE);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
		        graphics.drawString("Your score : ", (width/2)-(width/10), height/2);
		        graphics.drawString(score + "", (width/2)-(width/10), 10*height/105  + height/2);
	    	});
	    }
	    
	    /**
	     * draw when the game is lost
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     */
	    public void drawLose(ApplicationContext context, float width, float height) {
	    	context.renderFrame(graphics -> {
		        graphics.setColor(Color.BLACK);
		        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
		        graphics.setColor(Color.WHITE);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
		        graphics.drawString("You Lose ", (width/2)-(width/10), height/2);
		        graphics.drawString("Game Over", (width/2)-(width/10), 10*height/105  + height/2);
	    	});
	    }
	    
	    /**
	     * look into the score value to call the corresponding function and wait for a click of the mouse
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param score An int to indicate the score to display
	     */
		public void waitScore(ApplicationContext context, float width, float height, int score){
	    	Event event = null;
			Action action = null;
			if(score == -1)
				drawLose(context, width, height);
			else
				drawScore(context, width, height, score);      
	        while (event == null || action == Action.KEY_PRESSED || action == Action.KEY_RELEASED || action == Action.POINTER_UP || action == Action.POINTER_MOVE) {
		        event = context.pollOrWaitEvent(1000000000);
		        action = event.getAction();
		    }
	    }
	    
	    /**
	     * wait for a click of the mouse on one of the choices
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param action An int to indicates the chosen action 
	     * @param nb_player An int to indicate the number of player
	     * @return Give the player's choice
	     */
		public int clickChoice(ApplicationContext context, float width, float height, int action, int nb_player){
	    	Point2D point; 
	    	int i = -1;
	        while(i == -1) {
	        	point = waitPoint(context);
	        	i = clickAnnex(context, width, height, action, nb_player, i, point);
	        }
	        return i;
	     }
	    
	    /**
	     * an annex function for clickChoice
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param action An int to indicates the chosen action 
	     * @param nb_player An int to indicate the number of player
	     * @param i An int to indicate the player'choice
	     * @param point the coordinates of the player's click
	     * @return Give the player's choice
	     */
		public int clickAnnex(ApplicationContext context, float width, float height, int action, int nb_player, int i, Point2D point){
	    	if(action == 6)
        		i = detectBetween(context, width, height, (float)point.getX(), (float)point.getY());
        	else if(action == 0 || action >= 2) {
	        	i = detect(context, width, height, (float)point.getX(), (float)point.getY());
	        	if(i != -1)
	        		if((i > nb_player && action == 4) || (i == 5 && action == 2 && nb_player > 3))
	        			i = -1;
	        }
	        else
	        	i = detectAction(context, width, height, (float)point.getX(), (float)point.getY());
	    	return i;
	    }
	    
	    /**
	     * wait for a click of the mouse
	     * @param context contains the informations of the current graphic window
	     * @return Give the coordinates of the player's click
	     */
		public Point2D waitPoint(ApplicationContext context) {
	    	Point2D point;
	    	Event event = null;
	        Action action = null;        
        	while (event == null || action == Action.KEY_PRESSED || action == Action.KEY_RELEASED || action == Action.POINTER_UP || action == Action.POINTER_MOVE) {
	        	event = context.pollOrWaitEvent(1000000000);
	        	action = event.getAction();
	        }
        	point = event.getLocation();
        	return point;
	        
        }
	    
	    /**
	     * wait for a click when restarting your turn or going to the next player
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param again An int to indicates the messages to display
	     * @param player An int to indicates the next player
	     */
		public void waitNextPlayer(ApplicationContext context, float width, float height, int again, int player) {
	    	Event event = null;
	        Action action = null;
	        context.renderFrame(graphics -> {
		        graphics.setColor(Color.BLACK);
		        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
		        graphics.setColor(Color.WHITE);
		        graphics.setFont(new Font("Arial", Font.BOLD, (int) (5*(width+height)/(192 + 105))));
		        if(again == -1)	graphics.drawString("Last turn!", (width/2)-(width/10), height/2);
		        else if(again == 0)	graphics.drawString("Restart your turn...", (width/2)-(width/10), height/2);
		        else	graphics.drawString("Next Player P" + player + "!", (width/2)-(width/10), height/2);
		    });
        	while (event == null || action == Action.KEY_PRESSED || action == Action.KEY_RELEASED || action == Action.POINTER_UP || action == Action.POINTER_MOVE) {
	        	event = context.pollOrWaitEvent(1000000000);
	        	action = event.getAction();}
        	context.renderFrame(graphics -> {
		        graphics.setColor(Color.BLACK);
		        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
		    });       
        }
	    
	    /**
	     * detect which choice for a card (a clue or playing a card) has been clicked on and return its value
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     * @return Give the choice of the player's click
	     */
		public int detect(ApplicationContext context, float width, float height, float x, float y){
	    	float w = width/4;
	    	for(int i = 0; i < 5; i++) {
	    		if(x >= (w + width/192 + i*(10*width/192 + width/2)/5) && x <= (w + width/192  + i*(10*width/192  + width/2)/5) + ((10*width/192 + width/2)/5 - width/192) &&
	    		   y >= (-9*height/105 + 3*height/4) && y <= (-9*height/105  + 3*height/4) + 13*height/105 ){
	    			return i + 1;
	    		}
	    	}
	    	if(x >= width - width/10 && y <= height/10)
	    		return 0;
	    	return -1;
	    }
	    
	    /**
	     * detect which action has been clicked on and return its value
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     * @return Give the choice of the player's click
	     */
		public int detectAction(ApplicationContext context, float width, float height, float x, float y){
	    	float w = width/4;
	    	for(int i = 0; i < 3; i++) {
	    		if(x >= (w + width/192 + i*(10*width/192 + width/2)/3) && x <= (w + width/192 + i*(10*width/192 + width/2)/3) + ((10*width/192 + width/2)/3 - width/192) &&
	    		   y >= (-9*height/105 + 3*height/4) && y <= (-9*height/105 + 3*height/4) + 13*height/105){
	    			return i + 1;
	    		}
	    	}
	    	if(x >= (3*w - width/192) && x <= (3*w + 11*width/192) &&
	 	    	   y >= ((height/2) - 11*height/105) && y <= ((height/2) - 11*height/105) + 17*height/105 ){
	 	    			return 7;
	 	    }
	    	if(x >= width - width/10 && y <= height/10)
	    		return 0;
	    	return -1;
	    }
	    
	    /**
	     * detect which choice has been clicked on and return its value
	     * @param context contains the informations of the current graphic window
	     * @param width the width of the game screen
	     * @param height the height of the game screen
	     * @param x indication for the horizontal position of the left top corner boxes 
	     * @param y indication for the vertical position of the left top corner boxes 
	     * @return Give the choice of the player's click
	     */
		public int detectBetween(ApplicationContext context, float width, float height, float x, float y) {
		   float w = width/4;
		   if(x >= (w + width/12 + 0*(10*width/192 + width/2)/3) && x <= (w + width/12 + width/192 + 0*(10*width/192 + width/2)/3) + ((10*width/192 + width/2)/3 - width/192) &&
    		   y >= (-9*height/105 + 3*height/4) && y <= (-9*height/105 + 3*height/4) + 13*height/105){
    			return 1;
		   }
		   else if(x >= (w + width/8 + 1*(10*width/192 + width/2)/3) && x <= (w + width/8 + width/192 + 1*(10*width/192 + width/2)/3) + ((10*width/192 + width/2)/3 - width/192) &&
 	    		   y >= (-9*height/105 + 3*height/4) && y <= (-9*height/105 + 3*height/4) + 13*height/105){
 	    		return 2;
		   }
		   else if(x >= width - width/10 && y <= height/10)
	    		return 0;
		   return -1;
	   }
	}
}
