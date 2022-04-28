package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import exceptions.InvalidFirstNameException;
import exceptions.InvalidLastNameException;
import exceptions.InvalidPhoneNumber;
import model.Client.eGender;

public class SingeltonBarbershop implements BarberShopObservable{

	private final LocalTime startTime = LocalTime.parse("09:00");
	private final LocalTime endTime = LocalTime.parse("20:00");
	
	private String name;
	private ArrayList<BarberShopObserver> allObservers;
	private Set<Client> allClients;
	private static SingeltonBarbershop _instace = null;
	private ServiceFactory serviceFactory;
	private ArrayList<IService> allServices;
	private ArrayList<Vaction> vactions;
	private Set<DayOfWeek> daysOff;
	private SaveToSql saveToSql;

	private SingeltonBarbershop() {
		this.name = "Rainbow Barbershop";
		this.allObservers = new ArrayList<BarberShopObserver>();
		serviceFactory = new ServiceFactory();
		this.allServices = new ArrayList<IService>();
		this.saveToSql = new SaveToSql();
		loadData();
	}

	public static SingeltonBarbershop getInstance() {
		if (_instace == null)
			_instace = new SingeltonBarbershop();
		return _instace;

	}

	public void loadData() {
		LoadFromSql l = new LoadFromSql();
		this.allClients = l.loadClients();
		l.getServices(this);
		this.allObservers = LoadFromSql.getAppointment(this);
		this.setDaysOff(l.getDaysOff());
		this.vactions = l.getVactions();
		LoadFromSql.clearCanceledAppointment();
		Vaction.ID = vactions.size() + 1;
	}

	public void saveData() {
		saveToSql.saveCllients(this);
		saveToSql.saveAppointments(this);
		saveToSql.saveDaysOff(this);
	}

	
	@Override
	public void addBarberShopObserver(String phoneNumber, LocalDate date, LocalTime time, String serviceName) {
		Client c = getClientByPhone(phoneNumber);
		Appointment a = new Appointment(date, time, serviceName, phoneNumber);
		c.getMyAppointments().add(a);
		allClients.add(c);
		this.allObservers.add(a);
		//return true;
	}

	@Override
	public void removeBarberShopObserver(BarberShopObserver bo) {
		this.allObservers.remove(bo);
		Appointment a = (Appointment)bo;
		Client c = getClientByPhone(a.getCustomerPhone());
		c.getMyAppointments().remove(a);
		SaveToSql.removeAppointment(a.getID());
		
	}

	@Override
	public String notifyAllBarberShopObservers(ArrayList<BarberShopObserver> observers) {
		StringBuffer sb = new StringBuffer();
		for (BarberShopObserver bo : observers) {
			Appointment a = (Appointment)bo;
			String name = getClientByPhone(a.getCustomerPhone()).getFirstName();
			sb.append(bo.notifyBarberShopUpdated(name, a.getDateOfAppointment(), a.getServiceName()));
			removeBarberShopObserver(bo);
		}
		return sb.toString();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<BarberShopObserver> getAllObservers() {
		return allObservers;
	}

	 public Set<DayOfWeek> getDaysOff() {
		 return daysOff;
	}

	public void setDaysOff(Set<DayOfWeek> daysOff) {
		this.daysOff = daysOff;
	 }
	
	public void addDayOff (DayOfWeek d) {
		this.daysOff.add(d);
	}

	public Set<Client> getAllClients() {
		return allClients;
	}

	public Client getClientByPhone(String phoneNumber) {
		for (Client c : allClients) {
			if (c.getPhoneNumber().equals(phoneNumber))
				return c;
		}
		return null;
	}

	public Appointment getAppointmentById(int id) {
		for (BarberShopObserver bo : allObservers) {
			if (bo instanceof Appointment) {
				Appointment a = (Appointment) bo;
				if (a.getID() == id)
					return a;
			}
		}
		return null;
	}

	public ArrayList<Appointment> getAppointmentsByDate(LocalDate d) {
		ArrayList<Appointment> allApps = new ArrayList<Appointment>();
		for (BarberShopObserver bo : allObservers) {
			if (bo instanceof Appointment) {
				Appointment a = (Appointment) bo;
				if (a.getDateOfAppointment().compareTo(d) == 0)
					allApps.add(a);
			}
		}
		if (allApps.size() == 0)
			return null;
		return allApps;
	}
	
	public void addClientToList(String firstName, String lastName, String phone, eGender gender) throws InvalidPhoneNumber, InvalidFirstNameException, InvalidLastNameException {
		Client c;
		c = new Client(firstName, lastName, phone, gender);
		allClients.add(c);
		
	}

	public void printAllCustomers() {
		for (Client c : allClients)
			System.out.println(c.toString() + "\n");
	}

	public IService getServiceByName(String name) {
		for (IService s : allServices) {
			if (s.getName().equals(name))
				return s;
		}
		return null;
	}
	
	public void createService(String name) {
		IService s = serviceFactory.getService(name);
		allServices.add(s);
	}
	
	public Set<Appointment> getSortedSetByDate (LocalDate date){
		Set<Appointment> list = new TreeSet<Appointment>();
		for (int i = 0; i < allObservers.size(); i++) {
			if (allObservers.get(i) instanceof Appointment) {
				Appointment a = (Appointment)allObservers.get(i);
				if (a.getDateOfAppointment().equals(date))
					list.add(a);
			}
		}
		return list;
	}

	public ArrayList<Vaction> getVactions() {
		return vactions;
	}

	public void setVactions(ArrayList<Vaction> vactions) {
		this.vactions = vactions;
	}

	public void addVactionToList(Vaction v) {
		vactions.add(v);
	}
	
	public int getVacationIdByStartDate (LocalDate startDate) {
		for (Vaction v : vactions) {
			if (v.getStartDate().equals(startDate))
				return v.getSerialNumber();
		}
		return -1;
	}

	public String AppointmentToDelete(LocalDate start, LocalDate end) {
		ArrayList<BarberShopObserver> toDelete = new ArrayList<BarberShopObserver>();
		for (BarberShopObserver bo : allObservers) {
			Appointment a = (Appointment) bo;
			LocalDate temp = start;
			while (temp.compareTo(end) < 0) {
				if (a.getDateOfAppointment().equals(temp)) {
					int VID = getVacationIdByStartDate(start);
					SaveToSql.saveCanceledAppointments(VID, a.getID());
					toDelete.add(a);
					
				}
				temp = temp.plusDays(1);
				
			}

		}
		String st = notifyAllBarberShopObservers(toDelete);
		return st;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	

}
