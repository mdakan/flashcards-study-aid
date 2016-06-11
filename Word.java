// Support file for StudyTime.java by Myles Louis Dakan.

import java.util.*;

public class Word {
  //data members
  private String lenape;
  private String english;

  //constructor
  public Word(String len,String eng){
    lenape = len;
    english = eng;
  }

  //methods
  public String len(){ return lenape; }
  public String eng(){ return english; }
}