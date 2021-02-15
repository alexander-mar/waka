package ghost;
import processing.core.PImage;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Math;
public class Ignorant extends Ghost {
    

    HashMap<Integer, Integer> prevDistances;
    

    public Ignorant(int x, int y, PImage display, long speed, long[] modeLengths) {
        
        super(x, y, display, speed, modeLengths);
        ogX = x;
        ogY = y;
        xv = (int) (-1 * speed);
        yv = 0;

        this.x = x;
        this.y = y;
        
    }
    /**
     * deals with the ignorant logic
     * @param app
     * @param layout
     */
    public void logic(App app, Entity[][] layout) {
        HashMap<Integer, Integer>distances = prevDistances;
        
        Double distancefrom = Math.sqrt(Math.pow((x - app.getWaka().getXc()),2) + Math.pow(y  - app.getWaka().getYc(),2));

        
        
            
        if (checkModes()== true){
            //gonna have to change target
            
            target = layout[32][0];

        }
        else{
            if (distancefrom < 128){
                target = app.getWaka();
            }
            else{
                target = layout[32][0];
        
            }
        }

        
        distances = getDistances();
        
        
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
    
    
