package com.example.yatrisathi.Guest;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

public class LoginController {


    @FXML
    private Label welcome;
    public Label home, guides, attractions, alerts;
    public Label login, signup, close, warning;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField Username;
    public PasswordField Password;

    @FXML
    private void initialize() {

        ResourceBundle bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        welcome.setText(bundle.getString("welcome"));
        home.setText(bundle.getString("home"));
        guides.setText(bundle.getString("guides"));
        attractions.setText(bundle.getString("attractions"));
        alerts.setText(bundle.getString("alerts"));

        welcome.setText("Welcome, Guest!");

        setupCloseHoverEffects();
        setupButtonEffects();

    }

    @FXML
    private void CloseLogin(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Home.fxml", "YatriSathi");

    }

    @FXML
    private void HandelSignup(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Signup.fxml", "YatriSathi");

    }

    @FXML
    private void Login(MouseEvent event) {
        String username = Username.getText();
        String password = Password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            if(username.isEmpty() && password.isEmpty()) {
                warning.setText("Please fill all the fields");
            } else if(password.isEmpty()) {
                warning.setText("Please Enter Password");
            } else {
                warning.setText("Please Enter Username");
            }
        } else if (validateCredentials(username, password) == 1) {
            try {
                warning.setText("Login Successful");

                // Get the stage from the event source
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();

                // Load the new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/yatrisathi/User/UserHome.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("YatriSathi");
                stage.show();

            } catch (IOException e) {
                System.out.println("Error loading UserHome: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (validateCredentials(username, password) == 2) {
            try {
                warning.setText("Login Successful");

                // Similar approach for admin
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/yatrisathi/User/UserHome.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("YatriSathi - Admin");
                stage.show();

            } catch (IOException e) {
                System.out.println("Error loading AdminHome: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (validateCredentials(username, password) == 3) {
            try {
                warning.setText("Login Successful");

                // Similar approach for guide
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/yatrisathi/Guide/GuidesHome.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("YatriSathi - Guide");
                stage.show();

            } catch (IOException e) {
                System.out.println("Error loading GuidesHome: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            warning.setText("Account not Found");
        }
    }

    public static int validateCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/users.txt"))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                System.out.println("Line " + lineNumber + ": " + line);

                String[] parts = line.split(",");
                System.out.println("Parts count: " + parts.length);
                for (int i = 0; i < parts.length; i++) {
                    System.out.println("  Part " + i + ": '" + parts[i].trim() + "'");
                }

                if (parts.length >= 4) {
                    String fileUsername = parts[0].trim();
                    String fileEmail = parts[1].trim();
                    String filePassword = parts[2].trim();
                    String authority = parts[3].trim();

                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        System.out.println("Match found! Authority: " + authority);
                        if (authority.equalsIgnoreCase("user")) {
                            Session.setUsername(fileUsername);
                            Session.setEmail(fileEmail);
                            Session.setAuthority(1);
                            return 1;
                        } else if (authority.equalsIgnoreCase("admin")) {
                            Session.setUsername(fileUsername);
                            Session.setEmail(fileEmail);
                            Session.setAuthority(2);
                            return 2;
                        } else if (authority.equalsIgnoreCase("guide")) {
                            Session.setUsername(fileUsername);
                            Session.setEmail(fileEmail);
                            Session.setAuthority(3);
                            return 3;
                        }
                    }
                } else {
                    System.out.println("Skipping line - not enough parts");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return 0;
    }

    // Effects
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

    private void setupButtonEffects() {

        setupButtonHover(login);

    }

    private void setupButtonHover(Label card) {
        String normalStyle = "-fx-background-color: " + "#E7C6A7" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#c7a483" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

}