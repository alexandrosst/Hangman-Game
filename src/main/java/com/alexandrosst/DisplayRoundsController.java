package com.alexandrosst;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        Word.setCellValueFactory(new PropertyValueFactory<Rounds, String>("Word"));
        NumberOfEfforts.setCellValueFactory(new PropertyValueFactory<>("NumberOfEfforts"));
        Winner.setCellValueFactory(new PropertyValueFactory<>("Winner"));
        TableStatistics.getItems().addAll(Rounds.rounds);

        alignColumn(Word);
        alignColumn(NumberOfEfforts);
        alignColumn(Winner);

    }

    private <T> void alignColumn(TableColumn<Rounds, T> column) {
        column.setCellFactory(tc -> new TableCell<Rounds, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }
}