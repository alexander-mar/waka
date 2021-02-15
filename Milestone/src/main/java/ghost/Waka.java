package ghost;
import processing.core.PImage;


public class Waka extends Entity {

    public Waka(int x, int y, PImage sprite, long speed, int lives) {
        super(x, y, sprite);
        xv = (int) (-1 * speed);
        // this.xv = 0;

        this.x = x;
        this.y = y;
        yv = 0;
        this.lives = lives;
        this.speed = (int) speed;
    }

    String pUp = "src/main/resources/playerUp.png";
    String pDo = "src/main/resources/playerDown.png";
    String pL = "src/main/resources/playerLeft.png";
    String pR = "src/main/resources/playerRight.png";
    String pCl = "src/main/resources/playerClosed.png";
    String relevant = "src/main/resources/playerClosed.png";
    Integer previouskey = 0;
    Integer xv;
    Integer yv;

    //storing the starting location
    int ogX = x;
    int ogY = y;

    int score = 0;
    int speed;
    Boolean VerMov = true;
    Boolean hit = false;
    boolean open = false;
    int i = 0;
    int lives;

    PImage relevantSprite = sprite;
    PImage nextSprite;
    PImage prevSprite;

    int nextXv;
    int nextYv;
    int prevXv;
    int prevYv;
    Boolean nextIntersection = false;
    Boolean futureMove;
    Boolean prevMove;

    // game flags
    boolean GAME_OVER_LOSE = false;
    boolean GAME_OVER_WIN = false;
    boolean GAME_NOT_OVER = true;

    /**
     * Logic of movement, done every time key is pressed
     * @param key
     * @param app
     * @param layout
     * @return true if a new move has been stored
     */
    
    public Boolean logic(int key, App app, Entity[][] layout) {
        // current state info
        //returns true if next direction is updated

        prevXv = xv;
        prevYv = yv;
        prevSprite = relevantSprite;
        prevMove = VerMov;

        // vertical movement
        // up
        if (key == 38) {

            this.xv =0;
            this.yv =(int) -this.speed;
            VerMov = true;
            relevantSprite = app.loadImage(pUp);
            //relevant = pUp;
            
        }
        
        // down
        else if (key == 40) {
            this.xv =0;
            this.yv =(int) this.speed;
            VerMov = true;
            relevantSprite = app.loadImage(pDo);
            //relevant = pDo;

        }

        // horizontal movement
        // left
        else if (key == 37) {

            // need to get this to speed not 1
            this.xv = -(int)this.speed;
            this.yv = 0;
            VerMov = false;
            relevantSprite = app.loadImage(pL);
            //relevant = pL;

        }
        // right
        else if (key == 39) {

            this.xv = (int)this.speed;
            this.yv = 0;
            VerMov = false;
            relevantSprite = app.loadImage(pR);
            //relevant = pR;

        }

        // checking if collision will occur w movement
        nextIntersection = false;
        hit = false;
        hitsWall(layout, app);

        // if there is a collision, store movement as a future move
        if (hit == true) {

            // set what the future state is gonna be
            nextIntersection = true;
            nextXv = xv;
            nextYv = yv;
            nextSprite = relevantSprite;
            futureMove = VerMov;

            // keep the xv and yv as they were before we tested
            VerMov = prevMove;
            this.xv = prevXv;
            this.yv = prevYv;
            relevantSprite = prevSprite;
            move(layout, app);
            return true;

        } else {
            nextIntersection = false;
            move(layout, app);
            return false;
            
            
        }
        

    }

    /**
     * Actually moves waka, done every frame
     * @param layout
     * @param app
     * @return true if move has been made
     */
    public Boolean move(Entity[][] layout, App app) {
        // gets called every frame
        // opening closing waka feature
        
        
        i += 1;
        if (i == 16) {
            i = 0;
        }
        if (i <= 8) {
            this.sprite = app.loadImage(pCl);
        }
        if (i > 8 && i <= 16) {
            sprite = relevantSprite;
        }


        

        // checking if w next state a collision occurs, if not then apply it
        if ((this.x != ogX || this.y != ogY) && nextIntersection) {

            xv = nextXv;
            yv = nextYv;
            
            hit = false;
            hitsWall(layout, app);

            //if the move were to cause a collision, send through og state
            if (hit == true) {

                this.xv = prevXv;
                this.yv = prevYv;
                VerMov = prevMove;
                relevantSprite = prevSprite;


            //if it doesnt cause a collision, send the new state
            } else if (hit == false) {
                
                VerMov = futureMove;
                relevantSprite = nextSprite;
            }

        }
        
        hit = false;
        hitsWall(layout, app);
        

        // if not hitting wall, make the move
        if (hit == false) {
            //sprite = relevantSprite;
            if (VerMov == true) {

                this.y += yv;
            } else {
                
                this.x += xv;
            }

            // if hitting a fruit
            onFruit(layout, app);
            // if hitting a ghost
            hitsGhost(layout, app);
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Collision checker function
     * @param layout
     * @param app
     * @return true if a collision occurs
     */
    public Boolean hitsWall(Entity[][] layout, App app) {
        // should deal with sprites as well

        hit = false;

        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {

                // need to store move as well to use at next available intersection

                // need to take into account momentum
                if (layout[i][j] instanceof Obstacle) {

                    int wallLeft = layout[i][j].getXc();
                    int wallRight = layout[i][j].getXc() + 16;
                    int wallTop = layout[i][j].getYc();
                    int wallBottom = layout[i][j].getYc() + 16;

                    int wakaLeft = this.x+6 ;
                    int wakaRight = (this.x) + 16 + 6;
                    int wakaTop = this.y + 6;
                    int wakaBottom = (this.y) + 16 + 6;

                    if (wallLeft < wakaRight + xv && wallRight > wakaLeft + xv && wallTop < wakaBottom + yv
                            && wallBottom > wakaTop + yv) {

                        // need to store move to use at next intersection

                        if (VerMov == false) {

                            // moving to the right
                            if (xv > 0) {
                                hit = true;
                                break;

                            }

                            // moving to the left
                            else {
                                hit = true;
                                break;
                            }
                        }

                        else {
                            // moving up
                            if (yv < 0) {
                                hit = true;
                                break;
                            }

                            // moving down
                            else {
                                hit = true;
                                break;
                            }

                        }

                    }

                }

            }

        }
        return hit;

    }

    /**
     * Checking if we have eaten a fruit
     * @param layout
     * @param app
     * @return true if on fruit
     */
    public Boolean onFruit(Entity[][] layout, App app) {
        hit = false;
        // NEED TO SORT SCORING MECHANISM OUT
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {

                if (layout[i][j] instanceof Fruit) {

                    int fruitLeft = layout[i][j].getXc();
                    int fruitRight = layout[i][j].getXc() + 16;
                    int fruitTop = layout[i][j].getYc();
                    int fruitBottom = layout[i][j].getYc() + 16;

                    int wakaLeft = this.x + 6;
                    int wakaRight = (this.x) + 16 + 6;
                    int wakaTop = this.y + 6;
                    int wakaBottom = (this.y) + 16 + 6;

                    if (fruitLeft < wakaRight + xv && fruitRight > wakaLeft + xv && fruitTop < wakaBottom + yv
                            && fruitBottom > wakaTop + yv) {

                        layout[i][j] = null;
                        score += 1;
                        hit = true;

                        // if (score == layout[i][j].get){
                        // GAME_OVER_WIN = true;
                        // GAME_NOT_OVER = false;
                        // }

                    }

                }
            }
        }
        return hit;
    }

    // deals with contact with ghost
    /**
     * Checks if waka has hit ghost
     * @param layout
     * @param app
     * @return true if has hit
     */

    public Boolean hitsGhost(Entity[][] layout, App app) {
        hit = false;
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {

                if (layout[i][j] instanceof Ghost) {

                    int ghostLeft = layout[i][j].getXc();
                    int ghostRight = layout[i][j].getXc() + 16;
                    int ghostTop = layout[i][j].getYc();
                    int ghostBottom = layout[i][j].getYc() + 16;

                    int wakaLeft = this.x + 6;
                    int wakaRight = (this.x) + 16 + 6;
                    int wakaTop = this.y + 6;
                    int wakaBottom = (this.y) + 16 + 6;

                    if (ghostLeft < wakaRight + xv && ghostRight > wakaLeft + xv && ghostTop < wakaBottom + yv
                            && ghostBottom > wakaTop + yv) {

                        lives -= 1;
                        if (lives == 0) {
                            GAME_OVER_LOSE = true;
                            GAME_NOT_OVER = false;
                        }
                        hit = true;
                        // move waka back to og position
                        this.x = ogX;
                        this.y = ogY;
                        break;

                    }
                }
            }
        }
        return hit;
    }
}


