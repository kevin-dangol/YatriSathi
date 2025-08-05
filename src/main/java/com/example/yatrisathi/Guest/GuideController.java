package com.example.yatrisathi.Guest;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class GuideController implements Initializable {

    @FXML
    public Label welcome;
    public Label home, guides, attractions, bookings, reports, admin, switch_user;
    public Label alerts, login;
    public Label GuideTours, GuideTours1, GuideTours2, GuideTours3, GuideTours4, GuideTours11, GuideTours12, GuideTours13;
    public Label GuideRaters, GuideRaters1,  GuideRaters2, GuideRaters3, GuideRaters4, GuideRaters11, GuideRaters12, GuideRaters13;
    public Label GuideLocation, GuideLocation1, GuideLocation2, GuideLocation3, GuideLocation4, GuideLocation11, GuideLocation12, GuideLocation13;
    public Label GuidesFreeDate1, GuidesFreeDate2, GuidesFreeDate3, GuidesFreeDate4;
    public Label GuideName1, GuideName2, GuideName3, GuideName4, GuideName5, GuideName6, GuideName7, GuideName8;
    public Label Rating, Rating1, Rating2, Rating3, Rating4, Rating11, Rating12, Rating13;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField GuideSearch;

    @FXML
    public ChoiceBox<String> LocationSort, Sort;

    @FXML
    public Button BookButton, BookButton1, BookButton2, BookButton3, BookButton4, BookButton5, BookButton6, BookButton7;

    private List<Guide> allGuides = new ArrayList<>();
    private List<Guide> displayedGuides = new ArrayList<>();

    // Guide data class
    public static class Guide {
        public String firstName, lastName, tours, address, location, number, nextFreeDate, raters, rating;

        public Guide(String firstName, String lastName, String Tours, String address, String location,
                     String number, String nextFreeDate, String raters, String rating) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.tours = Tours;
            this.address = address;
            this.location = location;
            this.number = number;
            this.nextFreeDate = nextFreeDate;
            this.raters = raters;
            this.rating = rating;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        welcome.setText(bundle.getString("welcome"));
        home.setText(bundle.getString("home"));
        guides.setText(bundle.getString("guides"));
        attractions.setText(bundle.getString("attractions"));
        alerts.setText(bundle.getString("alerts"));
        login.setText(bundle.getString("login"));

        welcome.setText("Welcome, Guest!");

        if(Session.getAuthority() == 1){
            reports.setVisible(false);
            admin.setVisible(false);
        }else if(Session.getAuthority() == 2){
            reports.setVisible(true);
            admin.setVisible(true);
        }else if(Session.getAuthority() == 3){
            reports.setVisible(false);
            admin.setVisible(false);
            switch_user.setVisible(true);
        }

        setupMenuItemHoverEffects();
        setupButtonHoverEffects();
        loadGuides(); // Load guides first
        setupLocationFilter(); // Then setup filter
        setupSearchFilter(); // Setup search functionality
    }

    private void loadGuides() {
        allGuides.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/guides.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    Guide guide = new Guide(
                            parts[0].trim(), // firstName
                            parts[1].trim(), // lastName
                            parts[2].trim(), // tours
                            parts[3].trim(), // address
                            parts[4].trim(), // location
                            parts[5].trim(), // number
                            parts[6].trim(), // nextFreeDate
                            parts[7].trim(), // raters
                            parts[8].trim()  // rating
                    );
                    allGuides.add(guide);
                }
            }
            br.close();
            displayedGuides = new ArrayList<>(allGuides);
            displayGuides();
        } catch (Exception e) {
            System.out.println("Error loading guides: " + e.getMessage());
        }
    }

    private void setupLocationFilter() {
        LocationSort.getItems().clear();
        LocationSort.getItems().add("All Locations");

        Set<String> locations = new HashSet<>();
        for (Guide guide : allGuides) {
            locations.add(guide.location);
        }
        LocationSort.getItems().addAll(locations);
        LocationSort.setValue("All Locations");

        LocationSort.setOnAction(e -> filterGuides());
    }

    private void setupSearchFilter() {
        GuideSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterGuides();
        });
    }

    private void filterGuides() {
        String selectedLocation = LocationSort.getValue();
        String searchText = GuideSearch.getText().toLowerCase().trim();

        displayedGuides = new ArrayList<>();

        for (Guide guide : allGuides) {
            boolean matchesLocation = selectedLocation == null ||
                    selectedLocation.equals("All Locations") ||
                    guide.location.equals(selectedLocation);

            boolean matchesSearch = searchText.isEmpty() ||
                    guide.getFullName().toLowerCase().contains(searchText) ||
                    guide.tours.toLowerCase().contains(searchText) ||
                    guide.location.toLowerCase().contains(searchText) ||
                    guide.address.toLowerCase().contains(searchText);

            if (matchesLocation && matchesSearch) {
                displayedGuides.add(guide);
            }
        }

        displayGuides();
    }

    private void displayGuides() {
        clearAllPanels();

        int guidesToShow = Math.min(8, displayedGuides.size());
        for (int i = 0; i < guidesToShow; i++) {
            Guide guide = displayedGuides.get(i);
            setGuideToPanel(i, guide);
        }
    }

    private void clearAllPanels() {
        // Clear left panel (4 guides)
        GuideName1.setText("");
        GuideTours1.setText("");
        GuideLocation1.setText("");
        GuideRaters1.setText("");
        GuidesFreeDate1.setText("");
        Rating1.setText("");

        GuideName2.setText("");
        GuideTours11.setText("");
        GuideLocation11.setText("");
        GuideRaters11.setText("");
        GuidesFreeDate2.setText("");
        Rating11.setText("");

        GuideName3.setText("");
        GuideTours12.setText("");
        GuideLocation12.setText("");
        GuideRaters12.setText("");
        GuidesFreeDate3.setText("");
        Rating12.setText("");

        GuideName4.setText("");
        GuideTours13.setText("");
        GuideLocation13.setText("");
        GuideRaters13.setText("");
        GuidesFreeDate4.setText("");
        Rating13.setText("");
    }

    private void setGuideToPanel(int panelIndex, Guide guide) {
        switch (panelIndex) {
            case 0: // First panel (left side)
                GuideName1.setText(guide.getFullName());
                GuideTours1.setText(guide.tours);
                GuideLocation1.setText(guide.location);
                GuideRaters1.setText(guide.number);
                GuidesFreeDate1.setText(guide.nextFreeDate);
                Rating1.setText(guide.rating + "/5");
                break;
            case 1: // Second panel (left side)
                GuideName2.setText(guide.getFullName());
                GuideTours11.setText(guide.tours);
                GuideLocation11.setText(guide.location);
                GuideRaters11.setText(guide.number);
                GuidesFreeDate2.setText(guide.nextFreeDate);
                Rating11.setText(guide.rating + "/5");
                break;
            case 2: // Third panel (left side)
                GuideName3.setText(guide.getFullName());
                GuideTours12.setText(guide.tours);
                GuideLocation12.setText(guide.location);
                GuideRaters12.setText(guide.number);
                GuidesFreeDate3.setText(guide.nextFreeDate);
                Rating12.setText(guide.rating + "/5");
                break;
            case 3: // Fourth panel (left side)
                GuideName4.setText(guide.getFullName());
                GuideTours13.setText(guide.tours);
                GuideLocation13.setText(guide.location);
                GuideRaters13.setText(guide.number);
                GuidesFreeDate4.setText(guide.nextFreeDate);
                Rating13.setText(guide.rating + "/5");
                break;
            case 4: // First panel (right side)
                GuideName5.setText(guide.getFullName());
                GuideTours.setText(guide.tours);
                GuideLocation.setText(guide.location);
                GuideRaters.setText(guide.number);
                Rating.setText(guide.rating + "/5");
                break;
            case 5: // Second panel (right side)
                GuideName6.setText(guide.getFullName());
                GuideTours2.setText(guide.tours);
                GuideLocation2.setText(guide.location);
                GuideRaters2.setText(guide.number);
                Rating2.setText(guide.rating + "/5");
                break;
            case 6: // Third panel (right side)
                GuideName7.setText(guide.getFullName());
                GuideTours3.setText(guide.tours);
                GuideLocation3.setText(guide.location);
                GuideRaters3.setText(guide.number);
                Rating3.setText(guide.rating + "/5");
                break;
            case 7: // Fourth panel (right side)
                GuideName8.setText(guide.getFullName());
                GuideTours4.setText(guide.tours);
                GuideLocation4.setText(guide.location);
                GuideRaters4.setText(guide.number);
                Rating4.setText(guide.rating + "/5");
                break;
        }
    }

    @FXML
    public void handleGuideBooking(MouseEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Guide selectedGuide = null;

        // Find which button was clicked and get corresponding guide info
        if (clickedButton == BookButton) {
            selectedGuide = findGuideByName(GuideName1.getText());
        } else if (clickedButton == BookButton1) {
            selectedGuide = findGuideByName(GuideName2.getText());
        } else if (clickedButton == BookButton2) {
            selectedGuide = findGuideByName(GuideName3.getText());
        } else if (clickedButton == BookButton3) {
            selectedGuide = findGuideByName(GuideName4.getText());
        } else if (clickedButton == BookButton4) {
            selectedGuide = findGuideByName(GuideName5.getText());
        } else if (clickedButton == BookButton5) {
            selectedGuide = findGuideByName(GuideName6.getText());
        } else if (clickedButton == BookButton6) {
            selectedGuide = findGuideByName(GuideName7.getText());
        } else if (clickedButton == BookButton7) {
            selectedGuide = findGuideByName(GuideName8.getText());
        }

        if (selectedGuide != null) {
            // Store selected guide in a static variable or session for the booking controller
            SelectedGuideHolder.setSelectedGuide(selectedGuide);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Login.fxml", "YatriSathi");
        }
    }

    private Guide findGuideByName(String fullName) {
        for (Guide guide : displayedGuides) {
            if (guide.getFullName().equals(fullName)) {
                return guide;
            }
        }
        return null;
    }

    // Helper class to hold selected guide data
    public static class SelectedGuideHolder {
        private static Guide selectedGuide;

        public static void setSelectedGuide(Guide guide) {
            selectedGuide = guide;
        }

        public static Guide getSelectedGuide() {
            return selectedGuide;
        }
    }

    private void setupMenuItemHoverEffects() {

        guides.setOnMouseEntered(e -> {
        });

        setupMenuItemHover(home);
        setupMenuItemHover(attractions);
        setupMenuItemHover(login);

    }

    private void setupMenuItemHover(Label menuItem) {
        menuItem.setOnMouseEntered(e -> {
            menuItem.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 5; -fx-cursor: hand;");
        });

        menuItem.setOnMouseExited(e -> {
            menuItem.setStyle("-fx-cursor: hand;");
        });
    }

    private void setupButtonHoverEffects() {

        setupButtonHover(BookButton);
        setupButtonHover(BookButton1);
        setupButtonHover(BookButton2);
        setupButtonHover(BookButton3);
        setupButtonHover(BookButton4);
        setupButtonHover(BookButton5);
        setupButtonHover(BookButton6);
        setupButtonHover(BookButton7);

    }

    private void setupButtonHover(Button card) {
        String normalStyle = "-fx-background-color: " + "#c7a483" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#E7C6A7" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    // Helper method to set active menu item
    private void setActiveMenuItem(Label activeItem) {

        resetMenuItem(home);
        resetMenuItem(guides);
        resetMenuItem(attractions);
        resetMenuItem(login);

        activeItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; -fx-cursor: hand;");
    }

    private void resetMenuItem(Label menuItem) {
        menuItem.setStyle("-fx-cursor: hand;");
    }

    // Menu item click handlers
    @FXML
    private void handleHomeClick(MouseEvent event) throws IOException {

        setActiveMenuItem(home);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Home.fxml", "YatriSathi");

    }

    @FXML
    private void handleGuidesClick(MouseEvent event) throws IOException {

        setActiveMenuItem(guides);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Guide.fxml", "YatriSathi");

    }

    @FXML
    private void handleAttractionsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(attractions);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Attractions.fxml", "YatriSathi");

    }

    @FXML
    private void handleAlertsClick(MouseEvent event) throws IOException {

        setActiveMenuItem(alerts);
        ChangeScene.openScene("/com/example/yatrisathi/Alert.fxml", "YatriSathi");

    }

    @FXML
    private void handleButtonClick(MouseEvent event) throws IOException {

        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Login.fxml", "Login");

    }

    @FXML
    private void handleLoginClick(MouseEvent event) throws IOException {

        setActiveMenuItem(login);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Login.fxml", "Login");


    }

}