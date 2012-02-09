package uk.ac.cam.jdb75.tick4;

public class PatternFormatException extends Exception {

	private String message;
	
	public PatternFormatException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}