package hotel.userinterface;

import hotel.model.Hotel;
import hotel.model.KamerType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import javafx.stage.Stage;

public class BoekingenController {
    @FXML private Label errorLabel;
    @FXML private TextField naamTextField;
    @FXML private TextField adresTextField;
    @FXML private DatePicker aankomstDatePicker;
    @FXML private DatePicker vertrekDatePicker;
    @FXML private ComboBox<KamerType> kamertypeComboBox;

    private HotelOverzichtController hotelOverzichtController;
    private Stage stage;  // Add a field to store the reference to the stage

    // Setter method to set the stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private Hotel hotel = Hotel.getHotel();

    public void setHotelOverzichtController(HotelOverzichtController hotelOverzichtController) {
        this.hotelOverzichtController = hotelOverzichtController;
    }

    @FXML
    private void initialize() {
        // Initialize the kamertypeComboBox with available KamerTypes
        ObservableList<KamerType> kamerTypes = FXCollections.observableArrayList(hotel.getKamerTypen());
        kamertypeComboBox.setItems(kamerTypes);
        System.out.println("BoekingenController initialized!");
    }

    @FXML
    private void resetForm() {
        // Reset all fields to their initial state
        naamTextField.clear();
        adresTextField.clear();
        aankomstDatePicker.setValue(null);
        vertrekDatePicker.setValue(null);
        kamertypeComboBox.getSelectionModel().clearSelection();
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    @FXML
    private void handleBoekButtonAction() {
        System.out.println("Boek button clicked!");

        // Ensure that errorLabel is not null before invoking setText
        if (errorLabel != null) {
            errorLabel.setText("");  // Clear any previous error messages
        }

        try {
            // Get the selected values from the form
            String naam = naamTextField.getText();
            String adres = adresTextField.getText();
            LocalDate aankomstDatum = aankomstDatePicker.getValue();
            LocalDate vertrekDatum = vertrekDatePicker.getValue();
            KamerType selectedKamerType = kamertypeComboBox.getValue();

            // Perform basic validation
            if (naam.isEmpty() || adres.isEmpty() || aankomstDatum == null || vertrekDatum == null || selectedKamerType == null) {
                throw new Exception("Vul alle velden in");
            }

            // Perform validation for enddate
            if (aankomstDatum.isBefore(LocalDate.now())) {
                throw new Exception("De aankomstdatum moet vandaag of later zijn");
            }
            
            // Perform validation for end date being later than the arrival date
            if (aankomstDatum.isAfter(vertrekDatum)) {
                throw new Exception("De vertrekdatum moet later zijn dan de aankomstdatum");
            }

            // Try to create a new booking
            hotel.voegBoekingToe(aankomstDatum, vertrekDatum, naam, adres, selectedKamerType);

            // Reset the form after a successful booking
            resetForm();

            // Call a method on HotelOverzichtController to update UI
            if (hotelOverzichtController != null) {
                hotelOverzichtController.updateBoekingen();
            }

            // Close the Boekingen screen
            if (stage != null) {
                stage.close();
            }
        } catch (Exception e) {
            // Handle the exception, display an error message
            if (errorLabel != null) {
                errorLabel.setText("Fout bij het maken van de boeking: " + e.getMessage());

                System.out.println(e.getMessage());
            }
        }
    }
}
