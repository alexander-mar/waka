package ghost;

import processing.core.PImage;
import java.util.HashMap;

public class Chaser extends Ghost {
    public Chaser(int x, int y, PImage display, long speed) {
        super(x, y, display, speed);
        xv = (int) (-1 * speed);
        yv = 0;

    }

    public void logic(App app, Entity[][] layout) {
        //target = layout[0][0];
        target = app.getWaka();
        HashMap<Integer, Integer> distances = getDistances();
        //System.out.println(distances);
        pickDirection(distances, layout, app);
        
        

    }

    // PROBLEM IS THE HIT BOOLEAN AND VERMOV VARIABLE

    
}