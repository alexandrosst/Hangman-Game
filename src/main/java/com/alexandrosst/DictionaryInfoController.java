package com.alexandrosst;

import javafx.fxml.FXML;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class DictionaryInfoController {

  @FXML
  private Label DictionaryName;
  @FXML
  private PieChart PieChartRepresentation;
  @FXML
  private BarChart<String, Number> BarChartRepresentation;
  private int Words_Six_Letters, Words_Seven_To_Nine_Letters, Words_At_Least_10_Letters;

  public void SetUpScene(String str, ArrayList<String> arr) {
    DictionaryName.setText(str);
    ShowResults(arr);
  }

  private void ShowResults(ArrayList<String> arr) {
    int TotalWords = arr.size();
    DictionaryName.setText(DictionaryName.getText() + " (" + TotalWords + " words in total)");
    for (var i : arr) {
      int length = i.length();
      if (length == 6)
        Words_Six_Letters++;
      else if (length > 6 && length < 10)
        Words_Seven_To_Nine_Letters++;
      else
        Words_At_Least_10_Letters++;
    }
    String[] pourcentages = { String.format("%.2f", (double) Words_Six_Letters / TotalWords * 100),
        String.format("%.2f", (double) Words_Seven_To_Nine_Letters / TotalWords * 100),
        String.format("%.2f", (double) Words_At_Least_10_Letters / TotalWords * 100) };
    ObservableList<Data> ListForPieChart = FXCollections.observableArrayList(
        new PieChart.Data("Words with 6 letters\n         (" + pourcentages[0] + "%)", Words_Six_Letters),
        new PieChart.Data("Words with 7 to 9 letters\n            (" + pourcentages[1] + "%)",
            Words_Seven_To_Nine_Letters),
        new PieChart.Data("Words with at least 10 letters\n                (" + pourcentages[2] + "%)",
            Words_At_Least_10_Letters));
    PieChartRepresentation.setData(ListForPieChart);

    XYChart.Series<String, Number> SeriesForBarChart = new XYChart.Series<>();
    SeriesForBarChart.setName("Word Length");
    SeriesForBarChart.getData().add(new XYChart.Data<>("6 letters", Words_Six_Letters));
    SeriesForBarChart.getData().add(new XYChart.Data<>("7-9 letters", Words_Seven_To_Nine_Letters));
    SeriesForBarChart.getData().add(new XYChart.Data<>("≥ 10 letters", Words_At_Least_10_Letters));
    BarChartRepresentation.getData().add(SeriesForBarChart);
  }
}