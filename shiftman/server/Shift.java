package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author David Xiao
 *This class creates shift object, which is used to store the manager of a shift, the day it is on, and the staff
 *assigned to the shift.
 *Time is extended because one of the main components of a shift is its time.
 */
public class Shift extends Time{


	private int _minimumWorkers;
	private List<Shift> _assigned = new ArrayList<Shift>();
	private Shift _manager;
	private String _day;

	/**
	 * This constuctor creates an object that stores all essential info for a shift
	 * @param time
	 * @param minimumWorkers
	 * @param day of shift
	 * @throws BadTimeException, this comes from calling the super constructor Time(...)
	 */
	Shift(Time time, String minimumWorkers, String day){
		super(time,day);
		_minimumWorkers = Integer.parseInt(minimumWorkers);
		_day = day;
	}

	
	/**
	 * Default constructor used by child classes to instantiate class
	 */
	Shift(){
		super()	;
	}

	/**
	 * This method checks to see if the desired shift overlaps with any existing shift
	 * @param list
	 * @return true = there are overlapping shifts, false = given shift does not overlap
	 */
	boolean overlappingShift(List<Shift> list) {
		for (Shift shift : list) {
			if(this.overlapping(shift)){
				return true;
			}
		}
		return false;
	}


	/**
	 * 
	 * @param time
	 * @param staff
	 * @param isManager
	 */
	void assignStaffToShift(Shift staff, boolean isManager){
		if (isManager) {
			_manager = staff;
		}else {
		_assigned = staff.addSort(_assigned);
		}
	}


	/**
	 * Simple method that checks if  shift has less than the min number of workers
	 * @return true = shift is understaffed
	 */
	boolean understaffedShift() {
		return (_assigned.size()<_minimumWorkers);
	}

	/**
	 * Simple method that checks if shift has more than the min number of workers
	 * @return true = shift is overstaffed
	 */
	boolean overstaffedShift() {
		return (_assigned.size()>_minimumWorkers);
	}


	/**
	 * This method returns the manager for a shift
	 * @return
	 */
	Shift checkManager() {
		return _manager;
	}


	/**
	 * This method prints out the information for a shift in the required format
	 * @return
	 */
	String printS() {
		return _day + "["+getFirstToString()+"-"+getSecondToString()+"]";
	}


	/**
	 * This method adds object to a list and sorts the list accordingly
	 * @param list of objects
	 * @return sorted list of objects
	 */
	List<Shift> addSort(List<Shift> list){

		list.add(this);
		Collections.sort(list);
		return list;
	}

	/**
	 * This method searches a list of objects for a given object
	 * @param list of objects
	 * @return if desired object is found, it is outed
	 * @throws DoesNotExistException, this exception is thrown if the object does not exist,(object info, errorMsg)
	 */
	Shift findObject(List<Shift> list) throws DoesNotExistException{
		for (Shift staff:list) {
			if(this.equals(staff)) {
				return staff;
			}
		}
		throw new DoesNotExistException(this,8);
	}

	/**
	 * This method displays _assigned, converts it from a List<Shift> into a List<String>
	 * @return List in string format
	 */
	String displayAssigned(List<Shift> list){
		String shifts ="";
		int count =0;
		if(list.size()==0) {
			shifts = ("[No workers assigned]");
			return shifts;
		}
		for (Shift ShiftStaff : list) {
			count++;
			shifts = shifts + ShiftStaff.printS();
			if(count!=list.size()) {
				shifts = shifts +", ";
			}
		}
		return shifts;
	}

	/**
	 * 
	 * @param list
	 * @param level
	 * @return
	 */
	String printShiftsInfo(){
		String shiftInfo = "["+printS();

		if(checkManager()!=null) {
			shiftInfo= shiftInfo +" " + (" Manager:" + checkManager().toString())+" ";
		}else {
			shiftInfo= shiftInfo +" [No manager assigned] ";
		}
		
		shiftInfo=shiftInfo+displayAssigned(getAssigned())+"]";
		return shiftInfo;
	}

	
	/**
	 * similar to method above, but just returns List<Shift> of objects
	 * @return List<Shift>
	 */
	List<Shift> getAssigned(){
		return _assigned;
	}
}
