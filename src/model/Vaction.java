package model;


import java.time.LocalDate;

public class Vaction {
	private int serialNumber;
	protected static int ID = 1;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public Vaction () {
		this.setSerialNumber(ID);
	}
	
	public Vaction (LocalDate start, LocalDate end) {
		this.setSerialNumber(ID++);
		this.startDate = start;
		this.endDate = end;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public String toString () {
		return startDate.toString() + " - " + endDate.toString() + "\n";
	}
	
	
	
	
	
}
