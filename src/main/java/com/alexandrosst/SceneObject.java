package com.alexandrosst;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class SceneObject {
    private String basePath;
    private Scene scene;
    private FXMLLoader loader;

    public SceneObject(String basePath) throws IOException {
        this.basePath = basePath;
        this.scene = loadScene();
    }

    public SceneObject(String basePath, String errorMessage) {
        try {
            this.basePath = basePath;
            this.scene = loadScene();
        } catch (IOException e) {
            DialogUtils.showErrorDialog(errorMessage);
        }
    }

    private Scene loadScene() throws IOException {
        URL resource = getClass().getResource(basePath + ".fxml");
        if (resource == null) {
            throw new IOException();
        }
        this.loader = new FXMLLoader(resource);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        URL cssURL = getClass().getResource(basePath + ".css");
        if (cssURL != null) {
            scene.getStylesheets().add(cssURL.toExternalForm());
        }

        return scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    public <T> T getController() {
        return this.loader.getController();
    }
}