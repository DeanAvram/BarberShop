package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements BarberShopObserver, Comparable<Appointment>{
	
	private int ID;
	protected static int IdGenerator = 0;
	private LocalDate dateOfAppointment;
	private LocalTime timeOfAppointment;
	private String serviceName;
	private String customerPhone;
	
	
	public Appointment(LocalDate dateOfAppointment, LocalTime timeOfAppointment, String serviceName, String customerPhone) {
		this.ID = ++IdGenerator;
		this.dateOfAppointment = dateOfAppointment;
		this.timeOfAppointment = timeOfAppointment;
		this.serviceName = serviceName;
		this.customerPhone = customerPhone;
	}
	
	public Appointment () {
		IdGenerator++;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}

	public LocalDate getDateOfAppointment() {
		return dateOfAppointment;
	}


	public void setDateOfAppointment(LocalDate dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}
	
	
	public LocalTime getTimeOfAppointment() {
		return timeOfAppointment;
	}


	public void setTimeOfAppointment(LocalTime timeOfAppointment) {
		this.timeOfAppointment = timeOfAppointment;
	}


	@Override
	public String notifyBarberShopUpdated(String firstName, LocalDate date, String serviceName) {
		String dateStr = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		return "Hi " + firstName +  " Your Appointment to " + serviceName + " at "  
				+ dateStr +  " canceled\n";
	}
	
	public String getServiceName () {
		return serviceName;
	}

	
	public void setServiceName (String name) {
		this.serviceName = name;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	
	@Override
	public int compareTo(Appointment a) {
		return this.getTimeOfAppointment().compareTo(a.timeOfAppointment);
	}

}
