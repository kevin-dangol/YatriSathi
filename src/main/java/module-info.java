module com.example.yatrisathi {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.javadoc;
    requires java.desktop;


    opens com.example.yatrisathi to javafx.fxml;
    exports com.example.yatrisathi;
    exports com.example.yatrisathi.Guest;
    opens com.example.yatrisathi.Guest to javafx.fxml;
    exports com.example.yatrisathi.User;
    opens com.example.yatrisathi.User to javafx.fxml;
    exports com.example.yatrisathi.Guide;
    opens com.example.yatrisathi.Guide to javafx.fxml;
    exports com.example.yatrisathi.Admin;
    opens com.example.yatrisathi.Admin to javafx.fxml;
}