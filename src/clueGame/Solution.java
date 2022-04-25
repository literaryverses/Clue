package clueGame;

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution() {
	}
	
	/*
	 * Checks to make sure card type of given card is in solution
	 */
	public boolean hasCardType(Card card) { 
		//checks to see if the solution already has a card of the same type
		CardType type = card.getType();
		if (type==CardType.ROOM) {
			if (this.room == null) {
				return false;
			}
		}
		else if (type==CardType.PERSON) {
			if (this.person == null) {
				return false;
			}
		}
		else if (type==CardType.WEAPON) {
			if (this.weapon == null) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Adds card to solution
	 */
	public void add(Card card) {
		//adds a card to the solution and puts it into the right type
		CardType type = card.getType();
		if (type==CardType.ROOM) {
			this.room = card;
		}
		else if (type==CardType.PERSON) {
			this.person = card;
		}
		else if (type==CardType.WEAPON) {
			this.weapon = card;
		}
	}
	
	//Checks to see if solution has three cards
	public boolean isFull() {
		if (this.room == null || this.person == null || this.weapon == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public String toString() {
		return person.getName() + ", " + weapon.getName() + ", " + room.getName();
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
