/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

import java.lang.Math;
import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Connor Penrod
 */

//this class contains the actual game logic
public class ChaserViewController implements Initializable {

    @FXML
    private Label restartButton;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private HBox nameForm;
    
    @FXML
    private Label menuButton;
    
    @FXML
    private Pane rootPane;
    
    @FXML
    private Text scoreText;
        
    
    private Text confirmMsg;
    
    private IntegerProperty width;
    private IntegerProperty height;
    
    private MovableSprite mouse;
    private MovableSprite chaser;
        
    private ArrayList<MovableSprite> sprites = new ArrayList<MovableSprite>();
    
    private GameModel gm;
    
    private AnimationTimer at;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       initializeGame();
    }    
    
    //handles initial variable states and begins the game loop
    private void initializeGame()
    {
        gm = new GameModel();
                
        if(sprites.size() > 0)
        {
            sprites.forEach(sprite -> rootPane.getChildren().remove(sprite.getView()));
            sprites.clear();
        }
        
        if(confirmMsg != null)
        {
            rootPane.getChildren().remove(confirmMsg);
        }
        
        width = new SimpleIntegerProperty();
        height = new SimpleIntegerProperty();
                
        width.bind(rootPane.widthProperty());
        height.bind(rootPane.heightProperty());
        
        hideGUI();
        setupMouse();
        
        addSprites();

        System.out.println("Initialized.");
        gameLoop();
    }
    
    private void setupMouse()
    {
        rootPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse.setX(event.getX() - mouse.getWidth()/2);
                mouse.setY(event.getY() - mouse.getHeight()/2);
            }
        });
    }
    
    //used for when the user decides to restart
    private void hideGUI()
    {   
        restartButton.setVisible(false);
        restartButton.setDisable(true);
        menuButton.setVisible(false);
        menuButton.setDisable(true);
        nameForm.setVisible(false);
        nameForm.setDisable(true);
        gm.setSubmitStatus(false);
    }
    
    //setup sprites
    private void addSprites()
    {
        mouse = new MovableSprite(new Image(getClass().getResource("blacksquare.png").toString()),500,500,0,0,0,0);
        mouse.setDimensions(10,10);
        
        chaser = new MovableSprite(new Image(getClass().getResource("pixelirwin.png").toString()),0,0,180,1,0,0);
        chaser.setDimensions(70,70);
        
        
        sprites.add(mouse);
        sprites.add(chaser);
        
        sprites.forEach(sprite -> rootPane.getChildren().add(sprite.getView()));
    }
    
    private int jumpCheck = 0;
    
    //handles the mouse's movements and makes sure it stays inbounds
    private void updateMouse()
    {
        if(gm.getMouseCaughtStatus()){ 
            mouse.setX(chaser.getX() + chaser.getWidth()+2);
            mouse.setY(chaser.getY());
        }
    }
    
    private int testVar = 0;
    
    //controls chaser behavior
    private void updateChaser()
    {
        Random bRand = new Random();
        int switchDirChance = bRand.nextInt(50) + 1;
        int jumpChance = bRand.nextInt(gm.getJumpChance()) + 1;
               
        switch(gm.getChaserBehavior())
        {
            //handles chaser's "lurking" behavior when he's on the walls
            case LURKING:
            {
                
                chaser.switchImage(new Image(getClass().getResource("pixelirwin.png").toString()));
                
                
                //check if the chaser has caught the mouse. If it did, then behavior is different
                if(gm.getChaserWinStatus())
                {
                    gm.setChaserBehavior(Behavior.WIN);
                    break;
                }
                
                if((chaser.getX() + chaser.getWidth()) > width.getValue().intValue())
                {
                    chaser.setDx(-1 * gm.getHorizontalLurk());
                    break;
                }
                if(chaser.getX() < 0)
                {
                    chaser.setDx(1 * gm.getHorizontalLurk());
                    break;
                }
                
                if((chaser.getY() + chaser.getHeight()) > height.getValue().intValue())
                {
                    chaser.setDy(-1 * gm.getVerticalLurk());
                    break;
                }
                if(chaser.getY() < 0)
                {
                    chaser.setDy(1 * gm.getVerticalLurk());
                    break;
                }
                        
                /*if(switchDirChance == 1)
                {
                    int sign = Integer.signum((int)chaser.getDx());
                    int sign2 = Integer.signum((int)chaser.getDy());
                                        
                    chaser.setDx(-1 * sign * (bRand.nextInt(3) + 1) * horizontalSkulk);
                    chaser.setDy(-1 * sign2 * (bRand.nextInt(3) + 1) * verticalSkulk);
                }*/
                
                int sign = Integer.signum((int)(mouse.getX() - chaser.getX()));
                int sign2 = Integer.signum((int)(mouse.getY() - chaser.getY()));
                
                chaser.setDx(sign * gm.getHorizontalLurk() * gm.getChaserLurkSpeed());
                chaser.setDy(sign2 * gm.getVerticalLurk() * gm.getChaserLurkSpeed());
                
                if(gm.getHorizontalLurk() == 1)
                {
                    if(sign > 0 && sign2 < 0)
                        chaser.flip(1);

                    else if(sign > 0 && sign2 > 0)
                        chaser.flip(-1);

                    else if(sign < 0 && sign2 > 0)
                        chaser.flip(1);

                    else if(sign < 0 && sign2 < 0)
                        chaser.flip(-1);

                    else
                        chaser.flip(1);
                }
                else
                {
                    if(sign > 0 && sign2 < 0)
                        chaser.flip(-1);

                    else if(sign > 0 && sign2 > 0)
                        chaser.flip(1);

                    else if(sign < 0 && sign2 > 0)
                        chaser.flip(-1);

                    else if(sign < 0 && sign2 < 0)
                        chaser.flip(1);

                    else
                        chaser.flip(1);
                }
                  
                
                
                if(jumpChance == 1)
                {
                    gm.setChaserBehavior(Behavior.JUMPING);
                }
                
                break;
            }
            //handles the chaser's jumping towards the mouse
            case JUMPING:
            {
                if(!gm.getJumpStatus())
                {
                    gm.setJumpStatus(true);
                    
                    chaser.switchImage(new Image(getClass().getResource("pixelirwin_jumping.png").toString()));
                    
                    float x_diff = (float)(mouse.getX() - chaser.getX());
                    float y_diff = (float)(mouse.getY() - chaser.getY());
                    float magnitude = (float)Math.sqrt(Math.pow(x_diff,2) + Math.pow(y_diff,2));
                    
                    float unitDx = x_diff/magnitude;
                    float unitDy = y_diff/magnitude;
                    
                    float delta_x = (float)(mouse.getX() - chaser.getX());
                    float delta_y = (float)(mouse.getY() - chaser.getY());
                    float theta_radians = (float)Math.atan2(delta_y, delta_x);
                    
                    chaser.setDx(unitDx * gm.getChaserSpeed());
                    chaser.setDy(unitDy * gm.getChaserSpeed());
                    
                    chaser.setR((theta_radians + Math.PI/2) * (180/Math.PI));
                    break;
                }
                else
                {
                    
                    //if chaser hits left/right walls
                    if((chaser.getX() + chaser.getWidth()) > width.getValue().intValue() || chaser.getX() < 0)
                    {
                        if(chaser.getX() > 0)
                        {
                            chaser.setX(width.getValue().intValue() - chaser.getWidth());
                            chaser.setR(270);
                        }
                        else
                        {
                            chaser.setX(0);
                            chaser.setR(90);
                        }
                        
                        chaser.setDx(0);
                        chaser.setDy(1);
                        gm.setVerticalLurk(1);
                        gm.setHorizontalLurk(0);
                        
                        gm.setJumpStatus(false);
                        gm.setChaserBehavior(Behavior.LURKING);
                        
                        testVar = 1;
                        break;
                    }
                    
                    //if chaser hits top/bottom walls
                    else if((chaser.getY() + chaser.getHeight()) > height.getValue().intValue() || chaser.getY() < 0)
                    {
                        if(chaser.getY() > 0)
                        {
                            chaser.setY(height.getValue().intValue() - chaser.getHeight());
                            chaser.setR(0);
                        }
                        else
                        {
                            chaser.setY(0);
                            chaser.setR(180);
                        }
                        
                        chaser.setDx(1);
                        chaser.setDy(0);
                        gm.setVerticalLurk(0);
                        gm.setHorizontalLurk(1);
                        
                        gm.setJumpStatus(false);
                        gm.setChaserBehavior(Behavior.LURKING);
                        
                        testVar = 1;
                        break;
                    }
                }
                break;
            }
            //handles chasers move back to the center to display his prize
            case WIN:
            {
                if(gm.getChaserWinStatus())
                {   
                    gm.setChaserWinStatus(false);
                    
                    chaser.switchImage(new Image(getClass().getResource("pixelirwin_jumping.png").toString()));
                    
                    if(chaser.getY() > (height.getValue().intValue()/2) && gm.getHorizontalLurk() == 1)
                    {
                        chaser.setDy(0);
                        chaser.setDx(0);
                        chaser.setY(height.getValue().intValue() - chaser.getHeight());
                        chaser.setR(0);
                        break;
                    }
                    
                    float x_diff = (float)(width.getValue().intValue()/2 - chaser.getX());
                    float y_diff = (float)((height.getValue().intValue()-chaser.getHeight()) - chaser.getY());
                    float magnitude = (float)Math.sqrt(Math.pow(x_diff,2) + Math.pow(y_diff,2));

                    float unitDx = x_diff/magnitude;
                    float unitDy = y_diff/magnitude;
                    
                    float delta_x = (float)(mouse.getX() - chaser.getX());
                    float delta_y = (float)(mouse.getY() - chaser.getY());
                    float theta_radians = (float)Math.atan2(delta_y, delta_x);

                    chaser.setDx(unitDx * gm.getChaserSpeed());
                    chaser.setDy(unitDy * gm.getChaserSpeed());
                    chaser.setR(theta_radians * (180/Math.PI));
                    break;
                }
                
                if(chaser.getY() + chaser.getHeight() >= height.getValue().intValue())
                {
                    chaser.setDy(0);
                    chaser.setDx(0);
                    chaser.setY(height.getValue().intValue() - chaser.getHeight());
                    chaser.setR(0);
                }
            }
        }
    }
    
    //checks if the mouse has collided with the chaser
    private void checkCollision(int seconds)
    {
        if(mouse.collidesWith(chaser) && seconds >= 1)
        {
            gm.setChaserWinStatus(true);
            gm.setMouseCaughtStatus(true);
        }
    }
    
    //this function increases difficulty over time by increasing chaser's jump speed and probability to jump each frame
    private void updateDifficulty(int seconds)
    {
        if(seconds % 5 == 0 && seconds != gm.getTimeLock())
        {
            
            if(gm.getChaserSpeed() > gm.getMaxChaserSpeed())
            {
                gm.setChaserSpeed(gm.getMaxChaserSpeed());
            }
            else
            {
                gm.setChaserSpeed(gm.getChaserSpeed() + 3);
            }
            
            if(gm.getJumpChance() > gm.getMinimumJumpChance())
            {
                gm.setJumpChance(gm.getJumpChance() - 5);
            }
            else
            {
                gm.setJumpChance(gm.getMinimumJumpChance());
            }
            gm.setTimeLock(seconds);
        }
    }
    
    //updates the score, which is based on time elapsed
    private void updateScore(float seconds)
    {
        if(!gm.getMouseCaughtStatus())
        {
            scoreText.setText("Score: " + gm.getScore());
            gm.setScore((int)seconds/100);
            
            scoreText.setX(width.getValue().intValue() - scoreText.getWrappingWidth());
            scoreText.setY(0);
        }
    }
    
    //setup and present the menu when the player loses
    private void updateLossOptions()
    {
        if(gm.getMouseCaughtStatus())
        {
            restartButton.setLayoutX(width.getValue().intValue()/2 - restartButton.getWidth()/2);
            restartButton.setLayoutY(height.getValue().intValue()/2 - restartButton.getHeight()/2);
            
            restartButton.setVisible(true);
            restartButton.setDisable(false);
            
            nameForm.setLayoutX(width.getValue().intValue()/2 - nameForm.getWidth()/2);
            nameForm.setLayoutY(restartButton.getLayoutY() - restartButton.getHeight() - 100);
            
            nameForm.setVisible(true);
            nameForm.setDisable(false);
            
            if(!gm.getSubmitStatus())
            {
                nameField.setVisible(true);
                nameField.setDisable(false);
                submitButton.setVisible(true);
                submitButton.setDisable(false);
                gm.setSubmitStatus(true);
            }
            
            scoreText.setX(width.getValue().intValue()/2 - scoreText.getWrappingWidth()/2);
            scoreText.setY(nameForm.getLayoutY() - nameForm.getHeight() - 10);
            
            menuButton.setLayoutX(width.getValue().intValue()/2 - menuButton.getWidth()/2);
            menuButton.setLayoutY(height.getValue().intValue()/2 + restartButton.getHeight());
            
            menuButton.setVisible(true);
            menuButton.setDisable(false);
            
            
            if(!gm.getCursorLockStatus())
            {
                restartButton.getScene().setCursor(Cursor.DEFAULT);
                gm.setCursorLockStatus(true);
            }
            
            restartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                at.stop();
                restartButton.getScene().setCursor(Cursor.NONE);
                
                initializeGame();
            }
            });
            
            restartButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                restartButton.setTextFill(Color.RED);
                //restartButton.getScene().setCursor(Cursor.HAND);
            }
            });

            restartButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                restartButton.setTextFill(Color.BLACK);
                //restartButton.getScene().setCursor(Cursor.DEFAULT);
            }
            });
            
            menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                try
                {
                    menuButton.getScene().setCursor(Cursor.DEFAULT);
                    menuButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("TitleScreenView.fxml")));
                }
                catch (IOException io)
                {
                    System.out.println("Error occurred loading main menu.");
                }
            }
            });
            
            menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                menuButton.setTextFill(Color.RED);
                //restartButton.getScene().setCursor(Cursor.HAND);
            }
            });

            menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                menuButton.setTextFill(Color.BLACK);
                //restartButton.getScene().setCursor(Cursor.DEFAULT);
            }
            });
            
            submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                if(!nameField.getText().trim().isEmpty())
                {
                    int check = saveScore(nameField.getText(), Integer.toString(gm.getScore()));
                    
                    if(check == 1)
                    {
                        System.out.println("Score saved.");
                        confirmMsg = new Text("Score saved successfully.");
                        confirmMsg.setLayoutX(width.getValue().intValue()/2 - confirmMsg.getBoundsInLocal().getWidth()/2);
                        confirmMsg.setLayoutY(nameForm.getLayoutY() + nameForm.getHeight() + 20);
                        rootPane.getChildren().add(confirmMsg);
                        
                        nameField.setDisable(true);
                        nameField.setText("");
                        submitButton.setDisable(true);
                    }
                    
                    else
                    {
                        System.out.println("Error occurred during score save.");
                    }
                }
            }
            });
            
        }
    }
    
    private void updateGUI(float seconds)
    {
        updateScore(seconds);
        updateLossOptions();
    }
    
    //handles the frame-by-frame execution of the game
    private void gameLoop()
    {
        System.out.println("Starting game loop...");
        final long startNanoTime = System.nanoTime();
        at = new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                int t = (int)((currentNanoTime-startNanoTime)/1000000000);
                float t_millis = (int)((currentNanoTime-startNanoTime)/1000000);
                
                checkCollision(t);
                updateMouse();
                updateChaser();
                sprites.forEach(sprite -> sprite.move());
                sprites.forEach(sprite -> sprite.updateUI());
                
                updateDifficulty(t);
                updateGUI(t_millis);
            }
        };
        
        at.start();
    }
    
    //save the score with the name submitted by the user
    private int saveScore(String name, String score){
        
        if(name == null || score == null)
        {
            System.out.println("Invalid save data.");
            return -1;
        }
        
        BufferedWriter bw = null;
        try
        {
            File file = new File("src/steveerwinchaser/scores.txt");

            if (!file.exists()) {
               file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(name.replace(" ", "_") + " " + score + "\n");
            
        } 
        
        catch (IOException ioe) {
            ioe.printStackTrace();
            return -1;
	}
        
	finally
	{ 
            try
            {
                if(bw != null)
                {
                    bw.close();
                    
                    updateLeaderboardScores();
                    return 1;
                }
            }
            catch(Exception ex){
	       System.out.println("Error in closing the BufferedWriter: "+ex);
               return -1;
	    }
	}
        
        return -1;
    }
    
    //sorts the scores in descending order
    private void updateLeaderboardScores()
    {
        try
        {
            File file = new File("src/steveerwinchaser/scores.txt");
            if(!file.exists())
            {
                System.out.println("Scores.txt does not exist.");
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            TreeMap<Integer, String> scores = new TreeMap<Integer, String>(Collections.reverseOrder());
            
            String line;

            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                int score = Integer.parseInt(words[1]);
                
                scores.put(score, words[0]);
            }
            
            br.close();
            
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(Map.Entry<Integer, String> entry : scores.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();

                bw.write(value + " " + key + "\n");
            }
            
            bw.close();
        }
        catch (IOException io)
        {
            System.out.println("Error occurred while updating scores.");
        }
    }
}
