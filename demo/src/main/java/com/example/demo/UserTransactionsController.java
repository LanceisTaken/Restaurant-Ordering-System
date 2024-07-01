package com.example.demo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserTransactionsController {
    private LoginMenuController loginMenuController;



    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableColumn<Transaction, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Transaction, String> reportColumn;

    private ArrayList<Transaction> transactions;
    private TransactionDatabase transactionDatabase;
    private Stage userTransactionStage;

    public void setup(LoginMenuController controller) {
        this.loginMenuController = controller;
    }

    public void initializeController() {
        this.transactionDatabase = loginMenuController.getTransactionDatabase();
        this.transactions = transactionDatabase.getObjectArray();
        transactionsTable.setItems(FXCollections.observableArrayList(transactionDatabase.getObjectArray()));

        // Populate table with transactions
        populateTransactionsTable();
    }

    public void setUserTransactionStage(Stage userTransactionStage) {
        this.userTransactionStage = userTransactionStage;
    }

    private void populateTransactionsTable() {
        // Clear table
        transactionsTable.getItems().clear();

        // Get current user's transactions
        int currentUserID = loginMenuController.getLoggedInUser().getUserID();
        for (Transaction transaction : transactions) {
            if (transaction.getUserID()==(currentUserID)) {
                transactionsTable.getItems().add(transaction);
            }
        }
    }
    public void handleReturn(ActionEvent event){
        returnMenu();
    }
    private void returnMenu(){
            //Close current stage
            userTransactionStage.close();
            // Open the Main Menu
            MainMenu mainMenu = new MainMenu(loginMenuController);
            try {
                mainMenu.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}

