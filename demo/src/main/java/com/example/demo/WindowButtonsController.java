package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowButtonsController extends HBox {

    // Reference to the stage to perform actions on
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void closeAction(ActionEvent event) {
        stage.close();
    }
    // Method for minimizing the stage
    @FXML
    private void minimizeAction() {
        if (stage != null) {
            stage.setIconified(true);
            System.out.println("pressed me good");
        }
    }
    // Method for maximizing or restoring the stage
    @FXML
    private void maximizeAction() {
        if (stage != null) {
            if (stage.isMaximized()) {
                stage.setMaximized(false); // Restore the stage
            } else {
                stage.setMaximized(true); // Maximize the stage
            }
        }
    }
}
