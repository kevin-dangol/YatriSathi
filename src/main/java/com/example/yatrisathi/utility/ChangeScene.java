package com.example.yatrisathi.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ChangeScene {

    public static void switchScene(ActionEvent event, String fxmlPath, String Title, int width, int height) throws IOException {

        Parent root = FXMLLoader.load(ChangeScene.class.getResource(fxmlPath));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(Title);
        stage.setScene(new Scene(root, width, height));
        stage.show();

    }

    public static void switchScene(ActionEvent event, String fxmlPath, String Title) throws IOException {

        Parent root = FXMLLoader.load(ChangeScene.class.getResource(fxmlPath));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(Title);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void switchScene(MouseEvent event, String fxmlPath, String Title) throws IOException {

        Parent root = FXMLLoader.load(ChangeScene.class.getResource(fxmlPath));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(Title);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void openScene(String fxmlPath, String Title, int width, int height) throws IOException {

        FXMLLoader loader = new FXMLLoader(ChangeScene.class.getResource(fxmlPath));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle(Title);
        newStage.setScene(new Scene(root, width, height));
        newStage.show();

    }

    public static void openScene(String fxmlPath, String Title) throws IOException {

        FXMLLoader loader = new FXMLLoader(ChangeScene.class.getResource(fxmlPath));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle(Title);
        newStage.setScene(new Scene(root));
        newStage.show();

    }

}
