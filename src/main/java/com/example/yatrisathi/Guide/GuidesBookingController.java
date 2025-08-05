package com.example.yatrisathi.Guide;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ResourceBundle;

public class GuidesBookingController {

    @FXML
    public Label welcome, User;
    public Label home, requests, bookings;
    public Label alerts, settings, logout;
    public Label BookingName, BookingName1, BookingName2, BookingName3;
    public Label BookingLocation, BookingLocation1, BookingLocation2, BookingLocation3;
    public Label BookingTourists, BookingTourists1, BookingTourists2, BookingTourists3;
    public Label BookingDate, BookingDate1, BookingDate2, BookingDate3;
    public Label BookingStatus, BookingStatus1, BookingStatus2, BookingStatus3;

    @FXML
    public TextField BookingSearch;

    @FXML
    public ChoiceBox<String> LocationSort, Sort;

    @FXML
    public Button CancelButton, CancelButton1, CancelButton2, CancelButton3;
    public Button RescheduleButton, RescheduleButton1, RescheduleButton2, RescheduleButton3;

    private ResourceBundle bundle;

    @FXML
    private void initialize() {

        ResourceBundle bundle = ResourceBundle.getBundle("lang.messages", Session.getLocale());
        welcome.setText(bundle.getString("welcome"));
        home.setText(bundle.getString("home"));
        bookings.setText(bundle.getString("bookings"));
        alerts.setText(bundle.getString("alerts"));
        settings.setText(bundle.getString("settings"));
        logout.setText(bundle.getString("logout"));
        requests.setText(bundle.getString("requests"));

        User.setText(", " + Session.getUsername() + "!");

        System.out.println("Initializing GuidesBookingController...");

        try {
            setupMenuItemHoverEffects();
            setupButtonHoverEffects();
            setupChoiceBoxes();
            loadSampleBookingData();

        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupChoiceBoxes() {
        try {
            if (LocationSort != null) {
                LocationSort.getItems().addAll(
                        "All Locations", "Kathmandu", "Pokhara", "Chitwan",
                        "Lumbini", "Everest", "Annapurna", "Bandipur", "Gorkha",
                        "Mustang", "Dolpo"
                );
                LocationSort.setValue("All Locations");
            }

            if (Sort != null) {
                Sort.getItems().addAll("Recent", "Oldest", "By Date", "By Status", "By Location");
                Sort.setValue("Recent");
            }
        } catch (Exception e) {
            System.err.println("Error setting up choice boxes: " + e.getMessage());
        }
    }

    private void loadSampleBookingData() {
        try {
            // Load sample booking data
            if (BookingName != null) BookingName.setText("Everest Base Camp Trek");
            if (BookingLocation != null) BookingLocation.setText("Everest Region");
            if (BookingTourists != null) BookingTourists.setText("4 Tourists");
            if (BookingDate != null) BookingDate.setText("2025-08-15");
            if (BookingStatus != null) BookingStatus.setText("Confirmed");

            if (BookingName1 != null) BookingName1.setText("Annapurna Circuit");
            if (BookingLocation1 != null) BookingLocation1.setText("Annapurna");
            if (BookingTourists1 != null) BookingTourists1.setText("2 Tourists");
            if (BookingDate1 != null) BookingDate1.setText("2025-08-20");
            if (BookingStatus1 != null) BookingStatus1.setText("Pending");

            if (BookingName2 != null) BookingName2.setText("Chitwan Safari");
            if (BookingLocation2 != null) BookingLocation2.setText("Chitwan");
            if (BookingTourists2 != null) BookingTourists2.setText("6 Tourists");
            if (BookingDate2 != null) BookingDate2.setText("2025-08-25");
            if (BookingStatus2 != null) BookingStatus2.setText("Confirmed");

            if (BookingName3 != null) BookingName3.setText("Kathmandu Valley Tour");
            if (BookingLocation3 != null) BookingLocation3.setText("Kathmandu");
            if (BookingTourists3 != null) BookingTourists3.setText("8 Tourists");
            if (BookingDate3 != null) BookingDate3.setText("2025-08-30");
            if (BookingStatus3 != null) BookingStatus3.setText("Completed");

        } catch (Exception e) {
            System.err.println("Error loading sample booking data: " + e.getMessage());
        }
    }

    private void setupMenuItemHoverEffects() {
        try {
            if (bookings != null) {
                bookings.setOnMouseEntered(e -> {});
            }

            if (home != null) setupMenuItemHover(home);
            if (bookings != null) setupMenuItemHover(bookings);
            if (requests != null) setupMenuItemHover(requests);
            if (alerts != null) setupMenuItemHover(alerts);
            if (settings != null) setupMenuItemHover(settings);
            if (logout != null) setupMenuItemHover(logout);

        } catch (Exception e) {
            System.err.println("Error setting up menu item hover effects: " + e.getMessage());
        }
    }

    private void setupMenuItemHover(Label menuItem) {
        try {
            menuItem.setOnMouseEntered(e -> {
                menuItem.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 5; -fx-cursor: hand;");
            });

            menuItem.setOnMouseExited(e -> {
                menuItem.setStyle("-fx-cursor: hand;");
            });
        } catch (Exception e) {
            System.err.println("Error setting up menu hover for " + menuItem + ": " + e.getMessage());
        }
    }

    private void setupButtonHoverEffects() {
        try {
            // Setup hover effects for Cancel buttons (red theme)
            if (CancelButton != null) setupButtonHover(CancelButton, "#D95151", "#F65D5D");
            if (CancelButton1 != null) setupButtonHover(CancelButton1, "#D95151", "#F65D5D");
            if (CancelButton2 != null) setupButtonHover(CancelButton2, "#D95151", "#F65D5D");
            if (CancelButton3 != null) setupButtonHover(CancelButton3, "#D95151", "#F65D5D");

            // Setup hover effects for Reschedule buttons (normal theme)
            if (RescheduleButton != null) setupButtonHover(RescheduleButton, "#c7a483", "#E7C6A7");
            if (RescheduleButton1 != null) setupButtonHover(RescheduleButton1, "#c7a483", "#E7C6A7");
            if (RescheduleButton2 != null) setupButtonHover(RescheduleButton2, "#c7a483", "#E7C6A7");
            if (RescheduleButton3 != null) setupButtonHover(RescheduleButton3, "#c7a483", "#E7C6A7");

        } catch (Exception e) {
            System.err.println("Error setting up button hover effects: " + e.getMessage());
        }
    }

    private void setupButtonHover(Button card, String NBG, String BG) {
        try {
            String normalStyle = "-fx-background-color: " + NBG + "; -fx-cursor: hand; -fx-background-radius: 15;";
            String hoverStyle = "-fx-background-color: " + BG + "; -fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02; -fx-background-radius: 10;";

            card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
            card.setOnMouseExited(e -> card.setStyle(normalStyle));
        } catch (Exception e) {
            System.err.println("Error setting up button hover for " + card + ": " + e.getMessage());
        }
    }

    // Helper method to set active menu item
    private void setActiveMenuItem(Label activeItem) {
        try {
            if (home != null) resetMenuItem(home);
            if (bookings != null) resetMenuItem(bookings);
            if (requests != null) resetMenuItem(requests);
            if (alerts != null) resetMenuItem(alerts);
            if (settings != null) resetMenuItem(settings);
            if (logout != null) resetMenuItem(logout);

            if (activeItem != null) {
                activeItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; -fx-cursor: hand;");
            }
        } catch (Exception e) {
            System.err.println("Error setting active menu item: " + e.getMessage());
        }
    }

    private void resetMenuItem(Label menuItem) {
        try {
            if (menuItem != null) {
                menuItem.setStyle("-fx-cursor: hand;");
            }
        } catch (Exception e) {
            System.err.println("Error resetting menu item: " + e.getMessage());
        }
    }

    // Button click handlers for bookings
    @FXML
    private void handleCancelBooking(MouseEvent event) {
        handleBookingAction("cancelled", 0);
    }

    @FXML
    private void handleCancelBooking1(MouseEvent event) {
        handleBookingAction("cancelled", 1);
    }

    @FXML
    private void handleCancelBooking2(MouseEvent event) {
        handleBookingAction("cancelled", 2);
    }

    @FXML
    private void handleCancelBooking3(MouseEvent event) {
        handleBookingAction("cancelled", 3);
    }

    @FXML
    private void handleRescheduleBooking(MouseEvent event) {
        handleBookingAction("rescheduled", 0);
    }

    @FXML
    private void handleRescheduleBooking1(MouseEvent event) {
        handleBookingAction("rescheduled", 1);
    }

    @FXML
    private void handleRescheduleBooking2(MouseEvent event) {
        handleBookingAction("rescheduled", 2);
    }

    @FXML
    private void handleRescheduleBooking3(MouseEvent event) {
        handleBookingAction("rescheduled", 3);
    }

    private void handleBookingAction(String action, int bookingIndex) {
        try {
            String bookingName = getBookingName(bookingIndex);
            String bookingDate = getBookingDate(bookingIndex);

            System.out.println("Booking " + action + ": " + bookingName + " on " + bookingDate);

            if (action.equals("cancelled")) {
                updateBookingStatus(bookingIndex, "Cancelled");
                hideBookingButtons(bookingIndex);
            } else if (action.equals("rescheduled")) {
                updateBookingStatus(bookingIndex, "Rescheduled");
                // For reschedule, you might want to open a date picker dialog
                System.out.println("Opening reschedule dialog for booking: " + bookingName);
            }

        } catch (Exception e) {
            System.err.println("Error handling booking action: " + e.getMessage());
        }
    }

    private String getBookingName(int index) {
        try {
            switch (index) {
                case 0: return BookingName != null ? BookingName.getText() : "Unknown Booking";
                case 1: return BookingName1 != null ? BookingName1.getText() : "Unknown Booking";
                case 2: return BookingName2 != null ? BookingName2.getText() : "Unknown Booking";
                case 3: return BookingName3 != null ? BookingName3.getText() : "Unknown Booking";
                default: return "Unknown Booking";
            }
        } catch (Exception e) {
            return "Unknown Booking";
        }
    }

    private String getBookingDate(int index) {
        try {
            switch (index) {
                case 0: return BookingDate != null ? BookingDate.getText() : "Unknown Date";
                case 1: return BookingDate1 != null ? BookingDate1.getText() : "Unknown Date";
                case 2: return BookingDate2 != null ? BookingDate2.getText() : "Unknown Date";
                case 3: return BookingDate3 != null ? BookingDate3.getText() : "Unknown Date";
                default: return "Unknown Date";
            }
        } catch (Exception e) {
            return "Unknown Date";
        }
    }

    private void updateBookingStatus(int index, String status) {
        try {
            switch (index) {
                case 0:
                    if (BookingStatus != null) BookingStatus.setText(status);
                    break;
                case 1:
                    if (BookingStatus1 != null) BookingStatus1.setText(status);
                    break;
                case 2:
                    if (BookingStatus2 != null) BookingStatus2.setText(status);
                    break;
                case 3:
                    if (BookingStatus3 != null) BookingStatus3.setText(status);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error updating booking status: " + e.getMessage());
        }
    }

    private void hideBookingButtons(int index) {
        try {
            switch (index) {
                case 0:
                    if (CancelButton != null) CancelButton.setVisible(false);
                    if (RescheduleButton != null) RescheduleButton.setVisible(false);
                    break;
                case 1:
                    if (CancelButton1 != null) CancelButton1.setVisible(false);
                    if (RescheduleButton1 != null) RescheduleButton1.setVisible(false);
                    break;
                case 2:
                    if (CancelButton2 != null) CancelButton2.setVisible(false);
                    if (RescheduleButton2 != null) RescheduleButton2.setVisible(false);
                    break;
                case 3:
                    if (CancelButton3 != null) CancelButton3.setVisible(false);
                    if (RescheduleButton3 != null) RescheduleButton3.setVisible(false);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error hiding booking buttons: " + e.getMessage());
        }
    }

    // Menu item click handlers
    @FXML
    private void handleHomeClick(MouseEvent event) throws IOException {
        try {
            setActiveMenuItem(home);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesHome.fxml", "YatriSathi");
        } catch (Exception e) {
            System.err.println("Error handling home click: " + e.getMessage());
        }
    }

    @FXML
    private void handleBookingsClick(MouseEvent event) throws IOException {
        try {
            setActiveMenuItem(bookings);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesBookings.fxml", "YatriSathi");
        } catch (Exception e) {
            System.err.println("Error handling bookings click: " + e.getMessage());
        }
    }

    @FXML
    private void handleRequestsClick(MouseEvent event) throws IOException {
        try {
            setActiveMenuItem(requests);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Guide/GuidesRequests.fxml", "YatriSathi");
        } catch (Exception e) {
            System.err.println("Error handling requests click: " + e.getMessage());
        }
    }

    @FXML
    private void handleAlertsClick(MouseEvent event) throws IOException {
        try {
            setActiveMenuItem(alerts);
            ChangeScene.openScene("/com/example/yatrisathi/Alert.fxml", "YatriSathi");
        } catch (Exception e) {
            System.err.println("Error handling alerts click: " + e.getMessage());
        }
    }

    @FXML
    private void handleSettingsClick(MouseEvent event) throws IOException {
        try {
            setActiveMenuItem(settings);
            ChangeScene.openScene("/com/example/yatrisathi/Settings.fxml", "YatriSathi");
        } catch (Exception e) {
            System.err.println("Error handling settings click: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogoutClick(MouseEvent event) throws IOException {
        try {
            setActiveMenuItem(logout);
            ChangeScene.switchScene(event, "/com/example/yatrisathi/Guest/Home.fxml", "YatriSathi");
        } catch (Exception e) {
            System.err.println("Error handling logout click: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearchAction(MouseEvent event) {
        try {
            if (BookingSearch != null) {
                String searchText = BookingSearch.getText();
                System.out.println("Searching bookings for: " + searchText);
                // Implement search functionality here
                // You could filter the bookings based on the search text
            }
        } catch (Exception e) {
            System.err.println("Error handling search: " + e.getMessage());
        }
    }

    @FXML
    private void handleLocationSortChange(MouseEvent event) {
        try {
            if (LocationSort != null && LocationSort.getValue() != null) {
                String selectedLocation = LocationSort.getValue();
                System.out.println("Filtering bookings by location: " + selectedLocation);
                // Implement location filtering here
            }
        } catch (Exception e) {
            System.err.println("Error handling location sort: " + e.getMessage());
        }
    }

    @FXML
    private void handleSortChange(MouseEvent event) {
        try {
            if (Sort != null && Sort.getValue() != null) {
                String sortType = Sort.getValue();
                System.out.println("Sorting bookings by: " + sortType);
                // Implement sorting functionality here
            }
        } catch (Exception e) {
            System.err.println("Error handling sort: " + e.getMessage());
        }
    }
}