package com.example.yatrisathi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Font.loadFont(getClass().getResourceAsStream("/fonts/Kosugi-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Koulen-Regular.ttf"), 12);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/yatrisathi/Guest/Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("YatriSathi");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}