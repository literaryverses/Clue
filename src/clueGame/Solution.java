package clueGame;

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution() {
	}
	
	public boolean hasCard(Card c) { 
		//checks to see if the solution already has a card of the same type
		CardType t = c.getType();
		if (t==CardType.ROOM) {
			if (this.room == null) {
				return false;
			}
		}
		else if (t==CardType.PERSON) {
			if (this.person == null) {
				return false;
			}
		}
		else if (t==CardType.WEAPON) {
			if (this.weapon == null) {
				return false;
			}
		}
		return true;
	}
	
	public void add(Card c) {
		//adds a card to the solution and puts it into the right type
		CardType t = c.getType();
		if (t==CardType.ROOM) {
			this.room = c;
		}
		else if (t==CardType.PERSON) {
			this.person = c;
		}
		else if (t==CardType.WEAPON) {
			this.weapon = c;
		} else {
			System.out.println("Something went wrong in dealing answer");
		}
	}
	
	public boolean isFull() {
		if (this.room == null || this.person == null || this.weapon == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public Card getRoom() {
		return room;
	}

	public Card getPerson() {
		return person;
	}

	public Card getWeapon() {
		return weapon;
	}

}
