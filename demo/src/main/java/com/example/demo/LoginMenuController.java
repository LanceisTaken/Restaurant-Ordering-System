package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LoginMenuController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    Stage loginMenuStage;
    Stage mainMenuStage;
    private UserDatabase userDatabase;
    private TransactionDatabase transactionDatabase;
    private ItemDatabase itemDatabase;
    private ArrayList<Users> users;
    private Users currentUser;
    private LoginMenuLogic logic;
    private LoginMenu mainApp;
    public void setup(UserDatabase userdatabase,ItemDatabase itemDatabase,TransactionDatabase transactionDatabase) {
        this.userDatabase = userdatabase;
        this.itemDatabase = itemDatabase;
        this.transactionDatabase = transactionDatabase;
        this.logic = new LoginMenuLogic(this.userDatabase);
        this.users = userDatabase.getObjectArray();
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }
    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public TransactionDatabase getTransactionDatabase() {
        return transactionDatabase;
    }

    public ItemDatabase getItemDatabase() {
        return itemDatabase;
    }

    public LoginMenuController(){
    }

    public void setMainApp(LoginMenu mainApp) {
        this.mainApp = mainApp;
    }
    // Method to handle logout
    public void handleLogout() {
        if (mainApp != null) {
            mainApp.logout();
        }
    }
    public void handleLogin(ActionEvent event) {
        String username = nameField.getText();
        String password = passwordField.getText();
        if (logic.login(username, password,users)) {
            this.currentUser = logic.getLoggedInUser();
            //System.out.println("Login Good");
            if(currentUser.isAdmin()){
                openAdminMenu(mainApp.getPrimaryStage());
                return;
            }
            openMainMenu(mainApp.getPrimaryStage());


        } else {
            //Login failed, user already exists
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Unsuccessful");
            alert.setHeaderText(null);
            alert.setContentText("Name/Password does not match existing users.");
            alert.showAndWait();
        }
    }
    public void register(String username,String password){
        if (logic.register(username, password, users)) {
            // Registration successful, display a message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("Registration successful!");
            alert.showAndWait();
        } else {
            // Registration failed, user already exists
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Unsuccessful");
            alert.setHeaderText(null);
            alert.setContentText("User already exists!");
            alert.showAndWait();
        }
    }
    public void handleSignUp(ActionEvent event) {
        openRegistrationMenu();
    }
    public Users getLoggedInUser() {
        return logic.getLoggedInUser();
    }

    public void setStage(Stage primaryStage) {
        this.loginMenuStage = primaryStage;
    }
    public void openRegistrationMenu(){
        try {
            // Open the RegistrationMenu
            RegistrationMenu registrationMenu = new RegistrationMenu(mainApp.getController());
            Stage stage = new Stage();
            registrationMenu.start(stage);
        } catch (Exception e) {
            System.out.println("Exception opening MainMenu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void openMainMenu(Stage primaryStage) {
        try {
            // Close the login menu stage
            primaryStage.close();
            // Open the MainMenu
            MainMenu mainMenu = new MainMenu(mainApp.getController());
            Stage stage = new Stage();
            mainMenu.start(stage);
        } catch (Exception e) {
            System.out.println("Exception opening MainMenu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void openAdminMenu(Stage primaryStage) {
        try {
            // Close the login menu stage
            primaryStage.close();
            // Open the MainMenu
            AdminMenu adminMenu = new AdminMenu(mainApp.getController());
            Stage stage = new Stage();
            adminMenu.start(stage);
        } catch (Exception e) {
            System.out.println("Exception opening MainMenu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void setMainMenuStage(Stage mainMenuStage) {
        this.mainMenuStage = mainMenuStage;
    }
    @FXML
    private void quit(ActionEvent event){
        Platform.exit();
    }
}
