package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddItemWindow extends Application {
    private AdminMenuController adminMenuController;
    private LoginMenuController loginMenuController;

    public AddItemWindow(AdminMenuController adminMenuController,LoginMenuController loginMenuController) {
        this.adminMenuController = adminMenuController;
        this.loginMenuController = loginMenuController;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddItem.fxml"));
        Parent AddItemWindowRoot = loader.load();
        AddItemWindowController addItemWindowController = loader.getController();
        addItemWindowController.setup(adminMenuController,loginMenuController);
        addItemWindowController.setStage(primaryStage);

        primaryStage.setTitle("Add Item Window");
        primaryStage.setScene(new Scene(AddItemWindowRoot, 320, 250));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
