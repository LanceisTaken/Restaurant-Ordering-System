package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TopUpMenuController {
    @FXML
    private TextField AmountField;
    Users currentUser;
    UserDatabase userDatabase;
    TransactionDatabase transactionDatabase;
    LoginMenuController loginMenuController;
    Stage TopUpMenuStage;


    public void setup(LoginMenuController controller) {this.loginMenuController=controller;}

    public void setMainMenuStage(Stage primaryStage) {this.TopUpMenuStage=primaryStage;}

    public void initializeController() {
        this.userDatabase = loginMenuController.getUserDatabase();
        this.transactionDatabase = loginMenuController.getTransactionDatabase();
        currentUser = loginMenuController.getLoggedInUser();
    }
    public void handleTopUp(ActionEvent event) {
        String amountText = AmountField.getText().trim();

        // Check if the amount field is empty
        if (amountText.isEmpty()) {
            // Show error message for empty amount field
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Amount cannot be empty.");
            alert.showAndWait();
            return; // Exit the method
        }

        // Check if the amount is a valid number with up to 2 decimal places
        if (amountText.matches("^\\d+(\\.\\d{1,2})?$")) {
            double amount = Double.parseDouble(amountText);

            // Top up successful
            userDatabase.addBalance(amount, currentUser);
            Transaction transaction = new Transaction(currentUser.getUserID(), "TopUp: +RM"+amount);
            transactionDatabase.addObject(transaction);


            // Show success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Top up successful");
            alert.showAndWait();

            // Clear the TextField
            AmountField.clear();
        } else {
            // Show error message for invalid amount format
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid amount format. Please enter a valid number with up to 2 decimal places.");
            alert.showAndWait();
        }
    }
    public void handleGoBack(ActionEvent event){
        //Close TopUp Menu
        TopUpMenuStage.close();

        //Open the Order Menu
        MainMenu mainMenu = new MainMenu(loginMenuController);
        try {
            mainMenu.start(new Stage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
