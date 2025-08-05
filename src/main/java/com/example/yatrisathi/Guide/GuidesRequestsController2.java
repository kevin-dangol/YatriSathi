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

public class GuidesRequestsController2 {


    @FXML
    public Label welcome;
    public Label home, requests, bookings;
    public Label alerts, settings, logout;
    public Label AttractionName4, AttractionName5, AttractionName6, AttractionName7;
    public Label AttractionTours4, AttractionTours5, AttractionTours6, AttractionTours7;
    public Label AttractionRaters4, AttractionRaters5, AttractionRaters6, AttractionRaters7;
    public Label Rating4, Rating5, Rating6, Rating7;
    public Label RequestName, RequestName1, RequestName2, RequestName3;
    public Label RequestTourist, RequestTourist1, RequestTourist2, RequestTourist3;
    public Label RequestDate, RequestDate1, RequestDate2, RequestDate3;
    public Label RequestLocation, RequestLocation1, RequestLocation2, RequestLocation3;

    @FXML
    public TextField AttractionSearch;

    @FXML
    public ChoiceBox LocationSort, Sort;

    @FXML
    public Button AcceptButton, AcceptButton1, AcceptButton2, AcceptButton3;
    public Button RejectButton, RejectButton1, RejectButton2, RejectButton3;


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

        setupButtonHover(AcceptButton, "#c7a483", "#E7C6A7");
        setupButtonHover(AcceptButton1, "#c7a483", "#E7C6A7");
        setupButtonHover(AcceptButton2, "#c7a483", "#E7C6A7");
        setupButtonHover(AcceptButton3, "#c7a483", "#E7C6A7");
        setupButtonHover(RejectButton, "#D95151", "#F65D5D");
        setupButtonHover(RejectButton1, "#D95151", "#F65D5D");
        setupButtonHover(RejectButton2, "#D95151", "#F65D5D");
        setupButtonHover(RejectButton3, "#D95151", "#F65D5D");

    }

    private void setupButtonHover(Button card, String NBG, String BG) {
        String normalStyle = "-fx-background-color: " + NBG + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + BG + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
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