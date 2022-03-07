// Matt Brause
// Jun Lee

package clueGame;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Entry in file(s) has incorrect format");
	}
	
	public BadConfigFormatException(String message) {
		super(message + "in the Layout File");
	}
}

