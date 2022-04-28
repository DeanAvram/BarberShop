package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import model.Client.eGender;


public class LoadFromSql {

	private static String dbUrl = "jdbc:mysql://localhost:3306/barbershop";
	private static Connection conn = null;

	public LoadFromSql() {
		try {
			conn = DriverManager.getConnection(dbUrl, "root", "dean6198");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Set<Client> loadClients() {
		Set<Client> allClients = new LinkedHashSet<Client>();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM client");
			ResultSet rs = stmt.executeQuery();
			Client c;
			while (rs.next()) {
				c = new Client();
				c.setFirstName(rs.getString("firstName").trim());
				c.setLastName(rs.getString("lastName").trim());
				c.setPhoneNumber(rs.getString("phoneNumber"));
				String gender = rs.getString("gender");
				c.setGender(eGender.valueOf(gender));
				allClients.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allClients;
	}

	public void getServices(SingeltonBarbershop b) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT name FROM service");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String serviceName = rs.getString("name");
				b.createService(serviceName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<BarberShopObserver> getAppointment(SingeltonBarbershop b) {
		ArrayList<BarberShopObserver> list = new ArrayList<BarberShopObserver>();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Appointment");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Appointment a = new Appointment();
				int id = rs.getInt("ID");
				String stringDate = rs.getString("dateOfAppointment");
				LocalDate date = LocalDate.parse(stringDate);
				Time t = rs.getTime("timeOfAppointment");
				LocalTime time = t.toLocalTime();
				String serviceName = rs.getString("serviceName");
				String customerPhone = rs.getString("customerPhone");
				a.setID(id);
				a.setDateOfAppointment(date);
				a.setTimeOfAppointment(time);
				a.setCustomerPhone(customerPhone);
				a.setServiceName(serviceName);
				a.setCustomerPhone(customerPhone);
				list.add(a);
				b.getClientByPhone(customerPhone).getMyAppointments().add(a);
				Appointment.IdGenerator = id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	public Set<DayOfWeek> getDaysOff() {
		Set<DayOfWeek> daysOff = new LinkedHashSet<DayOfWeek>();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DAYS_OFF");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
				daysOff.add(DayOfWeek.valueOf(rs.getString("dayOff").trim().toUpperCase()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return daysOff;
	}

	public ArrayList<Vaction> getVactions() {
		ArrayList<Vaction> list = new ArrayList<Vaction>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM VACATIONS");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Vaction v = new Vaction();
				int ID = rs.getInt("VID");
				LocalDate startD = LocalDate.parse(rs.getString("startDate").trim());
				LocalDate endD = LocalDate.parse(rs.getString("endDate").trim());
				v.setID(ID);
				v.setStartDate(startD);
				v.setEndDate(endD);
				list.add(v);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void clearCanceledAppointment() {
		try {
			Statement stmt = conn.createStatement();
			String s = "DELETE FROM CANCELED_APPOINTMENTS";
			stmt.executeUpdate(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
