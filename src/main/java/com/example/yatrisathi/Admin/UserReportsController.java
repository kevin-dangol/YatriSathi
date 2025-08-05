package com.example.yatrisathi.Admin;

import com.example.yatrisathi.utility.ChangeScene;
import com.example.yatrisathi.utility.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserReportsController {

    @FXML
    public Label welcome, User;
    public Label home, guides, attractions, bookings, reports, admin, switch_user;
    public Label alerts, settings, logout;

    @FXML
    private PieChart PieChart;

    @FXML
    private BarChart<String, Number> BarChart;

    @FXML
    private LineChart<String, Number> LineChart;

    @FXML
    private AreaChart<String, Number> AreaChart;

    @FXML
    private Button Export;

    // Data storage for CSV export
    private int touristCount = 0;
    private int guideCount = 0;
    private int attractionCount = 0;
    private int bookingCount = 0;
    private Map<String, Integer> locationStats = new HashMap<>();
    private Map<String, Integer> dailyStats = new HashMap<>();

    @FXML
    private void initialize() {
        System.out.println("Initializing UserReportsController...");

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

        if (reports != null) reports.setVisible(true);
        if (admin != null) admin.setVisible(true);

        setupMenuItemHoverEffects();

        // Add null check for exportCSVButton
        if (Export != null) {
            setupButtonHoverEffects();
        } else {
            System.err.println("ERROR: exportCSVButton is null! Check fx:id in FXML file.");
        }

        loadRealData();

        // Add null checks for charts
        if (PieChart != null) {
            generatePieChartData();
        } else {
            System.err.println("ERROR: PieChart is null!");
        }

        if (BarChart != null) {
            generateBarChartData();
        } else {
            System.err.println("ERROR: BarChart is null!");
        }

        if (LineChart != null) {
            generateLineChartData();
        } else {
            System.err.println("ERROR: LineChart is null!");
        }

        if (AreaChart != null) {
            generateAreaChartData();
        } else {
            System.err.println("ERROR: AreaChart is null!");
        }

    }

    private void loadRealData() {
        loadGuideData();
        loadAttractionData();
        loadBookingData();
        loadTouristData();
    }

    private void loadGuideData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/guides.txt"));
            String line;
            guideCount = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    guideCount++;
                    String location = parts[4].trim();
                    locationStats.put(location, locationStats.getOrDefault(location, 0) + 1);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading guide data: " + e.getMessage());
        }
    }

    private void loadAttractionData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/attraction.txt"));
            String line;
            attractionCount = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    attractionCount++;
                    String location = parts[1].trim();
                    locationStats.put(location, locationStats.getOrDefault(location, 0) + 1);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading attraction data: " + e.getMessage());
        }
    }

    private void loadBookingData() {
        try {
            // Try to load booking data if file exists
            File bookingFile = new File("src/main/resources/bookings.txt");
            if (bookingFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(bookingFile));
                String line;
                bookingCount = 0;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        bookingCount++;
                        // Simulate daily booking data for the last 7 days
                        LocalDate date = LocalDate.now().minusDays(bookingCount % 7);
                        String dateStr = date.format(DateTimeFormatter.ofPattern("MMM dd"));
                        dailyStats.put(dateStr, dailyStats.getOrDefault(dateStr, 0) + 1);
                    }
                }
                br.close();
            } else {
                // Generate sample booking data if no file exists
                Random random = new Random();
                bookingCount = 25 + random.nextInt(50);

                // Generate daily booking data for the last 7 days
                for (int i = 6; i >= 0; i--) {
                    LocalDate date = LocalDate.now().minusDays(i);
                    String dateStr = date.format(DateTimeFormatter.ofPattern("MMM dd"));
                    dailyStats.put(dateStr, 2 + random.nextInt(8));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading booking data: " + e.getMessage());
        }
    }

    private void loadTouristData() {
        try {
            // Try to load tourist/user data if file exists
            File userFile = new File("src/main/resources/users.txt");
            if (userFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(userFile));
                String line;
                touristCount = 0;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 1) {
                        touristCount++;
                    }
                }
                br.close();
            } else {
                // Generate sample tourist count if no file exists
                Random random = new Random();
                touristCount = 100 + random.nextInt(200);
            }
        } catch (Exception e) {
            System.out.println("Error loading tourist data: " + e.getMessage());
        }
    }

    private void generatePieChartData() {
        try {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    new PieChart.Data("Tourists (" + touristCount + ")", touristCount),
                    new PieChart.Data("Guides (" + guideCount + ")", guideCount),
                    new PieChart.Data("Attractions (" + attractionCount + ")", attractionCount)
            );

            PieChart.setData(pieData);
            PieChart.setTitle("System Overview");
        } catch (Exception e) {
            System.err.println("Error generating pie chart data: " + e.getMessage());
        }
    }

    private void generateBarChartData() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Count");

            series.getData().add(new XYChart.Data<>("Tourists", touristCount));
            series.getData().add(new XYChart.Data<>("Guides", guideCount));
            series.getData().add(new XYChart.Data<>("Attractions", attractionCount));
            series.getData().add(new XYChart.Data<>("Bookings", bookingCount));

            BarChart.getData().clear();
            BarChart.getData().add(series);
            BarChart.setTitle("System Statistics");
        } catch (Exception e) {
            System.err.println("Error generating bar chart data: " + e.getMessage());
        }
    }

    private void generateLineChartData() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily New Users");

            // Use real daily stats or generate sample data
            if (dailyStats.isEmpty()) {
                Random random = new Random();
                for (int day = 6; day >= 0; day--) {
                    LocalDate date = LocalDate.now().minusDays(day);
                    String dateStr = date.format(DateTimeFormatter.ofPattern("MMM dd"));
                    series.getData().add(new XYChart.Data<>(dateStr, 5 + random.nextInt(15)));
                }
            } else {
                // Sort dates and add to chart
                List<String> sortedDates = new ArrayList<>(dailyStats.keySet());
                Collections.sort(sortedDates);

                for (String date : sortedDates) {
                    series.getData().add(new XYChart.Data<>(date, dailyStats.get(date)));
                }
            }

            LineChart.getData().clear();
            LineChart.getData().add(series);
            LineChart.setTitle("Daily New Users (Last 7 Days)");
        } catch (Exception e) {
            System.err.println("Error generating line chart data: " + e.getMessage());
        }
    }

    private void generateAreaChartData() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily Bookings");

            // Generate booking trend data
            Random random = new Random();
            for (int day = 6; day >= 0; day--) {
                LocalDate date = LocalDate.now().minusDays(day);
                String dateStr = date.format(DateTimeFormatter.ofPattern("MMM dd"));
                int bookings = dailyStats.getOrDefault(dateStr, 3 + random.nextInt(10));
                series.getData().add(new XYChart.Data<>(dateStr, bookings));
            }

            AreaChart.getData().clear();
            AreaChart.getData().add(series);
            AreaChart.setTitle("Daily Bookings Trend");
        } catch (Exception e) {
            System.err.println("Error generating area chart data: " + e.getMessage());
        }
    }

    @FXML
    private void handleExportCSV(MouseEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Report as CSV");
            fileChooser.setInitialFileName("yatrisathi_report_" +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv");

            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            Stage stage = (Stage) Export.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                exportToCSV(file);
                System.out.println("Report exported to: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error exporting CSV: " + e.getMessage());
        }
    }

    private void exportToCSV(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        // Write header
        writer.write("YatriSathi System Report");
        writer.newLine();
        writer.write("Generated on: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        writer.newLine();
        writer.newLine();

        // Write system overview
        writer.write("System Overview");
        writer.newLine();
        writer.write("Category,Count");
        writer.newLine();
        writer.write("Tourists," + touristCount);
        writer.newLine();
        writer.write("Guides," + guideCount);
        writer.newLine();
        writer.write("Attractions," + attractionCount);
        writer.newLine();
        writer.write("Bookings," + bookingCount);
        writer.newLine();
        writer.newLine();

        // Write location statistics
        if (!locationStats.isEmpty()) {
            writer.write("Location Statistics");
            writer.newLine();
            writer.write("Location,Count");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : locationStats.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            writer.newLine();
        }

        // Write daily statistics
        if (!dailyStats.isEmpty()) {
            writer.write("Daily Statistics (Last 7 Days)");
            writer.newLine();
            writer.write("Date,Bookings");
            writer.newLine();

            List<String> sortedDates = new ArrayList<>(dailyStats.keySet());
            Collections.sort(sortedDates);

            for (String date : sortedDates) {
                writer.write(date + "," + dailyStats.get(date));
                writer.newLine();
            }
        }

        writer.close();
    }

    private void setupMenuItemHoverEffects() {
        if (reports != null) reports.setOnMouseEntered(e -> {});

        if (home != null) setupMenuItemHover(home);
        if (guides != null) setupMenuItemHover(guides);
        if (attractions != null) setupMenuItemHover(attractions);
        if (bookings != null) setupMenuItemHover(bookings);
        if (admin != null) setupMenuItemHover(admin);
        if (alerts != null) setupMenuItemHover(alerts);
        if (settings != null) setupMenuItemHover(settings);
        if (logout != null) setupMenuItemHover(logout);
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
            System.err.println("Error setting up menu item hover for " + menuItem + ": " + e.getMessage());
        }
    }

    private void setupButtonHoverEffects() {
        if (Export != null) {
            setupButtonHover(Export);
        } else {
            System.err.println("ERROR: exportCSVButton is null in setupButtonHoverEffects!");
        }
    }

    private void setupButtonHover(Button button) {
        try {
            String normalStyle = "-fx-background-color: #c7a483; -fx-cursor: hand; -fx-background-radius: 15;";
            String hoverStyle = "-fx-background-color: #E7C6A7; -fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02; -fx-background-radius: 10;";

            button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
            button.setOnMouseExited(e -> button.setStyle(normalStyle));
        } catch (Exception e) {
            System.err.println("Error setting up button hover: " + e.getMessage());
        }
    }

    // Helper method to set active menu item
    private void setActiveMenuItem(Label activeItem) {
        if (home != null) resetMenuItem(home);
        if (guides != null) resetMenuItem(guides);
        if (bookings != null) resetMenuItem(bookings);
        if (attractions != null) resetMenuItem(attractions);
        if (reports != null) resetMenuItem(reports);
        if (admin != null) resetMenuItem(admin);
        if (alerts != null) resetMenuItem(alerts);
        if (settings != null) resetMenuItem(settings);
        if (logout != null) resetMenuItem(logout);

        if (activeItem != null) {
            activeItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; -fx-cursor: hand;");
        }
    }

    private void resetMenuItem(Label menuItem) {
        if (menuItem != null) {
            menuItem.setStyle("-fx-cursor: hand;");
        }
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