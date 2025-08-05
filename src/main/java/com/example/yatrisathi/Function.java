package com.example.yatrisathi;

import javafx.fxml.FXML;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Function {

    @FXML
    public static String DashBoardTourists() {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/dashboard.txt"))) {

            String[] parts = reader.readLine().split(",");
            if (parts.length == 3) {

                return parts[0].trim();

            }

        } catch (IOException e) {

            System.out.println("Error reading user file: " + e.getMessage());

        }

        return null;

    }

    @FXML
    public static String DashBoardGuides() {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/dashboard.txt"))) {

            String[] parts = reader.readLine().split(",");
            if (parts.length == 3) {

                return parts[1].trim();

            }

        } catch (IOException e) {

            System.out.println("Error reading user file: " + e.getMessage());

        }

        return null;

    }

    @FXML
    public static String DashBoardAttraction() {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/dashboard.txt"))) {

            String[] parts = reader.readLine().split(",");
            if (parts.length == 3) {

                return parts[2].trim();

            }

        } catch (IOException e) {

            System.out.println("Error reading user file: " + e.getMessage());

        }

        return null;

    }

    //save guide booking.txt
    public static void SaveGuideBookings(String Name, String Location, String Attraction, String Date, String Username) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt", true))) {

            writer.write(Name + "," + Location + "," + Attraction + "," + Date + "," + Username);
            writer.newLine();

        } catch (IOException e) {

            System.err.println("Error saving booking.txt: " + e.getMessage());

        }
    }

    //save attraction.txt booking.txt
    public static void SaveAttractionBookings(String Name, String Location, String GuideName, String Date, String Username) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/booking.txt", true))) {

            writer.write(Name + "," + Location + "," + GuideName + "," + Date + "," + Username);
            writer.newLine();

        } catch (IOException e) {

            System.err.println("Error saving booking.txt: " + e.getMessage());

        }
    }

    //save Settings
    @FXML
    public static boolean SaveSettingsCredentials(String username, String email, String number, String password) {
        String filePath = "users.txt";
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    // Replace the user line
                    String updatedUser = username + "," + email + "," + password + "," + number + "," + "User";
                    updatedLines.add(updatedUser);
                } else {
                    updatedLines.add(line); // keep other lines unchanged
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
            return false;
        }

        // Write updated lines back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            return true;

        } catch (IOException e) {
            System.out.println("Error writing to user file: " + e.getMessage());
        }

        return false;
    }

}
