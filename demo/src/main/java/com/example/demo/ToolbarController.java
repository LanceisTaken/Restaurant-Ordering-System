package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class ToolbarController {
    @FXML
    private ToolBar toolbar;
    @FXML
    private HBox windowButtonsContainer;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        ((Stage) toolbar.getScene().getWindow()).setX(event.getScreenX() - xOffset);
        ((Stage) toolbar.getScene().getWindow()).setY(event.getScreenY() - yOffset);
    }


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