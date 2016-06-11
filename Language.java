// Support file for StudyTime.java by Myles Louis Dakan.

import java.util.*;

public class Language {
  //data members
  private ArrayList<Unit> units;
  private LinkedList<String> subjects;
  static Random rng = new Random(System.currentTimeMillis());

  //constructor
  public Language() {
    units = new ArrayList<Unit>();
    subjects = new LinkedList<String>();
  }

  //methods
  public int size(){
      return units.size();
  }

  public void add(Unit u){
    units.add(u);
    subjects.add(u.name());
  }

  public Unit get(int i){
    return units.get(i);
  }

  public Unit getRand(int i){
    int j = rng.nextInt(i);
    return get(j);
  }

  public String toString(){
    String s = "";
    int i = 0;
    for (String n : subjects){
      i++;
      s = s+i+". "+n+"\n";
    }
    return s;
  }
}