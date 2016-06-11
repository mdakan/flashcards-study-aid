// Support file for StudyTime.java by Myles Louis Dakan.

import java.util.*;

public class Unit {
  //data members
  private String name;
  private ArrayList<Word> words;
  static Random rng = new Random(System.currentTimeMillis());

  //constructor
  public Unit(String nm){
    name = nm;
    words = new ArrayList<Word>();;
  }

  //methods
  public String name(){ return name; }

  public void add(String len,String eng){
    Word w = new Word(len,eng);
    words.add(w);
  }

  public int size(){
    return words.size();
  }

  public Word get(int i){
    return words.get(i);
  }

  public Word get(){
    int i = rng.nextInt(words.size());
    return get(i);
  }
}