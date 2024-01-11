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
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;
import hotel.model.Boeking;
import java.util.stream.Collectors;

import java.io.IOException;

import java.time.LocalDate;

public class HotelOverzichtController {
    @FXML private Label hotelnaamLabel;
    @FXML private ListView boekingenListView;
    @FXML private DatePicker overzichtDatePicker;

    private Hotel hotel = Hotel.getHotel();

    public void initialize() {
        hotelnaamLabel.setText("Boekingen hotel " + hotel.getNaam());
        overzichtDatePicker.setValue(LocalDate.now());
        toonBoekingen();
    }

    public void toonVorigeDag(ActionEvent actionEvent) {
        LocalDate dagEerder = overzichtDatePicker.getValue().minusDays(1);
        overzichtDatePicker.setValue(dagEerder);
    }

    public void toonVolgendeDag(ActionEvent actionEvent) {
        LocalDate dagLater = overzichtDatePicker.getValue().plusDays(1);
        overzichtDatePicker.setValue(dagLater);
    }

    public void handleDateSelected(LocalDate selectedDate) {
        overzichtDatePicker.setValue(selectedDate);
        toonBoekingen();
    }

    @FXML

    public void nieuweBoeking() {
        System.out.println("Opening BoekingenInvoeren.fxml...");

        try {
            // Load the 'BoekingenInvoeren.fxml' file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoekingenInvoeren.fxml"));
            Parent root = loader.load();

            // Create a new stage for the booking input
            Stage bookingStage = new Stage();
            bookingStage.initModality(Modality.APPLICATION_MODAL);
            bookingStage.setTitle("Nieuwe Boeking");
            bookingStage.setScene(new Scene(root));

            // Show the booking input stage
            bookingStage.showAndWait();

            // Update the booking overview after closing the booking input stage
            toonBoekingen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toonBoekingen() {
        System.out.println("toonBoekingen() is nog niet geïmplementeerd!");
        ObservableList<String> boekingStrings = FXCollections.observableArrayList();

        // Filter bookings based on the selected date
        LocalDate selectedDate = overzichtDatePicker.getValue();
        List<Boeking> filteredBoekingen = hotel.getBoekingen().stream()
                .filter(boeking -> boeking.getAankomstDatum().equals(selectedDate))
                .collect(Collectors.toList());

        // Convert filtered bookings to strings and update the ListView
        filteredBoekingen.forEach(boeking -> boekingStrings.add(boeking.toString()));
        boekingenListView.setItems(boekingStrings);
    }

    public Hotel getHotel() {
        return hotel;
    }

    private BoekingenInvoerenController boekingenInvoerenController;

    public void setBoekingenInvoerenController(BoekingenInvoerenController boekingenInvoerenController) {
        this.boekingenInvoerenController = boekingenInvoerenController;
    }
}