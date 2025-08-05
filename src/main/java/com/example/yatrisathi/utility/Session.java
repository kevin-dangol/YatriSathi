package com.example.yatrisathi.utility;

import java.util.Locale;

public class Session {

    private static String username;
    private static String Email;
    private static String Address;
    private static String Password;
    private static String Number;
    private static int authority;
    private static Locale locale = new Locale("en"); // Default to English
    private static String userType;
    private static int userId;


    public static void setLocale(String language) {
        locale = "Nepali".equals(language) ? new Locale("ne") : new Locale("en");
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setUsername(String user) {
        username = user;
    }

    public static void setAuthority(int auth) {
        authority = auth;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static void setAddress(String address) {
        Address = address;
    }

    public static void setNumber(String number) {
        Number = number;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static int getAuthority() {
        return authority;
    }

    public static String getEmail() {
        return Email;
    }

    public static String getAddress() {
        return Address;
    }

    public static String getPassword() {
        return Password;
    }

    public static String getNumber() {
        return Number;
    }

    public static boolean isAdmin() {
        return "ADMIN".equals(userType) || "SUPER_ADMIN".equals(userType);
    }

    public static boolean isGuide() {
        return "GUIDE".equals(userType);
    }

    public static boolean isTourist() {
        return "TOURIST".equals(userType) || "USER".equals(userType);
    }
}