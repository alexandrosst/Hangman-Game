package com.alexandrosst;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageObject {
    private Stage stage;
    private boolean isResizable = false;
    private String title;
    private boolean isModal;

    public StageObject(Stage stage, Scene scene, String title, boolean isModal) {
        this.stage = stage;
        this.title = title;
        this.isModal = isModal;
        setStageProperties(scene);
    }

    private void setStageProperties(Scene scene) {
        this.stage.setScene(scene);
        this.stage.setTitle(this.title);
        this.stage.setResizable(this.isResizable);
        this.stage.centerOnScreen();
        this.stage.setOpacity(0.9);
        if (this.isModal)
            this.stage.initModality(Modality.APPLICATION_MODAL);
    }

    public void showAndWait() {
        if (this.stage.getModality() == Modality.APPLICATION_MODAL) {
            this.stage.showAndWait();
        } else {
            this.stage.show();
        }
    }

    public void setScene(Scene scene) {
        setStageProperties(scene);
    }
}