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

public class GuidesRequestsController3 {

    @FXML
    public Label welcome;
    public Label home, requests, bookings;
    public Label alerts, settings, logout;
    public Label AttractionName4, AttractionName5, AttractionName6, AttractionName7;
    public Label AttractionTours4, AttractionTours5, AttractionTours6, AttractionTours7;
    public Label AttractionRaters4, AttractionRaters5, AttractionRaters6, AttractionRaters7;
    public Label Rating4, Rating5, Rating6, Rating7;
    public Label RequestName, RequestName1, RequestName2, RequestName3;
    public Label RequestTourist, RequestTourist1, RequestTourist2, RequestTourist3;
    public Label RequestDate, RequestDate1, RequestDate2, RequestDate3;
    public Label RequestLocation, RequestLocation1, RequestLocation2, RequestLocation3;

    @FXML
    public TextField AttractionSearch;

    @FXML
    public ChoiceBox<String> LocationSort, Sort;

    @FXML
    public Button AcceptButton, AcceptButton1, AcceptButton2, AcceptButton3;
    public Button RejectButton, RejectButton1, RejectButton2, RejectButton3;

    private ResourceBundle bundle;

    @FXML
    private void initialize() {
        System.out.println("Initializing GuidesRequestsController...");

        // Check if FXML injection worked properly
        checkFXMLInjection();

        try {
            setupMenuItemHoverEffects();
            setupButtonHoverEffects();
            setupChoiceBoxes();
            loadSampleData();

            if (welcome != null && Session.getUsername() != null) {
                welcome.setText("Welcome, " + Session.getUsername() + "!");
            } else if (welcome != null) {
                welcome.setText("Welcome, Guide!");
            }
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkFXMLInjection() {
        System.out.println("=== FXML Injection Check ===");
        System.out.println("welcome: " + (welcome != null ? "OK" : "NULL"));
        System.out.println("home: " + (home != null ? "OK" : "NULL"));
        System.out.println("requests: " + (requests != null ? "OK" : "NULL"));
        System.out.println("bookings: " + (bookings != null ? "OK" : "NULL"));
        System.out.println("alerts: " + (alerts != null ? "OK" : "NULL"));
        System.out.println("settings: " + (settings != null ? "OK" : "NULL"));
        System.out.println("logout: " + (logout != null ? "OK" : "NULL"));
        System.out.println("AttractionSearch: " + (AttractionSearch != null ? "OK" : "NULL"));
        System.out.println("LocationSort: " + (LocationSort != null ? "OK" : "NULL"));
        System.out.println("Sort: " + (Sort != null ? "OK" : "NULL"));
        System.out.println("AcceptButton: " + (AcceptButton != null ? "OK" : "NULL"));
        System.out.println("AcceptButton1: " + (AcceptButton1 != null ? "OK" : "NULL"));
        System.out.println("AcceptButton2: " + (AcceptButton2 != null ? "OK" : "NULL"));
        System.out.println("AcceptButton3: " + (AcceptButton3 != null ? "OK" : "NULL"));
        System.out.println("RejectButton: " + (RejectButton != null ? "OK" : "NULL"));
        System.out.println("RejectButton1: " + (RejectButton1 != null ? "OK" : "NULL"));
        System.out.println("RejectButton2: " + (RejectButton2 != null ? "OK" : "NULL"));
        System.out.println("RejectButton3: " + (RejectButton3 != null ? "OK" : "NULL"));
        System.out.println("RequestName: " + (RequestName != null ? "OK" : "NULL"));
        System.out.println("RequestName1: " + (RequestName1 != null ? "OK" : "NULL"));
        System.out.println("RequestName2: " + (RequestName2 != null ? "OK" : "NULL"));
        System.out.println("RequestName3: " + (RequestName3 != null ? "OK" : "NULL"));
        System.out.println("=============================");
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
                Sort.getItems().addAll("Recent", "Oldest", "By Tourist Name", "By Location");
                Sort.setValue("Recent");
            }
        } catch (Exception e) {
            System.err.println("Error setting up choice boxes: " + e.getMessage());
        }
    }

    private void loadSampleData() {
        try {
            // Load sample request data
            if (RequestName != null) RequestName.setText("Mountain Trek Request");
            if (RequestTourist != null) RequestTourist.setText("John Smith");
            if (RequestDate != null) RequestDate.setText("2025-08-15");
            if (RequestLocation != null) RequestLocation.setText("Everest Base Camp");

            if (RequestName1 != null) RequestName1.setText("Cultural Tour");
            if (RequestTourist1 != null) RequestTourist1.setText("Maria Garcia");
            if (RequestDate1 != null) RequestDate1.setText("2025-08-20");
            if (RequestLocation1 != null) RequestLocation1.setText("Kathmandu");

            if (RequestName2 != null) RequestName2.setText("Wildlife Safari");
            if (RequestTourist2 != null) RequestTourist2.setText("David Wilson");
            if (RequestDate2 != null) RequestDate2.setText("2025-08-25");
            if (RequestLocation2 != null) RequestLocation2.setText("Chitwan");

            if (RequestName3 != null) RequestName3.setText("Lakeside Adventure");
            if (RequestTourist3 != null) RequestTourist3.setText("Sarah Johnson");
            if (RequestDate3 != null) RequestDate3.setText("2025-08-30");
            if (RequestLocation3 != null) RequestLocation3.setText("Pokhara");

            // Load sample attraction data
            if (AttractionName4 != null) AttractionName4.setText("Mount Everest");
            if (AttractionTours4 != null) AttractionTours4.setText("Base Camp Trek");
            if (AttractionRaters4 != null) AttractionRaters4.setText("1250");
            if (Rating4 != null) Rating4.setText("4.8");

            if (AttractionName5 != null) AttractionName5.setText("Phewa Lake");
            if (AttractionTours5 != null) AttractionTours5.setText("Boat Tour");
            if (AttractionRaters5 != null) AttractionRaters5.setText("890");
            if (Rating5 != null) Rating5.setText("4.6");

            if (AttractionName6 != null) AttractionName6.setText("Chitwan National Park");
            if (AttractionTours6 != null) AttractionTours6.setText("Safari Tour");
            if (AttractionRaters6 != null) AttractionRaters6.setText("650");
            if (Rating6 != null) Rating6.setText("4.5");

            if (AttractionName7 != null) AttractionName7.setText("Swayambhunath");
            if (AttractionTours7 != null) AttractionTours7.setText("Temple Visit");
            if (AttractionRaters7 != null) AttractionRaters7.setText("420");
            if (Rating7 != null) Rating7.setText("4.3");

        } catch (Exception e) {
            System.err.println("Error loading sample data: " + e.getMessage());
        }
    }

    private void setupMenuItemHoverEffects() {
        try {
            if (requests != null) {
                requests.setOnMouseEntered(e -> {});
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
            if (AcceptButton != null) setupButtonHover(AcceptButton, "#c7a483", "#E7C6A7");
            if (AcceptButton1 != null) setupButtonHover(AcceptButton1, "#c7a483", "#E7C6A7");
            if (AcceptButton2 != null) setupButtonHover(AcceptButton2, "#c7a483", "#E7C6A7");
            if (AcceptButton3 != null) setupButtonHover(AcceptButton3, "#c7a483", "#E7C6A7");
            if (RejectButton != null) setupButtonHover(RejectButton, "#D95151", "#F65D5D");
            if (RejectButton1 != null) setupButtonHover(RejectButton1, "#D95151", "#F65D5D");
            if (RejectButton2 != null) setupButtonHover(RejectButton2, "#D95151", "#F65D5D");
            if (RejectButton3 != null) setupButtonHover(RejectButton3, "#D95151", "#F65D5D");
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

    // Button click handlers for requests
    @FXML
    private void handleAcceptRequest(MouseEvent event) {
        handleRequestAction("accepted", 0);
    }

    @FXML
    private void handleAcceptRequest1(MouseEvent event) {
        handleRequestAction("accepted", 1);
    }

    @FXML
    private void handleAcceptRequest2(MouseEvent event) {
        handleRequestAction("accepted", 2);
    }

    @FXML
    private void handleAcceptRequest3(MouseEvent event) {
        handleRequestAction("accepted", 3);
    }

    @FXML
    private void handleRejectRequest(MouseEvent event) {
        handleRequestAction("rejected", 0);
    }

    @FXML
    private void handleRejectRequest1(MouseEvent event) {
        handleRequestAction("rejected", 1);
    }

    @FXML
    private void handleRejectRequest2(MouseEvent event) {
        handleRequestAction("rejected", 2);
    }

    @FXML
    private void handleRejectRequest3(MouseEvent event) {
        handleRequestAction("rejected", 3);
    }

    private void handleRequestAction(String action, int requestIndex) {
        try {
            String requestName = getRequestName(requestIndex);
            String touristName = getTouristName(requestIndex);

            System.out.println("Request " + action + ": " + requestName + " by " + touristName);

            // Here you would typically:
            // 1. Update the request status in your data file
            // 2. Send notification to the tourist
            // 3. Update the UI to reflect the change

            // For now, just hide the buttons to show it's been processed
            hideRequestButtons(requestIndex);

        } catch (Exception e) {
            System.err.println("Error handling request action: " + e.getMessage());
        }
    }

    private String getRequestName(int index) {
        try {
            switch (index) {
                case 0: return RequestName != null ? RequestName.getText() : "Unknown Request";
                case 1: return RequestName1 != null ? RequestName1.getText() : "Unknown Request";
                case 2: return RequestName2 != null ? RequestName2.getText() : "Unknown Request";
                case 3: return RequestName3 != null ? RequestName3.getText() : "Unknown Request";
                default: return "Unknown Request";
            }
        } catch (Exception e) {
            return "Unknown Request";
        }
    }

    private String getTouristName(int index) {
        try {
            switch (index) {
                case 0: return RequestTourist != null ? RequestTourist.getText() : "Unknown Tourist";
                case 1: return RequestTourist1 != null ? RequestTourist1.getText() : "Unknown Tourist";
                case 2: return RequestTourist2 != null ? RequestTourist2.getText() : "Unknown Tourist";
                case 3: return RequestTourist3 != null ? RequestTourist3.getText() : "Unknown Tourist";
                default: return "Unknown Tourist";
            }
        } catch (Exception e) {
            return "Unknown Tourist";
        }
    }

    private void hideRequestButtons(int index) {
        try {
            switch (index) {
                case 0:
                    if (AcceptButton != null) AcceptButton.setVisible(false);
                    if (RejectButton != null) RejectButton.setVisible(false);
                    break;
                case 1:
                    if (AcceptButton1 != null) AcceptButton1.setVisible(false);
                    if (RejectButton1 != null) RejectButton1.setVisible(false);
                    break;
                case 2:
                    if (AcceptButton2 != null) AcceptButton2.setVisible(false);
                    if (RejectButton2 != null) RejectButton2.setVisible(false);
                    break;
                case 3:
                    if (AcceptButton3 != null) AcceptButton3.setVisible(false);
                    if (RejectButton3 != null) RejectButton3.setVisible(false);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error hiding request buttons: " + e.getMessage());
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
            if (AttractionSearch != null) {
                String searchText = AttractionSearch.getText();
                System.out.println("Searching for: " + searchText);
                // Implement search functionality here
            }
        } catch (Exception e) {
            System.err.println("Error handling search: " + e.getMessage());
        }
    }
}