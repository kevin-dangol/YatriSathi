package com.example.yatrisathi.Guest;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;

import java.io.*;
import java.util.ResourceBundle;

public class SignupController {


    @FXML
    public Label welcome;
    public Label home, guides, attractions, alerts;
    public Label Login, Signup, close, warning;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField Username, Email;
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
    private void HandelLogin(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Login.fxml", "YatriSathi");

    }

    @FXML
    private void Signup(MouseEvent event) throws IOException {

        String username = Username.getText();
        String email = Email.getText();
        String password = Password.getText();

        if (!email.contains("@") || !email.contains(".")) {

            warning.setText("Enter a Valid Email");

        }

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {

            if(username.isEmpty() && password.isEmpty() && email.isEmpty()) {

                warning.setText("Empty Fields");

            }else if(password.isEmpty()) {

                warning.setText("Enter Password");

            }else if(email.isEmpty()) {

                warning.setText("Enter a Valid Email");

            }else {

                warning.setText("Enter Username");

            }

        } else {

            if(SaveCredentials(username,password,email)) {

                ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Login.fxml", "YatriSathi");

            }

        }

    }

    @FXML
    public static boolean SaveCredentials(String username, String password, String email) {

        String filePath = "src/main/resources/users.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            String newUser = username + "," + email + "," + password+ "," + "User";
            writer.write(newUser);
            writer.newLine();
            return true;

        } catch (IOException e) {

            System.out.println("Error writing to user file: " + e.getMessage());

        }

        return false;

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

        setupButtonHover(Signup);

    }

    private void setupButtonHover(Label card) {
        String normalStyle = "-fx-background-color: " + "#E7C6A7" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#c7a483" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

}