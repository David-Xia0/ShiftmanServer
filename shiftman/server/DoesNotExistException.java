package shiftman.server;


public class DoesNotExistException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _stringMsg;
	private int _errorNum;
	DoesNotExistException(Time obj,int num){
		_stringMsg=obj.getFirstToString() +" " +obj.getSecondToString();
		_errorNum=num;
	}

	String getErrorMsg() {
		return _stringMsg;
	}
	
	int getErrorNum() {
		return _errorNum;
	}
}
