/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evil.hangman;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 *
 * @author kobay
 */
public class SampleController implements Initializable {
    
    private gamePlay game;
    List<String> picts = new ArrayList<>();
    
    @FXML
    private Label lblWord,lblWL;
    @FXML
    private Label lblNumWrong;
    @FXML
    private ImageView imgHang;
    @FXML
    private Label lblDisplay;
    @FXML
    private Label lblGameOver;
    @FXML
    private Label lblName;
    @FXML
    private Label lblCommas;
    
    @FXML
    private ListView listViewTopTen;  
    
    @FXML
    private Button btnGuess;
    
    @FXML 
    private Button btnStart;
    
    @FXML
    private Button btnGuessWord;
    
    private TopTen topTenList = new TopTen();
    
    @FXML
    private void handleGuessWord(ActionEvent event){
        if(game.checkGuessWord(JOptionPane.showInputDialog("What is you guess?"))){
            lblGameOver.setText("Congratulations! You have guessed the word!");
            topTenList.addPlayer(new Player(game.getName(),game.getNumWrong()));
            topTenList.saveTextFile();
            lblWord.setText(game.getWord());
            topTenList.loadTextFile();
            listViewTopTen.getItems().clear();
            listViewTopTen.setItems(topTenList.setPlayers());
            btnGuess.setDisable(true);
            btnGuessWord.setDisable(true);
            btnStart.setDisable(false);
        }
        displayHangMan();
        lblNumWrong.setText(14 - game.getNumWrong() + "");
    }
    
    @FXML
    private void handleStart(ActionEvent event) 
    {
        String difficulty = JOptionPane.showInputDialog("Select a Game Mode(DICTIONARY or PHRASES)");
        if(difficulty.equalsIgnoreCase("phrases")){
            lblCommas.setVisible(true);
        }
        else{
            lblCommas.setVisible(false);
        }
        topTenList.loadTextFile();
        listViewTopTen.getItems().clear();
        listViewTopTen.setItems(topTenList.setPlayers());
        game = new gamePlay(difficulty);
        lblWL.setText(game.getGuessedLetters());       
        picts.clear();
        picts.add("resources/fourteen.jpg");
        picts.add("resources/thirteen.jpg");
        picts.add("resources/twelve.jpg");
        picts.add("resources/eleven.jpg");
        picts.add("resources/ten.jpg");
        picts.add("resources/nine.jpg");
        picts.add("resources/eight.jpg");
        picts.add("resources/seven.jpg");
        picts.add("resources/six.jpg");
        picts.add("resources/five.jpg");
        picts.add("resources/four.jpg");
        picts.add("resources/three.jpg");
        picts.add("resources/two.jpg");
        picts.add("resources/one.jpg");
        picts.add("resources/one.jpg");
        lblWord.setText(game.checkGuess());
        lblGameOver.setText("");
        displayHangMan();
        game.setName(JOptionPane.showInputDialog("What is your name?"));
        lblName.setText(game.getName());
        btnGuess.setDisable(false);
        btnGuessWord.setDisable(false);
        btnStart.setDisable(true);
    }
    
    //displays the images of hangman
    public void displayHangMan(){
        imgHang.setImage(new Image(picts.get(game.getNumWrong())));
    }
    
    @FXML
    private void handleGuess(ActionEvent event)
    {
        lblDisplay.setText(game.updateGuessedLetters(JOptionPane.showInputDialog("Guess a Letter")).toUpperCase());
        lblWord.setText(game.checkGuess());
        System.out.println(game.getWord());
        lblWL.setText(game.getGuessedLetters());
        lblGameOver.setText(game.checkGameOver());
        if(game.isGameOver()){
            topTenList.addPlayer(new Player(game.getName(),game.getNumWrong()));
            topTenList.saveTextFile();
            lblWord.setText(game.getWord());
            topTenList.loadTextFile();
            listViewTopTen.getItems().clear();
            listViewTopTen.setItems(topTenList.setPlayers());
            btnGuessWord.setDisable(true);
            btnStart.setDisable(false);
        }
        btnGuess.setDisable(game.disableButton());
        displayHangMan();
        lblNumWrong.setText(14 - game.getNumWrong() + "");
         
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        listViewTopTen.setItems(topTenList.setPlayers());
        
    }    
}
