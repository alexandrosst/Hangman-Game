package com.alexandrosst;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import com.google.gson.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URI;
import java.util.Scanner;
import java.util.HashSet;

public class CreateNewDictionaryController {
  private static final int MIN_WORD_LENGTH = 5;
  private static final int LONG_WORD_LENGTH = 9;
  private static final int MIN_DISTINCT_WORDS = 20;
  private static final double MIN_LONG_WORD_PERCENTAGE = 20.0;

  @FXML
  private Button CreateDictionaryButton;

  @FXML
  private AnchorPane windowID;

  @FXML
  private TextField DictionaryID;

  @FXML
  private TextField OpenLibraryID;

  @FXML
  private Label StatusMessage;

  @FXML
  private ImageView StatusImage;

  @FXML
  public void initialize() {
    resetScene();
  }

  private void resetScene() {
    CreateDictionaryButton.disableProperty().bind(
        DictionaryID.textProperty().isEmpty()
            .or(OpenLibraryID.textProperty().isEmpty()));

    DictionaryID.setText("");
    OpenLibraryID.setText("");

    Platform.runLater(() -> {
      windowID.requestFocus();
    });
    windowID.setOnMouseClicked(event -> windowID.requestFocus());
  }

  @FXML
  public void OnMouseClicked(MouseEvent event) {
    String dictionaryID = DictionaryID.getText().strip();
    String openlibraryID = OpenLibraryID.getText().strip();

    try {
      Book book = fetchBookData(openlibraryID);

      String[] words = book.getDescription();

      HashSet<String> distinctWords = new HashSet<>();

      try {
        validateDictionary(distinctWords, words);

        FileHandler fileHandler = new FileHandler();
        fileHandler.writeFile(dictionaryID, distinctWords);

        displayStatusMessage("success", "Dictionary was created successfully!");
      } catch (UnbalancedException e) {
        displayStatusMessage("error", e.getMessage());
      } catch (UndersizeException e) {
        displayStatusMessage("error", e.getMessage());
      }
    } catch (Exception e) {
      displayStatusMessage("error", "Something went wrong with given Open Library ID!");
    }

    resetScene();
  }

  private static class Book {
    Object description;

    String[] getDescription() {
      String[] words;

      if (this.description != null) {
        words = this.description.toString().split("\\W+");
        for (var i = 0; i < words.length; i++)
          words[i] = words[i].toUpperCase();
        return words;
      }
      return null;
    }
  }

  private static class DescriptionDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      if (json.isJsonObject()) {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject descriptionObject = jsonObject.getAsJsonObject("description");
        if (descriptionObject.has("value")) {
          return descriptionObject.get("value").getAsString();
        }
      } else if (json.isJsonPrimitive()) {
        return json.getAsString();
      }
      return null;
    }
  }

  private void displayStatusMessage(String messageType, String message) {
    double transitionDuration = 2;
    Image inputImage = new Image("/CreateNewDictionary/InputImage.png");
    Image statusImage;
    Color textColor;

    switch (messageType) {
      case "success":
        statusImage = new Image("/CreateNewDictionary/OkImage.png");
        textColor = Color.GREEN;
        break;
      case "error":
        statusImage = new Image("/CreateNewDictionary/CancelImage.png");
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

  private Book fetchBookData(String openlibraryID) throws Exception {
    URI uri = new URI("https://openlibrary.org/works/" + openlibraryID + ".json");
    URL url = uri.toURL();

    InputStreamReader reader = new InputStreamReader(url.openStream());
    try (Scanner s = new Scanner(reader).useDelimiter("\\A")) {
      String result = s.hasNext() ? s.next() : "";

      Gson gson = new GsonBuilder()
          .registerTypeAdapter(String.class, new DescriptionDeserializer())
          .create();

      Book book = gson.fromJson(result, Book.class);
      return book;
    }
  }

  private void validateDictionary(HashSet<String> distinctWords, String[] words)
      throws UndersizeException, UnbalancedException {
    int numLongWords = 0;
    for (var word : words) {
      int wordLength = word.length();
      if (word.matches(".*\\d.*") || word.matches(".*_.*"))
        continue;
      if (wordLength > MIN_WORD_LENGTH) {
        distinctWords.add(word);
        if (wordLength >= LONG_WORD_LENGTH)
          numLongWords++;
      }
    }

    int distinctWordCount = distinctWords.size();
    if (distinctWordCount < MIN_DISTINCT_WORDS)
      throw new UndersizeException("At least 20 valid words are needed.");
    if ((double) numLongWords / distinctWordCount * 100 < MIN_LONG_WORD_PERCENTAGE)
      throw new UnbalancedException("At least 20% of words must have length ≥ 9");
  }
}