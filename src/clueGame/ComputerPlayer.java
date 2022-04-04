package clueGame;
import java.io.*;
import java.util.*;

public class ComputerPlayer extends Player {
	private String roomName;
	protected ArrayList<Card> seen = new ArrayList<Card>();
	
	public ComputerPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Solution createSuggestion(ArrayList<Card> deck) {
		Solution suggestion = new Solution();
		Collections.shuffle(deck);
		for (Card c : deck) {
			if (suggestion.isFull()) {
				break;
			}
			else if (this.seen.contains(c)) {
				continue;
			}
			else if (c.getName().equals(roomName)) { // player suggests room it's in
				suggestion.add(c);
			}		
		}
		return suggestion;
	}
	
	@Override
	public void setRoomName(Map<Character, Room> roomMap, BoardCell[][] grid) {
		BoardCell bc = grid[this.row][this.column];
    	Room r = roomMap.get(bc.getInitial());
    	if (r==null) {
    		this.roomName = "";
    	}
    	else {
    		this.roomName = r.getName();
    	}
	}
	
	public BoardCell selectTarget(Set<BoardCell> t, Map<Character,Room> roomMap) {
		int index = new Random().nextInt(t.size());
		int count = 0;
		ArrayList<BoardCell> moveTo = new ArrayList<BoardCell>();
		BoardCell random = new BoardCell(0,0);
		for (BoardCell bc : t) {
			if (count == index) {
				random = bc;
			}
			Room r = roomMap.get(bc.getInitial());
			for (Card c : this.seen) {
				if (c.getType()==CardType.ROOM && !c.getName().equals(roomName)) {
					moveTo.add(bc);
				}
			}
			count++;
		}
		if (moveTo.isEmpty()) {
			return random;
		}
		else {
			Collections.shuffle(moveTo);
			index = index % moveTo.size();
			return moveTo.get(index);
		}
	}
	
	@Override
	public void seeCard(Card c) {
		seen.add(c);
	}

}
