module org.example.kolos2023 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.swing;


    opens org.example.kolos2023 to javafx.fxml;
    exports org.example.kolos2023;
}