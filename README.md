# Barbershop

A Java application that manages appointment reservations at a barbershop.
The frontend is generated using the Javafx scene builder.
We used MySQL as a database.

We used three different design patterns:

1. Singelton - restricts the instantiation of a class to a singular instance. We used this pattern to restrict the barbershop to a single instance.
2. Factory - Creating objects without specifying the exact class of the object, using an interface instead.
3. Observer - An object maintains a list of its dependents' observers and notifies them automatically of any state changes. We used this pattern to notify some customers when the barber canceled their appointment.

