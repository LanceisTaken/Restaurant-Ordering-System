module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires org.testng;
    requires org.junit.jupiter.api;
    requires org.mockito;
    requires java.sql;
    requires com.jfoenix;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}