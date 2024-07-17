package com.example.demo;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class AdminMenu extends Application {
    private LoginMenuController loginMenuController;
    private AdminMenuController adminMenuController;

    public AdminMenu(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminMenu.fxml"));
        Parent root = loader.load();
        adminMenuController = loader.getController();
        adminMenuController.setLoginMenuController(loginMenuController);
        adminMenuController.setStage(primaryStage);
        adminMenuController.initializeController();

        primaryStage.setTitle("Admin Menu");
        primaryStage.setScene(new Scene(root, 1200, 780));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
