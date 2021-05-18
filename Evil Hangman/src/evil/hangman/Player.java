/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evil.hangman;

/**
 *
 * @author kobay
 */
public class Player {
    private String name;
    private int numWrong;
    public Player(String tempName, int tempNum){
        name = tempName;
        numWrong = tempNum;
    }
    
    public Player(String tempName){
        name = tempName;
    }
    
    //sets a score for the player
    public void setNumWrong(int score){
        numWrong = score;
    }
    
    //gets the number of wrong guesses
    public int getNumWrong(){
        return numWrong;
    }
    
    //gets the player's name
    public String getName(){
        return name;
    }
}
