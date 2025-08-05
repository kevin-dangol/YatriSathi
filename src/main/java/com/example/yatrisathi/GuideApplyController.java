package com.example.yatrisathi;

import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ResourceBundle;

public class GuideApplyController {

    @FXML
    public TextField FirstName, LastName, Address, Number;
    @FXML
    public ChoiceBox Location;
    @FXML
    public Label Apply, Certification;

    private ResourceBundle bundle;

    @FXML
    private void initialize() {
        bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        FirstName.setPromptText(bundle.getString("first_name"));
        LastName.setPromptText(bundle.getString("last_name"));
        Address.setPromptText(bundle.getString("address"));
        Number.setPromptText(bundle.getString("number"));
        Apply.setText(bundle.getString("apply"));
        Certification.setText(bundle.getString("certification"));
        setupButtonHoverEffects();
    }

    private void setupButtonHoverEffects() {
        setupNormalButtonHover(Certification);
        setupNormalButtonHover(Apply);
    }

    private void setupNormalButtonHover(Label card) {
        String normalStyle = "-fx-background-color: #c7a483; -fx-cursor: hand; -fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: #E7C6A7; -fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02; -fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    @FXML
    private void handelCertification(MouseEvent event) throws IOException {
        System.out.println(bundle.getString("upload"));
    }

    @FXML
    private void handelSend(MouseEvent event) throws IOException {
        System.out.println(bundle.getString("sent"));
    }
}