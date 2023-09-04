package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import controllers.Rounds;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class DisplayRoundsController {
  @FXML
  private TableView<Rounds> TableStatistics;
  @FXML
  private TableColumn<Rounds, Integer> NumberOfEfforts;
  @FXML
  private TableColumn<Rounds, String> Winner;
  @FXML
  private TableColumn<Rounds, String> Word;

  public void SetUpTable() {
    Word.setCellValueFactory(new PropertyValueFactory<Rounds,String>("Word"));
    NumberOfEfforts.setCellValueFactory(new PropertyValueFactory<>("NumberOfEfforts"));
    Winner.setCellValueFactory(new PropertyValueFactory<>("Winner"));
    TableStatistics.getItems().addAll(Rounds.rounds);

  }
}
