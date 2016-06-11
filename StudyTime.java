/** This is the main code for the language study game designed
** by Myles Louis Dakan for notecard-free studying and memorization!
** Email: myles.dakan@gmail.com                                           **/

import java.io.*;
import java.util.*;

public class StudyTime {
  //data members
  private HashMap bank;
  private int bMaxSize;
  private Language lang;
  static Scanner kb = new Scanner(System.in);
  static Random rng = new Random(System.currentTimeMillis());

  //constructor
  public StudyTime(){
    lang = new Language();
    buildWords();
  }

  //methods for constructor
  private void buildWords(){
    try {
      System.out.println("Please enter the file name (.txt) of the language which you would like to study:");
      String fn = readString();
      System.out.println("Loading...\n");
      Scanner scanner = new Scanner(new FileReader(fn));
      while (scanner.hasNext()) {
	parseLine(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
      e.printStackTrace();
    }
  }

  private void parseLine(String line) {    //helper method to buildWords()
    Scanner lineScanner = new Scanner(line);
    lineScanner.useDelimiter("\t");
    String s1 = lineScanner.next();
    String s2 = lineScanner.next();
    //System.out.println(s1+" "+s2);

    if (s1.equals("!!!")){                 //make new unit
      Unit u = new Unit(s2);
      lang.add(u);
      //System.out.println("\nUnit: "+s2);
    }else{                                 //add word
      lang.get(lang.size()-1).add(s1,s2);
      //System.out.println(s1+" is the word for "+s2);
    }
  }

  //methods for interactivity
  public void interact(){
    printWelcome();
    int mode = 0;
    while (mode!=-1){
      mode = chooseMode();
      if (mode==-1){
	System.out.println("\nAre you sure you want to leave?\ny/n:");
	String a = readString();
	if (!a.equals("n")){
	  System.out.println("Thanks for playing.  Come again soon!");
	  return;
	}
	mode = 0;
	System.out.println();
	continue;
      }
      boolean rvw = isReview();
      int lvl = chooseLevel();
      askQs(mode,rvw,lvl);
    }
  }

  private void printWelcome(){
    System.out.println("Welcome to the language study game.\n");
  }

  private int chooseMode(){
    System.out.println("Please choose the game mode you would like to play:");
    System.out.println("1. Study\n2. Test\n3. Exit\nAnswer: ");
    int i = readInt();
    if (i==1 || i==2){
      return i;
    }
    return -1;
  }

  private boolean isReview(){
    System.out.println("\nWould you like to:\n1. study one word list, or");
    System.out.println("2. review everything up to a word list?\nAnswer: ");
    int i = readInt();
    if (i==2){ return true; }
    return false;
  }

  private int chooseLevel(){
    System.out.println("\nPlease choose a level by number:\n"+lang+"Answer: ");
    int i = readInt();
    return i;
  }

  private void askQs(int mode,boolean rvw,int lvl){
    if (mode==1){
      System.out.println("\nAnswer 0 at any time to return to the main menu.");
    }else{
      System.out.println("\n      To return to the main menu, answer 'exit'.");
    }
    setUpBank(rvw,lvl);
    bMaxSize = bank.size();
    int i = -1;
    while (i!=0){
      Word w1 = get1();
      if (bank.size()==0){
	setUpBank(rvw,lvl);
      }
      int r = rng.nextInt(2);
      if (r==0){
	System.out.println("\nWhat is '"+w1.len()+"' in English?");
      }else{
	System.out.println("\nPlease translate '"+w1.eng()+"'.");
      }
      if (mode==1){
	Word w2;
	Word w3;
	Word w4;
	do{
	  w2 = getQ(rvw,lvl);
	} while (w2==w1);
	do{
	  w3 = getQ(rvw,lvl);
	  while (w3==w2){
	    w3 = getQ(rvw,lvl);
	  }
	} while (w3==w1);
	do{
	  do{
	    w4 = getQ(rvw,lvl);
	    while (w4==w3){
	      w4 = getQ(rvw,lvl);
	    }
	  } while (w4==w2);
	} while (w4==w1);
	i = askQ(w1,w2,w3,w4,r);
      }else{
	i = askQ(w1,r);
      }
    }
    System.out.println("\n");
  }

  private void setUpBank(boolean rvw,int lvl){
    bank = new HashMap();
    if (rvw){
      for (int i=0; i<lvl; i++){
	addUnit(lang.get(i));
      }
    }else{
      addUnit(lang.get(lvl-1));
    }
  }

  private void addUnit(Unit u){
    for(int i=0; i<u.size(); i++){
      bank.put(bank.size(),u.get(i));
    }
  }

  private Word get1(){
    int i = rng.nextInt(bMaxSize);
    do{
      i = (i+1)%(bMaxSize);
    }while (!bank.containsKey(i));
    Word w = (Word) bank.remove(i);
    return w;
  }

  private Word getQ(boolean rvw,int lvl){
    Unit u;
    if (rvw){
      u = lang.getRand(lvl);
    }else{
      u = lang.get(lvl-1);
    }
    return u.get();
  }

  private int askQ(Word w1,Word w2,Word w3,Word w4,int r){
    ArrayList<Word> source = new ArrayList<Word>(4);
    source.add(w1);
    source.add(w2);
    source.add(w3);
    source.add(w4);
    LinkedList<Word> prompt = new LinkedList<Word>();
    int a = 0;
    int j = 0;
    while (prompt.size()<4){
      Word wrd = source.remove(rng.nextInt(source.size()));
      prompt.add(wrd);
      j++;
      String p;
      if (r==0){
	p = wrd.eng();
      }else{
	p = wrd.len();
      }
      System.out.print(j+". "+p+"   ");
      if (wrd==w1){
	a = j;
      }
    }

    String ans;
    if (r==0){
      ans = w1.eng();
    }else{
      ans = w1.len();
    }
    
    System.out.println("\nAnswer: ");
    int i = readInt();
    if (i==a){
      System.out.println("Very good!");
    }else if (i>0){
      System.out.println("The answer is: "+a+". "+ans);
      System.out.println("Better luck next time.");
      rebank(w1);
    }
    
    return i;
  }

  private void rebank(Word w){
    int i = rng.nextInt(bMaxSize);
    do{
      i = (i+1)%(bMaxSize);
    }while (!bank.containsKey(i));
    bank.put(i,w);
  }

  private int askQ(Word wrd,int r){
    int i = 0;
    System.out.println("Answer: ");
    String s = readString();
    if (s.equals("exit")){ return 0; }

    String ans;
    if (r==0){
      ans = wrd.eng();
    }else{
      ans = wrd.len();
    }

    if (s.equals(ans)){
      i = 2;
      System.out.println("Very good!");
    }else{
      i = 1;
      System.out.println("The answer is: "+ans+"\nBetter luck next time.");
    }

    return i;
  }

  private int readInt(){
    int a = -1;
    try {
      a = kb.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Please enter a valid number from the menu.");
    }
    return a;
  }

  private String readString(){
    kb = new Scanner(System.in);
    String s = null;
    try {
      s = kb.nextLine();
    } catch (InputMismatchException e) {
      System.out.println("Please enter a valid string of letters.");
    }
    return s;
  }

  //main function
  public static void main(String args[]){
    StudyTime st = new StudyTime();
    st.interact();
  }
}