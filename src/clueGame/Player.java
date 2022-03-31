package clueGame;

import java.util.*;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	private static int nextColor;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name) {
		super();
		interpretColor();
		this.name = name;
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
		return color;
	}

	public void updateHand(Card card) { //FIXME
		hand.add(card);
	}
}
