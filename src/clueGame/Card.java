package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String name) {
		cardName = name;
	}
	
	public void setType(String label) {
		cardType.valueOf(label);
	}
	
	public CardType getType() {
		return cardType;
	}
	
	public boolean equals(Card Target) {
		return true; //FIXME
	}
}
