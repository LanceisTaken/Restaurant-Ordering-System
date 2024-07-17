package com.example.demo;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;
import java.net.URL;


public class LoginMenu extends Application {
    UserDatabase userdatabase;
    TransactionDatabase transactionDatabase;
    ItemDatabase itemDatabase;

    public Stage primaryStage;
    LoginMenuController controller;

    public UserDatabase getUserdatabase() {
        return userdatabase;}

    public LoginMenuController getController() {
        return controller;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage = primaryStage;
        this.userdatabase = new UserDatabase("users.dat");
        this.itemDatabase = new ItemDatabase("items.dat");
        this.transactionDatabase = new TransactionDatabase("transactions.dat");

        try {
            //Sets the icon for Taskbar
            InputStream inputStream = getClass().getResourceAsStream("/Happy.png");

            // Verify if input stream is null
            if (inputStream == null) {
                System.out.println("Image file not found or could not be loaded.");
                return;
            }

            Image icon = new Image(inputStream);

            URL fxmlUrl = getClass().getResource("/RestaurantGUI.fxml");
            if (fxmlUrl == null) {
                System.out.println("FXML file not found!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            this.controller = loader.getController();
            controller.setMainApp(this);
            controller.setup(userdatabase,itemDatabase,transactionDatabase);

            primaryStage.setTitle("Restaurant Main Menu");
            primaryStage.setScene(new Scene(root, 681, 462));
            primaryStage.getIcons().add(icon);
            primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Exception loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to handle logout
    public void logout() {
        resetUserSession();
        openLoginMenu();
    }

    // Method to open the login menu
    public void openLoginMenu() {
        try {
            // Create a new instance of LoginMenu and start it
            LoginMenu loginMenu = new LoginMenu();
            Stage stage = new Stage();
            loginMenu.start(stage);
        } catch (Exception e) {
            System.out.println("Exception opening LoginMenu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetUserSession() {
        controller.setCurrentUser(null);
    }


}
