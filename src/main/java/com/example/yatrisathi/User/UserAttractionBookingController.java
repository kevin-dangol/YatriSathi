package com.example.yatrisathi.User;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserAttractionBookingController implements Initializable {

    @FXML
    public Label welcome, User;
    public Label home, guides, attractions, bookings, reports, admin, switch_user, warning;
    public Label alerts, settings, logout;
    public Label AttractionName, AttractionName1, AttractionName2, AttractionName3, AttractionName4, AttractionName5, AttractionName6, AttractionName7;
    public Label AttractionLocation,  AttractionLocation1, AttractionLocation2, AttractionLocation3, AttractionLocation4;
    public Label AttractionTours, AttractionTours1, AttractionTours2, AttractionTours3, AttractionTours4, AttractionTours5, AttractionTours6, AttractionTours7;
    public Label AttractionRaters, AttractionRaters1, AttractionRaters2, AttractionRaters3,  AttractionRaters4, AttractionRaters5, AttractionRaters6, AttractionRaters7;
    public Label Rating, Rating1, Rating2, Rating3, Rating4, Rating5, Rating6, Rating7;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField AttractionSearch;

    @FXML
    public ChoiceBox<String> LocationSort, Sort, GuideSelect;

    @FXML
    public DatePicker DateSelect;

    @FXML
    public Button BookButton, BookButton1, BookButton2, BookButton3, BookButton4, BookButton5, BookButton6, BookButton7;
    public Button ImagesButton, ImagesButton1, ImagesButton2, ImagesButton3;

    @FXML
    public GridPane GuidePanel;

    private UserAttractionController.Attraction selectedAttraction;
    private String currentUsername = Session.getUsername();
    private List<Guide> allGuides = new ArrayList<>();

    public static class Guide {
        public String firstName, lastName, location, number;

        public Guide(String firstName, String lastName, String location, String number) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.location = location;
            this.number = number;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        @Override
        public String toString() {
            return getFullName() + " - " + location + " (Contact: " + number + ")";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        selectedAttraction = UserAttractionController.SelectedAttractionHolder.getSelectedAttraction();

        loadGuides();
        filterGuidesByLocation();
        setupDatePicker();
        displaySelectedAttractionInfo();

    }

    private void displaySelectedAttractionInfo() {
        if (selectedAttraction != null) {
            // You can display attraction info in labels if needed
            System.out.println("Selected Attraction: " + selectedAttraction.getName() + " at " + selectedAttraction.Location);
        }
    }

    private void loadGuides() {
        allGuides.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/guides.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String firstName = parts[0].trim();
                    String lastName = parts[1].trim();
                    String guideLocation = parts[4].trim();
                    String guideNumber = parts[5].trim();
                    allGuides.add(new Guide(firstName, lastName, guideLocation, guideNumber));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading guides: " + e.getMessage());
            // If file doesn't exist, use default guides
            allGuides.add(new Guide("Ram", "Shrestha", "Kathmandu", "9801234567"));
            allGuides.add(new Guide("Sita", "Gurung", "Pokhara", "9801234568"));
            allGuides.add(new Guide("Hari", "Tamang", "Chitwan", "9801234569"));
        }
    }

    private void filterGuidesByLocation() {
        GuideSelect.getItems().clear();

        if (selectedAttraction != null) {
            // Filter guides based on attraction's location
            for (Guide guide : allGuides) {
                if (guide.location.equalsIgnoreCase(selectedAttraction.Location)) {
                    GuideSelect.getItems().add(guide.toString());
                }
            }

            // If no guides found for attraction's location, show all guides
            if (GuideSelect.getItems().isEmpty()) {
                for (Guide guide : allGuides) {
                    GuideSelect.getItems().add(guide.toString());
                }
            }
        } else {
            // If no attraction selected, show all guides
            for (Guide guide : allGuides) {
                GuideSelect.getItems().add(guide.toString());
            }
        }
    }

    private void setupDatePicker() {
        DateSelect.setValue(LocalDate.now().plusDays(1));
        DateSelect.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Disable past dates
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }

    @FXML
    public void handleConfirmBooking(MouseEvent event) throws IOException {
        // Validate inputs
        if (GuideSelect.getValue() == null) {
            warning.setText("Please select a guide");
            warning.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        if (DateSelect.getValue() == null) {
            warning.setText("Please select a date");
            warning.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        if (selectedAttraction == null) {
            warning.setText("No attraction selected");
            warning.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        // Extract guide info from selection
        String guideInfo = GuideSelect.getValue();
        Guide selectedGuide = findGuideFromSelection(guideInfo);

        if (selectedGuide == null) {
            warning.setText("Error finding guide information");
            warning.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        // Save booking to file
        saveBooking(selectedGuide);

        // Show success message
        warning.setText("Booking confirmed!");
        warning.setTextFill(javafx.scene.paint.Color.GREEN);

        // Navigate back after delay
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event2 -> {
            try {
                ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserAttractions.fxml", "YatriSathi");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
        delay.play();
    }

    private Guide findGuideFromSelection(String guideInfo) {
        // Extract guide name from the selection string
        String guideName = guideInfo.split(" - ")[0];

        for (Guide guide : allGuides) {
            if (guide.getFullName().equals(guideName)) {
                return guide;
            }
        }
        return null;
    }

    private void saveBooking(Guide guide) {
        try (FileWriter writer = new FileWriter("src/main/resources/bookings.txt", true)) {
            // Format: username, guidename, attractionname, location, number, date
            String booking = String.format("%s,%s,%s,%s,%s,%s%n",
                    currentUsername,
                    guide.getFullName(),
                    selectedAttraction.getName(),
                    selectedAttraction.Location,
                    guide.number,
                    DateSelect.getValue().toString()
            );
            writer.write(booking);
        } catch (IOException e) {
            warning.setText("Error saving booking: " + e.getMessage());
            warning.setTextFill(javafx.scene.paint.Color.RED);
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

    @FXML
    private void CloseClick(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserAttractions.fxml", "YatriSathi");
    }

    public void SwitchUser(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");
    }
}