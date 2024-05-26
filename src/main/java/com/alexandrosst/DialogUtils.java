package com.alexandrosst;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class DialogUtils {
    public static void showErrorDialog(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText(message);

            ButtonType exitButton = new ButtonType("Exit", ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(exitButton);

            alert.showAndWait();
            Platform.exit();
        });
    }
}