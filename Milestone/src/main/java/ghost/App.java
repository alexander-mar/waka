package ghost;
import javax.swing.border.EmptyBorder;

import processing.core.PApplet;
import processing.core.PFont;

public class App extends PApplet {

    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;
    private gameParser par;
    private Entity[][] map;
    private Waka waka;
    private Chaser chaser;
    private Ambusher ambusher;
    private PFont gameFont;
    private Ignorant ignorant;
    private Whim whim;
    /**
     * creates an instance of the game parser
     */
    public App() {
        par = new gameParser(); // setting up your objects   
    }
    /**
     * Sets up some of the key features
     */
    public void setup() {
        this.gameFont = this.createFont("src/main/resources/PressStart2P-Regular.ttf", 25);
        frameRate(60);
        this.map = par.converter(this);
    }
    public Entity[][] getMap(){
        return map;
    }
    public Chaser getChaser(){
        return this.chaser;
    }
    public void setLocationWaka(Waka w) {
        this.waka = w;
    }

    public void setLocationChaser(Chaser chase) {
        this.chaser = chase;
    }
    public void setLocationAmbusher(Ambusher ambush) {
        this.ambusher= ambush;
    }
    public void setLocationIgnorant(Ignorant ignorant){
        this.ignorant = ignorant;

    }
    public void setLocationWhim(Whim whim){
        this.whim = whim;
    }
    /**
     * takes in key pressed and passes to waka logic
     */
    public void keyPressed() {
        waka.logic(keyCode, this, map);
        // chaser.logic(this, map);
    }
    /**
     * sets the width and height of window
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    /**
     * actually draws the map depending on outcome
     */
    public void draw() {
        // main loop here
        background(0, 0, 0);
        if (waka.GAME_OVER_LOSE) {
            background(0, 0, 0);
            
            this.background(0,0,0);
            this.textFont(this.gameFont);
            this.text("YOU LOSE", 100, 100);
            
        }
        if (waka.GAME_OVER_WIN || waka.score == Fruit.size) {
            background(0, 0, 0);
            this.textFont(this.gameFont);
            this.text("YOU WIN", 100, 100);
        }
        else if (waka.GAME_NOT_OVER) {
            waka.move(map, this);
            chaser.logic(this, map);
            chaser.move(this, map);
            ambusher.logic(this,map);
            ambusher.move(this, map);
            ignorant.logic(this, map);
            ignorant.move(this, map);
            whim.logic(this, map);
            whim.move(this, map);
            // print lives at bottom
            for (int i = 0; i < this.map.length; i += 1) {
                for (int j = 0; j < this.map[i].length; j += 1) {
                    if (this.map[i][j] != null) {
                        this.map[i][j].draw(this);
                    }
                }
            }
            for (int i = 0; i < waka.lives; i += 1) {
                int x = 16;
                x = i * x;
                image(loadImage("src/main/resources/playerRight.png"), x, 550.0f, 16.0f, 16.0f);
            }
        }
    }
    public Waka getWaka() {
        return waka;
    }

    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }

}