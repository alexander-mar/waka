package ghost;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import processing.core.PImage;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import processing.core.PApplet;

public class GhostTest {

    @Test 
    public void constructor() {

        int x = 10;
        int y = 10;
        PImage sprite;
        sprite = null;
        long speed = 2;
        Ghost ghost = new Ghost(x,y,sprite, speed, null);
        assertNotNull(ghost);
    }

    @Test
    //Testing if the ghost can move properly
    public void testGhostMovement(){
        
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Chaser chaser = app.getChaser();
        for (int i = 0; i<5;i++){
            chaser.logic(app, map);
        }
        //moving Ghost and testing if it has changed position
        assertNotEquals(187, app.getWaka().getXc());
    }

    @Test
    //Testing if the ghost can detect intersections correctly
    public void testGhostIntersectDetector(){
        
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Chaser chaser = app.getChaser();
        Boolean intersection = chaser.isIntersect(map, app);
        assertTrue(intersection);
        
        
    }

    @Test
    //Testing if the ghost can detect intersections correctly
    public void FreePathDetector(){
        
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Chaser chaser = app.getChaser();
        ArrayList<Boolean> freePaths = chaser.checkFreePath(map, app);
        
        //left and right should be free
        assertTrue(freePaths.get(2));
        assertTrue(freePaths.get(3));
        
        
    }

    @Test
    //Testing if the ghost can detect intersections correctly
    public void NotFreePathDetector(){
        
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Chaser chaser = app.getChaser();
        ArrayList<Boolean> freePaths = chaser.checkFreePath(map, app);
        
        //bottom shouldnt be free
        assertFalse(freePaths.get(1));
        
        
    }
}