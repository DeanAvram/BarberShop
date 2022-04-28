package model;

import java.time.LocalDate;

public interface BarberShopObserver {
	String notifyBarberShopUpdated(String firstName, LocalDate date, String serviceName);
}
