package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryWindowController {
    @FXML
    TextField categoryField;

    private Stage stage;
    private AdminMenuController adminMenuController;
    private LoginMenuController loginMenuController;
    private ItemDatabase itemDatabase;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setup(AdminMenuController adminMenuController,LoginMenuController controller) {
        this.adminMenuController = adminMenuController;
        this.loginMenuController = controller;
        this.itemDatabase = loginMenuController.getItemDatabase();
    }
    @FXML
    private void addCategory(ActionEvent event) {
        String category = categoryField.getText();
        // Check if any of the fields are empty
        if (category.isEmpty()) {
            adminMenuController.showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
            return;
        }
        itemDatabase.addCategory(category);
        adminMenuController.showAlert(Alert.AlertType.INFORMATION,"Success","Successfully Added","Your new Category has been added successfully");

        // Close the popup window
        stage.close();
    }

    @FXML
    private void closeWindow(){
        stage.close();
    }

}