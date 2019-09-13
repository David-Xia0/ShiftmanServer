package shiftman.server;

import java.util.List;
import java.util.ArrayList;

public class Staff extends Shift{

	private String _firstName;
	private String _lastName;
	private List<Shift> _assignedAsWorker = new ArrayList<Shift>();
	private List<Shift> _assignedAsManager = new ArrayList<Shift>();


	/**
	 * 
	 * @param firstName
	 * @param lastName
	 */
	Staff(String firstName, String lastName){
		super();
		_firstName = firstName;
		_lastName = lastName;


	}

	Staff (String name){
		super();
		int nameSpace = name.indexOf(" ");
		_lastName = name.substring(0, nameSpace);
		_firstName = name.substring(nameSpace+1);
	}

	void assignStaffToShift(Shift shift, boolean isManager){

		if(isManager) {
			_assignedAsManager = shift.addSort(_assignedAsManager);
		}else {
			_assignedAsWorker = shift.addSort(_assignedAsWorker);
		}
	}


	String printS() {
		return getSecondToString()+" "+getFirstToString();
	}

	public boolean equals(Time obj) {
		return ((obj.toString()).equalsIgnoreCase(getFirstToString()+", "+getSecondToString()));
	}

	public String toString() {
		return _lastName+", "+_firstName;
	}

	String getFirstToString(){
		return _lastName;
	}

	String getSecondToString(){
		return _firstName;
	}


	String displayAssigned(List<Shift> list){
		String shifts ="";
		int count =0;
		if(list.size()==0) {
			return "";
		}

		for (Shift ShiftStaff : list) {
			count++;
			shifts = shifts + ShiftStaff.printS();
			if(count != list.size()) {
				shifts = shifts + ", ";
			}
		}
		return shifts;
	}

	List<Shift> getAssigned(){
		return _assignedAsWorker;
	}

	String getManaged(){

		return displayAssigned(_assignedAsManager);
	}

}
