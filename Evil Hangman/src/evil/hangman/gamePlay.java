/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evil.hangman;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author kobay
 */
public class gamePlay 
{
    private String guessedLetters;
    private int wordLength;
    private String word;
    private List<String> possibleWords;
    // ^^ list of words before a letter is guessed. This is used if a letter is guessed, reulting in no words left to choose
    private List<String> possibleWordsTest;
    // ^^ list of words after a letter is guessed. This is used if a letter is guesses, and there is more than zero words left
    private String[] displayWord; 
    private String checkDisplay = "";
    private int numWrong = 0;
    private String name;
    
    private Dictionary testDic;
    //private TopTen topTenList = new TopTen();
    
    public gamePlay(String mode){
        testDic = new Dictionary(mode);
        guessedLetters = "";
        if(mode.equalsIgnoreCase("dictionary")){
            wordLength = 2 + (int)(Math.random()*13);
        }
        else if(mode.equalsIgnoreCase("phrases")){
            wordLength = 7 + (int)(Math.random()*7);
        }
        word = "";
        possibleWords = new ArrayList<String>();
        possibleWordsTest = new ArrayList<String>();
        displayWord = new String[wordLength];
        for(int i = 0; i < wordLength; i++){
            displayWord[i] = "_";
            checkDisplay+="_";
        }
        
    }
    
    //checks if the guesses word equals the word
    public boolean checkGuessWord(String guessWord){
        if(guessWord.equalsIgnoreCase(word)){
            checkDisplay = word;
            return true;
        }
        numWrong++;
        return false;
    }
    
    //sets the user's name
    public void setName(String tempName){
        name = tempName;
    }
    
    //returns the string of guessed letters
    public String getGuessedLetters(){
        return guessedLetters;
    }
    
    //adds a letter to the string of guessed letters
    public String updateGuessedLetters(String guess){
        guess = guess.toUpperCase();
        if(guessedLetters.contains(guess)){
            numWrong--;
            return "You have already guessed this letter";
        }
        else if(guess.length() > 1 || guess.length() == 0){
            numWrong--;
            return "You can only guess 1 Letter";
        }
        else{
            guessedLetters+=guess;
        }
        return "";
    }
    
    //chooses the word if... 
    //1)there is one word left in the array of possible words 
    //2)if there are no words left in the array of possible words, selects a word from the last possible choices of words
    public void chooseWord(){
        if(possibleWordsTest.size() == 1){
            word = possibleWordsTest.get(0);
        }
        else if(possibleWordsTest.size() == 0 && word.equals("")){
            int rnum = (int)(Math.random()*possibleWords.size());
            word = possibleWords.get(rnum);
        }
    }
    
    //stop the game when the user has ran out of guesses
    public String checkGameOver(){
        if(numWrong == 14){
            return "You have ran out of guesses. Game Over.";
        }
        else if(word.equals(checkDisplay)){
            return "Congratulations! You guessed the word!";
        }
        return "";
    }
    
    //gets the user's name
    public String getName(){
        return name;
    }
    
    //returns true if the game is over
    public boolean isGameOver(){
        if(numWrong == 14 || word.equals(checkDisplay)){
            return true;
        }
        return false;
    }
    
    //checks if the guessed letters are in the word
    public String checkGuess(){        

        getAllPossibleWords(guessedLetters, wordLength);
        
        for(int i = 0; i < wordLength; i++){
            if(!word.equals("")){
                if(word.substring(i,i+1).equals(guessedLetters.substring(guessedLetters.length()-1))){
                    displayWord[i] = guessedLetters.substring(guessedLetters.length()-1);
                }
                
            }
    
        }
        String dispWord = "";
        for(int i = 0; i < displayWord.length; i++){
            dispWord+=displayWord[i];
        }
        if(checkDisplay.equals(dispWord)){
            numWrong++;
        }
        checkDisplay = dispWord;
        
        return dispWord;
    }
    
    //returns whether or not the "Check Guess" button should be disabled or not
    public boolean disableButton(){
        if(numWrong == 14 || word.equals(checkDisplay)){
            return true;
        }
        
        return false;
    }
    
    //returns the number of wrong guesses
    public int getNumWrong(){
        return numWrong;
    }
    
    //returns the word
    public String getWord(){
        return word;
    }
    
    //returns the length of the word
    public int getWordLength(){
        return wordLength;
    }
    
    //updates the list of possible words(possibleWords) if possibleWordsTest doesn't equal 1 or 0
    public void updatePossibleWords(){
        if(possibleWordsTest.size() >= 2){
            possibleWords = possibleWordsTest;
        }
        else{
            chooseWord();
        }
    }
    
    //puts all the possible words into the array of possible words(test)
    public void getAllPossibleWords(String letters, int len){
        possibleWordsTest = testDic.getArrayNoMatch(letters,len);
        updatePossibleWords();
    }
}
