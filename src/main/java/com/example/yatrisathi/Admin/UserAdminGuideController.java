package com.example.yatrisathi.Admin;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserAdminGuideController {

    @FXML
    public Label welcome, User, home, guides, attractions, bookings, reports, admin, alerts, settings, logout;
    public Label close;

    @FXML
    public TextField FirstName, LastName, Address, Number;

    @FXML
    public ChoiceBox<String> Location;

    @FXML
    public Label CertificationView, CertificationAdd, GuideKick, GuideSave;

    private UserAdminController.Guide currentGuide;

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
        setupLocationChoiceBox();
        loadGuideData();
    }

    private void setupLocationChoiceBox() {
        Location.getItems().addAll(
                "Kathmandu", "Pokhara", "Chitwan", "Lumbini", "Everest",
                "Annapurna", "Bandipur", "Gorkha", "Mustang", "Dolpo"
        );
    }

    private void loadGuideData() {
        currentGuide = UserAdminController.SelectedGuideHolder.getSelectedGuide();
        if (currentGuide != null) {
            FirstName.setText(currentGuide.firstName);
            LastName.setText(currentGuide.lastName);
            Address.setText(currentGuide.address);
            Number.setText(currentGuide.number);
            Location.setValue(currentGuide.location);
        }
    }

    private void setupButtonHoverEffects() {
        //Normal
        setupNormalButtonHover(CertificationView);
        setupNormalButtonHover(CertificationAdd);
        setupNormalButtonHover(GuideSave);

        //Reds
        setupRedButtonHover(GuideKick);
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
    private void handelCertificationView(MouseEvent event) throws IOException {
        System.out.println("View Certification for: " + FirstName.getText() + " " + LastName.getText());
    }

    @FXML
    private void handelCertificationAdd(MouseEvent event) throws IOException {
        System.out.println("Add Certification for: " + FirstName.getText() + " " + LastName.getText());
    }

    @FXML
    private void handelSaveButton(MouseEvent event) throws IOException {
        if (validateInput()) {
            saveGuideData();
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");
        }
    }

    @FXML
    private void handelKickButton(MouseEvent event) throws IOException {
        if (currentGuide != null) {
            removeGuideData();
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");
        }
    }

    private boolean validateInput() {
        if (FirstName.getText().trim().isEmpty()) {
            System.out.println("First name cannot be empty");
            return false;
        }
        if (LastName.getText().trim().isEmpty()) {
            System.out.println("Last name cannot be empty");
            return false;
        }
        if (Address.getText().trim().isEmpty()) {
            System.out.println("Address cannot be empty");
            return false;
        }
        if (Number.getText().trim().isEmpty()) {
            System.out.println("Phone number cannot be empty");
            return false;
        }
        if (Location.getValue() == null) {
            System.out.println("Please select a location");
            return false;
        }
        return true;
    }

    private void saveGuideData() {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/guides.txt"));
            String line;
            boolean updated = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9 && currentGuide != null &&
                        parts[0].trim().equals(currentGuide.firstName) &&
                        parts[1].trim().equals(currentGuide.lastName)) {
                    // Update existing guide
                    String updatedLine = FirstName.getText().trim() + "," +
                            LastName.getText().trim() + "," +
                            parts[2].trim() + "," + // Keep existing tours
                            Address.getText().trim() + "," +
                            Location.getValue() + "," +
                            Number.getText().trim() + "," +
                            parts[6].trim() + "," + // Keep existing nextFreeDate
                            parts[7].trim() + "," + // Keep existing raters
                            parts[8].trim();        // Keep existing rating
                    lines.add(updatedLine);
                    updated = true;
                } else {
                    lines.add(line);
                }
            }
            br.close();

            // If it's a new guide (not updated), add it
            if (!updated) {
                String newLine = FirstName.getText().trim() + "," +
                        LastName.getText().trim() + "," +
                        "Cultural Tours," + // Default tours
                        Address.getText().trim() + "," +
                        Location.getValue() + "," +
                        Number.getText().trim() + "," +
                        "Available," + // Default nextFreeDate
                        "0," + // Default raters
                        "0";   // Default rating
                lines.add(newLine);
            }

            // Write back to file
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/guides.txt"));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println("Guide data saved successfully");

        } catch (Exception e) {
            System.out.println("Error saving guide data: " + e.getMessage());
        }
    }

    private void removeGuideData() {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/guides.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9 && currentGuide != null &&
                        parts[0].trim().equals(currentGuide.firstName) &&
                        parts[1].trim().equals(currentGuide.lastName)) {
                    // Skip this line (remove the guide)
                    continue;
                } else {
                    lines.add(line);
                }
            }
            br.close();

            // Write back to file
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/guides.txt"));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println("Guide removed successfully");

        } catch (Exception e) {
            System.out.println("Error removing guide: " + e.getMessage());
        }
    }
}