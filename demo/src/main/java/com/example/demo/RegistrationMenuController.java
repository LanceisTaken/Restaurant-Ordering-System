package com.example.demo;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationMenuController {
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button signUpButton;
    private LoginMenuController loginMenuController;
    public void setup(LoginMenuController loginMenuController){
        this.loginMenuController = loginMenuController;
    }
    @FXML
    public void signUp() {
        String name = nameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Name and password fields cannot be empty!");
        } else if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
        } else {
            loginMenuController.register(name,password);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
        }
    }
    @FXML
    public void quit() {
        // Close the window
        signUpButton.getScene().getWindow().hide();
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

