package hotel.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import hotel.model.Boeking;
import javafx.stage.Stage;


public class BoekingenInvoerenController {
    @FXML private DatePicker aankomstDatePicker;
    @FXML private DatePicker vertrekDatePicker;
    @FXML private DatePicker datePicker;
    @FXML private TextField guestNameTextField;

    private HotelOverzichtController hotelOverzichtController;

    public void setHotelOverzichtController(HotelOverzichtController hotelOverzichtController) {
        this.hotelOverzichtController = hotelOverzichtController;
    }

    @FXML
    public void submitBooking(ActionEvent event) {
        // Retrieve data from the input fields
        LocalDate selectedDate = datePicker.getValue();
        String guestName = guestNameTextField.getText();

        try {
            // Add the new booking to the hotel
            hotelOverzichtController.getHotel().voegBoekingToe(selectedDate, selectedDate, guestName, "Address", null);

            // Update the booking overview in the HotelOverzichtController
            hotelOverzichtController.toonBoekingen();

            // Close the booking window
            closeBookingWindow();
        } catch (Exception e) {
            // Handle the exception, e.g., show an error message
            e.printStackTrace();
        }
    }

    // Other methods for handling booking creation

    private void closeBookingWindow() {
        // Close the current stage (window)
        ((Stage) datePicker.getScene().getWindow()).close();
    }
}
