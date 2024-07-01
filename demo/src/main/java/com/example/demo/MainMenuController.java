package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class MainMenuController {
    @FXML
    private Label balanceLabel;
    public LoginMenuController loginMenuController;
    private MainMenuLogic mainMenuLogic;
    private Stage mainMenuStage; // Keep reference to the stage of MainMenu
    public void setup(LoginMenuController controller) {
        this.loginMenuController = controller;
        this.mainMenuLogic = new MainMenuLogic(loginMenuController);
    }
    public Stage  getMainMenuStage() {
        return mainMenuStage;
    }
    public void setBalanceLabelText(String text) {
        balanceLabel.setText(text);
    }
    public void setMainMenuStage(Stage primaryStage) {
        this.mainMenuStage = primaryStage;
    }
    public void initializeController() {
        mainMenuLogic.initializeController(this);
    }
    // Method to handle logout from the main menu
    public void handleLogout(ActionEvent event) {
        mainMenuLogic.handleLogout(loginMenuController,this);
    }
    // New method to initialize the controller after loginMenuController is set
    public void handleOrdering(ActionEvent event){
        boolean pressed = true;
        openOrderMenu(mainMenuLogic.handleOrdering(pressed));
    }
    private void openOrderMenu(boolean pressed) {
        if (pressed) {
            // Close the main menu stage
            if (mainMenuStage != null) {
                mainMenuStage.close();
            }
            //Open the Order Menu
            OrderMenu orderMenu = new OrderMenu(loginMenuController);
            try {
                orderMenu.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void handleUserTransactions(ActionEvent event){
        boolean pressed = true;
        openUserTransactions(pressed);
    }
    private void openUserTransactions(boolean pressed){
        if (pressed) {
            // Close the main menu stage
            if (mainMenuStage != null) {
                mainMenuStage.close();
            }
            //Open the Order Menu
            UserTransactions userTransactions = new UserTransactions(loginMenuController);
            try {
                userTransactions.start(new Stage());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void handleTopUp(ActionEvent event){
        boolean pressed = true;
        openTopUp(mainMenuLogic.handleTopUp(pressed));
    }
    private void openTopUp(boolean pressed){
        if(pressed){
            // Close the main menu stage
            if (mainMenuStage != null) {
                mainMenuStage.close();
            }
            //Open the Order Menu
            TopUpMenu topUpMenu = new TopUpMenu(loginMenuController);
            try {
                topUpMenu.start(new Stage());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
