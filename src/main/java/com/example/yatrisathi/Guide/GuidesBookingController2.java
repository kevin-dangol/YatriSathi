package com.example.yatrisathi.Guide;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ResourceBundle;

public class GuidesBookingController2 {


    @FXML
    public Label welcome;
    public Label home, requests, bookings;
    public Label alerts, settings, logout;
    public Label BookingName, BookingName1, BookingName2, BookingName3;
    public Label BookingLocation, BookingLocation1, BookingLocation2, BookingLocation3;
    public Label BookingTourists, BookingTourists1, BookingTourists2, BookingTourists3;
    public Label BookingDate,  BookingDate1, BookingDate2, BookingDate3;
    public Label BookingStatus, BookingStatus1, BookingStatus2, BookingStatus3;

    @FXML
    public TextField BookingSearch;

    @FXML
    public ChoiceBox LocationSort, Sort;

    @FXML
    public Button CancelButton, CancelButton1, CancelButton2, CancelButton3;
    public Button RescheduleButton, RescheduleButton1, RescheduleButton2, RescheduleButton3;

    private ResourceBundle bundle;

    @FXML
    private void initialize() {

        setupMenuItemHoverEffects();
        setupButtonHoverEffects();

        welcome.setText("Welcome, " + Session.getUsername() + "!");

    }

    private void setupMenuItemHoverEffects() {

        requests.setOnMouseEntered(e -> {
        });

        setupMenuItemHover(home);
        setupMenuItemHover(bookings);
        setupMenuItemHover(requests);
        setupMenuItemHover(alerts);
        setupMenuItemHover(settings);
        setupMenuItemHover(logout);

    }

    private void setupMenuItemHover(Label menuItem) {
        menuItem.setOnMouseEntered(e -> {
            menuItem.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 5; -fx-cursor: hand;");
        });

        menuItem.setOnMouseExited(e -> {
            menuItem.setStyle("-fx-cursor: hand;");
        });
    }

    private void setupButtonHoverEffects() {

        setupButtonHover(CancelButton);
        setupButtonHover(CancelButton1);
        setupButtonHover(CancelButton2);
        setupButtonHover(CancelButton3);
        setupButtonHover(RescheduleButton);
        setupButtonHover(RescheduleButton1);
        setupButtonHover(RescheduleButton2);
        setupButtonHover(RescheduleButton3);

    }

    private void setupButtonHover(Button card) {
        String normalStyle = "-fx-background-color: " + "#c7a483" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#E7C6A7" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

//        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
//        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    // Helper method to set active menu item
    private void setActiveMenuItem(Label activeItem) {

        resetMenuItem(home);
        resetMenuItem(bookings);
        resetMenuItem(requests);
        resetMenuItem(alerts);
        resetMenuItem(settings);
        resetMenuItem(logout);

        activeItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; -fx-cursor: hand;");
    }

    private void resetMenuItem(Label menuItem) {
        menuItem.setStyle("-fx-cursor: hand;");
    }

    // Menu item click handlers
    @FXML
    private void handleHomeClick(MouseEvent event) throws IOException {

        setActiveMenuItem(home);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");

    }

    @FXML
    private void handleBookingsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(bookings);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesBookings.fxml", "YatriSathi");

    }

    @FXML
    private void handleRequestsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(requests);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesRequests.fxml", "YatriSathi");

    }

    @FXML
    private void handleAlertsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(alerts);
        ChangeScene.openScene("/com/example/yatrisathi/Alert.fxml", "YatriSathi");

    }

    @FXML
    private void handleSettingsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(settings);
        ChangeScene.openScene("/com/example/yatrisathi/Settings.fxml", "YatriSathi");

    }

    @FXML
    private void handleLogoutClick(MouseEvent event) throws IOException {

        setActiveMenuItem(logout);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Home.fxml", "YatriSathi");

    }
}