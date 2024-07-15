package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AddCategoryWindow extends Application {
    private AdminMenuController adminMenuController;
    private LoginMenuController loginMenuController;

    public AddCategoryWindow(AdminMenuController adminMenuController,LoginMenuController loginMenuController) {
        this.adminMenuController = adminMenuController;
        this.loginMenuController = loginMenuController;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCategory.fxml"));
        Parent addCategoryWindowRoot = loader.load();
        AddCategoryWindowController addCategoryWindowController = loader.getController();
        addCategoryWindowController.setup(adminMenuController,loginMenuController);
        addCategoryWindowController.setStage(primaryStage);

        primaryStage.setTitle("Add Category Window");
        primaryStage.setScene(new Scene(addCategoryWindowRoot, 444, 241));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
