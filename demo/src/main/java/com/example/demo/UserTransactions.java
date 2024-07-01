package com.example.demo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserTransactions extends Application {
    LoginMenuController loginMenuController;
    UserTransactionsController userTransactionsController;

    public UserTransactions(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserTransactions.fxml"));
        Parent root = loader.load();
        userTransactionsController = loader.getController();
        userTransactionsController.setup(loginMenuController);
        userTransactionsController.setUserTransactionStage(primaryStage);
        userTransactionsController.initializeController();
        primaryStage.setTitle("User Transactions");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/dracula.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
