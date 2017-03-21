package erasmus.properties;

public class ParseException extends Exception {

	private static final long serialVersionUID = 856153590702835150L;
	
	public String variableType;
	public String key;
	public String value;
	
	public ParseException(String variableType, String key, String value) {
		this.variableType = variableType;
		this.key = key;
		this.value = value;
	}
}
