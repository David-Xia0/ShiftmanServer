package shiftman.server;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author David Xiao
 * This class creates our roster object, this object is used to reset our roster, set name and add staff. 
 *
 */
public class Roster {
	/**
	 * 
	 */
	private String _rosterName;
	private List<Shift> _registeredStaff = new ArrayList<Shift>();
	private List<Shift> _unassignedStaff = new ArrayList<Shift>();

	
	
	/**
	 * Constructor, stores name of our roster. Also becasue only one roster is allowed at a time, everyhting else is reset
	 * @param name
	 */
	Roster(String name){
		_rosterName=name;
		//resets Enum Class so only 1 roster exists at a time
		for (Days days: Days.values()) {
			days.resetDays();
		}	
	}

	/**
	 * @return name of roster
	 */
	String getRosterName(){
		return _rosterName;
	}

	/**
	 * adds staff to roster
	 * @param staff
	 */
	void registerStaffToRoster(Shift staff){
		_registeredStaff = staff.addSort(_registeredStaff);
		_unassignedStaff = staff.addSort(_unassignedStaff);

	}

	/**
	 * @return a list of all registered staff
	 */
	List<Shift> getStaffOnRoster(){
		return _registeredStaff;
	}
	
	List<String> getUnassignedStaff(){
		List<String> stringList = new ArrayList<String>();
		for(Shift staff:_unassignedStaff) {
			stringList.add(staff.printS());
		}
		return stringList;
	}
	
	/**
	 * 
	 * @param time
	 * @param staff
	 * @param isManager
	 * @param dayOfWeek
	 * @throws DoesNotExistException
	 */
	void assignStaffToRoster(Time time, Shift staff,boolean isManager, String dayOfWeek ) throws DoesNotExistException{
		staff = staff.findObject(_registeredStaff);
		
		Days.valueOf(dayOfWeek).assignToShiftForDay(time,staff, isManager);
		_unassignedStaff.remove(staff);
	}

	/**
	 * 
	 * @return
	 */
	List<Shift> getAllShifts(){
		List<Shift> shifts = new ArrayList<Shift>();
		for (Days day : Days.values()){
			for (Shift shift : day.getShiftsToday()){
				shifts.add(shift);
			}
		}
		return shifts;
	}
}
