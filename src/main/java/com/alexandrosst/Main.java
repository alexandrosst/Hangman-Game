package com.alexandrosst;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
  private static final String ERROR_MESSAGE = "Something went wrong! Hangman Game could not be loaded!";

  @Override
  public void start(Stage primaryStage) throws Exception {
    SceneObject introductionScene = new SceneObject("/Introduction/Introduction", ERROR_MESSAGE);
    if (introductionScene.getScene() == null) {
      return;
    }
    StageObject stage = new StageObject(primaryStage, introductionScene.getScene(), "Hangman Game", false);
    stage.showAndWait();

    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    pause.setOnFinished(event -> loadMainScene(stage));
    pause.play();
  }

  private void loadMainScene(StageObject stage) {
    SceneObject mainScene = new SceneObject("/MainWindow/MainWindow", ERROR_MESSAGE);
    stage.setScene(mainScene.getScene());
    stage.showAndWait();
  }

  public static void main(String[] args) {
    launch(args);
  }
}