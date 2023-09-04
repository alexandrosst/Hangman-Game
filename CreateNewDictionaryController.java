package controllers;
import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import com.google.gson.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

public class CreateNewDictionaryController {
  public static class Book1 {
    Map<String, String> description;
  }
  public static class Book2 {
    String description;
  }
  @FXML
    private TextField DictionaryID;
  @FXML
    private TextField OpenLibraryID;
  @FXML
    private Label StatusMessage;
  @FXML
    private ImageView StatusImage;
  @FXML
  void OnMouseClicked(MouseEvent event) {
    String dictionaryID = DictionaryID.getText();
    String openlibraryID = OpenLibraryID.getText();

    if (dictionaryID.equals("") || openlibraryID.equals("")) {
      Image cancelImage = new Image("resources/CreateNewDictionary/CancelImage.png");
      Image inputImage = new Image("resources/CreateNewDictionary/InputImage.png");

      Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"You have to fill in all fields!"), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
      timeline.play();
      DictionaryID.setText("");
      OpenLibraryID.setText("");
    }
    else {
      Image okImage = new Image("resources/CreateNewDictionary/OkImage.png");
      Image inputImage = new Image("resources/CreateNewDictionary/InputImage.png");

      Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), okImage), new KeyValue(StatusMessage.textProperty(),"Dictionary was created successfully!"), new KeyValue(StatusMessage.textFillProperty(),Color.GREEN)),
                new KeyFrame(Duration.seconds(2), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));

      timeline.play();
      DictionaryID.setText("");
      OpenLibraryID.setText("");
      try {
        URL url = new URL("https://openlibrary.org/works/" + openlibraryID + ".json");
        InputStreamReader reader = new InputStreamReader(url.openStream());
        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";


        Gson gson = new Gson();
        String[] parts;
        if (result.contains("\"description\": {")) {
          Book1 b = gson.fromJson(result, Book1.class);
          parts = b.description.get("value").split("\\W+");
        }
        else {
          Book2 b = gson.fromJson(result, Book2.class);
          parts = b.description.split("\\W+");
        }
        for (var i = 0; i < parts.length; i++) parts[i] = parts[i].toUpperCase();

        HashSet<String> SetOfWords = new HashSet<String>();
        int WordsGreaterEqual9 = 0;
        for (var part : parts) {
          int temp = part.length();
          if (part.matches(".*\\d.*") || part.matches(".*_.*")) {System.out.println(part);continue;} //if word contains digits get rid of it
          if (temp > 5) {
            SetOfWords.add(part);
            if (temp > 8) WordsGreaterEqual9++;
          }
        }
        try {
          // for (var j : SetOfWords) System.out.println(j);
          int SetOfWordsSize = SetOfWords.size();
          if (SetOfWordsSize <= 20) throw new UndersizeException("At least 20 valid words are needed.");
          if ((double) WordsGreaterEqual9/SetOfWordsSize*100 < 0.2) throw new UnbalancedException("At least 20% of words must have length >= 9");

          File file = new File("./medialab/hangman_" + dictionaryID + ".txt");
          file.getParentFile().mkdirs();
          try(FileWriter filewriter = new FileWriter(file)) {
            for (var word : SetOfWords) {
              filewriter.write(word);
              filewriter.write(System.lineSeparator());
            }
          }
          catch (IOException e) {
            System.err.println(e);
          }

        }
        catch(UnbalancedException e) {
          Image cancelImage = new Image("resources/CreateNewDictionary/CancelImage.png");

          timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),e.toString()), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                    new KeyFrame(Duration.seconds(3), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
          timeline.play();
          DictionaryID.setText("");
          OpenLibraryID.setText("");
          // System.out.println(e.toString());
        }
        catch(UndersizeException e) {
          Image cancelImage = new Image("resources/CreateNewDictionary/CancelImage.png");

          timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),e.toString()), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                    new KeyFrame(Duration.seconds(3), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
          timeline.play();
          DictionaryID.setText("");
          OpenLibraryID.setText("");
          // System.out.println(e.toString());
        }
      }
      catch (Exception e) {
        Image cancelImage = new Image("resources/CreateNewDictionary/CancelImage.png");
        System.out.println("here");
        timeline = new Timeline(
                  new KeyFrame(Duration.ZERO, new KeyValue(StatusImage.imageProperty(), cancelImage), new KeyValue(StatusMessage.textProperty(),"Problem occured with the given Open Library ID."), new KeyValue(StatusMessage.textFillProperty(),Color.RED)),
                  new KeyFrame(Duration.seconds(3), new KeyValue(StatusImage.imageProperty(), inputImage), new KeyValue(StatusMessage.textProperty(),"Waiting for input..."), new KeyValue(StatusMessage.textFillProperty(),Color.BLACK)));
        timeline.play();
        DictionaryID.setText("");
        OpenLibraryID.setText("");
      }



      // StatusImage.setImage(new Image("resources/CreateNewDictionary/OkImage.png"));
      // StatusMessage.setText("Dictionary was created successfully!");
      // DictionaryID.setText("");
      // OpenLibraryID.setText("");
      // StatusImage.setImage(new Image("resources/CreateNewDictionary/InputImage.png"));
    }
  }
}
