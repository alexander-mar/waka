package ghost;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import processing.core.PImage;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import processing.core.PApplet;

public class WakaTest {
 
    @Test 
    public void constructor() {
        
        int x = 10;
        int y = 10;
        PImage sprite;
        sprite = null;
        long speed = 2;
        int lives =3;

        Waka waka = new Waka(x, y, sprite, speed, lives);
        assertNotNull(waka);
        
    }
   
    @Test
    //Testing if the waka can move successfully to the right
    public void moveWakaSuccessfullRight(){
        
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Waka waka = app.getWaka();

        for (int i = 0; i<5;i++){
            waka.logic(39, app, map);
        }
        //moving waka to the right and checking if it has registed there
        assertNotEquals(202, app.getWaka().getXc());
        assertEquals(314, app.getWaka().getYc());
        waka.setXc(202);
        waka.setYc(314);
    }

    @Test
    //Testing if the waka can move successfully to the left
    public void moveWakaSuccessfullLeft(){
        
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Waka waka = app.getWaka();

        for (int i = 0; i<5;i++){
            waka.logic(37, app, map);
        }
        //moving waka to the right and checking if it has registed there
        assertEquals(197, app.getWaka().getXc());
        assertEquals(314, app.getWaka().getYc());
        waka.setXc(202);
        waka.setYc(314);
    }

    @Test
    //Testing if the waka can hit a wall and not keep moving
    public void moveWakaUnsuccessfully(){
        
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Waka waka = app.getWaka();

        for (int i = 0; i<100;i++){
            waka.logic(40, app, map);
        }
        //moving waka to the right and checking if it has registed there
        Boolean hit = waka.hit;
        assertFalse(hit);
        waka.setXc(202);
        waka.setYc(314);
    }

   
    @Test
    //testing if the hitswall function works correctly when hit
    public void actuallyHitsWall(){
        App app = new App();
        PApplet.runSketch(new String[] {""},app);
        app.delay(1000);
        app.setup();
        Entity[][] map = app.getMap();
        Waka waka = app.getWaka();

        for (int i = 0; i<500;i++){
            waka.logic(37, app, map);
        }
        //testing if the hit boolean returns as true
        Boolean hit = waka.hit;
        assertTrue(hit);
        waka.setXc(202);
        waka.setYc(314);
    }



    @Test
    public void onFruit(){
        
    }
    @Test
    public void notOnFruit(){
        
    }
    @Test
    public void hitsGhost(){
        
    }
    @Test
    public void doesntHitGhost(){
         
    }






}