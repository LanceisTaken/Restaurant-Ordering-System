package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistrationMenu extends Application {
    public LoginMenuController loginMenuController;

    public RegistrationMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegistrationMenu.fxml"));
        Parent RegistrationMenuRoot = loader.load();
        RegistrationMenuController registrationMenuController = loader.getController();
        registrationMenuController.setup(loginMenuController);

        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(RegistrationMenuRoot, 400, 250));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

