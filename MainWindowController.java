package controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import java.util.Random;
import javafx.scene.control.MenuItem;
import controllers.Rounds;

public class MainWindowController {
  String target;
  int target_length;
  ArrayList<String> all_dictionary_words;
  ArrayList<String> candidate_words;
  ArrayList<Button> buttons;
  // Rounds TotalRounds = new Rounds();
  @FXML
  HBox CurrentDictionaryTotal, AvailableWordsTotal;
  @FXML
  ImageView HangmanImage, StatusImage;
  @FXML
  Label CurrentDictionary, AvailableWords, CorrectChoices, PointsCurrentGame, CandidateLetters, StatusMessage, CandidateLettersField;
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
    candidate_words.clear();
    for (var i : all_dictionary_words) candidate_words.add(i);

    target = candidate_words.get(new Random().nextInt(candidate_words.size()));
    target_length = target.length();
    candidate_words.removeIf(u -> u.length() != target_length);
    IndexChosen = -1;
    errors = 1;
    totalChoices = 0;
    remainingLetters = target_length;
    Input.setDisable(false); InsertButton.setDisable(false); ClearButton.setDisable(false); GiveUpBotton.setDisable(false); SolutionMenuItem.setDisable(false);
    StatusImage.setImage(new Image("resources/MainWindow/InputImage.png"));
    StatusMessage.setText("Waiting for a letter..."); StatusMessage.setTextFill(Color.BLACK);
    HangmanImage.setImage(new Image("resources/MainWindow/hangman" + errors + ".png"));
    CorrectChoices.setText("0");
    PointsCurrentGame.setText("0");
    CandidateLettersField.setText("");
    Input.setDisable(false);
    WordField.getChildren().clear();

    buttons = new ArrayList<Button>();
    for (int i = 0; i < target_length; i++) buttons.add(new Button("_"));
    WordField.getChildren().addAll(buttons);

    for (int i = 0; i < target_length; i++) {
      final var index = i;
      buttons.get(i).setOnAction(newevent -> {IndexChosen = index;
        if (buttons.get(IndexChosen).getText().equals("_")) Input.setDisable(false);
        else Input.setDisable(true);
        // for (var j = 0; j < controller.target_length; j++) if (!controller.buttons.get(controller.IndexChosen).getText().equals("_")) {controller.Input.setText(""); controller.Input.setDisable(true);} else controller.Input.setDisable(false);
        for (var j = 0; j < target_length; j++) if (j == index && buttons.get(IndexChosen).getText().equals("_")) buttons.get(IndexChosen).setStyle("-fx-background-color:#FFA07A"); else buttons.get(j).setStyle(null);

        Arrays.fill(freq, 0);
        for (var j : candidate_words) freq[j.charAt(IndexChosen) - 'A']++;
        for (var j = 0; j < 26; j++) freq[j] /= candidate_words.size();

        String str = "";
        for (var j = 0; j < 26; j++) if (freq[j] > 0) str += (char)('A' + j) + ((str.length() == 12) ? "\n" : " ");
        CandidateLettersField.setText((!Input.isDisabled()) ? str : "");
      });
    }
  }

  @FXML
  void ClickDictionaryMenuItem(ActionEvent event) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./../resources/DictionaryInfo/DictionaryInfo.fxml"));
    Parent DictionaryInfoRoot = loader.load();
    DictionaryInfoController dictionaryInfoController = loader.getController();
    dictionaryInfoController.SetUpScene(CurrentDictionary.getText(), all_dictionary_words);
    Stage stage = new Stage();
    stage.setResizable(false);
    Scene DictionaryInfo = new Scene(DictionaryInfoRoot);
    stage.getIcons().add(new Image("Icon.png"));
    stage.setTitle("Dictionary Info");
    stage.setScene(DictionaryInfo);
    stage.show();
  }

  @FXML
  void ClickLoadMenuItem(ActionEvent event) throws Exception {
    // Parent SearchDictionaryRoot = FXMLLoader.load(getClass().getResource("./../resources/SearchDictionary/SearchDictionary.fxml"));
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./../resources/SearchDictionary/SearchDictionary.fxml"));
    Parent SearchDictionaryRoot = loader.load();
    SearchDictionaryController child = loader.getController();
    child.SetParentController(this);
    Stage stage = new Stage();
    stage.setResizable(false);
    Scene SearchDictionary = new Scene(SearchDictionaryRoot);
    stage.getIcons().add(new Image("Icon.png"));
    stage.setTitle("Search Dictionary");
    stage.setScene(SearchDictionary);
    stage.show();
  }

  @FXML
  void ClickClearButton(MouseEvent event) {
    Input.setText("");
  }

  @FXML
  void ClickCreateMenuItem(ActionEvent event) throws Exception {
    Parent CreateNewDictionaryRoot = FXMLLoader.load(getClass().getResource("./../resources/CreateNewDictionary/CreateNewDictionary.fxml"));
    Stage stage = new Stage();
    stage.setResizable(false);
    Scene CreateNewDictionary = new Scene(CreateNewDictionaryRoot);
    stage.getIcons().add(new Image("Icon.png"));
    stage.setTitle("Create New Dictionary");
    stage.setScene(CreateNewDictionary);
    stage.show();
  }

  @FXML
  void ClickGiveUpButton(MouseEvent event) {
    for (var letter = 0; letter < target_length; letter++) buttons.get(letter).setText(Character.toString(target.charAt(letter)));
    StatusImage.setImage(new Image("resources/MainWindow/GiveUpImage.png"));
    // StatusMessage.setText("You failed to guess the missing word! Better luck next time...");
    StatusMessage.setText("You gave up! Don't be coward and give it another try...");
    StatusMessage.setTextFill(Color.RED);
    new Rounds(target, totalChoices, "Computer");
    Input.setDisable(true); InsertButton.setDisable(true); GiveUpBotton.setDisable(true); ClearButton.setDisable(true); SolutionMenuItem.setDisable(true);
  }

  @FXML
  void ClickSolutionMenuItem(ActionEvent event) {
    for (var letter = 0; letter < target_length; letter++) buttons.get(letter).setText(Character.toString(target.charAt(letter)));
    StatusImage.setImage(new Image("resources/MainWindow/GiveUpImage.png"));
    // // StatusMessage.setText("You failed to guess the missing word! Better luck next time...");
    StatusMessage.setText("You gave up! Don't be coward and give it another try...");
    StatusMessage.setTextFill(Color.RED);
    new Rounds(target, totalChoices, "Computer");
    Input.setDisable(true); InsertButton.setDisable(true); GiveUpBotton.setDisable(true); ClearButton.setDisable(true); SolutionMenuItem.setDisable(true);
  }

  @FXML
  void ClickRoundsMenuItem(ActionEvent event) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./../resources/DisplayRounds/DisplayRounds.fxml"));
    Parent DisplayRoundsRoot = loader.load();
    DisplayRoundsController displayRoundsController = loader.getController();
    displayRoundsController.SetUpTable();
    // FXMLLoader loader = new FXMLLoader(getClass().getResource("./../resources/DisplayRounds/DisplayRounds.fxml"));
    // Parent DisplayRoundsRoot = loader.load();
    Stage stage = new Stage();
    stage.setResizable(false);
    Scene DisplayRounds = new Scene(DisplayRoundsRoot);
    stage.getIcons().add(new Image("Icon.png"));
    stage.setTitle("Rounds");
    stage.setScene(DisplayRounds);
    stage.show();
  }

  @FXML
  void ClickInsertButton(MouseEvent event) {
    String inputCharacter = Input.getText();
    Input.setText("");
    if (IndexChosen == -1) {
      Image cancelImage = new Image("resources/MainWindow/CancelImage.png");
      Image inputImage = new Image("resources/MainWindow/InputImage.png");

      Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"You have to choose an empty gap first!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
      timeline.play();
    }
    else if (Input.isDisabled()) {
      Image cancelImage = new Image("resources/MainWindow/CancelImage.png");
      Image inputImage = new Image("resources/MainWindow/InputImage.png");

      Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"Letter has been revealed. Choose another gap instead!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
      timeline.play();
    }
    else {
      if (inputCharacter.equals("")) {
        Image cancelImage = new Image("resources/MainWindow/CancelImage.png");
        Image inputImage = new Image("resources/MainWindow/InputImage.png");

        Timeline timeline = new Timeline(
                  new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"You have to fill in with a letter first!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                  new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
        timeline.play();
      }
      else if (!inputCharacter.matches("[A-Z]{1}")) {
        Image cancelImage = new Image("resources/MainWindow/CancelImage.png");
        Image inputImage = new Image("resources/MainWindow/InputImage.png");

        Timeline timeline = new Timeline(
                  new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"You have to fill in with a capital letter from A to Z!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                  new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
        timeline.play();
      }
      else {
        totalChoices++;
        if (inputCharacter.equals(target.charAt(IndexChosen) + "") && !Input.isDisabled()) {
          Input.setDisable(true);
          CandidateLettersField.setText("");
          remainingLetters--;
          buttons.get(IndexChosen).setText(inputCharacter);
          double possibility = freq[target.charAt(IndexChosen)-'A'];
          int points = 0;
          if (possibility >= 0.6) points += 5;
          else if (possibility < 0.6 && possibility >= 0.4) points += 10;
          else if (possibility < 0.4 && possibility >= 0.25) points += 15;
          else points += 30;
          PointsCurrentGame.setText(Integer.parseInt(PointsCurrentGame.getText()) + points + "");

          candidate_words.removeIf(u -> !u.matches(".{" + IndexChosen + "}" + inputCharacter + ".*"));
          if (remainingLetters == 0) {
            Input.setDisable(true);InsertButton.setDisable(true); ClearButton.setDisable(true); GiveUpBotton.setDisable(true); SolutionMenuItem.setDisable(true);
            StatusImage.setImage(new Image("resources/MainWindow/PassImage.png"));
            StatusMessage.setText("Congratulations! You have guessed the missing word successfully!");
            StatusMessage.setTextFill(Color.GREEN);

            new Rounds(target, totalChoices, "Player");
          }
        }
        else {
          int points = Integer.parseInt(PointsCurrentGame.getText());
          PointsCurrentGame.setText(Math.max(0, points -= 15) + "");
          candidate_words.removeIf(u -> u.matches(".{" + IndexChosen + "}" + inputCharacter + ".*"));


          Arrays.fill(freq, 0);
          for (var j : candidate_words) freq[j.charAt(IndexChosen) - 'A']++;
          for (var j = 0; j < 26; j++) freq[j] /= candidate_words.size();

          String str = "";
          for (var j = 0; j < 26; j++) if (freq[j] > 0) str += (char)('A' + j) + ((str.length() == 12) ? "\n" : " ");
          CandidateLettersField.setText((!Input.isDisabled()) ? str : "");

          errors++;
          HangmanImage.setImage(new Image("resources/MainWindow/hangman" + errors + ".png"));

          if (errors == 7) {
            for (var letter = 0; letter < target_length; letter++) {buttons.get(letter).setText(Character.toString(target.charAt(letter))); buttons.get(letter).setTextFill(Color.RED);}
            Input.setDisable(true);InsertButton.setDisable(true); ClearButton.setDisable(true); GiveUpBotton.setDisable(true); SolutionMenuItem.setDisable(true);
            StatusImage.setImage(new Image("resources/MainWindow/LoseImage.png"));
            StatusMessage.setText("You failed to guess the missing word! Better luck next time...");
            StatusMessage.setTextFill(Color.RED);

            new Rounds(target, totalChoices, "Computer");
          }
        }
        String correctChoices = String.format("%.2f", (double) (totalChoices - (errors - 1))/totalChoices*100);
        // double correctChoices = (double) (totalChoices - (errors - 1))/totalChoices*100;
        CorrectChoices.setText(correctChoices + " %");
      }
    }
  }
}
