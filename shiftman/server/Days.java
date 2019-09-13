package shiftman.server;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author pb
 *
 */
enum Days{
	Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;


	/**
	 * 
	 */


	private Time _workingHours;
	private List<Shift> _shifts = new ArrayList<Shift>();
	private List<Shift>_shiftsWithoutManagers = new ArrayList<Shift>();


	/**
	 * 
	 * @param startTime
	 * @param endTime
	 */
	int setDayWorkHours( String startTime,String endTime) throws BadTimeException{
		_workingHours = new Time(startTime, endTime, this.toString());
		return 0;
	}

	/**
	 * 
	 * @param time
	 * @param minimumWorkers
	 * @return
	 */
	void addShiftForDay(Time time, String minimumWorkers) throws BadTimeException{
		Shift shift = new Shift(time, minimumWorkers,this.name());
		if(_workingHours !=null && _workingHours.withIn(shift)) {
			if(shift.overlappingShift(_shifts)){
				throw new BadTimeException(4);
			}
			_shifts=shift.addSort(_shifts);
			_shiftsWithoutManagers=shift.addSort(_shiftsWithoutManagers);
		}else {
			throw new BadTimeException(11);
		}
	}

	/**
	 * 
	 * @param time
	 * @param staff
	 * @param isManager
	 * @throws ShiftDoesNotExistException
	 */
	void assignToShiftForDay(Time time,Shift staff,boolean isManager) throws DoesNotExistException{

		for(Shift shift : _shifts) {
			if ((shift).equals(time)) {
				shift.assignStaffToShift(staff,isManager);
				staff.assignStaffToShift(shift, isManager);
				if(isManager) {
					_shiftsWithoutManagers.remove(shift);
				}
				return;
			}
		}
		throw new DoesNotExistException(time,9);
	}


	/**
	 * 
	 * @return Shifts for a given day of the week
	 */
	List<Shift> getShiftsToday(){
		return _shifts;
	}

	/**
	 * 
	 * @return
	 */
	List<Shift> getUnassignedShiftsToday(){
		return _shiftsWithoutManagers;
	}

	/**
	 * 
	 * @return
	 * @throws BadTimeException
	 */
	String getWorkingHoursToday()throws BadTimeException {
		try {
			return _workingHours.toString();
		}catch(NullPointerException e) {
			throw new BadTimeException(13);
		}
	}

	/**
	 * 
	 */
	void resetDays() {
		_workingHours = null;
		_shifts.clear();
	}

	/**
	 * 
	 * @param rosterName
	 * @return
	 */
	List<String> printRosterForDay(String rosterName){
		List<String> rosterInfo = new ArrayList<String>();
		rosterInfo.add(rosterName);
		try {
			rosterInfo.add( this +" " + getWorkingHoursToday());
			
			if(_shifts.size()==0) {
				return new ArrayList<String>();
			}
			for(Shift shifts:_shifts){
				rosterInfo.add(shifts.printShiftsInfo());
			}
		}catch(BadTimeException e) {
			return new ArrayList<String>();
		}
		return rosterInfo;
	}
}	


