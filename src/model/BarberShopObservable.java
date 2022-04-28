package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface BarberShopObservable {
	void addBarberShopObserver(String phoneNumber, LocalDate date, LocalTime time, String serviceName);
	void removeBarberShopObserver(BarberShopObserver bo);
	String notifyAllBarberShopObservers(ArrayList<BarberShopObserver> observers);

}
