package ghost;

import processing.core.PImage;
import java.util.HashMap;
import java.util.ArrayList;
public class Whim extends Ghost{


    HashMap<Integer, Integer> prevDistances;

    public Whim(int x, int y, PImage display, long speed, long[] modeLengths) {
        
        super(x, y, display, speed, modeLengths);
        ogX = x;
        ogY = y;
        xv = (int) (-1 * speed);
        yv = 0;
          
    }
    /**
     * deals with the whim logic
     * @param app
     * @param layout
     */
    public void logic(App app, Entity[][] layout) {
        HashMap<Integer, Integer>distances = prevDistances;
        //target = layout[0][0];
        if (checkModes()== true){
            //gonna have to change target
            
            target = layout[32][20];

        }
        else{
            target = app.getWaka();
        }
        
        //if (isIntersect(layout, app) == true || this.x == ogX && this.y == ogY) {
        distances = getDistances();
        //}
        
        ArrayList<Boolean> freePaths = checkFreePath(layout, app);
        
        pickDirection(distances, layout, app, freePaths);
        prevDistances = distances;

        this.move(app, layout);

        if(this.alive) {
            this.x += xv;
            this.y += yv;
        }
    }


    
}

