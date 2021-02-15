package ghost;
import processing.core.PImage;

public class Superfruit extends Fruit{

    Boolean consumed = false;
    

    public Superfruit(int x, int y, PImage sprite, int timeFreightened){

        super(x, y, sprite);
        //sends signal that superfruit has been eaten
    }

    
}