package ghost;

import processing.core.PImage;
import processing.core.PApplet;
public class Entity {

    protected int x;
    protected int y;
    protected PImage sprite;

    public Entity(int x, int y, PImage sprite){
        
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    
    /**
     * draws the entity
     * @param app
     */
    public void draw(PApplet app){
        //handles graphics
        app.image(this.sprite, x, y);
    }

    public int getXc(){
        return x;
    }
    public int getYc(){
        return y;
    }
    public int getWidth(){
        return this.sprite.width;
    }
    public int getHeight(){
        return this.sprite.height;
    }

    public void setXc(int c){
        this.x = c;
    }

    public void setYc(int c){
        this.y = c;
    }

   
}