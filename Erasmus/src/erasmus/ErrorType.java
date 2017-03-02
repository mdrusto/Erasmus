package erasmus;

public enum ErrorType {
	GUILDNOTFOUND("Guild with id %s was not found"),
	TEXTCHANNELNOTFOUND("Text channel with id %s was not found"),
	COULDNOTPARSE("The following value couldn't be parsed:\nKey: %s\nValue: %s\nVariable type: %s"),
	OTHER("%s");
	
	public String message;
	
	private ErrorType(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
