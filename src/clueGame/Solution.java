package clueGame;

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution() {
	}
	
	public boolean hasCard(Card c) {
		CardType t = c.getType();
		if (t==CardType.ROOM) {
			System.out.println("Checking Room");
			if (this.room == null) {
				return false;
			}
		}
		else if (t==CardType.PERSON) {
			System.out.println("Checking Person");
			if (this.person == null) {
				return false;
			}
		}
		else if (t==CardType.WEAPON) {
			System.out.println("Checking Weapon");
			if (this.weapon == null) {
				return false;
			}
		}
		return true;
	}
	
	public void add(Card c) {
		System.out.println("Adding a card to solution");
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
			System.out.println("The card isnt full");
			return false;
		}
		else {
			System.out.println("The card is full");
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
