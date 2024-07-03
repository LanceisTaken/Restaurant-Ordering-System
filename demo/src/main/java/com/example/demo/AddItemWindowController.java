package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddItemWindowController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField filePathField;

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
        categoryComboBox.getItems().addAll(itemDatabase.getCategorySet());
    }



    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            filePathField.setText(imagePath);
            imageView.setImage(new Image("file:" + imagePath));
        }
    }

    @FXML
    private void addItem(ActionEvent event) {
        String name = nameField.getText();
        String priceText = priceField.getText();
        String category = categoryComboBox.getValue();
        String filePath = filePathField.getText();

        // Check if any of the fields are empty
        if (name.isEmpty() || priceText.isEmpty() || category == null) {
            adminMenuController.showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
            return;
        }

        // Validate the price format
        double price;
        try {
            price = Double.parseDouble(String.format("%.2f", Double.parseDouble(priceText)));
        } catch (NumberFormatException e) {
            // Handle invalid price format
            adminMenuController.showAlert(Alert.AlertType.ERROR, "Error", "Invalid Price", "Price must be a valid number.");
            return;
        }

        // Ensure price has no more than 2 decimal places
        if ((String.valueOf(price).indexOf('.') != -1 ? String.valueOf(price).substring(String.valueOf(price).indexOf('.') + 1).length() : 0) > 2) {
            // Display error message or handle as appropriate
            adminMenuController.showAlert(Alert.AlertType.ERROR, "Error", "Invalid Price", "Price must have no more than 2 decimal places.");
            return;
        }

        // Create a new item with the input
        Items newItem = new Items(name, price, category, filePath);

        // Add the new item to the database
        adminMenuController.itemDatabase.addObject(newItem);
        //Updates the TableView
        adminMenuController.itemList.add(newItem);

        // Clear the text fields
        nameField.clear();
        priceField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        imageView.setImage(null);

        // Close the popup window
        stage.close();
    }

    @FXML
    private void closeWindow(){
        stage.close();
    }

}