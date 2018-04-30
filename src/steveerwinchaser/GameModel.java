/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

/**
 *
 * @author Conno
 */

public class GameModel {
    
    
    private Behavior chaserBehavior;
    private boolean isJumping;
    private boolean hasWon;
    private int timeLock;
    private float chaserSpeed;
    private float maxChaserSpeed;
    private int verticalSkulk;
    private int horizontalSkulk;
    private int jChance;
    private int minJChance;
    private int chaserLurkSpeed;

    private boolean caught;
    private boolean cursorLock;
    private boolean submitStatus;

    private int score;
    
    public GameModel()
    {
        chaserBehavior = Behavior.LURKING;
        isJumping = false;
        hasWon = false;
        timeLock = -1;
        chaserSpeed = 20f;
        maxChaserSpeed = 70f;
        verticalSkulk = 0;
        horizontalSkulk = 1;
        jChance = 140;
        minJChance = 10;
        chaserLurkSpeed = 2;

        caught = false;
        cursorLock = false;
        submitStatus = false;

        score = 0;
    }
    
    // As it turns out, MVC isn't super helpful for games, especially not small ones.
    // This is a lot of getters/setters that handle state variables.
    public Behavior getChaserBehavior(){return chaserBehavior;}
    public void setChaserBehavior(Behavior b){chaserBehavior = b;}
    public boolean getJumpStatus(){return isJumping;}
    public void setJumpStatus(boolean b){isJumping = b;}
    public boolean getChaserWinStatus(){return hasWon;}
    public void setChaserWinStatus(boolean b){hasWon = b;}
    public int getTimeLock(){return timeLock;}
    public void setTimeLock(int i){timeLock = i;}
    public float getChaserSpeed(){return chaserSpeed;}
    public void setChaserSpeed(float f){chaserSpeed = f;}
    public float getMaxChaserSpeed(){return maxChaserSpeed;}
    public void setMaxChaserSpeed(float f){maxChaserSpeed = f;}
    public int getVerticalLurk(){return verticalSkulk;}
    public void setVerticalLurk(int i){verticalSkulk = i;}
    public int getHorizontalLurk(){return horizontalSkulk;}
    public void setHorizontalLurk(int i){horizontalSkulk = i;}
    public int getJumpChance(){return jChance;}
    public void setJumpChance(int i){jChance = i;}
    public int getMinimumJumpChance(){return minJChance;}
    public void setMinimumJumpChance(int i){minJChance = i;}
    public int getChaserLurkSpeed(){return chaserLurkSpeed;}
    public void setChaserLurkSpeed(int i){chaserLurkSpeed = i;}
    public boolean getMouseCaughtStatus(){return caught;}
    public void setMouseCaughtStatus(boolean b){caught = b;}
    public boolean getCursorLockStatus(){return cursorLock;}
    public void setCursorLockStatus(boolean b){cursorLock = b;}
    public int getScore(){return score;}
    public void setScore(int i){score = i;}
    public boolean getSubmitStatus(){return submitStatus;}
    public void setSubmitStatus(boolean b){submitStatus = b;}
}
