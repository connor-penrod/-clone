/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
public class TitleScreenViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label startButton;
    
    @FXML
    private Label aboutButton;
    
    @FXML
    private Label leaderButton;
    
    @FXML
    private Label exitButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeMainMenuUI();
        checkLeaderboard();
    }    
    
    private void initializeMainMenuUI()
    {
        
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent e) {
            try
            {
                startButton.getScene().setCursor(Cursor.NONE);
                startButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("ChaserView.fxml")));
            }
            catch (IOException io)
            {
                System.out.println("Error occurred loading game.");
            }
        }
        });
        
        startButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                startButton.setTextFill(Color.RED);
                startButton.getScene().setCursor(Cursor.HAND);
            }
        });
        
        startButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                try
                {
                    startButton.setTextFill(Color.BLACK);
                    //startButton.getScene().setCursor(Cursor.DEFAULT);
                } catch(NullPointerException npe)
                {
                    System.out.println("Non-issue null pointer exception caught.");
                }
            }
        });
        
        leaderButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                try
                    {
                        startButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("LeaderboardView.fxml")));
                    }
                catch (IOException io)
                    {
                        System.out.println("Error occurred loading leaderboard. " + io.toString());
                    }
            }
        });
        
        leaderButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                leaderButton.setTextFill(Color.RED);
                leaderButton.getScene().setCursor(Cursor.HAND);
            }
        });
        
        leaderButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try
                    {
                        leaderButton.setTextFill(Color.BLACK);
                        leaderButton.getScene().setCursor(Cursor.DEFAULT);
                    }
                catch(NullPointerException npe)
                    {
                        System.out.println("Non-issue null pointer caught.");
                    }
            }
        });
        
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                System.exit(1);
            }
        });
        
        exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                exitButton.setTextFill(Color.RED);
                exitButton.getScene().setCursor(Cursor.HAND);
            }
        });
        
        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                try
                    {
                        exitButton.setTextFill(Color.BLACK);
                        exitButton.getScene().setCursor(Cursor.DEFAULT);
                    }
                catch(NullPointerException npe)
                    {
                        System.out.println("Non-issue null pointer caught.");
                    }
            }
        });
        
        aboutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                try
                    {
                        aboutButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("AboutView.fxml")));
                    }
                catch (IOException io)
                    {
                        System.out.println("Error occurred loading about page.");
                    }
            }
        });
        
        aboutButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                aboutButton.setTextFill(Color.RED);
                aboutButton.getScene().setCursor(Cursor.HAND);
            }
        });
        
        aboutButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                try
                    {
                        aboutButton.setTextFill(Color.BLACK);
                        aboutButton.getScene().setCursor(Cursor.DEFAULT);
                    }
                catch(NullPointerException npe)
                    {
                        System.out.println("Non-issue null pointer caught.");
                    }
            }
        });
    }    
    
    private void checkLeaderboard()
    {
        try
        {
            File file = new File("src/steveerwinchaser/scores.txt");
            
            if(file.exists())
            {
               return; 
            }
            else
            {
                file.createNewFile();

                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                bw.write("Hotrod 900\n");
                bw.write("Bill_S._Preston 700\n");
                bw.write("Bjorn_Haarlnjornsgaard 600\n");
                bw.write("The_Undertaker 400\n");
                bw.write("Harry 200\n");
                bw.write("Suzuki_Watamashimatamoto 70\n");
                bw.write("Anaander_Mianaai 30\n");
                bw.write("Jacob_Suckora -10\n");

                bw.close();
            }
        }
        catch (IOException io)
        {
            System.out.println("Error occurred populating leaderboard.");
        }
    }
}
