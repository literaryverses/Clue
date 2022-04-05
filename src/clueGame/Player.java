package clueGame;

import java.util.*;

public abstract class Player {
	protected String name;
	private String color;
	protected int row, column;
	private static int nextColor;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name) {
		super();
		interpretColor();
		this.name = name;
	}
	
	abstract public BoardCell selectTarget(Set<BoardCell> t, Map<Character,Room> roomMap);
	
	public void seeCard(Card c) {
	}
	
	public void setRoomName(Map<Character,Room> roomMap, BoardCell[][] grid) {
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		for (Card c : hand) {
			if (c.equals(suggestion.getPerson())) {
				return c;
			}
			if (c.equals(suggestion.getWeapon())) {
				return c;
			}
			if (c.equals(suggestion.getRoom())) {
				return c;
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
	
	
	public void interpretColor() {
		switch(nextColor) {
		case 0:
			this.color = "Red";
			break;
		case 1:
			this.color = "Orange";
			break;
		case 2:
			this.color = "Yellow";
			break;
		case 3:
			this.color = "Green";
			break;
		case 4:
			this.color = "Blue";
			break;
		case 5:
			this.color = "Purple";
			break;
		}
		nextColor++;
	}
	
	public String getColor() {
		return this.color;
	}

	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	public void overrideHand(Card c1, Card c2, Card c3) {
		hand.clear();
		hand.add(c1);
		hand.add(c2);
		hand.add(c3);
	}
}
