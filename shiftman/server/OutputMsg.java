package shiftman.server;

import java.util.ArrayList;
import java.util.List;

public abstract class OutputMsg {
	
	private String[] _error = {
			"",
			"ERROR 1: Shop name is empty or null",
			"ERROR 2: Invalid start Time and End Time (End Time is earlier than Start Time)",
			"ERROR 3: Invalid End Time (End Time is later than 2400)",
			"ERROR 4: StartTime and EndTime overlap with existing shifts",
			"ERROR 5: A String Input is empty or null",
			"ERROR 6: FamilyName is empty or null",
			"ERROR 7: The value of the day of week is invalid - ",
			"ERROR 8: No object Exists with the Value : ",
			"ERROR 9: No object Exists with the Value : ",
			"ERROR 10: ",
			"ERROR 11: StartTime or EndTime are outside of Working Hours",
			"ERROR 12: No Roster Registered",
			"ERROR 13: No set working Hours for Given day"} ;
	
	String getOutputMsg(int num) {
		return _error[num];
	}
	
	/**
	 * 
	 * @param string from output messages
	 * @return string inside list
	 */
	List<String> asArrayList(String string){
		List<String> output = new ArrayList<String>();
		output.add(string);
		return output;
	}
	
	boolean checkStringInput(String string) {
		if (string==null|| string =="") {
			return true;
		}
		return false;
	}
}
