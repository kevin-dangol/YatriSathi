package com.example.yatrisathi.Guest;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.example.yatrisathi.Function.*;

public class HomeController {


    @FXML
    public Label welcome;
    public Label home, guides, attractions;
    public Label alerts, login;

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
        guides.setText(bundle.getString("guides"));
        attractions.setText(bundle.getString("attractions"));
        alerts.setText(bundle.getString("alerts"));
        login.setText(bundle.getString("login"));

        welcome.setText("Welcome, Guest!");

        setupMenuItemHoverEffects();
        setupCardHoverEffects();

        //dashboards
        total_tourists.setText(DashBoardTourists());
        total_guides.setText(DashBoardGuides());
        total_attractions.setText(DashBoardAttraction());

    }

    private void setupMenuItemHoverEffects() {

        home.setOnMouseEntered(e -> {
        });

        setupMenuItemHover(guides);
        setupMenuItemHover(attractions);
        setupMenuItemHover(login);

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
        resetMenuItem(guides);
        resetMenuItem(attractions);
        resetMenuItem(login);

        activeItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; -fx-cursor: hand;");
    }

    private void resetMenuItem(Label menuItem) {
        menuItem.setStyle("-fx-cursor: hand;");
    }

    // Menu item click handlers
    @FXML
    private void handleHomeClick(MouseEvent event) throws IOException {

        setActiveMenuItem(home);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Home.fxml", "YatriSathi");

    }

    @FXML
    private void handleGuidesClick(MouseEvent event) throws IOException {

        setActiveMenuItem(guides);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Guide.fxml", "YatriSathi");

    }

    @FXML
    private void handleAttractionsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(attractions);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Attractions.fxml", "YatriSathi");

    }

    @FXML
    private void handleAlertsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(alerts);
        ChangeScene.openScene("/com/example/yatrisathi/Alert.fxml", "YatriSathi");

    }

    @FXML
    private void handleLoginClick(MouseEvent event) throws IOException {

        setActiveMenuItem(login);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Login.fxml", "Login");


    }

}