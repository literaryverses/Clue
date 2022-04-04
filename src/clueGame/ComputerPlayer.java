package clueGame;
import java.io.*;
import java.util.*;

public class ComputerPlayer extends Player {
	private String roomName;
	
	public ComputerPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Solution createSuggestion() {
		Solution suggestion = new Solution();
		Collections.shuffle(this.unseen);
		for (Card c : this.unseen) {
			if (suggestion.isFull()) {
				break;
			}
			else if (c.getName().equals(roomName)) { // player suggests room it's in
				suggestion.add(c);
			}
			else if (c.getType()!=CardType.ROOM) {
				// prevent player from suggesting itself
				if (c.getType()==CardType.PERSON && c.getName().equals(this.name)) {
					continue;
				}
			}		
		}
		return suggestion;
	}
	
	public void setRoomName(String str) {
		this.roomName = str;
	}

}
