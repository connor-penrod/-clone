/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Conno
 */
public class SteveErwinChaser extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Loading FXML...");
        Parent root = FXMLLoader.load(getClass().getResource("TitleScreenView.fxml"));
        
        Scene scene = new Scene(root);
        scene.setCursor(Cursor.DEFAULT);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setFullScreen(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
