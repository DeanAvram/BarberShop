package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import exceptions.InvalidFirstNameException;
import exceptions.InvalidLastNameException;
import exceptions.InvalidPhoneNumber;
import model.Client;

public class ClientTest {
	
	@Test
	public void testPhoneNumber() throws InvalidPhoneNumber {
		Client c = new Client();
		c.setPhoneNumber("0525775739");
		String phone = c.getPhoneNumber();
		assertTrue(phone.matches("[0][5][0-9]{8}"));
	}
	
	@Test
	public void testFirstName() throws InvalidPhoneNumber, InvalidFirstNameException {
		Client c = new Client();
		c.setFirstName("Djkhkj");
		String firstName = c.getFirstName();
		assertTrue(firstName.matches("[a-z | A-Z]{2,}?"));
	}
	
	@Test
	public void testLastName() throws InvalidPhoneNumber, InvalidLastNameException {
		Client c = new Client();
		c.setLastName("ben ben - moshe");
		String lastName = c.getLastName();
		assertTrue(lastName.matches("[a-z | A-Z]{2,}-?[a-z | A-Z]{2,}?"));
	}
	
	

}
