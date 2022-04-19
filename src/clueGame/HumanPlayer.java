package clueGame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player{

	public HumanPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Solution createSuggestion(ArrayList<Card> deck) {
		return null; //TODO
	}
	
	public BoardCell selectTarget(Set<BoardCell> t, Map<Character,Room> roomMap, ArrayList<Card> deck) {
		return new BoardCell(0,0); // TODO
	}
	
	
	//Keeps track of the current room of the player
	public void setRoomPlayerIn(Map<Character, Room> roomMap, BoardCell[][] grid) {
	} //TODO

	public void seeCard(Card card) {
	} //TODO

}
