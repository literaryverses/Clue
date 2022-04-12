package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
	protected String name;
	private Color color;
	protected int row, column;
	private static int nextColor;
	protected ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name) {
		super();
		interpretColor();
		this.name = name;
	}
	
	public void draw(Graphics g, int cellWidth, int cellHeight) {
		int xPos = column*cellWidth;
    	int yPos = row*cellHeight;
    	
    	g.setColor(color);
		g.fillOval(xPos, yPos, cellWidth, cellHeight);
		g.setColor(Color.BLACK);
		g.drawOval(xPos, yPos, cellWidth, cellHeight);
	}
	
	abstract public BoardCell selectTarget(Set<BoardCell> targets, Map<Character,Room> roomMap,
			ArrayList<Card> deck);
	
	public void setRoomName(Map<Character,Room> roomMap, BoardCell[][] grid) {
	}
	
	public void seeCard(Card card) {
	}
	
	/*
	 * Disproves suggestion based on player cards
	 */
	public Card disproveSuggestion(Solution suggestion) {
		for (Card card : hand) {
			if (card.equals(suggestion.getPerson())&&!card.getName().equals(this.name)) {
				return card;
			}
			if (card.equals(suggestion.getWeapon())) {
				return card;
			}
			if (card.equals(suggestion.getRoom())) {
				return card;
			}
		}
		
		return null;
	}
	
	public void setPlace(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
	
	/*
	 * Chooses the player color
	 */
	public void interpretColor() {
		switch(nextColor) {
		case 0:
			this.color = Color.RED;
			break;
		case 1:
			this.color = Color.CYAN;
			break;
		case 2:
			this.color = Color.ORANGE;
			break;
		case 3:
			this.color = Color.GREEN;
			break;
		case 4:
			this.color = Color.BLUE;
			break;
		case 5:
			this.color = Color.MAGENTA;
			break;
		}
		nextColor++;
	}
	
	public Color getColor() {
		return this.color;
	}

	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getHand() {
		return this.hand;
	}
}
