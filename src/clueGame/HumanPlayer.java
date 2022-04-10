package clueGame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player{

	private ArrayList<Card> seenCards = new ArrayList<Card>();;
	
	public HumanPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public BoardCell selectTarget(Set<BoardCell> t, Map<Character,Room> roomMap, ArrayList<Card> deck) {
		return new BoardCell(0,0); // FIXME
	}
	
	public void seeCard(Card card) {
		seenCards.add(card);
	}
	
	public ArrayList<Card> getSeen() {
		return seenCards;
	}

}
