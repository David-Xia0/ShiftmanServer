package shiftman.server;

import java.util.ArrayList;
import java.util.List;

public class ShiftManServer extends OutputMsg implements ShiftMan {


	private Roster _roster;


	@Override
	public String newRoster(String shopName) {
		if(checkStringInput(shopName)) {
			return getOutputMsg(1);
		}
		_roster = new Roster(shopName);
		return "";
	}

	@Override
	public String setWorkingHours(String dayOfWeek, String startTime, String endTime) {

		if (_roster==null) {
			return getOutputMsg(12);
		}
		try {
			return getOutputMsg(Days.valueOf(dayOfWeek).setDayWorkHours(startTime,endTime));
		}catch(BadTimeException e) {
			return getOutputMsg(e.getErrorNum());
		}catch(EnumConstantNotPresentException f) {
			return getOutputMsg(7) + f.constantName();
		}
	}

	@Override
	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers) {

		if (_roster==null) {
			return getOutputMsg(12);
		}
		try {
			Time time = new Time(startTime,endTime,dayOfWeek);
			Days.valueOf(dayOfWeek).addShiftForDay(time, minimumWorkers);
		}catch(BadTimeException e) {
			return getOutputMsg(e.getErrorNum());
		}catch(EnumConstantNotPresentException f) {
			return getOutputMsg(7) + f.constantName();
		}
		return "";
	}

	@Override
	public String registerStaff(String givenName, String familyName) {

		if(checkStringInput(familyName) || checkStringInput(givenName)) {
			return getOutputMsg(5);
		}
		if (_roster==null) {
			return getOutputMsg(12);
		}

		//methods adds staff to list in a sorted manner
		_roster.registerStaffToRoster(new Staff(givenName,familyName));

		return "";
	}


	@Override
	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName,
			boolean isManager) {

		if(checkStringInput(familyName) || checkStringInput(givenName)) {
			return getOutputMsg(5);
		}

		try {
			Time time = new Time(startTime,endTime,dayOfWeek);
			Shift staff = new Staff(givenName,familyName);
			_roster.assignStaffToRoster(time, staff, isManager, dayOfWeek);
		}catch(BadTimeException e){
			return getOutputMsg(e.getErrorNum());
		}catch(EnumConstantNotPresentException f) {
			return getOutputMsg(7) + f.constantName();
		}catch(DoesNotExistException g) {
			return getOutputMsg(g.getErrorNum()) + g.getErrorMsg() ;
		}catch (NullPointerException h) {
			return getOutputMsg(12);
		}
		return "";
	}

	@Override
	public List<String> getRegisteredStaff() {
		List<String> registeredStaff = new ArrayList<String>();

		for(Shift staff:_roster.getStaffOnRoster()) {
			registeredStaff.add(staff.printS());
		}
		return registeredStaff;
	}


	@Override
	public List<String> getUnassignedStaff() {
		return _roster.getUnassignedStaff();
	}

	@Override
	public List<String> shiftsWithoutManagers() {
		List<String> shiftsWithNoManager = new ArrayList<String>();
		for (Days day : Days.values()) {
			for (Shift shift : day.getShiftsToday()) {
				if (shift.checkManager()==null) {
					shiftsWithNoManager.add(shift.printS());
				}
			}
		}
		return shiftsWithNoManager;
	}

	@Override
	public List<String> understaffedShifts() {

		List<String> underStaffedShifts = new ArrayList<String>();
		for (Shift shift : _roster.getAllShifts()) {
			if (shift.understaffedShift()) {
				underStaffedShifts.add(shift.printS());
			}
		}
		return underStaffedShifts;
	}


	@Override
	public List<String> overstaffedShifts() {
		List<String> overStaffedShifts = new ArrayList<String>();
		for (Shift shift : _roster.getAllShifts()) {
			if (shift.overstaffedShift()) {
				overStaffedShifts.add(shift.printS());
			}
		}
		return overStaffedShifts;
	}


	@Override
	public List<String> getRosterForDay(String dayOfWeek) {
		try {
			return Days.valueOf(dayOfWeek).printRosterForDay(_roster.getRosterName());
		}catch(EnumConstantNotPresentException e) {
			return asArrayList(getOutputMsg(7) + e.constantName());
		}
	}


	@Override
	public List<String> getRosterForWorker(String workerName) {
		Shift tempStaff = new Staff(workerName);
		List<String> shiftsWorked = new ArrayList<String>();
		try {
			tempStaff = tempStaff.findObject(_roster.getStaffOnRoster());
			shiftsWorked.add(tempStaff.displayAssigned(tempStaff.getAssigned()));
			return shiftsWorked;
		}catch(DoesNotExistException e) {
			return asArrayList(getOutputMsg(9) + e.getErrorMsg());
		}
	}

	@Override
	public List<String> getShiftsManagedBy(String managerName) {
		Shift manager = new Staff(managerName);
		List<String> shiftsManaged = new ArrayList<String>();
		try {
			manager = manager.findObject(_roster.getStaffOnRoster());
			shiftsManaged.add(((Staff) manager).getManaged());
			return shiftsManaged;
		}catch(DoesNotExistException e) {
			return asArrayList(getOutputMsg(9) + e.getErrorMsg());
		}
	}



	@Override
	public String reportRosterIssues() {
		return null;
	}

	@Override
	public String displayRoster(){
		return null;
	}






}
