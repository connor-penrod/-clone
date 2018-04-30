/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steveerwinchaser;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Conno
 */
public class MovableSprite extends BaseSprite{

    double dx;
    double dy;
    double dr;

    public MovableSprite(Image image, double x, double y, double r, double dx, double dy, double dr) {

        super(image, x, y, r);
        
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public void move() {
        
        x += dx;
        y += dy;
        r += dr;

    }
    

    public boolean collidesWith(BaseSprite otherSprite) {

        return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);

    }

    public void updateUI() {

        this.imageView.relocate(this.x, this.y);
        this.imageView.setRotate(this.r);

    }
}
