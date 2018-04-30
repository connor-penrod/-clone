/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Conno
 */
public class LeaderboardViewController implements Initializable {

    @FXML
    private Label spot1;
    
    @FXML
    private Label spot2;
    
    @FXML
    private Label spot3;
    
    @FXML
    private Label spot4;
    
    @FXML
    private Label spot5;
    
    @FXML
    private Label spot6;
    
    @FXML
    private Label spot7;
    
    @FXML
    private Label spot8;
    
    @FXML
    private Label backButton;
    
    private ArrayList<Label> names;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        names = new ArrayList<Label>();
        names.add(spot1); names.add(spot2); names.add(spot3); names.add(spot4); 
        names.add(spot5); names.add(spot6); names.add(spot7); names.add(spot8);
        
        setupBackButton();
        
        populateLeaderboard();
    }    
    
    private void setupBackButton()
    {
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent e) {
            try
            {
                backButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("TitleScreenView.fxml")));
            }
            catch (IOException io)
            {
                System.out.println("Error occurred loading title screen.");
            }
        }
        });
        
        backButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                backButton.setTextFill(Color.RED);
                backButton.getScene().setCursor(Cursor.HAND);
            }
        });
        
        backButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                try
                {
                    backButton.setTextFill(Color.BLACK);
                    backButton.getScene().setCursor(Cursor.DEFAULT);
                } catch(NullPointerException npe)
                {
                    System.out.println("Non-issue null pointer exception caught.");
                }
            }
        });
    }
    
    private void populateLeaderboard()
    {
        try
        {
            File file = new File("src/steveerwinchaser/scores.txt");
            if(!file.exists())
            {
                System.out.println("Scores.txt does not exist, using default values");
                createDefault();
                populateLeaderboard();
                return;
            }
            
            BufferedReader br = new BufferedReader(new FileReader(file));
                        
            String line;

            int count = 1;
            for(Label l : names)
            {
                line = br.readLine();
                if(line == null)
                    break;
                String[] words = line.split(" ");
                l.setText(Integer.toString(count++) + ". " + words[0].replace("_", " ") + " => " + words[1]);
            }
            
            br.close();
        }
        catch (IOException io)
        {
            System.out.println("Error occurred populating leaderboard.");
        }
    }
    
    //creates a default leaderboard if none is found on page load
    private void createDefault()
    {
        try
        {
            File file = new File("src/steveerwinchaser/scores.txt");
            file.createNewFile();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        
            bw.write("Hotrod 900\n");
            bw.write("Bill_S._Preston 700\n");
            bw.write("Bjorn Haarlnjornsgaard 600\n");
            bw.write("The_Undertaker 400\n");
            bw.write("Harry 200\n");
            bw.write("Suzuki_Watamashimatamoto 70\n");
            bw.write("Anaander_Mianaai 30\n");
            bw.write("Jacob_Suckora -10\n");
            
            bw.close();
        }
        catch (IOException io)
        {
            System.out.println("Error occurred populating leaderboard.");
        }
    }
    
}
