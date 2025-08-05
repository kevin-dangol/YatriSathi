package com.example.yatrisathi;

import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;

public class AlertController {

    @FXML
    public Label AlertTitle, AlertTitle1, AlertTitle2;
    public Label AlertDescription, AlertDescription1, AlertDescription2;

    @FXML
    private void initialize() {

//        ResourceBundle bundle = ResourceBundle.getBundle("com.example.yatrisathi.lang.messages", Session.getLocale());
//        AlertTitle.setText(bundle.getString("alerts"));

    }

}