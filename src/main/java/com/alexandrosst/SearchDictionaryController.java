package com.alexandrosst;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class SearchDictionaryController {
  private MainWindowController controller;

  public void SetParentController(MainWindowController controller) {
    this.controller = controller;
  }

  String dictionaryID;

  FileHandler fileHandler = new FileHandler();

  @FXML
  public void initialize() {
    SetCurrentDictionaryButton.disableProperty().bind(DictionaryID.textProperty().isEmpty());
    Platform.runLater(() -> {
      windowID.requestFocus();
    });
    windowID.setOnMouseClicked(event -> windowID.requestFocus());
  }

  @FXML
  private AnchorPane windowID;

  @FXML
  private TextField DictionaryID;

  @FXML
  private ImageView StatusImage;

  @FXML
  private Label StatusMessage;

  @FXML
  private Button SearchDictionaryButton;

  @FXML
  private Button SetCurrentDictionaryButton;

  private int dictionarySize;

  @FXML
  void OnMouseClicked(MouseEvent event) {
    String chosen_file = fileHandler.chooseFile();

    if (chosen_file != null) {
      DictionaryID.setText(chosen_file);
    }
  }

  @FXML
  void OnMouseClickforset(MouseEvent event) throws Exception {
    controller.candidate_words = new ArrayList<String>();
    controller.all_dictionary_words = new ArrayList<String>();

    dictionaryID = DictionaryID.getText().strip();

    try {
      fileHandler.readFile(dictionaryID, controller.all_dictionary_words);

      dictionarySize = controller.all_dictionary_words.size();

      controller.setupScene(dictionaryID, dictionarySize);

      displayStatusMessage("success", "Current dictionary has been successfully updated!");
    } catch (Exception e) {
      displayStatusMessage("error", "Unable to locate the specified dictionary!");
    }
  }

  private void displayStatusMessage(String messageType, String message) {
    double transitionDuration = 2;
    Image inputImage = new Image("/SearchDictionary/InputImage.png");
    Image statusImage;
    Color textColor;

    switch (messageType) {
      case "success":
        statusImage = new Image("/SearchDictionary/OkImage.png");
        textColor = Color.GREEN;
        break;
      case "error":
        statusImage = new Image("/SearchDictionary/CancelImage.png");
        textColor = Color.RED;
        break;
      default:
        return;
    }

    Timeline timeline = new Timeline(
        new KeyFrame(Duration.ZERO,
            new KeyValue(StatusImage.imageProperty(), statusImage),
            new KeyValue(StatusMessage.textProperty(), message),
            new KeyValue(StatusMessage.textFillProperty(), textColor)),
        new KeyFrame(Duration.seconds(transitionDuration),
            new KeyValue(StatusImage.imageProperty(), inputImage),
            new KeyValue(StatusMessage.textProperty(), "Waiting for input..."),
            new KeyValue(StatusMessage.textFillProperty(), Color.BLACK)));

    timeline.play();
  }

}