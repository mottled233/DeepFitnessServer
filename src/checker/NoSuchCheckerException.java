package checker;


public class NoSuchCheckerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4329237301503937346L;
	private int id;
	private String checker;
	public NoSuchCheckerException(String message, int id, String checker){
		super(message);
		this.id = id;
		this.checker = checker;
	}
	
	public int getId(){
		return id;
	}
	
	public String getWrongChecker(){
		return checker;
	}

}
