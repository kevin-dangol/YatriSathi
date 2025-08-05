package com.example.yatrisathi;

import com.example.yatrisathi.Admin.UserAdminController;
import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsController {

    @FXML
    public TextField Username, Email;
    @FXML
    public ChoiceBox<String> Language;
    @FXML
    public Label Save, GuideApply, warning;

    private ResourceBundle bundle;

    @FXML
    private void initialize() {
        setupButtonHoverEffects();
        loadCurrentUserData();

        // Setup language choice box
        Language.getItems().addAll("English", "Nepali");
        Language.setValue("English");

         bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
         Language.setValue(Session.getLocale().getLanguage().equals("ne") ? "Nepali" : "English");
         loadLanguage();
         Language.setOnAction(event -> switchLanguage());
    }

    private void loadCurrentUserData() {
        // Pre-fill fields with current session data
        if (Session.getUsername() != null) {
            Username.setText(Session.getUsername());
        }
        if (Session.getEmail() != null) {
            Email.setText(Session.getEmail());
        }
    }

    @FXML
    private void switchLanguage() {
        String selectedLanguage = Language.getValue();
        Session.setLocale(selectedLanguage.equals("Nepali") ? "Nepali" : "English");
        loadLanguage();
    }

    private void loadLanguage() {
         bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
         Username.setPromptText(bundle.getString("username"));
         Email.setPromptText(bundle.getString("email"));
         Save.setText(bundle.getString("save"));
         GuideApply.setText(bundle.getString("guide_apply"));
        warning.setText("");
    }

    private void setupButtonHoverEffects() {
        setupNormalButtonHover(GuideApply);
        setupNormalButtonHover(Save);
    }

    private void setupNormalButtonHover(Label card) {
        String normalStyle = "-fx-background-color: #c7a483; -fx-cursor: hand; -fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: #E7C6A7; -fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02; -fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    @FXML
    private void handelApply(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/GuideApply.fxml", "Apply");
    }

    @FXML
    private void handelSave(MouseEvent event) throws IOException {
        String username = Username.getText().trim();
        String email = Email.getText().trim();

        // Validate input
        if (username.isEmpty() || email.isEmpty()) {
            warning.setText("All fields are required!");
            warning.setStyle("-fx-text-fill: red;");
            return;
        }

        // Basic email validation
        if (!email.contains("@") || !email.contains(".")) {
            warning.setText("Please enter a valid email address!");
            warning.setStyle("-fx-text-fill: red;");
            return;
        }

        if (saveUserSettings(username, email)) {
            warning.setText("Settings saved successfully!");
            warning.setStyle("-fx-text-fill: green;");

            // Update session with new data
            Session.setUsername(username);
            Session.setEmail(email);
        } else {
            warning.setText("Failed to save settings. Please try again.");
            warning.setStyle("-fx-text-fill: red;");
        }
    }

    private boolean saveUserSettings(String newUsername, String newEmail) {
        try {
            Path path = Paths.get("src/main/resources/users.txt");
            if (!Files.exists(path)) {
                warning.setText("Users file not found!");
                warning.setStyle("-fx-text-fill: red;");
                return false;
            }

            List<String> lines = Files.readAllLines(path);
            boolean userFound = false;
            String currentUsername = Session.getUsername();

            // Check if new username already exists (but not for current user)
            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length >= 4) {
                    String existingUsername = parts[0].trim();
                    if (existingUsername.equals(newUsername) && !existingUsername.equals(currentUsername)) {
                        warning.setText("Username already exists!");
                        warning.setStyle("-fx-text-fill: red;");
                        return false;
                    }
                }
            }

            // Update the user's information
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(", ");
                if (parts.length >= 4) {
                    String existingUsername = parts[0].trim();
                    if (existingUsername.equals(currentUsername)) {

                        String password = parts[2].trim();
                        String authority = parts[3].trim();
                        lines.set(i, newUsername + ", " + newEmail + ", " + password + ", " + authority);
                        userFound = true;
                        break;

                    }
                }
            }

            if (!userFound) {
                warning.setText("User not found in database!");
                warning.setStyle("-fx-text-fill: red;");
                return false;
            }

            // Write back to file
            Files.write(path, lines);
            return true;

        } catch (IOException e) {
            System.err.println("Error saving user settings: " + e.getMessage());
            return false;
        }
    }

}