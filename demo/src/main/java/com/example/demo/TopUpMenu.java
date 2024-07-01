package com.example.demo;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class TopUpMenu extends Application {
    public LoginMenuController loginMenuController;
    public TopUpMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TopUpMenu.fxml"));
        Parent TopUpMenuRoot = loader.load();
        TopUpMenuController topUpMenuController = loader.getController();
        topUpMenuController.setup(loginMenuController);

        // Pass the reference to the MainMenu stage to the MainMenuController
        topUpMenuController.setMainMenuStage(primaryStage);

        // Initialize the controller after loginMenuController is set
        topUpMenuController.initializeController();

        primaryStage.setTitle("Top Up Menu");
        primaryStage.setScene(new Scene(TopUpMenuRoot, 600, 400));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

