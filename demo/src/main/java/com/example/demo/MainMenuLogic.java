package com.example.demo;

import javafx.stage.Stage;

import java.text.DecimalFormat;

public class MainMenuLogic {
    private final Users currentUser;
    private final UserDatabase userDatabase;

    public MainMenuLogic(LoginMenuController loginMenuController) {
        this.userDatabase = loginMenuController.getUserDatabase();
        this.currentUser = loginMenuController.getLoggedInUser();
    }

    public void initializeController(MainMenuController mainMenuController) {
        double balance = userDatabase.getBalance(currentUser);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String formattedBalance = decimalFormat.format(balance);
        mainMenuController.setBalanceLabelText(formattedBalance);
    }

    public boolean handleOrdering(boolean pressed) {
        return pressed;
    }

    public boolean handleUserTransactions(boolean pressed){
        return pressed;
    }

    public boolean handleTopUp(boolean pressed) {
        return pressed;
    }

    public void handleLogout(LoginMenuController loginMenuController, MainMenuController mainMenuController) {
        // Close the main menu stage
        mainMenuController.getMainMenuStage().close();

        // Handle logout in login menu controller
        loginMenuController.handleLogout();
    }
}
