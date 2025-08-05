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

public class UserBookingController implements Initializable {

    @FXML
    public Label welcome, User;
    public Label home, guides, attractions, bookings, reports, admin, switch_user;
    public Label alerts, settings, logout;
    public Label BookingName, BookingName1, BookingName2, BookingName3;
    public Label BookingLocation, BookingLocation1, BookingLocation2, BookingLocation3;
    public Label BookingGuide, BookingGuide1, BookingGuide2, BookingGuide3;
    public Label BookingDate, BookingDate1, BookingDate2, BookingDate3;
    public Label BookingStatus, BookingStatus1, BookingStatus2, BookingStatus3;

    @FXML
    public FlowPane tourists_card, guides_card, attractions_card;

    @FXML
    public TextField BookingSearch;

    @FXML
    public ChoiceBox<String> LocationSort, Sort;

    @FXML
    public Button CancelButton, CancelButton1, CancelButton2, CancelButton3;

    private List<Booking> allBookings = new ArrayList<>();
    private List<Booking> displayedBookings = new ArrayList<>();

    // Booking data class
    public static class Booking {
        public String attractionName, guideName, location, date, username, status, number;

        public Booking(String username, String guideName, String attractionName, String location, String number, String date, String status) {
            this.username = username;
            this.guideName = guideName;
            this.attractionName = attractionName;
            this.location = location;
            this.number = number;
            this.date = date;
            this.status = status;
        }

        public String getName() {
            return attractionName;
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
        loadBookings(); // Load bookings first
        setupLocationFilter(); // Then setup filter
        setupSearchFilter(); // Setup search functionality
    }

    private void loadBookings() {
        allBookings.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/bookings.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String bookingUsername = parts[0].trim();
                    String status = parts.length > 6 ? parts[6].trim() : "Pending";

                    // Only load bookings for current user
                    if (bookingUsername.equals(Session.getUsername())) {
                        Booking booking = new Booking(
                                bookingUsername, // username
                                parts[1].trim(), // guideName
                                parts[2].trim(), // attractionName
                                parts[3].trim(), // location
                                parts[4].trim(), // number
                                parts[5].trim(), // date
                                status           // status
                        );
                        allBookings.add(booking);
                    }
                }
            }
            br.close();
            displayedBookings = new ArrayList<>(allBookings);
            displayBookings();
        } catch (Exception e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }

    private void setupLocationFilter() {
        LocationSort.getItems().clear();
        LocationSort.getItems().add("All Locations");

        Set<String> locations = new HashSet<>();
        for (Booking booking : allBookings) {
            locations.add(booking.location);
        }
        LocationSort.getItems().addAll(locations);
        LocationSort.setValue("All Locations");

        LocationSort.setOnAction(e -> filterBookings());
    }

    private void setupSearchFilter() {
        BookingSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBookings();
        });
    }

    private void filterBookings() {
        String selectedLocation = LocationSort.getValue();
        String searchText = BookingSearch.getText().toLowerCase().trim();

        displayedBookings = new ArrayList<>();

        for (Booking booking : allBookings) {
            boolean matchesLocation = selectedLocation == null ||
                    selectedLocation.equals("All Locations") ||
                    booking.location.equals(selectedLocation);

            boolean matchesSearch = searchText.isEmpty() ||
                    booking.attractionName.toLowerCase().contains(searchText) ||
                    booking.guideName.toLowerCase().contains(searchText) ||
                    booking.location.toLowerCase().contains(searchText);

            if (matchesLocation && matchesSearch) {
                displayedBookings.add(booking);
            }
        }

        displayBookings();
    }

    private void displayBookings() {
        clearAllPanels();

        int bookingsToShow = Math.min(4, displayedBookings.size());
        for (int i = 0; i < bookingsToShow; i++) {
            Booking booking = displayedBookings.get(i);
            setBookingToPanel(i, booking);
        }
    }

    private void clearAllPanels() {
        BookingName.setText("");
        BookingLocation.setText("");
        BookingGuide.setText("");
        BookingDate.setText("");
        BookingStatus.setText("");

        BookingName1.setText("");
        BookingLocation1.setText("");
        BookingGuide1.setText("");
        BookingDate1.setText("");
        BookingStatus1.setText("");

        BookingName2.setText("");
        BookingLocation2.setText("");
        BookingGuide2.setText("");
        BookingDate2.setText("");
        BookingStatus2.setText("");

        BookingName3.setText("");
        BookingLocation3.setText("");
        BookingGuide3.setText("");
        BookingDate3.setText("");
        BookingStatus3.setText("");
    }

    private void setBookingToPanel(int panelIndex, Booking booking) {
        switch (panelIndex) {
            case 0: // First panel
                BookingName.setText(booking.attractionName);
                BookingLocation.setText(booking.location);
                BookingGuide.setText(booking.guideName);
                BookingDate.setText(booking.date);
                BookingStatus.setText(booking.status);
                break;
            case 1: // Second panel
                BookingName1.setText(booking.attractionName);
                BookingLocation1.setText(booking.location);
                BookingGuide1.setText(booking.guideName);
                BookingDate1.setText(booking.date);
                BookingStatus1.setText(booking.status);
                break;
            case 2: // Third panel
                BookingName2.setText(booking.attractionName);
                BookingLocation2.setText(booking.location);
                BookingGuide2.setText(booking.guideName);
                BookingDate2.setText(booking.date);
                BookingStatus2.setText(booking.status);
                break;
            case 3: // Fourth panel
                BookingName3.setText(booking.attractionName);
                BookingLocation3.setText(booking.location);
                BookingGuide3.setText(booking.guideName);
                BookingDate3.setText(booking.date);
                BookingStatus3.setText(booking.status);
                break;
        }
    }

    @FXML
    public void handleBookingCancellation(MouseEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Booking selectedBooking = null;

        // Find which button was clicked and get corresponding booking info
        if (clickedButton == CancelButton) {
            selectedBooking = findBookingByName(BookingName.getText());
        } else if (clickedButton == CancelButton1) {
            selectedBooking = findBookingByName(BookingName1.getText());
        } else if (clickedButton == CancelButton2) {
            selectedBooking = findBookingByName(BookingName2.getText());
        } else if (clickedButton == CancelButton3) {
            selectedBooking = findBookingByName(BookingName3.getText());
        }

        if (selectedBooking != null) {
            // Handle booking cancellation logic here
            cancelBooking(selectedBooking);
        }
    }

    private Booking findBookingByName(String name) {
        for (Booking booking : displayedBookings) {
            if (booking.getName().equals(name)) {
                return booking;
            }
        }
        return null;
    }

    private void cancelBooking(Booking bookingToCancel) {
        try {
            // Read all bookings
            List<String> allLines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/bookings.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String bookingUsername = parts.length > 5 ? parts[5].trim() : parts[4].trim();

                    // If this is the booking to cancel, mark it as cancelled
                    if (parts[0].trim().equals(Session.getUsername()) &&
                            parts[1].trim().equals(bookingToCancel.guideName) &&
                            parts[2].trim().equals(bookingToCancel.attractionName) &&
                            parts[3].trim().equals(bookingToCancel.location) &&
                            parts[4].trim().equals(bookingToCancel.number) &&
                            parts[5].trim().equals(bookingToCancel.date)) {

                        // Update status to "Cancelled"
                        if (parts.length > 6) {
                            line = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + parts[5] + ",Cancelled";
                        } else {
                            line = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + parts[5] + ",Cancelled";
                        }
                    }
                }
                allLines.add(line);
            }
            br.close();

            // Write back to file
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/bookings.txt"));
            for (String updatedLine : allLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
            bw.close();

            // Reload bookings to reflect changes
            loadBookings();

        } catch (Exception e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
        }
    }

    private void setupMenuItemHoverEffects() {
        bookings.setOnMouseEntered(e -> {});
        setupMenuItemHover(home);
        setupMenuItemHover(guides);
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
        setupButtonHover(CancelButton);
        setupButtonHover(CancelButton1);
        setupButtonHover(CancelButton2);
        setupButtonHover(CancelButton3);
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

    public void SwitchUser(MouseEvent event) throws IOException {
        ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");
    }
}