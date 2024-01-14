package hotel.userinterface;

import hotel.model.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;


import java.time.LocalDate;

public class HotelOverzichtController {
    @FXML
    private Label hotelnaamLabel;

    @FXML
    private ListView<String> boekingenListView;

    @FXML
    private DatePicker overzichtDatePicker;

    private Hotel hotel = Hotel.getHotel();

    public void initialize() {
        hotelnaamLabel.setText("Boekingen hotel " + hotel.getNaam());
        overzichtDatePicker.setValue(LocalDate.now());
        toonBoekingen();
    }

    @FXML
    public void toonVorigeDag(ActionEvent actionEvent) {
        LocalDate dagEerder = overzichtDatePicker.getValue().minusDays(1);
        overzichtDatePicker.setValue(dagEerder);
        toonBoekingen();
    }

    @FXML
    public void toonVolgendeDag(ActionEvent actionEvent) {
        LocalDate dagLater = overzichtDatePicker.getValue().plusDays(1);
        overzichtDatePicker.setValue(dagLater);
        toonBoekingen();
    }

    @FXML
    private void nieuweBoeking(ActionEvent actionEvent) {
        try {
            // Open Boekingen.fxml for nieuwe boeking
            // This logic should be updated based on your project structure
            // For now, let's just print a message
            System.out.println("Open Boekingen.fxml for nieuwe boeking");

            // Load the Boekingen.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Boekingen.fxml"));
            Parent root = loader.load();

            // Get the controller from the loader
            BoekingenController boekingenController = loader.getController();

            // Set the reference to HotelOverzichtController
            boekingenController.setHotelOverzichtController(this);  // Assuming you're in HotelOverzichtController

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Set the stage reference in BoekingenController
            boekingenController.setStage(stage);

            stage.show();
        } catch (IOException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
        }
    }


    @FXML
    public void toonBoekingen() {
        ObservableList<String> boekingen = FXCollections.observableArrayList();

        // Get bookings for the selected date from the Hotel object
        LocalDate selectedDate = overzichtDatePicker.getValue();
        hotel.getBoekingen().forEach(boeking -> {
            if (boeking.getAankomstDatum().equals(selectedDate) || boeking.getVertrekDatum().equals(selectedDate)) {
                boekingen.add(boeking.toString()); // Adjust the toString() method as needed
            }
        });

        boekingenListView.setItems(boekingen);
    }

    public void updateBoekingen() {
        // Your existing logic to populate boekingenListView
        toonBoekingen();
    }
}
