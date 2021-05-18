/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evil.hangman;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kobay
 */
public class TopTen {
    private List<Player> allPlayers;
    private List<Player> tempPlayers = new ArrayList<Player>();
    
    public TopTen(){
        allPlayers = new ArrayList<Player>();
    }
    
    //initializes the players in all players
    public ObservableList<String> setPlayers(){
        ObservableList<String> topPlayers = FXCollections.observableArrayList();
        loadTextFile();
        int max;
        if(getOrganizedList().size() > 10){
            max = 10;
        }
        else{
            max = getOrganizedList().size();
        }
        for(int i = 0; i < max; i++){
            topPlayers.add(getOrganizedList().get(i).getName() + ": " + getOrganizedList().get(i).getNumWrong());
        }
        
        return topPlayers;
    }
    
    //saves the players' name and scores in the text file
    public void saveTextFile(){
        String outFile = "src/resources/players.txt";
        
        try {
                PrintWriter out = new PrintWriter(outFile);
                for(int i = 0; i < allPlayers.size(); i++)
                {
                    out.println(allPlayers.get(i).getName());
                    out.println(allPlayers.get(i).getNumWrong());
                    
                }
                out.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SampleController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Something went wrong!");
        }
    }
    
    //loads the list of player's with their names and scores
    public void loadTextFile(){
        allPlayers.clear();
        try{
            FileReader reader = new FileReader("src/resources/players.txt");
            Scanner in = new Scanner(reader);
            while(in.hasNextLine())
            {
                allPlayers.add(new Player(in.nextLine(),Integer.parseInt(in.nextLine())));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("SOMETHING HAS GONE HORRIBLY WRONG WE'RE ALL GONNA DIE!");
        }
    }
    
    //creates a top ten list(lowest to highest score)
    public List<Player> getOrganizedList(){
        
        List<Player> tempPlayers = new ArrayList<Player>();
        
        for (int i = 0; i < allPlayers.size(); i++) {
            int pos = i;
            for (int j = i; j < allPlayers.size(); j++) {
                if(allPlayers.get(j).getNumWrong() < allPlayers.get(pos).getNumWrong()){
                    pos = j;
            }
            
            Player min = allPlayers.get(pos);
            allPlayers.set(pos,allPlayers.get(i));
            allPlayers.set(i, min);
            
        }
      
        }
        return allPlayers;
    }
    
    //adds a player to the list
    public void addPlayer(Player newPlayer){
        allPlayers.add(newPlayer);
        //saveTextFile();
    }
   
}
