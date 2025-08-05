package com.example.yatrisathi.Admin;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.ResourceBundle;

public class UserAdminRequestController {

    @FXML
    public Label welcome, User, home, guides, attractions, bookings, reports, admin, alerts, settings, logout;
    public Label close;
    public Label FirstName, LastName, Address, Location, Number;

    @FXML
    public Label Certification, Accept, Reject;


    @FXML
    private void initialize() {

        ResourceBundle bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        welcome.setText(bundle.getString("welcome"));
        home.setText(bundle.getString("home"));
        guides.setText(bundle.getString("guides"));
        attractions.setText(bundle.getString("attractions"));
        bookings.setText(bundle.getString("bookings"));
        reports.setText(bundle.getString("reports"));
        admin.setText(bundle.getString("admin"));
        alerts.setText(bundle.getString("alerts"));
        settings.setText(bundle.getString("settings"));
        logout.setText(bundle.getString("logout"));

        User.setText(", " + Session.getUsername() + "!");

        setupCloseHoverEffects();
        setupButtonHoverEffects();

    }

    private void setupButtonHoverEffects() {

        //Normal
        setupNormalButtonHover(Certification);
        setupNormalButtonHover(Accept);

        //Reds
        setupRedButtonHover(Reject);


    }

    private void setupNormalButtonHover(Label card) {
        String normalStyle = "-fx-background-color: " + "#c7a483" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#E7C6A7" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    private void setupRedButtonHover(Label card) {
        String normalStyle = "-fx-background-color: " + "#CB7B78" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#D8413C" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    @FXML
    private void setupCloseHoverEffects() {

        setupCloseHover(close);

    }

    private void setupCloseHover(Label menuItem) {
        menuItem.setOnMouseEntered(e -> {
            menuItem.setTextFill(Paint.valueOf("#000000"));
        });

        menuItem.setOnMouseExited(e -> {
            menuItem.setTextFill(Paint.valueOf("#ff0000"));
        });
    }

    @FXML
    private void handleHomeClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserHome.fxml", "YatriSathi");

    }

    @FXML
    private void handleGuidesClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserGuide.fxml", "YatriSathi");

    }

    @FXML
    private void handleBookingsClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserBookings.fxml", "YatriSathi");

    }

    @FXML
    private void handleAttractionsClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserAttractions.fxml", "YatriSathi");

    }

    @FXML
    private void handleReportsClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserReports.fxml", "YatriSathi");

    }

    @FXML
    private void handleAdminClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");

    }

    @FXML
    private void handleAlertsClick(MouseEvent event) throws IOException {

        ChangeScene.openScene("/com/example/yatrisathi/Alert.fxml", "YatriSathi");

    }

    @FXML
    private void handleSettingsClick(MouseEvent event) throws IOException {

        ChangeScene.openScene("/com/example/yatrisathi/Settings.fxml", "YatriSathi");

    }

    @FXML
    private void handleLogoutClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Home.fxml", "YatriSathi");

    }

    @FXML
    private void CloseLogin(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");

    }

    @FXML
    private void handelCertification(MouseEvent event) throws IOException {

        System.out.println("Certification");

    }

    @FXML
    private void handelAcceptButton(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");

    }

    @FXML
    private void handelRejectButton(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");

    }

}