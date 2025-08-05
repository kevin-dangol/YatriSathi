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

public class UserAdminAttractionController {

    @FXML
    public Label welcome, User, home, guides, attractions, bookings, reports, admin, alerts, settings, logout;
    public Label close;

    @FXML
    public TextField AttractionName, AttractionAddress;

    @FXML
    public ChoiceBox<String> AttractionLocation;

    @FXML
    public Label ImageView, ImageAdd, AttractionRemove, AttractionSave;

    private UserAdminController.Attraction currentAttraction;

    @FXML
    private void initialize() {
        System.out.println("Initializing UserAdminAttractionController...");

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

        // Add null check for AttractionLocation
        setupLocationChoiceBox();
        loadAttractionData();
    }

    private void setupLocationChoiceBox() {
        try {
            AttractionLocation.getItems().addAll(
                    "Kathmandu", "Pokhara", "Chitwan", "Lumbini", "Everest",
                    "Annapurna", "Bandipur", "Gorkha", "Mustang", "Dolpo"
            );
        } catch (Exception e) {
            System.err.println("Error setting up location choice box: " + e.getMessage());
        }
    }

    private void loadAttractionData() {
        try {
            currentAttraction = UserAdminController.SelectedAttractionHolder.getSelectedAttraction();
            if (currentAttraction != null) {
                if (AttractionName != null) {
                    AttractionName.setText(currentAttraction.name);
                }
                if (AttractionAddress != null) {
                    AttractionAddress.setText(currentAttraction.address);
                }
                if (AttractionLocation != null) {
                    AttractionLocation.setValue(currentAttraction.location);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading attraction data: " + e.getMessage());
        }
    }

    private void setupButtonHoverEffects() {
        // Check each control before setting up hover effects
        if (ImageView != null) {
            setupNormalButtonHover(ImageView);
        } else {
            System.err.println("ERROR: ImageView is null!");
        }

        if (ImageAdd != null) {
            setupNormalButtonHover(ImageAdd);
        } else {
            System.err.println("ERROR: ImageAdd is null!");
        }

        if (AttractionSave != null) {
            setupNormalButtonHover(AttractionSave);
        } else {
            System.err.println("ERROR: AttractionSave is null!");
        }

        if (AttractionRemove != null) {
            setupRedButtonHover(AttractionRemove);
        } else {
            System.err.println("ERROR: AttractionRemove is null!");
        }
    }

    private void setupNormalButtonHover(Label card) {
        try {
            String normalStyle = "-fx-background-color: " + "#c7a483" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
            String hoverStyle = "-fx-background-color: " + "#E7C6A7" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

            card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
            card.setOnMouseExited(e -> card.setStyle(normalStyle));
        } catch (Exception e) {
            System.err.println("Error setting up normal button hover for " + card + ": " + e.getMessage());
        }
    }

    private void setupRedButtonHover(Label card) {
        try {
            String normalStyle = "-fx-background-color: " + "#CB7B78" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
            String hoverStyle = "-fx-background-color: " + "#D8413C" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

            card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
            card.setOnMouseExited(e -> card.setStyle(normalStyle));
        } catch (Exception e) {
            System.err.println("Error setting up red button hover for " + card + ": " + e.getMessage());
        }
    }

    @FXML
    private void setupCloseHoverEffects() {
        if (close != null) {
            setupCloseHover(close);
        } else {
            System.err.println("ERROR: close label is null!");
        }
    }

    private void setupCloseHover(Label menuItem) {
        try {
            menuItem.setOnMouseEntered(e -> {
                menuItem.setTextFill(Paint.valueOf("#000000"));
            });

            menuItem.setOnMouseExited(e -> {
                menuItem.setTextFill(Paint.valueOf("#ff0000"));
            });
        } catch (Exception e) {
            System.err.println("Error setting up close hover: " + e.getMessage());
        }
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
    private void handelImageView(MouseEvent event) throws IOException {
        if (AttractionName != null) {
            System.out.println("View Image for: " + AttractionName.getText());
        } else {
            System.out.println("View Image - AttractionName is null!");
        }
    }

    @FXML
    private void handelImageAdd(MouseEvent event) throws IOException {
        if (AttractionName != null) {
            System.out.println("Add Image for: " + AttractionName.getText());
        } else {
            System.out.println("Add Image - AttractionName is null!");
        }
    }

    @FXML
    private void handelSaveButton(MouseEvent event) throws IOException {
        if (validateInput()) {
            saveAttractionData();
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");
        }
    }

    @FXML
    private void handelRemoveButton(MouseEvent event) throws IOException {
        if (currentAttraction != null) {
            removeAttractionData();
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");
        } else {
            System.out.println("No attraction selected to remove!");
        }
    }

    private boolean validateInput() {
        if (AttractionName == null || AttractionName.getText().trim().isEmpty()) {
            System.out.println("Attraction name cannot be empty");
            return false;
        }
        if (AttractionAddress == null || AttractionAddress.getText().trim().isEmpty()) {
            System.out.println("Attraction description cannot be empty");
            return false;
        }
        if (AttractionLocation == null || AttractionLocation.getValue() == null) {
            System.out.println("Please select a location");
            return false;
        }
        return true;
    }

    private void saveAttractionData() {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/attraction.txt"));
            String line;
            boolean updated = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && currentAttraction != null &&
                        parts[0].trim().equals(currentAttraction.name)) {
                    // Update existing attraction
                    String updatedLine = AttractionName.getText().trim() + "," +
                            AttractionLocation.getValue() + "," +
                            AttractionAddress.getText().trim() + "," +
                            parts[3].trim() + "," + // Keep existing raters
                            parts[4].trim();        // Keep existing rating
                    lines.add(updatedLine);
                    updated = true;
                } else {
                    lines.add(line);
                }
            }
            br.close();

            // If it's a new attraction (not updated), add it
            if (!updated) {
                String newLine = AttractionName.getText().trim() + "," +
                        AttractionLocation.getValue() + "," +
                        AttractionAddress.getText().trim() + "," +
                        "0," + // Default raters
                        "0";   // Default rating
                lines.add(newLine);
            }

            // Write back to file
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/attraction.txt"));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println("Attraction data saved successfully");

        } catch (Exception e) {
            System.out.println("Error saving attraction data: " + e.getMessage());
        }
    }

    private void removeAttractionData() {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/attraction.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && currentAttraction != null &&
                        parts[0].trim().equals(currentAttraction.name)) {
                    // Skip this line (remove the attraction)
                    continue;
                } else {
                    lines.add(line);
                }
            }
            br.close();

            // Write back to file
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/attraction.txt"));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println("Attraction removed successfully");

        } catch (Exception e) {
            System.out.println("Error removing attraction: " + e.getMessage());
        }
    }
}