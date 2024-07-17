package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AddUserWindow extends Application {
    private AdminMenuController adminMenuController;
    private LoginMenuController loginMenuController;

    public AddUserWindow(AdminMenuController adminMenuController,LoginMenuController loginMenuController) {
        this.adminMenuController = adminMenuController;
        this.loginMenuController = loginMenuController;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUser.fxml"));
        Parent addUserWindowRoot = loader.load();
        AddUserWindowController addUserWindowController = loader.getController();
        addUserWindowController.setup(adminMenuController,loginMenuController);
        addUserWindowController.setStage(primaryStage);

        primaryStage.setTitle("Add Category Window");
        primaryStage.setScene(new Scene(addUserWindowRoot, 444, 241));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
