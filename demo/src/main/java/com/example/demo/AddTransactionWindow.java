package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AddTransactionWindow extends Application {
    private AdminMenuController adminMenuController;
    private LoginMenuController loginMenuController;

    public AddTransactionWindow(AdminMenuController adminMenuController,LoginMenuController loginMenuController) {
        this.adminMenuController = adminMenuController;
        this.loginMenuController = loginMenuController;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTransaction.fxml"));
        Parent AddTransactionWindowRoot = loader.load();
        AddTransactionWindowController addTransactionWindowController = loader.getController();
        addTransactionWindowController.setup(adminMenuController,loginMenuController);
        addTransactionWindowController.setStage(primaryStage);

        primaryStage.setTitle("Add Item Window");
        primaryStage.setScene(new Scene(AddTransactionWindowRoot, 588, 329));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
