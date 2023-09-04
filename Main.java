// compile: javac --module-path "C:\javafx_files\javafx-sdk-17.0.1\lib" --add-modules javafx.controls,javafx.fxml Main.java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    // primaryStage.setResizable(false);
    // Parent CreateNewDictionaryRoot = FXMLLoader.load(getClass().getResource("resources/CreateNewDictionary/CreateNewDictionary.fxml"));
    // Scene CreateNewDictionary = new Scene(CreateNewDictionaryRoot);
    //
    // primaryStage.setTitle("Create New Dictionary");
    // primaryStage.setScene(CreateNewDictionary);
    // primaryStage.show();

    // primaryStage.setResizable(false);
    // Parent SearchDictionaryRoot = FXMLLoader.load(getClass().getResource("resources/SearchDictionary/SearchDictionary.fxml"));
    // Scene SearchDictionary = new Scene(SearchDictionaryRoot);
    // primaryStage.setTitle("Search Dictionary");
    // primaryStage.setScene(SearchDictionary);
    // primaryStage.show();

    primaryStage.setResizable(false);
    Parent MainWindowRoot = FXMLLoader.load(getClass().getResource("resources/MainWindow/MainWindow.fxml"));
    Scene MainWindow = new Scene(MainWindowRoot);
    primaryStage.getIcons().add(new Image("Icon.png"));
    primaryStage.setTitle("Hangman Game");
    primaryStage.setScene(MainWindow);
    primaryStage.show();
  }
  public static void main(String[] args) {
      launch(args);
  }
}
