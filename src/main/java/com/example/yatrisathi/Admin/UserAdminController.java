package com.example.yatrisathi.Admin;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.*;
import java.net.URL;
import java.util.*;

public class UserAdminController {

    @FXML
    public Label welcome, User;
    public Label home, guides, attractions, bookings, reports, admin, switch_user;
    public Label alerts, settings, logout;

    //Attraction
    @FXML
    public Label AttractionName, AttractionName1, AttractionName2, AttractionName3;
    public Label AttractionID, AttractionID1, AttractionID2, AttractionID3;
    public Label AttractionLocation, AttractionLocation1, AttractionLocation2, AttractionLocation3;
    public Label AttractionDetail, AttractionDetail1, AttractionDetail2, AttractionDetail3;
    public Button AttractionEdit, AttractionEdit1, AttractionEdit2, AttractionEdit3;
    public Button AttractionRemove, AttractionRemove1, AttractionRemove2, AttractionRemove3;

    //Guides
    @FXML
    public Label GuideName, GuideName1, GuideName2, GuideName3;
    public Label GuideLocation, GuideLocation1, GuideLocation2, GuideLocation3;
    public Label GuideDetails, GuideDetails1, GuideDetails2, GuideDetails3;
    public Button GuideEdit, GuideEdit1, GuideEdit2, GuideEdit3;
    public Button GuideKick, GuideKick1, GuideKick2, GuideKick3;
    public Label GuideID, GuideID1, GuideID2, GuideID3;

    //Requests
    @FXML
    public Label RequestName, RequestName1, RequestName2, RequestName3;
    public Label RequestDetail, RequestDetail1, RequestDetail2, RequestDetail3;
    public Label RequestID, RequestID1, RequestID2, RequestID3;
    public Label RequestLocation, RequestLocation1, RequestLocation2, RequestLocation3;
    public Button RequestAccept, RequestAccept1, RequestAccept2, RequestAccept3;
    public Button RequestReject, RequestReject1, RequestReject2, RequestReject3;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField AttractionSearch, GuideSearch, RequestSearch;

    @FXML
    public ChoiceBox LocationSort, Sort;

    // Data lists
    private List<Guide> allGuides = new ArrayList<>();
    private List<Guide> displayedGuides = new ArrayList<>();
    private List<Attraction> allAttractions = new ArrayList<>();
    private List<Attraction> displayedAttractions = new ArrayList<>();

    // Data classes
    public static class Guide {
        public String firstName, lastName, tours, address, location, number, nextFreeDate, raters, rating;

        public Guide(String firstName, String lastName, String tours, String address, String location,
                     String number, String nextFreeDate, String raters, String rating) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.tours = tours;
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

    public static class Attraction {
        public String name, address, location, tours, raters, rating;

        public Attraction(String name, String address, String location, String tours, String raters, String rating) {
            this.name = name;
            this.address = address;
            this.location = location;
            this.tours = tours;
            this.raters = raters;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }
    }

    // Static holders for selected items
    public static class SelectedGuideHolder {
        private static Guide selectedGuide;

        public static void setSelectedGuide(Guide guide) {
            selectedGuide = guide;
        }

        public static Guide getSelectedGuide() {
            return selectedGuide;
        }
    }

    public static class SelectedAttractionHolder {
        private static Attraction selectedAttraction;

        public static void setSelectedAttraction(Attraction attraction) {
            selectedAttraction = attraction;
        }

        public static Attraction getSelectedAttraction() {
            return selectedAttraction;
        }
    }

    @FXML
    private void initialize() {

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

        reports.setVisible(true);
        admin.setVisible(true);

        setupMenuItemHoverEffects();
        setupButtonHoverEffects();
        loadGuides();
        loadAttractions();
        setupSearchFilters();
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

    private void loadAttractions() {
        allAttractions.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/attraction.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    Attraction attraction = new Attraction(
                            parts[0].trim(), // name
                            parts[1].trim(), // address
                            parts[2].trim(), // location
                            parts[3].trim(), // tours
                            parts[4].trim(), // raters
                            parts[5].trim()  // rating
                    );
                    allAttractions.add(attraction);
                }
            }
            br.close();
            displayedAttractions = new ArrayList<>(allAttractions);
            displayAttractions();
        } catch (Exception e) {
            System.out.println("Error loading attractions: " + e.getMessage());
        }
    }

    private void setupSearchFilters() {
        GuideSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterGuides();
        });

        AttractionSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAttractions();
        });
    }

    private void filterGuides() {
        String searchText = GuideSearch.getText().toLowerCase().trim();
        displayedGuides = new ArrayList<>();

        for (Guide guide : allGuides) {
            boolean matchesSearch = searchText.isEmpty() ||
                    guide.getFullName().toLowerCase().contains(searchText) ||
                    guide.tours.toLowerCase().contains(searchText) ||
                    guide.location.toLowerCase().contains(searchText) ||
                    guide.address.toLowerCase().contains(searchText);

            if (matchesSearch) {
                displayedGuides.add(guide);
            }
        }
        displayGuides();
    }

    private void filterAttractions() {
        String searchText = AttractionSearch.getText().toLowerCase().trim();
        displayedAttractions = new ArrayList<>();

        for (Attraction attraction : allAttractions) {
            boolean matchesSearch = searchText.isEmpty() ||
                    attraction.getName().toLowerCase().contains(searchText) ||
                    attraction.tours.toLowerCase().contains(searchText) ||
                    attraction.location.toLowerCase().contains(searchText);

            if (matchesSearch) {
                displayedAttractions.add(attraction);
            }
        }
        displayAttractions();
    }

    private void displayGuides() {
        clearGuidePanels();

        int guidesToShow = Math.min(4, displayedGuides.size());
        for (int i = 0; i < guidesToShow; i++) {
            Guide guide = displayedGuides.get(i);
            setGuideToPanel(i, guide);
        }
    }

    private void displayAttractions() {
        clearAttractionPanels();

        int attractionsToShow = Math.min(4, displayedAttractions.size());
        for (int i = 0; i < attractionsToShow; i++) {
            Attraction attraction = displayedAttractions.get(i);
            setAttractionToPanel(i, attraction);
        }
    }

    private void clearGuidePanels() {
        GuideName.setText("");
        GuideLocation.setText("");
        GuideDetails.setText("");

        GuideName1.setText("");
        GuideLocation1.setText("");
        GuideDetails1.setText("");

        GuideName2.setText("");
        GuideLocation2.setText("");
        GuideDetails2.setText("");

        GuideName3.setText("");
        GuideLocation3.setText("");
        GuideDetails3.setText("");
    }

    private void clearAttractionPanels() {
        AttractionName.setText("");
        AttractionLocation.setText("");
        AttractionDetail.setText("");
        AttractionID.setText("");

        AttractionName1.setText("");
        AttractionLocation1.setText("");
        AttractionDetail1.setText("");
        AttractionID1.setText("");

        AttractionName2.setText("");
        AttractionLocation2.setText("");
        AttractionDetail2.setText("");
        AttractionID2.setText("");

        AttractionName3.setText("");
        AttractionLocation3.setText("");
        AttractionDetail3.setText("");
        AttractionID3.setText("");
    }

    private void setGuideToPanel(int panelIndex, Guide guide) {
        switch (panelIndex) {
            case 0:
                GuideName.setText(guide.getFullName());
                GuideLocation.setText(guide.location);
                GuideDetails.setText("Rating: " + guide.rating + "/5");
                GuideID.setText(guide.tours);
                break;
            case 1:
                GuideName1.setText(guide.getFullName());
                GuideLocation1.setText(guide.location);
                GuideDetails1.setText("Rating: " + guide.rating + "/5");
                GuideID1.setText(guide.tours);
                break;
            case 2:
                GuideName2.setText(guide.getFullName());
                GuideLocation2.setText(guide.location);
                GuideDetails2.setText("Rating: " + guide.rating + "/5");
                GuideID2.setText(guide.tours);
                break;
            case 3:
                GuideName3.setText(guide.getFullName());
                GuideLocation3.setText(guide.location);
                GuideDetails3.setText("Rating: " + guide.rating + "/5");
                GuideID3.setText(guide.tours);
                break;
        }
    }

    private void setAttractionToPanel(int panelIndex, Attraction attraction) {
        switch (panelIndex) {
            case 0:
                AttractionName.setText(attraction.getName());
                AttractionLocation.setText(attraction.location);
                AttractionDetail.setText("Rating: " + attraction.rating + "/5");
                AttractionID.setText(attraction.tours);
                break;
            case 1:
                AttractionName1.setText(attraction.getName());
                AttractionLocation1.setText(attraction.location);
                AttractionDetail1.setText("Rating: " + attraction.rating + "/5");
                AttractionID1.setText(attraction.tours);
                break;
            case 2:
                AttractionName2.setText(attraction.getName());
                AttractionLocation2.setText(attraction.location);
                AttractionDetail2.setText("Rating: " + attraction.rating + "/5");
                AttractionID2.setText(attraction.tours);
                break;
            case 3:
                AttractionName3.setText(attraction.getName());
                AttractionLocation3.setText(attraction.location);
                AttractionDetail3.setText("Rating: " + attraction.rating + "/5");
                AttractionID3.setText(attraction.tours);
                break;
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

    private Attraction findAttractionByName(String name) {
        for (Attraction attraction : displayedAttractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }
        return null;
    }

    private void setupMenuItemHoverEffects() {
        attractions.setOnMouseEntered(e -> {
        });

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
        //Normal
        setupNormalButtonHover(GuideEdit);
        setupNormalButtonHover(GuideEdit1);
        setupNormalButtonHover(GuideEdit2);
        setupNormalButtonHover(GuideEdit3);

        setupNormalButtonHover(AttractionEdit);
        setupNormalButtonHover(AttractionEdit1);
        setupNormalButtonHover(AttractionEdit2);
        setupNormalButtonHover(AttractionEdit3);

        setupNormalButtonHover(RequestAccept);
        setupNormalButtonHover(RequestAccept1);
        setupNormalButtonHover(RequestAccept2);
        setupNormalButtonHover(RequestAccept3);

        //Reds
        setupRedButtonHover(GuideKick);
        setupRedButtonHover(GuideKick1);
        setupRedButtonHover(GuideKick2);
        setupRedButtonHover(GuideKick3);

        setupRedButtonHover(AttractionRemove);
        setupRedButtonHover(AttractionRemove1);
        setupRedButtonHover(AttractionRemove2);
        setupRedButtonHover(AttractionRemove3);

        setupRedButtonHover(RequestReject);
        setupRedButtonHover(RequestReject1);
        setupRedButtonHover(RequestReject2);
        setupRedButtonHover(RequestReject3);
    }

    private void setupNormalButtonHover(Button card) {
        String normalStyle = "-fx-background-color: " + "#c7a483" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#E7C6A7" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(normalStyle));
    }

    private void setupRedButtonHover(Button card) {
        String normalStyle = "-fx-background-color: " + "#CB7B78" + "; ;-fx-cursor: hand;-fx-background-radius: 15;";
        String hoverStyle = "-fx-background-color: " + "#D8413C" + ";-fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;-fx-background-radius: 10;";

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
    private void handelGuideEditClick(MouseEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Guide selectedGuide = null;

        if (clickedButton == GuideEdit) {
            selectedGuide = findGuideByName(GuideName.getText());
        } else if (clickedButton == GuideEdit1) {
            selectedGuide = findGuideByName(GuideName1.getText());
        } else if (clickedButton == GuideEdit2) {
            selectedGuide = findGuideByName(GuideName2.getText());
        } else if (clickedButton == GuideEdit3) {
            selectedGuide = findGuideByName(GuideName3.getText());
        }

        if (selectedGuide != null) {
            SelectedGuideHolder.setSelectedGuide(selectedGuide);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdminGuide.fxml", "YatriSathi");
        }
    }

    @FXML
    private void handelAttractionEditClick(MouseEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Attraction selectedAttraction = null;

        if (clickedButton == AttractionEdit) {
            selectedAttraction = findAttractionByName(AttractionName.getText());
        } else if (clickedButton == AttractionEdit1) {
            selectedAttraction = findAttractionByName(AttractionName1.getText());
        } else if (clickedButton == AttractionEdit2) {
            selectedAttraction = findAttractionByName(AttractionName2.getText());
        } else if (clickedButton == AttractionEdit3) {
            selectedAttraction = findAttractionByName(AttractionName3.getText());
        }

        if (selectedAttraction != null) {
            SelectedAttractionHolder.setSelectedAttraction(selectedAttraction);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdminAttraction.fxml", "YatriSathi");
        }
    }

    @FXML
    private void handelRequestDetailClick(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Admin/UserAdminRequest.fxml", "YatriSathi");
    }

    public void SwitchUser(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");
    }
}