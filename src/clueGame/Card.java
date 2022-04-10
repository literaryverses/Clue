package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String name) {
		cardName = name;
	}
	
	public Card(String name, CardType type) {
		cardName = name;
		cardType = type;
	}
	
	public void setType(String label) {
		cardType = CardType.valueOf(label);
	}
	
	public CardType getType() {
		return cardType;
	}
	
	public String getName() {
		return cardName;
	}
	
	/*
	 * checks if given card is equal to instance card
	 */
	public boolean equals(Card target) {
		if (this.cardName == target.getName() && this.cardType == target.getType()) {
			return true;
		}
		else {
			return false;
		}
	}
}
