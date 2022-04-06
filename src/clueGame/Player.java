package clueGame;

import java.util.*;

public abstract class Player {
	protected String name;
	private String color;
	protected int row, column;
	private static int nextColor;
	protected ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name) {
		super();
		interpretColor();
		this.name = name;
	}
	
	abstract public BoardCell selectTarget(Set<BoardCell> t, Map<Character,Room> roomMap, ArrayList<Card> deck);
	
	public void seeCard(Card c) {
	}
	
	public void setRoomName(Map<Character,Room> roomMap, BoardCell[][] grid) {
	}
	
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
}
