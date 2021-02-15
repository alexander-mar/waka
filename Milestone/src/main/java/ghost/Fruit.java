package ghost;
import processing.core.PImage;

public class Fruit extends Entity {
    //amount of fruits
    public static int size = 0;

    //all the fruits on the board

    public Fruit(int x, int y, PImage sprite){
        super(x, y, sprite);
        size += 1;
        
    }

    public int getSize(){
        return size;
    }

 

    
}