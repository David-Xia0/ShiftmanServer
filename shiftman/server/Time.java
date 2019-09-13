package shiftman.server;
/**
 * 
 * @author David Xiao
 *This class/object stores all things to do with time, startTime and endTime, for the roster.
 */
public class Time implements Comparable<Time>{

	private String _startTime;
	private String _endTime;
	private String _day;


	/**
	 * This constructor stores the 2 params, but first checks there validity
	 * @param startTime
	 * @param endTime
	 * @throws BadTimeException is thrown from method CheckTime
	 */
	Time (String startTime, String endTime, String day) throws BadTimeException{
		CheckTime(startTime,endTime);
		_startTime = startTime;
		_endTime =endTime;
		_day = day;
	}

	Time (Time time, String day){
		_startTime = time.getFirstToString();
		_endTime =time.getSecondToString();
		_day = day;
	}
	/*
	 * Default constructor, used by child classes to instantiate Time class.
	 */
	Time(){	
	}

	/**
	 * This method checks if startTime and endTime are sensible. It also checks to see if endTime passes midnight
	 * @param startTime
	 * @param endTime
	 * @throws BadTimeException, If the given times are not sensible an exception is Thrown. 
	 * This exception is caught later to disply an error msg
	 */
	void CheckTime(String startTime, String endTime) throws BadTimeException{
		if (stringToInt(startTime)>=stringToInt(endTime)) {
			throw new BadTimeException(2);
		}else if(stringToInt(endTime)>2400) {
			throw new BadTimeException(3);
		}
	}

	/**
	 * This method checks to see if one time object is within the interval of another time object
	 * @param time For the working hours (larger constraint)
	 * @return Boolean type, true = within, false = outside of.
	 */
	boolean withIn(Time time){
		if (stringToInt(time.getFirstToString())<stringToInt(_startTime)) {
			return false;
		}
		if (stringToInt(time.getSecondToString())>stringToInt(_endTime)) {
			return false;
		}
		return true;
	}

	/**
	 * This method checks to see if two time intervals are overlapping 
	 * @param time object, with time interval stored inside/
	 * @return boolean, true = overlapping, false = not overlapping
	 */
	boolean overlapping(Time time) {

		int endTime1=stringToInt(time.getSecondToString());
		int endTime2=stringToInt(_endTime);

		int startTime1=stringToInt(time.getFirstToString());
		int startTime2=stringToInt(_startTime);

		//if both start time and end time are larger than their opposite counter parts they must be not overlapping
		if (startTime1>=endTime2 && endTime1>=startTime2) {
			return false;
		}
		if (startTime1<=endTime2 && endTime1<=startTime2) {
			return false;
		}
		return true;
	}


	/**
	 * Implementation from comparator interface. This method compares the value of 2 objects
	 * @param time1
	 * @param time2
	 * @return positive if 1> 2, negative if 1 < 2, 0 if equal
	 */
	public int compareTo(Time obj) {
		if(obj.getDay()==null ||this.getDay().equals(obj.getDay())) {
		return this.toString().compareTo(obj.toString());
		}
		return -this.getDay().compareTo(obj.getDay());
	}

	/**
	 * this method checks if two objects store equivalent information
	 * @param obj
	 * @return true = equal.
	 */
	boolean equals(Time obj) {
			return ((obj.toString()).equals(getFirstToString()+"-"+getSecondToString()));
		
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	int stringToInt(String time) {
		return Integer.parseInt(time.substring(0,2) + time.substring(3));
	}

	/**
	 * returns String representation of object
	 */
	public String toString() {
		return _startTime+"-"+_endTime;	
	}
	
	String getDay(){
		return _day;
	}
	
	/**
	 * 
	 * @return
	 */
	 String getFirstToString() {
		return _startTime;
	} 

	/**
	 * 
	 * @return
	 */
	String getSecondToString() {
		return _endTime;
	}
}
