package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import java.io.File;
import java.util.Arrays;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class SearchDictionaryController {
  private MainWindowController controller;
  public void SetParentController(MainWindowController controller) {
    this.controller = controller;
  }
  String dictionaryID;
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
  private int totalWordsChosenDictionary;
  @FXML
  void OnMouseClicked(MouseEvent event) {
    dictionaryID = DictionaryID.getText();
    if (dictionaryID.equals("")) {
      Image cancelImage = new Image("resources/SearchDictionary/CancelImage.png");
      Image inputImage = new Image("resources/SearchDictionary/InputImage.png");

      Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"You have to fill in all fields!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
      timeline.play();
    }
    else {
      File dir = new File("./medialab");
      if (!dir.exists()) {
        Image cancelImage = new Image("resources/SearchDictionary/CancelImage.png");
        Image inputImage = new Image("resources/SearchDictionary/InputImage.png");

        Timeline timeline = new Timeline(
                  new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"medialab directory was not found!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                  new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
        timeline.play();
        DictionaryID.setText("");
      }
      else {
        String[] files = dir.list();
        if (!Arrays.asList(files).contains("hangman_" + dictionaryID + ".txt")) {
          Image cancelImage = new Image("resources/SearchDictionary/CancelImage.png");
          Image inputImage = new Image("resources/SearchDictionary/InputImage.png");

          Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"Dictionary wasn't found!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                    new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
          timeline.play();
          DictionaryID.setText("");
        }
        else {
          Image okImage = new Image("resources/SearchDictionary/OkImage.png");
          Image inputImage = new Image("resources/SearchDictionary/InputImage.png");

          Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), okImage), new KeyValue(StatusMessage.textProperty(),"Dictionary was found successfully!"), new KeyValue(StatusMessage.textFillProperty(),Color.GREEN)),
                    new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));

          timeline.play();
          SetCurrentDictionaryButton.setDisable(false);
          // DictionaryID.setText("");
        }
      }
    }
  }

  @FXML
  void OnMouseClickforset(MouseEvent event) throws Exception {
    Image okImage = new Image("resources/SearchDictionary/OkImage.png");
    Image inputImage = new Image("resources/SearchDictionary/InputImage.png");

    Timeline timeline = new Timeline(
              new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), okImage), new KeyValue(StatusMessage.textProperty(),"Dictionary was set as current successfully!"), new KeyValue(StatusMessage.textFillProperty(),Color.GREEN)),
              new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));

    timeline.play();
    SetCurrentDictionaryButton.setDisable(true);
    DictionaryID.setText("");
    //Main Window Set
    // controller.CurrentDictionary.setText(controller.CurrentDictionary.getText() + " The Shining");
    // controller.CurrentDictionary.setDisable(false);
    File chosenDictionary = new File("./medialab/hangman_" + dictionaryID + ".txt");
    // ArrayList<String> candidate_words = new ArrayList<String>();
    controller.candidate_words = new ArrayList<String>();
    controller.all_dictionary_words = new ArrayList<String>();
    BufferedReader br = new BufferedReader(new FileReader(chosenDictionary));
    String temp;
    while ((temp = br.readLine()) != null) {controller.candidate_words.add(temp); controller.all_dictionary_words.add(temp);}
    totalWordsChosenDictionary = controller.candidate_words.size();
    controller.target = controller.candidate_words.get(new Random().nextInt(controller.candidate_words.size()));
    controller.target_length = controller.target.length();
    controller.candidate_words.removeIf(u -> u.length() != controller.target_length);
    // controller.target = target;
    // controller.target_length = target_length;
    // controller.candidate_words = candidate_words;
    controllerChanger();
  }
  public void controllerChanger() {
    controller.CurrentDictionary.setText(dictionaryID);
    controller.CurrentDictionaryTotal.setDisable(false);

    controller.AvailableWords.setText("" + totalWordsChosenDictionary);
    controller.AvailableWordsTotal.setDisable(false);

    controller.CorrectChoices.setText("0");
    controller.CorrectChoicesTotal.setDisable(false);

    controller.PointsCurrentGame.setText("0");
    controller.PointsCurrentGameTotal.setDisable(false);

    controller.IndexChosen = -1;
    controller.errors = 1;
    controller.totalChoices = 0;
    controller.remainingLetters = controller.target_length;
    controller.HangmanImage.setImage(new Image("resources/MainWindow/hangman" + controller.errors + ".png"));

    controller.StartMenuItem.setDisable(false);
    controller.DictionaryMenuItem.setDisable(false);
    controller.SolutionMenuItem.setDisable(false);
    controller.GiveUpBotton.setDisable(false);
    controller.Input.setDisable(false);
    controller.InsertButton.setDisable(false);
    controller.ClearButton.setDisable(false);
    controller.CandidateLettersField.setText("");
    controller.CandidateLettersTotal.setDisable(false);

    controller.StatusMessage.setText("Waiting for a letter..."); controller.StatusMessage.setTextFill(Color.BLACK);
    controller.StatusImage.setImage(new Image("resources/SearchDictionary/InputImage.png"));

    controller.buttons = new ArrayList<Button>();

    controller.WordField.getChildren().clear();

    for (int i = 0; i < controller.target_length; i++) controller.buttons.add(new Button("_"));
    controller.WordField.getChildren().addAll(controller.buttons);
    for (int i = 0; i < controller.target_length; i++) {
      final var index = i;
      controller.buttons.get(i).setOnAction(event -> {controller.IndexChosen = index;
        if (controller.buttons.get(controller.IndexChosen).getText().equals("_")) controller.Input.setDisable(false);
        else controller.Input.setDisable(true);
        // for (var j = 0; j < controller.target_length; j++) if (!controller.buttons.get(controller.IndexChosen).getText().equals("_")) {controller.Input.setText(""); controller.Input.setDisable(true);} else controller.Input.setDisable(false);
        for (var j = 0; j < controller.target_length; j++) if (j == index && controller.buttons.get(controller.IndexChosen).getText().equals("_")) controller.buttons.get(controller.IndexChosen).setStyle("-fx-background-color:#FFA07A"); else controller.buttons.get(j).setStyle(null);

        Arrays.fill(controller.freq, 0);
        for (var j : controller.candidate_words) controller.freq[j.charAt(controller.IndexChosen) - 'A']++;
        for (var j = 0; j < 26; j++) controller.freq[j] /= controller.candidate_words.size();

        String str = "";
        for (var j = 0; j < 26; j++) if (controller.freq[j] > 0) str += (char)('A' + j) + ((str.length() == 12) ? "\n" : " ");
        controller.CandidateLettersField.setText((!controller.Input.isDisabled()) ? str : "");
      });
    }
  }

}
