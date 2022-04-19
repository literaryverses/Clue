package clueGame;
import java.io.*;
import java.util.*;

public class ComputerPlayer extends Player {
	private String roomName;
	private ArrayList<Card> seen = new ArrayList<Card>();

	
	public ComputerPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Player creates a solution from cards it hasn't seen
	 */
	//This needs to get fixed, the computer will not select one of each type as it needs to
	public Solution createSuggestion(ArrayList<Card> deck) {
		Solution suggestion = new Solution();
		Collections.shuffle(deck); // randomize the order of cards
		for (Card card : deck) {
			if (suggestion.isFull()) { // if cards cannot be added anymore
				break;
			}
			else if (card.getName().equals(roomName)) { // player suggests room it's in
				suggestion.add(card);
			}
			else if (this.seen.contains(card)) { // if player has seen card, skip
				continue;
			}
			else if (!card.getType().equals(CardType.ROOM)) { // add to suggestion if card is not a room
				suggestion.add(card);
			}
		}
		return suggestion;
	}
	
	/*
	 * keeps track of the current room of the player
	 */
	public void setRoomPlayerIn(Map<Character, Room> roomMap, BoardCell[][] grid) {
		BoardCell newCell = grid[this.row][this.column];
    	Room room = roomMap.get(newCell.getInitial());
    	if (room==null) {
    		this.roomName = "";
    	}
    	else {
    		this.roomName = room.getName();
    	}
	}
	
	/*
	 * Player selects a target from given targetList
	 */
	public BoardCell selectTarget(Set<BoardCell> targetList, Map<Character,Room> roomMap, ArrayList<Card> deck) {
		int index = new Random().nextInt(targetList.size());
		int count = 0;
		ArrayList<BoardCell> moveTo = new ArrayList<BoardCell>();
		BoardCell random = new BoardCell(0,0);
		for (BoardCell newCell : targetList) {
			if (count == index) {
				random = newCell;
			}
			if (newCell.isRoomCenter()) { // if board cell 
				Room room = roomMap.get(newCell.getInitial());
				String roomName = room.getName();
				for (Card card : seen) {
					if (!card.getName().equals(roomName) && card.getType()==CardType.ROOM) {
						moveTo.add(newCell);
					}
				}
			}
			count++;
		} // if no priority rooms to move to, select random board cell from targetList
		if (moveTo.isEmpty()) {
			return random;
		}
		else {
			Collections.shuffle(moveTo);
			index = index % moveTo.size(); // narrows down index from deck to selected list of targets
			return moveTo.get(index);
		}
	}
	
	@Override
	public void updateHand(Card card) {
		hand.add(card);
		seeCard(card);
	}
	
	public void seeCard(Card card) {
		if (!this.seen.contains(card)) {
			seen.add(card);
		}
	}
}
