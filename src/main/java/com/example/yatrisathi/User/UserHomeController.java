package com.example.yatrisathi.User;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ResourceBundle;

import static com.example.yatrisathi.Function.*;

public class UserHomeController {

    @FXML
    public Label welcome, User, home, guides, attractions, bookings, reports, admin, switch_user, alerts, settings, logout;
    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;
    @FXML
    public Label total_tourists, total_guides, total_attractions;

    private ResourceBundle bundle;

    @FXML
    private void initialize() {
        bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        welcome.setText(bundle.getString("welcome"));
        home.setText(bundle.getString("home"));
        guides.setText(bundle.getString("guides"));
        attractions.setText(bundle.getString("attractions"));
        bookings.setText(bundle.getString("bookings"));
        reports.setText(bundle.getString("reports"));
        admin.setText(bundle.getString("admin"));
        switch_user.setText(bundle.getString("switch_user"));
        alerts.setText(bundle.getString("alerts"));
        settings.setText(bundle.getString("settings"));
        logout.setText(bundle.getString("logout"));

        User.setText(", " + Session.getUsername() + "!");

        if(Session.getAuthority() == 1){

            reports.setVisible(false);
            admin.setVisible(false);

        }else if(Session.getAuthority() == 2){

            reports.setVisible(true);
            admin.setVisible(true);

        }else if(Session.getAuthority() == 3){

            reports.setVisible(false);
            admin.setVisible(false);
            switch_user.setVisible(true);

        }

        total_tourists.setText(DashBoardTourists());
        total_guides.setText(DashBoardGuides());
        total_attractions.setText(DashBoardAttraction());
        setupMenuItemHoverEffects();
        setupCardHoverEffects();
    }

    @FXML
    private void SwitchUser(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");
    }

    private void setupMenuItemHoverEffects() {
        setupMenuItemHover(home);
        setupMenuItemHover(guides);
        setupMenuItemHover(attractions);
        setupMenuItemHover(bookings);
        setupMenuItemHover(reports);
        setupMenuItemHover(admin);
        setupMenuItemHover(switch_user);
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
        String normalStyle = "-fx-background-color: #E7C6A7; -fx-cursor: hand; -fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: #c7a483; -fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02; -fx-background-radius: 10;";
        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    private void setActiveMenuItem(Label activeItem) {
        resetMenuItem(home);
        resetMenuItem(guides);
        resetMenuItem(attractions);
        resetMenuItem(bookings);
        resetMenuItem(reports);
        resetMenuItem(admin);
        resetMenuItem(switch_user);
        resetMenuItem(alerts);
        resetMenuItem(settings);
        resetMenuItem(logout);
        activeItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; -fx-cursor: hand;");
    }

    private void resetMenuItem(Label menuItem) {
        menuItem.setStyle("-fx-cursor: hand;");
    }

    @FXML
    private void handleHomeClick(MouseEvent event) throws IOException {
        setActiveMenuItem(home);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserHome.fxml", "YatriSathi");
    }

    @FXML
    private void handleGuidesClick(MouseEvent event) throws IOException {
        setActiveMenuItem(guides);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserGuide.fxml", "YatriSathi");
    }

    @FXML
    private void handleAttractionsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(attractions);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserAttractions.fxml", "YatriSathi");
    }

    @FXML
    private void handleBookingsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(bookings);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserBookings.fxml", "YatriSathi");
    }

    @FXML
    private void handleReportsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(reports);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserReports.fxml", "YatriSathi");
    }

    @FXML
    private void handleAdminClick(MouseEvent event) throws IOException {
        setActiveMenuItem(admin);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");
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