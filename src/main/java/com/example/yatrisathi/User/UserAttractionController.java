package com.example.yatrisathi.User;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.*;
import java.net.URL;
import java.util.*;

public class UserAttractionController implements Initializable {

    @FXML
    public Label welcome, User;
    public Label home, guides, attractions, bookings, reports, admin, switch_user;
    public Label alerts, settings, logout;
    public Label AttractionName, AttractionName1, AttractionName2, AttractionName3, AttractionName4, AttractionName5, AttractionName6, AttractionName7;
    public Label AttractionTours, AttractionTours1, AttractionTours2, AttractionTours3, AttractionTours4, AttractionTours5, AttractionTours6, AttractionTours7;
    public Label AttractionRaters, AttractionRaters1, AttractionRaters2, AttractionRaters3,  AttractionRaters4, AttractionRaters5, AttractionRaters6, AttractionRaters7;
    public Label AttractionLocation,  AttractionLocation1, AttractionLocation2, AttractionLocation3, AttractionLocation4, AttractionLocation5, AttractionLocation6, AttractionLocation7;
    public Label Rating, Rating1, Rating2, Rating3, Rating4, Rating5, Rating6, Rating7;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField AttractionSearch;

    @FXML
    public ChoiceBox<String> LocationSort, Sort;

    @FXML
    public Button BookButton, BookButton1, BookButton2, BookButton3, BookButton4, BookButton5, BookButton6, BookButton7;
    public Button ImagesButton, ImagesButton1, ImagesButton2, ImagesButton3;

    private List<Attraction> allAttraction = new ArrayList<>();
    private List<Attraction> displayedAttraction = new ArrayList<>();

    // Attraction data class
    public static class Attraction {
        public String Name, Address, Location, Tours, raters, rating;

        public Attraction(String name, String address, String location, String tours, String raters, String rating) {
            this.Name = name;
            this.Address = address;
            this.Location = location;
            this.Tours = tours;
            this.raters = raters;
            this.rating = rating;
        }

        public String getName() {
            return Name;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        welcome.setText(bundle.getString("welcome"));
        home.setText(bundle.getString("home"));
        guides.setText(bundle.getString("guides"));
        attractions.setText(bundle.getString("attractions"));
        bookings.setText(bundle.getString("bookings"));
        reports.setText(bundle.getString("reports"));
        admin.setText(bundle.getString("admin"));
        switch_user.setText(bundle.getString("switch_user"));
        alerts.setText(bundle.getString("alerts"));
        settings.setText(bundle.getString("settings"));
        logout.setText(bundle.getString("logout"));

        User.setText(", " + Session.getUsername() + "!");

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
        loadAttractions(); // Load attractions first
        setupLocationFilter(); // Then setup filter
        setupSearchFilter(); // Setup search functionality

    }

    private void loadAttractions() {
        allAttraction.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/attraction.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    UserAttractionController.Attraction attraction = new UserAttractionController.Attraction(
                            parts[0].trim(), // Name
                            parts[1].trim(), // Address
                            parts[2].trim(), // Location
                            parts[3].trim(), // tours
                            parts[4].trim(), // raters
                            parts[5].trim() // rating
                    );
                    allAttraction.add(attraction);
                }
            }
            br.close();
            displayedAttraction = new ArrayList<>(allAttraction);
            displayAttraction();
        } catch (Exception e) {
            System.out.println("Error loading attractions: " + e.getMessage());
        }
    }

    private void setupLocationFilter() {
        LocationSort.getItems().clear();
        LocationSort.getItems().add("All Locations");

        Set<String> locations = new HashSet<>();
        for (UserAttractionController.Attraction attraction : allAttraction) {
            locations.add(attraction.Location);
        }
        LocationSort.getItems().addAll(locations);
        LocationSort.setValue("All Locations");

        LocationSort.setOnAction(e -> filterAttractions());
    }

    private void setupSearchFilter() {
        AttractionSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAttractions();
        });
    }

    private void filterAttractions() {
        String selectedLocation = LocationSort.getValue();
        String searchText = AttractionSearch.getText().toLowerCase().trim();

        displayedAttraction = new ArrayList<>();

        for (Attraction attraction : allAttraction) {
            boolean matchesLocation = selectedLocation == null ||
                    selectedLocation.equals("All Locations") ||
                    attraction.Location.equals(selectedLocation);

            boolean matchesSearch = searchText.isEmpty() ||
                    attraction.getName().toLowerCase().contains(searchText) ||
                    attraction.Tours.toLowerCase().contains(searchText) ||
                    attraction.Location.toLowerCase().contains(searchText);

            if (matchesLocation && matchesSearch) {
                displayedAttraction.add(attraction);
            }
        }

        displayAttraction();
    }

    private void displayAttraction() {
        clearAllPanels();

        int attractionToShow = Math.min(8, displayedAttraction.size());
        for (int i = 0; i < attractionToShow; i++) {
            Attraction attraction = displayedAttraction.get(i);
            setAttractionToPanel(i, attraction);
        }
    }

    private void clearAllPanels() {
        // Clear all panels
        AttractionName.setText("");
        AttractionTours.setText("");
        AttractionLocation.setText("");
        AttractionRaters.setText("");
        Rating.setText("");

        AttractionName1.setText("");
        AttractionTours1.setText("");
        AttractionLocation1.setText("");
        AttractionRaters1.setText("");
        Rating1.setText("");

        AttractionName2.setText("");
        AttractionTours2.setText("");
        AttractionLocation2.setText("");
        AttractionRaters2.setText("");
        Rating2.setText("");

        AttractionName3.setText("");
        AttractionTours3.setText("");
        AttractionLocation3.setText("");
        AttractionRaters3.setText("");
        Rating3.setText("");
    }

    private void setAttractionToPanel(int panelIndex, Attraction attraction) {
        switch (panelIndex) {
            case 0: // First panel
                AttractionName.setText(attraction.getName());
                AttractionTours.setText(attraction.Tours);
                AttractionLocation.setText(attraction.Location);
                AttractionRaters.setText(attraction.raters);
                Rating.setText(attraction.rating + "/5");
                break;
            case 1: // Second panel
                AttractionName1.setText(attraction.getName());
                AttractionTours1.setText(attraction.Tours);
                AttractionLocation1.setText(attraction.Location);
                AttractionRaters1.setText(attraction.raters);
                Rating1.setText(attraction.rating + "/5");
                break;
            case 2: // Third panel
                AttractionName2.setText(attraction.getName());
                AttractionTours2.setText(attraction.Tours);
                AttractionLocation2.setText(attraction.Location);
                AttractionRaters2.setText(attraction.raters);
                Rating2.setText(attraction.rating + "/5");
                break;
            case 3: // Fourth panel
                AttractionName3.setText(attraction.getName());
                AttractionTours3.setText(attraction.Tours);
                AttractionLocation3.setText(attraction.Location);
                AttractionRaters3.setText(attraction.raters);
                Rating3.setText(attraction.rating + "/5");
                break;
            case 4: // Fifth panel
                AttractionName4.setText(attraction.getName());
                AttractionTours4.setText(attraction.Tours);
                AttractionLocation4.setText(attraction.Location);
                AttractionRaters4.setText(attraction.raters);
                Rating4.setText(attraction.rating + "/5");
                break;
            case 5: // Sixth panel
                AttractionName5.setText(attraction.getName());
                AttractionTours5.setText(attraction.Tours);
                AttractionLocation5.setText(attraction.Location);
                AttractionRaters5.setText(attraction.raters);
                Rating5.setText(attraction.rating + "/5");
                break;
            case 6: // Seventh panel
                AttractionName6.setText(attraction.getName());
                AttractionTours6.setText(attraction.Tours);
                AttractionLocation6.setText(attraction.Location);
                AttractionRaters6.setText(attraction.raters);
                Rating6.setText(attraction.rating + "/5");
                break;
            case 7: // Eighth panel
                AttractionName7.setText(attraction.getName());
                AttractionTours7.setText(attraction.Tours);
                AttractionLocation7.setText(attraction.Location);
                AttractionRaters7.setText(attraction.raters);
                Rating7.setText(attraction.rating + "/5");
                break;
        }
    }

    @FXML
    public void handleAttractionBooking(MouseEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Attraction selectedAttraction = null;

        // Find which button was clicked and get corresponding attraction info
        if (clickedButton == BookButton) {
            selectedAttraction = findAttractionByName(AttractionName.getText());
        } else if (clickedButton == BookButton1) {
            selectedAttraction = findAttractionByName(AttractionName1.getText());
        } else if (clickedButton == BookButton2) {
            selectedAttraction = findAttractionByName(AttractionName2.getText());
        } else if (clickedButton == BookButton3) {
            selectedAttraction = findAttractionByName(AttractionName3.getText());
        } else if (clickedButton == BookButton4) {
            selectedAttraction = findAttractionByName(AttractionName4.getText());
        } else if (clickedButton == BookButton5) {
            selectedAttraction = findAttractionByName(AttractionName5.getText());
        } else if (clickedButton == BookButton6) {
            selectedAttraction = findAttractionByName(AttractionName6.getText());
        } else if (clickedButton == BookButton7) {
            selectedAttraction = findAttractionByName(AttractionName7.getText());
        }

        if (selectedAttraction != null) {
            // Store selected attraction in a static variable or session for the booking controller
            SelectedAttractionHolder.setSelectedAttraction(selectedAttraction);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserAttractionsBooking.fxml", "YatriSathi");
        }
    }

    private Attraction findAttractionByName(String Name) {
        for (Attraction attraction : displayedAttraction) {
            if (attraction.getName().equals(Name)) {
                return attraction;
            }
        }
        return null;
    }

    // Helper class to hold selected attraction data
    public static class SelectedAttractionHolder {
        private static Attraction selectedAttraction;

        public static void setSelectedAttraction(Attraction attraction) {
            selectedAttraction = attraction;
        }

        public static Attraction getSelectedAttraction() {
            return selectedAttraction;
        }
    }

    private void setupMenuItemHoverEffects() {
        attractions.setOnMouseEntered(e -> {});
        setupMenuItemHover(home);
        setupMenuItemHover(guides);
        setupMenuItemHover(bookings);
        setupMenuItemHover(reports);
        setupMenuItemHover(admin);
        setupMenuItemHover(alerts);
        setupMenuItemHover(settings);
        setupMenuItemHover(logout);
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
        setupButtonHover(ImagesButton);
        setupButtonHover(ImagesButton1);
        setupButtonHover(ImagesButton2);
        setupButtonHover(ImagesButton3);
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
        resetMenuItem(bookings);
        resetMenuItem(attractions);
        resetMenuItem(reports);
        resetMenuItem(admin);
        resetMenuItem(alerts);
        resetMenuItem(settings);
        resetMenuItem(logout);

        activeItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; -fx-cursor: hand;");
    }

    private void resetMenuItem(Label menuItem) {
        menuItem.setStyle("-fx-cursor: hand;");
    }

    // Menu item click handlers
    @FXML
    private void handleHomeClick(MouseEvent event) throws IOException {
        setActiveMenuItem(home);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserHome.fxml", "YatriSathi");
    }

    @FXML
    private void handleGuidesClick(MouseEvent event) throws IOException {
        setActiveMenuItem(guides);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserGuide.fxml", "YatriSathi");
    }

    @FXML
    private void handleBookingsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(bookings);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserBookings.fxml", "YatriSathi");
    }

    @FXML
    private void handleAttractionsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(attractions);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserAttractions.fxml", "YatriSathi");
    }

    @FXML
    private void handleReportsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(reports);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserReports.fxml", "YatriSathi");
    }

    @FXML
    private void handleAdminClick(MouseEvent event) throws IOException {
        setActiveMenuItem(admin);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdmin.fxml", "YatriSathi");
    }

    @FXML
    private void handleAlertsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(alerts);
        ChangeScene.openScene("/com/example/yatrisathi/Alert.fxml", "YatriSathi");
    }

    @FXML
    private void handleSettingsClick(MouseEvent event) throws IOException {
        setActiveMenuItem(settings);
        ChangeScene.openScene("/com/example/yatrisathi/Settings.fxml", "YatriSathi");
    }

    @FXML
    private void handleLogoutClick(MouseEvent event) throws IOException {
        setActiveMenuItem(logout);
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Home.fxml", "YatriSathi");
    }

    @FXML
    private void handelBookingClick(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserAttractionsBooking.fxml", "YatriSathi");
    }

    public void SwitchUser(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");
    }
}