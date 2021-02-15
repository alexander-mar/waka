package ghost;

import processing.core.PImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.*;
import java.util.Map;

public class Ghost extends Entity {
   
    Boolean scatter = false;
    Boolean frightened = false;
    Entity target;
    //Entity targetCorner;
    Integer xv; // temp holders
    Integer yv;
    int ogX;
    int ogY;
    int prevXx;
    int prevYv;
    Boolean hit = false;
    Boolean VerMov = false;
    Boolean found = false;
    private long modeCount;
    private long[] modeLengths;
    private int modeIndex;
    Boolean chasing;
    Boolean alive = true;
    long speed;
    double timeElapsed;
   
    public Ghost(int x, int y, PImage sprite, long speed, long[] modeLengths) {
        super(x, y, sprite);
        this.modeLengths = modeLengths;
        this.modeCount = 0;
        // mode index is even = scatter, odd = chase, loops through modeLengths
        this.modeIndex = 0;
        this.chasing = false;
        this.alive = true;
        this.speed = speed;
        

    }
    /**
     * checks which mode the ghost should be in currently
     * @return
     */
    public boolean checkModes(){
        boolean currently;
        double temp = this.modeCount / 60;
        double timeElapsed = temp;
        double elapsed = this.modeLengths[this.modeIndex];

        if (timeElapsed >= elapsed) {
            this.modeIndex += 1;
            this.modeCount = 0;

            if (this.modeIndex >= this.modeLengths.length) {
                this.modeIndex = 0;
            }
        }
        if (this.modeIndex % 2 == 0) {
            this.chasing = false;
            currently = false;
    
        } else {
            this.chasing = true;
            currently = true;
        }
        this.modeCount += 1;

        return currently;

    }

    /**
     * gets the distances from the target depending on what next move is
     * @return
     */
    public HashMap<Integer, Integer> getDistances() {
        HashMap<Integer, Integer> distances = new HashMap<Integer, Integer>();
        
        // up
        distances.put(0,
                ((x - target.getXc()) * (x - target.getXc()) + (y - 1 - target.getYc()) * (y - 1 - target.getYc())));
        // down
        distances.put(1,
                
                ((x - target.getXc()) * (x - target.getXc()) + (y + 1 - target.getYc()) * (y + 1 - target.getYc())));
        // left
        distances.put(2,
                ((x - 1 - target.getXc()) * (x - 1 - target.getXc()) + (y - target.getYc()) * (y - target.getYc())));
        // right
        distances.put(3,
                ((x + 1 - target.getXc()) * (x + 1 - target.getXc()) + (y - target.getYc()) * (y - target.getYc())));

        // hashmap to list
        List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(distances.entrySet());

        // sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (HashMap.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        
        return temp;

    }

    /**
     * Selects the distance which doesnt cause a collision and is shortest
     * @param distances
     * @param layout
     * @param app
     * @param freePaths
     */
    public void pickDirection(HashMap<Integer, Integer> distances, Entity[][] layout, App app,ArrayList<Boolean> freePaths ) {
        
        for (Integer key : distances.keySet()) {
            

            if (key == 0 && freePaths.get(0) == true ) { // UP
                this.yv = -1;
                this.xv = 0;
                VerMov = true;
                break;

            } else if (key == 1 && freePaths.get(1) == true) { // DOWN
                this.yv = 1;
                this.xv = 0;
                VerMov = true;
                break;
                
            } else if (key == 2 && freePaths.get(2) == true) { // LEFT
                VerMov = false;
                this.xv = -1;
                this.yv = 0;
                break;
                
            } else if (key == 3 && freePaths.get(3) == true) { // RIGHT
                VerMov = false;
                this.xv = 1;
                this.yv = 0;
                break;
                
            }
  
            
        }
        

    }

    /**
     * Makes the ghost movement
     * @param app
     * @param layout
     */
    public void move(App app, Entity[][] layout) {

        if (alive){
       
            if (VerMov == true) {
                if (yv<0){
                    this.xv =0;
                    this.yv =(int) -this.speed;
                    //up
                    
                }
                else{
                    this.xv =0;
                    this.yv =(int) this.speed;
                }
                
            } else {
                if (xv >0){
                    this.xv = (int)this.speed;
                    this.yv = 0;
                }
                else{
                    this.xv = -(int)this.speed;
                    this.yv = 0;
                }
                
            }
            
            
            
         }
    }

    /**
     * Determines if at an intersection
     * @param layout
     * @param app
     * @return true if at an intersect
     */
    public boolean isIntersect(Entity[] [] layout, App app){
        String can = "can go: ";
        int xPos = Math.floorDiv(x+5, 16);
        int yPos = Math.floorDiv(y+5, 16);
        int count = 0;      //left, right, up, down
        Entity[] around =  { layout[yPos][xPos-1], layout[yPos][xPos+1], layout[yPos-1][xPos], layout[yPos+1][xPos]};
        int i = 0;
        for (Entity place: around){
            if (place == null||place instanceof Fruit){
                //just for testing
                count +=1;
                if (i ==0){
                    can += "left";
                }
                if (i ==1){
                    can += "right";
                }
                if (i ==2){
                    can += "up";
                }
                if (i ==3){
                    can += "down";
                }
               

            }
            else{
                
            }
            i+=1;

        }
        
        if (around[0]==null && around[2] ==null||around[1]==null && around[3] ==null||
            around[0]==null && around[3] ==null||around[1]==null && around[2]==null){
            count +=1;
        }
        if (hit == true){ // if hitting a wall
            count +=1;
        }
       
        if (count > 2){
            
            return true;
        }
        else{
            return false;
        }
        
    }

    /**
     * Checks which directions are free for the next move
     * @param layout
     * @param app
     * @returns a list of booleans corresponding to the directions
     */
    public ArrayList<Boolean> checkFreePath(Entity[][] layout, App app) {

        ArrayList<Boolean> freePaths = new ArrayList<Boolean>();

        // UP DOWN LEFT RIGHT
        Boolean leftFree = true;
        Boolean rightFree = true;
        Boolean downFree = true;
        Boolean upFree = true;

        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {

                // need to store move as well to use at next available intersection

                // need to take into account momentum
                if (layout[i][j] instanceof Obstacle) {

                    int ghostLeft = this.x+6;
                    int ghostRight = this.x + 6+16;
                    int ghostTop = this.y+5;
                    int ghostBottom = this.y + 5+16;

                    int wallLeft = layout[i][j].getXc();
                    int wallRight = layout[i][j].getXc() + 16;
                    int wallTop = layout[i][j].getYc();
                    int wallBottom = layout[i][j].getYc() + 16;

                    // checking if a movement in a specific direction is allowed
                    // up
                    if (wallLeft < ghostRight
                        && wallRight > ghostLeft
                        && wallTop < ghostBottom - 1
                        && wallBottom > ghostTop - 1) {
                            upFree = false;
                    }

                    // down
                    if (wallLeft < ghostRight
                        && wallRight > ghostLeft
                        && wallTop < ghostBottom + 1
                        && wallBottom > ghostTop + 1) {
                            downFree = false;
                    }
                    
                    //left
                    if (wallLeft < ghostRight - 1
                        && wallRight > ghostLeft - 1
                        && wallTop < ghostBottom
                        && wallBottom > ghostTop) {
                            leftFree = false;
                    }

                    //right
                    if (wallLeft < ghostRight + 1
                        && wallRight > ghostLeft + 1
                        && wallTop < ghostBottom
                        && wallBottom > ghostTop) {
                            rightFree= false;
                    }
                    
                }
                
               
                }
            }
            freePaths.add(upFree);
            freePaths.add(downFree);
            freePaths.add(leftFree);
            freePaths.add(rightFree);
            
            return freePaths;
    }


}

