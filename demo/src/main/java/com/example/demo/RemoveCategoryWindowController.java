package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RemoveCategoryWindowController {
    @FXML
    ComboBox<String> removerBox;

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

        ArrayList<String>categorySet = itemDatabase.getCategorySet();
        String[] categoryArray = categorySet.toArray(new String[0]);

        removerBox.getItems().addAll(categoryArray);

        // Close the popup window
        stage.close();
    }
    @FXML
    public void removeCategory(){
        String category = removerBox.getValue();
        // Check if any of the fields are empty
        if (category == null || category.isEmpty()) {
            adminMenuController.showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please select a category to remove.");
            return;
        }

        // Check if any item is assigned the category being removed
        for (Items item : itemDatabase.getObjectArray()) {
            if (item.getCategory().equals(category)) {
                adminMenuController.showAlert(Alert.AlertType.ERROR, "Error", "Cannot Remove Category",
                        "Category cannot be removed as it is assigned to an item in the database.");
                return;
            }
        }
        // No items found with the category being removed, proceed with removal
        itemDatabase.removeCategory(category);
        adminMenuController.showAlert(Alert.AlertType.INFORMATION,"Success","Successfully Removed","Your Category has been removed successfully");

    }

    @FXML
    private void closeWindow(){
        stage.close();
    }

}