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
}
