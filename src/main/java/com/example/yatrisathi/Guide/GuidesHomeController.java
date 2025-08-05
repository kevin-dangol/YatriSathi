package com.example.yatrisathi.Guide;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ResourceBundle;

import static com.example.yatrisathi.Function.*;

public class GuidesHomeController {


    @FXML
    public Label welcome, User;
    public Label home, requests, bookings, switch_user1;
    public Label alerts, settings, logout;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public Label total_tourists, total_guides, total_attractions;

    private ResourceBundle bundle;

    @FXML
    private void initialize() {

        ResourceBundle bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        welcome.setText(bundle.getString("welcome"));
        home.setText(bundle.getString("home"));
        bookings.setText(bundle.getString("bookings"));
        switch_user1.setText(bundle.getString("switch_user"));
        alerts.setText(bundle.getString("alerts"));
        settings.setText(bundle.getString("settings"));
        logout.setText(bundle.getString("logout"));
        requests.setText(bundle.getString("requests"));

        User.setText(", " + Session.getUsername() + "!");

        setupMenuItemHoverEffects();
        setupCardHoverEffects();

        //for dashboard.txt
        total_tourists.setText(DashBoardTourists());
        total_guides.setText(DashBoardGuides());
        total_attractions.setText(DashBoardAttraction());

    }

    @FXML
    private void SwitchUser(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserHome.fxml", "YatriSathi");

    }

    // Effects
    @FXML
    private void setupMenuItemHoverEffects() {

        home.setOnMouseEntered(e -> {
        });

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

    private void setupCardHoverEffects() {
        setupCardHover(tourists_card);
        setupCardHover(guides_card);
        setupCardHover(attractions_card);
    }

    private void setupCardHover(FlowPane card) {
        String normalStyle = "-fx-background-color: " + "#E7C6A7" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#c7a483" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

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