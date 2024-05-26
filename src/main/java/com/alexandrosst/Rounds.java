package com.alexandrosst;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a round. Each round is described by a target word, the number of
 * efforts made by user and a winner.
 * It consists of an array with 5 most recent rounds.
 * 
 * @author alexandrosst
 */
public class Rounds {
  private SimpleStringProperty Word, Winner;
  private SimpleIntegerProperty NumberOfEfforts;
  static ArrayList<Rounds> rounds = new ArrayList<Rounds>();

  /**
   * Creates a new {@link Rounds} record. Array of records is updated accordingly
   * at the same time so as to having an array with exactly 5 records.
   * 
   * @param Word    target word
   * @param Efforts number of efforts made by user
   * @param Winner  winner of the round
   */
  public Rounds(String Word, int Efforts, String Winner) {
    this.Word = new SimpleStringProperty(Word);
    this.NumberOfEfforts = new SimpleIntegerProperty(Efforts);
    this.Winner = new SimpleStringProperty(Winner);
    if (rounds.size() == 5)
      rounds.remove(0);
    rounds.add(this);
  }

  /**
   * @return target word of the round
   */
  public String getWord() {
    return this.Word.get();
  }

  /**
   * @return number of efforts made by user
   */
  public int getNumberOfEfforts() {
    return this.NumberOfEfforts.get();
  }

  /**
   * @return winner of round. It would be either PC or user
   */
  public String getWinner() {
    return this.Winner.get();
  }
}