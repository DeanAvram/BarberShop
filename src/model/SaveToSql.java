package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;

public class SaveToSql {

	private static String dbUrl = "jdbc:mysql://localhost:3306/barbershop";
	private static Connection conn = null;

	public SaveToSql() {
		try {
			conn = DriverManager.getConnection(dbUrl, "root", "dean6198");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveCllients(SingeltonBarbershop b) {
		for (Client c : b.getAllClients()) {
			try {
				Statement stmt = conn.createStatement();
				String s = "INSERT INTO client VALUES('	" + c.getFirstName() + " ','" + 
				c.getLastName() + " ','" + c.getPhoneNumber() + " ', '" + c.getGender() + "')";
				stmt.executeUpdate(s);
			} catch (Exception e) {
			}
		}
	}
	
	
	
	public void saveAppointments(SingeltonBarbershop b) {
		try {
			Statement stmt1 = conn.createStatement();
			stmt1.execute("SET FOREIGN_KEY_CHECKS=0");
			for (BarberShopObserver bo : b.getAllObservers()) {
				Appointment a;
				if (bo instanceof Appointment) {
					a = (Appointment) bo;
					Statement stmt = conn.createStatement();
					String s = "SELECT * FROM Appointment WHERE (Appointment.ID = '" + a.getID() + "' )";
					ResultSet rs = stmt.executeQuery(s);
					if (rs.next()) {
						s = "UPDATE Appointment SET dateOfAppointment='" + a.getDateOfAppointment().toString()
								+ "', timeOfAppointment ='" + a.getTimeOfAppointment() + "', serviceName= '"
								+ a.getServiceName() + "'" + " WHERE ID = '" + a.getID() + "'";
						stmt.executeUpdate(s);
					} else {
						s = "INSERT INTO Appointment VALUES('	" + a.getID() + " ','" + a.getDateOfAppointment()
								+ " ','" + a.getTimeOfAppointment() + " ','" + a.getServiceName() + " ','"
								+ a.getCustomerPhone() + "')";
						stmt.executeUpdate(s);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void removeAppointment(int appId) {
		try {
			Statement stmt = conn.createStatement();
			String s = "DELETE FROM Appointment WHERE ID =  '" + appId + "' ";
			stmt.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveDaysOff (SingeltonBarbershop b) {
		try {
			Statement stmt = conn.createStatement();
			String s = "DELETE FROM DAYS_OFF";
			stmt.executeUpdate(s);
			for (DayOfWeek d : b.getDaysOff()) {
				s = "INSERT INTO DAYS_OFF VALUES('	" + d.toString() + "')";
				stmt.executeUpdate(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void saveVacations(Vaction v) {
		try {
			Statement stmt = conn.createStatement();
			String s = "INSERT INTO VACATIONS VALUES('	" + v.getSerialNumber() + " ','" + v.getStartDate().toString()
					+ " ','" + v.getEndDate().toString() + "')";
			stmt.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void saveCanceledAppointments(int VID, int AID) {
		try {
			Statement stmt = conn.createStatement();
			String s = "INSERT INTO CANCELED_APPOINTMENTS VALUES ('	" + VID + " ','" + AID + "')";
			stmt.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}


