package com.alexandrosst;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.scene.control.MenuItem;

public class MainWindowController {
  private static final int BUTTON_SIZE = 40;
  private static final int FONT_SIZE = 15;
  private static final int ALPHABET_COUNT = 26;
  private static final Random RANDOM = new Random();
  FileHandler fileHandler = new FileHandler();

  String target;

  int target_length;

  ArrayList<String> all_dictionary_words;

  ArrayList<String> candidate_words;

  ArrayList<Button> buttons;

  @FXML
  private MenuBar menuBar;
  @FXML
  private VBox myVBox;

  public void initialize() {
    if (System.getProperty("os.name").startsWith("Mac")) {
      menuBar.setUseSystemMenuBar(true);
      AnchorPane.setTopAnchor(myVBox, 0.0);
    }

    Platform.runLater(() -> {
      windowID.requestFocus();
    });
    windowID.setOnMouseClicked(event -> windowID.requestFocus());
  }

  @FXML
  private AnchorPane windowID;

  @FXML
  HBox CurrentDictionaryTotal;

  @FXML
  ImageView HangmanImage, StatusImage;

  @FXML
  Label CurrentDictionary, AvailableWords, CorrectChoices, PointsCurrentGame, CandidateLetters, StatusMessage,
      CandidateLettersField;

  @FXML
  VBox CorrectChoicesTotal, PointsCurrentGameTotal, CandidateLettersTotal;

  @FXML
  FlowPane WordField;

  @FXML
  TextField Input;

  @FXML
  Button InsertButton, ClearButton, GiveUpBotton;

  @FXML
  MenuItem StartMenuItem, DictionaryMenuItem, SolutionMenuItem;

  int IndexChosen = -1;
  int previousIndexChosen = -1;

  int errors = 1;

  int totalChoices;

  int remainingLetters;

  double[] freq = new double[26];

  @FXML
  void ExitApplication(ActionEvent event) {
    Platform.exit();
  }

  @FXML
  void ClickStartMenuItem(ActionEvent event) {
    setupScene(null, null);
  }

  void setupScene(String dictionaryID, Integer dictionarySize) {
    if (dictionaryID != null && dictionarySize != null) {
      CurrentDictionary.setText(dictionaryID);
    }
    if (!fileHandler.fileExists(CurrentDictionary.getText())) {
      DialogUtils.showErrorDialog("Something went wrong! Current Dictionary wasn't found!");
    }
    candidate_words.clear();
    candidate_words.addAll(all_dictionary_words);
    target = candidate_words.get(RANDOM.nextInt(candidate_words.size()));
    target_length = target.length();
    candidate_words.removeIf(word -> word.length() != target.length());

    IndexChosen = -1;
    previousIndexChosen = -1;
    errors = 1;
    totalChoices = 0;
    remainingLetters = target_length;

    StartMenuItem.setDisable(false);
    Input.setDisable(false);
    InsertButton.setDisable(false);
    ClearButton.setDisable(false);
    GiveUpBotton.setDisable(false);
    SolutionMenuItem.setDisable(false);
    DictionaryMenuItem.setDisable(false);
    CorrectChoicesTotal.setDisable(false);
    PointsCurrentGameTotal.setDisable(false);
    CandidateLettersTotal.setDisable(false);
    CurrentDictionaryTotal.setDisable(false);

    StatusImage.setImage(new Image("/MainWindow/InputImage.png"));
    HangmanImage.setImage(new Image("/MainWindow/hangman" + errors + ".png"));

    StatusMessage.setText("Waiting for a letter...");
    StatusMessage.setTextFill(Color.BLACK);

    CorrectChoices.setText("0");
    PointsCurrentGame.setText("0");
    CandidateLettersField.setText("");

    WordField.getChildren().clear();
    buttons = (ArrayList<Button>) IntStream.range(0, target.length())
        .mapToObj(i -> {
          Button button = new Button("_");
          button.setPrefWidth(BUTTON_SIZE);
          button.setPrefHeight(BUTTON_SIZE);
          button.setFont(new Font(FONT_SIZE));
          button.setTextAlignment(TextAlignment.CENTER);
          button.setContentDisplay(ContentDisplay.CENTER);
          button.setOnAction(newevent -> {
            previousIndexChosen = IndexChosen;
            IndexChosen = i;
            Input.setDisable(!button.getText().equals("_"));
            if (previousIndexChosen != -1) {
              buttons.get(previousIndexChosen).setStyle(null);
            }
            if (button.getText().equals("_")) {
              button.setStyle("-fx-background-color:#FFA07A");
            }
            Arrays.fill(freq, 0);
            candidate_words.forEach(word -> freq[word.charAt(IndexChosen) - 'A']++);
            IntStream.range(0, ALPHABET_COUNT).forEach(j -> freq[j] /= candidate_words.size());
            String str = IntStream.range(0, ALPHABET_COUNT)
                .filter(j -> freq[j] > 0)
                .mapToObj(j -> String.valueOf((char) ('A' + j)))
                .collect(Collectors.joining(" "));
            CandidateLettersField.setText((!Input.isDisabled()) ? str : "");
          });
          return button;
        })
        .collect(Collectors.toList());
    WordField.getChildren().addAll(buttons);
  }

  @FXML
  void ClickDictionaryMenuItem(ActionEvent event) throws Exception {
    try {
      SceneObject dictionaryInfoScene = new SceneObject("/DictionaryInfo/DictionaryInfo");
      StageObject stage = new StageObject(new Stage(), dictionaryInfoScene.getScene(), "Dictionary Analysis", true);

      DictionaryInfoController dictionaryInfoController = dictionaryInfoScene.getController();
      dictionaryInfoController.SetUpScene(CurrentDictionary.getText(), all_dictionary_words);

      stage.showAndWait();
    } catch (IOException e) {
      DialogUtils.showErrorDialog("Something went wrong! Dictionary Analysis could not be loaded!");
    }
  }

  @FXML
  void ClickLoadMenuItem(ActionEvent event) throws Exception {
    try {
      SceneObject selectDictionaryScene = new SceneObject("/SearchDictionary/SearchDictionary");
      StageObject stage = new StageObject(new Stage(), selectDictionaryScene.getScene(), "Dictionary Selection", true);
      SearchDictionaryController selectDictionaryController = selectDictionaryScene.getController();
      selectDictionaryController.SetParentController(this);

      stage.showAndWait();
    } catch (IOException e) {
      DialogUtils.showErrorDialog("Something went wrong! Dictionary Selection could not be loaded!");
    }
  }

  @FXML
  void ClickClearButton(MouseEvent event) {
    Input.setText("");
  }

  @FXML
  void ClickCreateMenuItem(ActionEvent event) throws Exception {
    try {
      SceneObject createDictionaryScene = new SceneObject("/CreateNewDictionary/CreateNewDictionary");
      StageObject stage = new StageObject(new Stage(), createDictionaryScene.getScene(), "Dictionary Creation", true);

      stage.showAndWait();
    } catch (IOException e) {
      DialogUtils.showErrorDialog("Something went wrong! Dictionary Creation could not be loaded!");
    }
  }

  @FXML
  void ClickGiveUpButton(MouseEvent event) {
    for (var letter = 0; letter < target_length; letter++) {
      buttons.get(letter).setText(Character.toString(target.charAt(letter)));
    }
    StatusImage.setImage(new Image("/MainWindow/GiveUpImage.png"));
    StatusMessage.setText("You gave up! Don't be coward and give it another shot...");
    StatusMessage.setTextFill(Color.RED);
    new Rounds(target, totalChoices, "Computer");
    Input.setDisable(true);
    InsertButton.setDisable(true);
    GiveUpBotton.setDisable(true);
    ClearButton.setDisable(true);
    SolutionMenuItem.setDisable(true);
  }

  @FXML
  void ClickSolutionMenuItem(ActionEvent event) {
    for (var letter = 0; letter < target_length; letter++)
      buttons.get(letter).setText(Character.toString(target.charAt(letter)));
    StatusImage.setImage(new Image("/MainWindow/GiveUpImage.png"));

    StatusMessage.setText("You gave up! Don't be coward and give it another shot...");
    StatusMessage.setTextFill(Color.RED);
    new Rounds(target, totalChoices, "Computer");
    Input.setDisable(true);
    InsertButton.setDisable(true);
    GiveUpBotton.setDisable(true);
    ClearButton.setDisable(true);
    SolutionMenuItem.setDisable(true);
  }

  @FXML
  void ClickRoundsMenuItem(ActionEvent event) throws Exception {
    try {
      SceneObject displayRoundsScene = new SceneObject("/DisplayRounds/DisplayRounds");
      StageObject stage = new StageObject(new Stage(), displayRoundsScene.getScene(), "Rounds History", true);

      DisplayRoundsController displayRoundsController = displayRoundsScene.getController();
      displayRoundsController.SetUpTable();

      stage.showAndWait();
    } catch (IOException e) {
      DialogUtils.showErrorDialog("Something went wrong! Rounds history could not be loaded!");
    }
  }

  @FXML
  void ClickInsertButton(MouseEvent event) {
    String inputCharacter = Input.getText().strip();
    Input.setText("");

    if (inputCharacter.isEmpty()) {
      displayStatusMessage("error", "You have to fill in with a letter first!", Color.RED);
    } else if (!inputCharacter.matches("[A-Z]")) {
      displayStatusMessage("error", "You have to fill in with a capital letter from A to Z!", Color.RED);
    } else {
      handleValidInput(inputCharacter);
    }
  }

  private void displayStatusMessage(String messageType, String message, Color color) throws IllegalArgumentException {
    try {
      if (messageType == "error") {
        Image cancelImage = new Image("/MainWindow/CancelImage.png");
        Image inputImage = new Image("/MainWindow/InputImage.png");

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage),
                new KeyValue(StatusMessage.textProperty(), message),
                new KeyValue(StatusMessage.textFillProperty(), color)),
            new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage),
                new KeyValue(StatusMessage.textProperty(), "Waiting for input..."),
                new KeyValue(StatusMessage.textFillProperty(), Color.BLACK)));
        timeline.play();
      } else if (messageType == "success") {
        Image successImage = new Image("/MainWindow/PassImage.png");
        StatusImage.setImage(successImage);
        StatusMessage.setText(message);
        StatusMessage.setTextFill(color);
      } else if (messageType == "fail") {
        Image successImage = new Image("/MainWindow/LoseImage.png");
        StatusImage.setImage(successImage);
        StatusMessage.setText(message);
        StatusMessage.setTextFill(color);
      } else {
        throw new IllegalArgumentException(
            "Invalid argument: '" + messageType + "' in 'displayStatusMessage' function.");
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }

  }

  private int calculatePoints() {
    double possibility = freq[target.charAt(IndexChosen) - 'A'];
    int points = 0;
    if (possibility >= 0.6)
      points += 5;
    else if (possibility < 0.6 && possibility >= 0.4)
      points += 10;
    else if (possibility < 0.4 && possibility >= 0.25)
      points += 15;
    else
      points += 30;

    return points;
  }

  private void handleValidInput(String inputCharacter) {
    totalChoices++;
    if (inputCharacter.equals(target.charAt(IndexChosen) + "")) {
      Input.setDisable(true);
      CandidateLettersField.setText("");
      remainingLetters--;
      buttons.get(IndexChosen).setText(inputCharacter);

      int addedPoints = calculatePoints();
      int currentPoints = Integer.parseInt(PointsCurrentGame.getText());
      PointsCurrentGame.setText(currentPoints + addedPoints + "");

      candidate_words.removeIf(u -> !u.matches(".{" + IndexChosen + "}" + inputCharacter + ".*"));

      if (remainingLetters == 0) {
        Input.setDisable(true);
        InsertButton.setDisable(true);
        ClearButton.setDisable(true);
        GiveUpBotton.setDisable(true);
        SolutionMenuItem.setDisable(true);
        displayStatusMessage("success", "Congratulations! You have guessed the missing word successfully!",
            Color.GREEN);
        new Rounds(target, totalChoices, "Player");
      }
    } else {
      int currentPoints = Integer.parseInt(PointsCurrentGame.getText());
      PointsCurrentGame.setText(Math.max(0, currentPoints -= 15) + "");
      candidate_words.removeIf(u -> u.matches(".{" + IndexChosen + "}" + inputCharacter + ".*"));

      Arrays.fill(freq, 0);
      for (var j : candidate_words)
        freq[j.charAt(IndexChosen) - 'A']++;
      for (var j = 0; j < 26; j++)
        freq[j] /= candidate_words.size();

      String str = "";
      for (var j = 0; j < 26; j++)
        if (freq[j] > 0)
          str += (char) ('A' + j) + ((str.length() == 12) ? "\n" : " ");
      CandidateLettersField.setText((!Input.isDisabled()) ? str : "");

      errors++;
      HangmanImage.setImage(new Image("/MainWindow/hangman" + errors + ".png"));

      if (errors == 7) {
        for (var letter = 0; letter < target_length; letter++) {
          buttons.get(letter).setText(Character.toString(target.charAt(letter)));
          buttons.get(letter).setTextFill(Color.RED);
        }
        Input.setDisable(true);
        InsertButton.setDisable(true);
        ClearButton.setDisable(true);
        GiveUpBotton.setDisable(true);
        SolutionMenuItem.setDisable(true);
        displayStatusMessage("fail", "You failed to guess the missing word! Better luck next time...", Color.RED);

        new Rounds(target, totalChoices, "Computer");
      }
    }

    String correctChoices = String.format("%.2f", (double) (totalChoices - (errors - 1)) / totalChoices * 100);
    CorrectChoices.setText(correctChoices + " %");
  }

}