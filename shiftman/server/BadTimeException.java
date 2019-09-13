package shiftman.server;

public class BadTimeException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _errorNumber;
	
	public BadTimeException(int errorNumber) {
		_errorNumber = errorNumber;
	}
	
	int getErrorNum() {
		return _errorNumber;
	}

}
