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

public class UserGuideBookingController implements Initializable {

    @FXML
    public Label welcome, User;
    public Label home, guides, attractions, bookings, reports, admin, switch_user, warning;
    public Label alerts, settings, logout;
    public Label GuideName1, GuideName2, GuideName3, GuideName4, GuideName5, GuideName6, GuideName7, GuideName8;
    public Label GuideTours, GuideTours1, GuideTours2, GuideTours3, GuideTours4, GuideTours11, GuideTours12, GuideTours13;
    public Label GuideRaters, GuideRaters1,  GuideRaters2, GuideRaters3, GuideRaters4, GuideRaters11, GuideRaters12, GuideRaters13;
    public Label GuideLocation, GuideLocation1, GuideLocation2, GuideLocation3, GuideLocation4, GuideLocation11, GuideLocation12, GuideLocation13;
    public Label GuidesFreeDate1, GuidesFreeDate2, GuidesFreeDate3, GuidesFreeDate4;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField GuideSearch;

    @FXML
    public ChoiceBox<String> LocationSort, Sort, AttractionSelect;

    @FXML
    public DatePicker DateSelect;

    @FXML
    public Button BookButton, BookButton1, BookButton2, BookButton3, BookButton4, BookButton5, BookButton6, BookButton7;
    public Button SaveBook;

    @FXML
    public GridPane GuidePanel;

    private UserGuideController.Guide selectedGuide;
    private String currentUsername = Session.getUsername();
    private List<Attraction> allAttractions = new ArrayList<>();

    public static class Attraction {
        public String name, location;

        public Attraction(String name, String location) {
            this.name = name;
            this.location = location;
        }

        @Override
        public String toString() {
            return name + " - " + location;
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

        // Get selected guide from holder
        selectedGuide = UserGuideController.SelectedGuideHolder.getSelectedGuide();

        loadAttractions();
        filterAttractionsByLocation();
        setupDatePicker();
    }

    private void loadAttractions() {
        allAttractions.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attractions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String attractionName = parts[0].trim();
                    String attractionLocation = parts[1].trim();
                    allAttractions.add(new Attraction(attractionName, attractionLocation));
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, use default attractions
            allAttractions.add(new Attraction("Everest Base Camp", "Solukhumbu"));
            allAttractions.add(new Attraction("Annapurna Circuit", "Annapurna"));
            allAttractions.add(new Attraction("Pokhara Lakeside", "Pokhara"));
            allAttractions.add(new Attraction("Chitwan National Park", "Chitwan"));
            allAttractions.add(new Attraction("Lumbini", "Lumbini"));
        }
    }

    private void filterAttractionsByLocation() {
        AttractionSelect.getItems().clear();

        if (selectedGuide != null) {
            // Filter attractions based on guide's location
            for (Attraction attraction : allAttractions) {
                if (attraction.location.equalsIgnoreCase(selectedGuide.location)) {
                    AttractionSelect.getItems().add(attraction.toString());
                }
            }

            // If no attractions found for guide's location, show all attractions
            if (AttractionSelect.getItems().isEmpty()) {
                for (Attraction attraction : allAttractions) {
                    AttractionSelect.getItems().add(attraction.toString());
                }
            }
        } else {
            // If no guide selected, show all attractions
            for (Attraction attraction : allAttractions) {
                AttractionSelect.getItems().add(attraction.toString());
            }
        }
    }

    private void setupDatePicker() {
        DateSelect.setValue(LocalDate.now().plusDays(1)); // Default to tomorrow
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
        if (AttractionSelect.getValue() == null) {
            warning.setText("Please select an attraction");
            warning.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        if (DateSelect.getValue() == null) {
            warning.setText("Please select a date");
            warning.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        if (selectedGuide == null) {
            warning.setText("No guide selected");
            warning.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        // Extract attraction info
        String attractionInfo = AttractionSelect.getValue();
        String[] attractionParts = attractionInfo.split(" - ");
        String attractionName = attractionParts[0];
        String attractionLocation = attractionParts.length > 1 ? attractionParts[1] : "";

        // Save booking to file
        saveBooking(attractionName, attractionLocation);

        // Show success message
        warning.setText("Booking confirmed!");
        warning.setTextFill(javafx.scene.paint.Color.GREEN);

        // You can navigate back after a delay if needed
        PauseTransition delay = new PauseTransition(Duration.seconds(2)); // 2-second delay
        delay.setOnFinished(event2 -> {
            try {

                ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserGuide.fxml", "YatriSathi");

            } catch (IOException e) {

                System.out.println(e.getMessage());

            }
        });
        delay.play();

    }

    private void saveBooking(String attractionName, String attractionLocation) {
        try (FileWriter writer = new FileWriter("src/main/resources/bookings.txt", true)) {
            // Format: username, guide's name, attraction's name, location, guide number, date
            String booking = String.format("%s, %s, %s, %s, %s, %s%n",
                    currentUsername,
                    selectedGuide.getFullName(),
                    attractionName,
                    attractionLocation,
                    selectedGuide.number,
                    DateSelect.getValue().toString()
            );
            writer.write(booking);
        } catch (IOException e) {
            warning.setText("Error saving booking: " + e.getMessage());
            warning.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    private void setupMenuItemHoverEffects() {
        guides.setOnMouseEntered(e -> {});
        setupMenuItemHover(home);
        setupMenuItemHover(bookings);
        setupMenuItemHover(attractions);
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
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserGuideBooking.fxml", "YatriSathi");
    }

    @FXML
    private void CloseClick(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/User/UserGuide.fxml", "YatriSathi");
    }

    @FXML
    public void SwitchUser(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");
    }
}