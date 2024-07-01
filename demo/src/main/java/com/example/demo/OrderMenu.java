package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class OrderMenu extends Application {
    public LoginMenuController loginMenuController;
    public OrderMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderMenu.fxml"));
        Parent OrderMenuRoot = loader.load();
        OrderMenuController orderMenuController = loader.getController();
        orderMenuController.setLoginMenuController(loginMenuController);

        // Pass the reference to the OrderMenu stage to the OrderMenuController
        orderMenuController.setOrderMenuStage(primaryStage);
        orderMenuController.initializeController();

        primaryStage.setTitle("Restaurant Ordering Menu");
        primaryStage.setScene(new Scene(OrderMenuRoot, 1200, 780));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
