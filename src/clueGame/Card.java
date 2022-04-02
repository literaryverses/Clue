package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String name) {
		cardName = name;
	}
	
	public void setType(String label) {
		System.out.println(label);
		cardType = CardType.valueOf(label);
	}
	
	public CardType getType() {
		return cardType;
	}
	
	public String getName() {
		return cardName;
	}
	
	public boolean equals(Card target) {
		if (this.cardName == target.getName() && this.cardType == target.getType()) {
			return true;
		}
		else {
			return false;
		}
	}
}
