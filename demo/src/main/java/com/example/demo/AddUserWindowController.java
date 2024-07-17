package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserWindowController {
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;

    private Stage stage;
    private AdminMenuController adminMenuController;
    private LoginMenuController loginMenuController;
    private UserDatabase userDatabase;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setup(AdminMenuController adminMenuController,LoginMenuController controller) {
        this.adminMenuController = adminMenuController;
        this.loginMenuController = controller;
        this.userDatabase = loginMenuController.getUserDatabase();
    }
    @FXML
    public void addUser() {
        // Get the input from the text fields
        String name = usernameField.getText();
        String password = passwordField.getText();

        // Create a new user with the input
        Users newUser = new Users(name, password);

        // Add the new user to the database
        userDatabase.addObject(newUser);
        //Updates the TableView
        adminMenuController.userList.add(newUser);

        usernameField.clear();
        passwordField.clear();

        //Alert
        adminMenuController.showAlert(Alert.AlertType.INFORMATION,"Success","Successfully Added","User has been added successfully");


        stage.close();
    }

    @FXML
    private void closeWindow(){
        stage.close();
    }

}