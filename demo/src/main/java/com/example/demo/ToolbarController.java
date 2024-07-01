package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ToolbarController {
    @FXML
    private ToolBar toolbar;

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

}