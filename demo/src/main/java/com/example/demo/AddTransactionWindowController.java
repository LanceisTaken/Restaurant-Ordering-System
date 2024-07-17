package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class AddTransactionWindowController {
    @FXML
    private ComboBox<Users> userComboBox;
    @FXML
    private TextArea receiptArea;
    private Stage stage;
    private AdminMenuController adminMenuController;
    private LoginMenuController loginMenuController;
    private TransactionDatabase transactionDatabase;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setup(AdminMenuController adminMenuController,LoginMenuController controller) {
        this.adminMenuController = adminMenuController;
        this.loginMenuController = controller;
        this.transactionDatabase = loginMenuController.getTransactionDatabase();
        userComboBox.getItems().addAll(adminMenuController.userList);
    }
    @FXML
    public void addTransaction() {
        // Get the input from the text fields
        int userId = userComboBox.getValue().getUserID();
        String report = receiptArea.getText();

        // Check if any of the fields are empty
        if (report.isEmpty()) {
            adminMenuController.showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
            return;
        }
        // Create a new transaction
        Transaction newTransaction = new Transaction(userId,report);
        // Add the new transaction to the database
        transactionDatabase.addObject(newTransaction);
        //Updates the TableView
        adminMenuController.transactionList.add(newTransaction);
        //Clear Fields
        receiptArea.clear();
        userComboBox.getItems().clear();


        //Alert
        adminMenuController.showAlert(Alert.AlertType.INFORMATION,"Success","Successfully Added","Transaction has been added successfully");

        stage.close();
    }

    @FXML
    private void closeWindow(){
        stage.close();
    }

}