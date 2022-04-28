package model;

import java.util.ArrayList;

import exceptions.InvalidFirstNameException;
import exceptions.InvalidLastNameException;
import exceptions.InvalidPhoneNumber;

public class Client {
	
	public enum eGender {
		MALE, FEMALE
	};
	

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private eGender gender;
	private ArrayList<Appointment> myAppointments;
	
	
	public Client(String firstName, String lastName, String phoneNumber, eGender gender) throws InvalidPhoneNumber, InvalidFirstNameException, InvalidLastNameException {
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		this.setGender(gender);
		this.myAppointments = new ArrayList<Appointment>();
	}
	
	public Client () {
		this.myAppointments = new ArrayList<Appointment>();
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) throws InvalidPhoneNumber {
		if (phoneNumber.length() != 10)
			throw new InvalidPhoneNumber();
		if (phoneNumber.charAt(0) != '0' || phoneNumber.charAt(1) != '5') 
			throw new InvalidPhoneNumber();
		for (int i = 0; i < phoneNumber.length(); i++)
			if (phoneNumber.charAt(i) > '9' || phoneNumber.charAt(i) < '0')
				throw new InvalidPhoneNumber();
		this.phoneNumber = phoneNumber;
	}


	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) throws InvalidFirstNameException {
		if (firstName.length() < 2)
			throw new InvalidFirstNameException("First Name Too Short");
		for (int i = 0; i < firstName.length(); i++) {
			char c = firstName.charAt(i);
			if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
				throw new InvalidFirstNameException();
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) throws InvalidLastNameException {
		if (lastName.length() < 2)
			throw new InvalidLastNameException("Last Name Too Short");
		for (int i = 0; i < lastName.length(); i++) {
			char c = lastName.charAt(i);
			if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != ' ' && c != '-')
				throw new InvalidLastNameException();
		}
		this.lastName = lastName;
	}
	
	public ArrayList<Appointment> getMyAppointments() {
		return myAppointments;
	}

	@Override
	public String toString() {
		return "name: " + firstName + " " + lastName +
				"\nphone number: " + phoneNumber +  "\n";
	}

	public eGender getGender() {
		return gender;
	}

	public void setGender(eGender gender) {
		this.gender = gender;
	}

}
