/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

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
public class AboutViewController implements Initializable {

    @FXML
    private Label backButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupBackButton();
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
    
}
