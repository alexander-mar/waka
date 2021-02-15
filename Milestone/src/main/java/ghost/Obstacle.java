package ghost;
import processing.core.PApplet;
import processing.core.PImage;

public class Obstacle extends Entity {
    Integer x;
    Integer y;
    PImage sprite;
    PApplet app;

    public Obstacle(int x, int y, PImage sprite){
        
        super(x, y, sprite);
        this.x = x;
        this.y = y;
        this.sprite= sprite;
        
    }
    

    public static void main(String[] args){
        
                        
    }
}