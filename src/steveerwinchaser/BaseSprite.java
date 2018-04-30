/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Connor Penrod
 */

//this is a base sprite template 
public abstract class BaseSprite implements Viewable {

    Image image;
    ImageView imageView;

    double x;
    double y;
    double r;

    double w;
    double h;
    
    
    public BaseSprite(Image image, double x, double y, double r) {

        this.x = x;
        this.y = y;
        this.r = r;

        this.image = image;
        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        this.imageView.setRotate(r);

        this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();
    }
    
    public void setDimensions(int w, int h)
    {
        this.imageView.setFitWidth(w);
        this.imageView.setFitHeight(h);
        this.w = w;
        this.h = h;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }


    public ImageView getView() {
        return imageView;
    }


    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    public void switchImage(Image img)
    {
        this.imageView.setImage(img);
    }
    
    public void flip(int dir)
    {
        if(dir > 0)
        {
            this.imageView.setScaleX(-1);
        }
        else
        {
            this.imageView.setScaleX(1);
        }
    }
    
    public abstract boolean collidesWith(BaseSprite otherSprite);
}