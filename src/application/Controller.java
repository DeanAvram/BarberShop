package application;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.JOptionPane;

import exceptions.InvalidFirstNameException;
import exceptions.InvalidLastNameException;
import exceptions.InvalidPhoneNumber;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Client;
import model.Client.eGender;
import model.IService;
import model.SaveToSql;
import model.SingeltonBarbershop;
import model.Vaction;

public class Controller implements Initializable {

	@FXML
	private TableView<Appointment> tableView;

	@FXML
	private TableColumn<Appointment, Integer> idCol;

	@FXML
	private TableColumn<Appointment, String> dateCol;

	@FXML
	private TableColumn<Appointment, String> timeCol;

	@FXML
	private TableColumn<Appointment, String> serviceCol;

	@FXML
	private TableView<Appointment> barberTableView;

	@FXML
	private TableColumn<Appointment, String> timeColB;

	@FXML
	private TableColumn<Appointment, String> serviceColB;

	@FXML
	private TableColumn<Appointment, String> customerPhoneB;

	@FXML
	private TabPane tabView;

	@FXML
	private Tab tab1;

	@FXML
	private Tab tab2;

	@FXML
	private Tab tab3;

	@FXML
	private Tab tab4;

	@FXML
	private TabPane tabChange;

	@FXML
	private Tab tab1InChange;

	@FXML
	private Tab tab2InChange;

	@FXML
	private Tab tab3InChange;

	@FXML
	private Tab tab4InChange;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextField firstNameInput;

	@FXML
	private TextField lastNameInput;

	@FXML
	private ComboBox<LocalTime> timeCmb;

	@FXML
	private ComboBox<String> serviceCmb;

	@FXML
	private ComboBox<String> phoneCmb;

	@FXML
	private TextField phoneTf;
	
	@FXML
	private ComboBox<eGender> genderCmb;

	@FXML
	private Button switchToTab2Btn;
	
	@FXML
	private Label nameLbl;

	@FXML
	private Label firstNameLbl;

	@FXML
	private Label lastNameLbl;

	@FXML
	private Label phoneNumberLbl;

	@FXML
	private Label dateLbl;

	@FXML
	private Label timeLbl;

	@FXML
	private Label serviceLbl;

	@FXML
	private Label serviceLen;

	@FXML
	private Label servicePrice;

	@FXML
	private Label dateBtn;

	@FXML
	private Button submitShow;

	@FXML
	private Button showAppsToChange;

	@FXML
	private Button ConfirmChange;

	@FXML
	private Label sunday;

	@FXML
	private Label monday;

	@FXML
	private Label tuesday;

	@FXML
	private Label wednesday;

	@FXML
	private Label thursday;

	@FXML
	private Label friday;

	@FXML
	private Label saturday;

	@FXML
	private DatePicker startDate;

	@FXML
	private DatePicker endDate;
	
	@FXML
	private Button chosenAppToEdit;
	
	@FXML
	private Button chosenAppToDelete;
	
	@FXML
	private Label enjoyVacationLbl;

	@FXML
	private ImageView vacationImage;
	
	

	private Stage stage;
	private Scene scene;
	private Parent root;
	private static SingeltonBarbershop b = SingeltonBarbershop.getInstance();
	private String maleServices[] = { "Male Hair Cut", "Kid Hair Cut" };
	private String femaleServices[] = {"Female Hair Cut", "Japanese Straightening", "Coloring"};
	private String phonesPrefix[] = { "050", "051", "052", "053", "054", "058" };
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (startDate != null)
			initStartDate();
		if (datePicker != null)
			initDatePickerGeneral(datePicker);
		if (sunday != null) {
			Label days[] = {monday, tuesday, wednesday, thursday, friday, saturday, sunday};
			for (int i = 0; i < DayOfWeek.values().length; i++) {
				if (!b.getDaysOff().contains(DayOfWeek.values()[i])) {
					days[i].setText(b.getStartTime() + "-" + b.getEndTime());
				}
			}
		}
		
		if (phoneCmb != null)
			phoneCmb.getItems().addAll(phonesPrefix);
	}

	public static void shutDown() {
		b.saveData();
	}

	public void initializeDatePikcer() {
		initDatePickerGeneral(datePicker);
	}
	
	public void initGenderCmb () {
		genderCmb.getItems().add(eGender.FEMALE);
		genderCmb.getItems().add(eGender.MALE);
	}
	
	public void submitShowAllAppointment(ActionEvent event) {
		initTable();
		String phoneNumber = phoneCmb.getValue() + phoneTf.getText();
		Client c = b.getClientByPhone(phoneNumber);
		if (c == null) {
			JOptionPane.showMessageDialog(null, "You Do Not Have Any Appointments");
			phoneCmb.setValue(null);
			phoneTf.setText(null);
			return;
		}
		ObservableList<Appointment> appointments = tableView.getItems();
		appointments.addAll(c.getMyAppointments());
		tableView.setItems(appointments);
	}

	public void submitShowAllAppointmentForChange(ActionEvent event) {
		initTable();
		String phoneNumber = phoneCmb.getValue() + phoneTf.getText();
		Client c = b.getClientByPhone(phoneNumber);
		if (c == null || c.getMyAppointments().size() == 0) {
			JOptionPane.showMessageDialog(null, "You Do Not Have Any Appointments");
			phoneCmb.setValue(null);
			phoneTf.setText(null);
			return;
		}
		ObservableList<Appointment> appointments = tableView.getItems();
		appointments.addAll(c.getMyAppointments());
		tableView.setItems(appointments);
	}
	
	public void initTable () {
		tableView.getItems().clear();
		idCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("ID"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dateOfAppointment"));
		timeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("timeOfAppointment"));
		serviceCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("serviceName"));
	}
	
	public void initServicesCmb (eGender gender) {
		serviceCmb.getItems().clear();
		if (gender == eGender.MALE) 
			serviceCmb.getItems().addAll(maleServices);
		else 
			serviceCmb.getItems().addAll(femaleServices);
	}
	
	
	public void phoneEntered (ActionEvent event) {
		if (checkValidValues(phoneCmb.getValue(), phoneTf)) {
			Client c = b.getClientByPhone(phoneCmb.getValue() + phoneTf.getText());
			if (c != null) {
				initServicesCmb(c.getGender());
				tab2.setDisable(false);
				tabView.getSelectionModel().selectNext();
			}
			else {
				nameLbl.setVisible(true);
				firstNameInput.setVisible(true);
				lastNameInput.setVisible(true);
				genderCmb.setVisible(true);
				initGenderCmb();
			}
		}
		else 
			JOptionPane.showMessageDialog(null, "Please Fill All The Fields");
	}

	public void switchToTab2(ActionEvent event) {
		if (!checkValidValues(firstNameInput, lastNameInput, genderCmb.getValue())) {
			JOptionPane.showMessageDialog(null, "Please Fill All The Fields");
			return;
		}
		String firstName = firstNameInput.getText();
		String lastName = lastNameInput.getText();
		String phone = phoneCmb.getValue() + phoneTf.getText();
		eGender gender = genderCmb.getValue();
		try {
			b.addClientToList(firstName, lastName, phone, gender);
		}catch (InvalidPhoneNumber | InvalidFirstNameException | InvalidLastNameException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "\nPlease Try Again");
			if (e instanceof InvalidPhoneNumber) {
				phoneCmb.setValue(null);
				phoneTf.setText(null);
			}
			else if (e instanceof InvalidFirstNameException)
				firstNameInput.setText(null);
			else
				lastNameInput.setText(null);
			return;
		}

		initServicesCmb(genderCmb.getValue());
		tab2.setDisable(false);
		tabView.getSelectionModel().selectNext();
	}

	public void switchToTab3(ActionEvent event) {
		if (!checkValidValues(serviceCmb.getValue())) {
			JOptionPane.showMessageDialog(null, "Please Fill All The Fields");
			return;
		}
		initDatePickerGeneral(datePicker);
		tab3.setDisable(false);
		tabView.getSelectionModel().selectNext();
	}
	
	public void dateSelected (ActionEvent event) {
		timeCmb.getItems().clear();
		int timeOfAppointment = b.getServiceByName(serviceCmb.getValue()).getLength();
		Set<Appointment> list = b.getSortedSetByDate(datePicker.getValue());
		LocalTime t = LocalTime.parse("09:00");
		for (Appointment a : list) {
			while (t.compareTo(LocalTime.parse("20:00")) <= 0 && 
					t.plusMinutes(timeOfAppointment).compareTo(a.getTimeOfAppointment()) <= 0) {
				timeCmb.getItems().add(t);
				t = t.plusMinutes(30);
			}
			int time = b.getServiceByName(a.getServiceName()).getLength();
			t = a.getTimeOfAppointment().plusMinutes(time);
		}
		while (t.compareTo(LocalTime.parse("20:00")) <= 0) {
			timeCmb.getItems().add(t);
			t = t.plusMinutes(30);
		}
	}
	
	

	public void addAppointment(ActionEvent event) {
		if (!checkValidValues(datePicker.getValue(), timeCmb.getValue())) {
			JOptionPane.showMessageDialog(null, "Please Fill All The Fields");
			return;
		}
		LocalDate date = datePicker.getValue();
		String dateStr = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		LocalTime time = timeCmb.getValue();
		String serviceName = serviceCmb.getValue();
		String phoneNumber = phoneCmb.getValue() + phoneTf.getText();
		b.addBarberShopObserver(phoneNumber, date, time, serviceName); 
		String firstName = b.getClientByPhone(phoneNumber).getFirstName();
		firstNameLbl.setText(firstName.toUpperCase() + ",");
		dateLbl.setText(dateStr);
		timeLbl.setText(time.toString());
		serviceLbl.setText(serviceName);
		tab4.setDisable(false);
		tabView.getSelectionModel().selectNext();
		tab1.setDisable(true);
		tab2.setDisable(true);
		tab3.setDisable(true);
	}

	public void mouseClicked (MouseEvent event) {
		chosenAppToDelete.setDisable(false);
		chosenAppToEdit.setDisable(false);
	}
	

	public void switchToTab2OnChange(ActionEvent event) {
		String phone = phoneCmb.getValue() + phoneTf.getText();
		initServicesCmb(b.getClientByPhone(phone).getGender());
		tab2InChange.setDisable(false);
		tabChange.getSelectionModel().selectNext();
	}
	
	public void switchToTab3OnChange(ActionEvent event) {
		if (!checkValidValues(serviceCmb.getValue())) {
			JOptionPane.showMessageDialog(null, "Please Fill All The Fields");
			return;
		}
		initDatePickerGeneral(datePicker);
		tab3InChange.setDisable(false);
		tabChange.getSelectionModel().selectNext();
	}
	
	public void switchToTab4OnChange(ActionEvent event) {
		editAppointment(event);
		LocalDate date = datePicker.getValue();
		LocalTime time = timeCmb.getValue();
		String serviceName = serviceCmb.getValue();
		dateLbl.setText(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		timeLbl.setText(time.toString());
		serviceLbl.setText(serviceName);
		tab4InChange.setDisable(false);
		tabChange.getSelectionModel().selectNext();
	}


	public void editAppointment(ActionEvent event) {
		if (!checkValidValues(serviceCmb.getValue())) {
			JOptionPane.showMessageDialog(null, "Please Fill All The Fields");
			return;
		}
		Client c = b.getClientByPhone(phoneCmb.getValue() + phoneTf.getText());
		int idToChange = tableView.getSelectionModel().getSelectedItem().getID();
		for (Appointment a : c.getMyAppointments()) {
			if (a.getID() == idToChange) {
				a.setDateOfAppointment(datePicker.getValue());
				LocalTime time = timeCmb.getValue();
				a.setTimeOfAppointment(time);
				a.setServiceName(serviceCmb.getValue());
				a.setCustomerPhone(phoneCmb.getValue() + phoneTf.getText());
				firstNameLbl.setText(c.getFirstName().toUpperCase() + ",");
				String dateStr = a.getDateOfAppointment().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				dateLbl.setText(dateStr);
				timeLbl.setText(time.toString());
				serviceLbl.setText(a.getServiceName());
			}

		}
		tab4InChange.setDisable(false);
		tabChange.getSelectionModel().selectNext();
		tab1InChange.setDisable(true);
		tab2InChange.setDisable(true);
		tab3InChange.setDisable(true);
	}
	
	public void deleteAppointment(ActionEvent event) throws IOException {
		Appointment a = tableView.getSelectionModel().getSelectedItem();
		b.removeBarberShopObserver(a);
		JOptionPane.showMessageDialog(null, "Appointment Deleted");
		switchToMainMenu(event);
	}


	

	public void switchServiceLables(ActionEvent event) {
		String serviceName = serviceCmb.getValue();
		IService iService = b.getServiceByName(serviceName);
		serviceLen.setText(iService.getLength() + " Min");
		servicePrice.setText(iService.getPrice() + " ILS");
		serviceLen.setVisible(true);
		servicePrice.setVisible(true);
	}

	

	public void submitShowAppointmentForDate(ActionEvent event) {
		LocalDate d = datePicker.getValue();
		String s = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		dateLbl.setText(s);
		dateLbl.setVisible(true);
		timeColB.setCellValueFactory(new PropertyValueFactory<Appointment, String>("timeOfAppointment"));
		serviceColB.setCellValueFactory(new PropertyValueFactory<Appointment, String>("serviceName"));
		customerPhoneB.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customerPhone"));
		ObservableList<Appointment> appointments = barberTableView.getItems();
		appointments.clear();
		ArrayList<Appointment> allAppointments = b.getAppointmentsByDate(d);
		if (allAppointments == null) {
			datePicker.hide();
			JOptionPane.showMessageDialog(null, "No Appointment In That Date");
			return;
		}
		appointments.addAll(allAppointments);
		barberTableView.setItems(appointments);
	}

	public void initDatePickerGeneral(DatePicker dp) {
		dp.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) < 0 || date.compareTo(today.plusYears(1)) > 0);
				if (dp == endDate)
					setDisable(date.compareTo(startDate.getValue()) < 0);
				for (int  i = 0; i < b.getVactions().size(); i++) {
					LocalDate start = b.getVactions().get(i).getStartDate();
					LocalDate end = b.getVactions().get(i).getEndDate();
					setDisable(isDisable() || (date.compareTo(start) >= 0 && date.compareTo(end) <= 0));
				}
				for (DayOfWeek d : b.getDaysOff()) {
					setDisable(isDisable() || date.getDayOfWeek().equals(d));
				}
				if (isDisable())
					setStyle("-fx-background-color: #ffc0cb; -fx-text-fill: #5E7982;");
			}
		});
	}

	public void initStartDate() {
		initDatePickerGeneral(startDate);

	}

	public void initEndDate(ActionEvent event) {
		endDate.setDisable(false);
		initDatePickerGeneral(endDate);
	}

	public void confirmVactionDate(ActionEvent event) throws IOException {
		Vaction v = new Vaction(startDate.getValue(), endDate.getValue());
		b.addVactionToList(v);
		SaveToSql.saveVacations(v);
		String st = b.AppointmentToDelete(startDate.getValue(), endDate.getValue());
		JOptionPane.showMessageDialog(null, st);
		enjoyVacationLbl.setVisible(true);
		vacationImage.setVisible(true);
	}

	public boolean checkValidValues(Object... args) {
		for (Object o : args) {
			if (o == null)
				return false;
			if (o instanceof TextField) {
				TextField tf = (TextField) o;
				if (tf.getText().isBlank()) {
					return false;
				}
			}
		}
		return true;
	}


	public void switchToCustomer(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToBarber(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("BarberView.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToChooseAnAppointment(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ChooseAppointmentView.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToChangeAnAppointment(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ChangeAppointmentView.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToShowAppointments(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ShowAppointmentsView.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToChangeDays(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ChooseAvailabilityView.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToCreateAppointment(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("CreateAnAppointment.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToMainMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void showAllAppointmentToBarber(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ShowAppointmentsToBarberView.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
