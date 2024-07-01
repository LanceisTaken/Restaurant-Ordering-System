package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainMenu extends Application {
    public LoginMenuController loginMenuController;
    public MainMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        Parent MainMenuRoot = loader.load();
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setup(loginMenuController);

        // Pass the reference to the MainMenu stage to the MainMenuController
        mainMenuController.setMainMenuStage(primaryStage);

        // Initialize the controller after loginMenuController is set
        mainMenuController.initializeController();

        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(MainMenuRoot, 600, 400));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();

        // Pass the reference to the MainMenu stage to the LoginMenuController
        loginMenuController.setMainMenuStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
