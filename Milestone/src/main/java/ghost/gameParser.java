package ghost;

import java.io.File;
import java.io.FileReader;
import processing.core.PApplet;
import org.json.simple.parser.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ghost.*;


public class gameParser{

    public Entity[][] map;

    /**
     * function that returns map
     * @return a 2d array of the map objects
     */
    public Entity[][] getMap(){
        return map;
    }
    
    public gameParser(){
    
    }
    String mapfile = "";
    int lives = 0;
    long speed = 0;
    int timeFreightend = 0;
    long[] modeLengths;

    /**
     * function that actually converts file to objects
     * @param app
     * @return a 2d array of the map objects
     */

    public Entity[][] converter(App app){
        
        //read json to get map name
        
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("config.json"));
            JSONObject jsonobj = (JSONObject)obj;
            this.mapfile = (String)jsonobj.get("map");
            this.speed = (long)jsonobj.get("speed");
            this.lives = Integer.parseInt(String.valueOf(jsonobj.get("lives")));
            this.timeFreightend = Integer.parseInt(String.valueOf(jsonobj.get("lives")));
            JSONArray modeLen = (JSONArray)jsonobj.get("modeLengths"); 
            this.modeLengths = new long[modeLen.size()];
            for(int i = 0;i<this.modeLengths.length;i++){
                this.modeLengths[i] = (long) modeLen.get(i);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        //can implement some error checking here
        String [] valid= {"0","1","2","3","4","5","6","7","p","g"};

        //reading in the map file
        ArrayList<String> lines = new ArrayList<>();
        try{
            File myobj = new File(mapfile);
            Scanner read = new Scanner(myobj);

            while (read.hasNextLine()){
                lines.add(read.nextLine());
            }
            read.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        //setting up 2 dimensional array the size of text file map
        String[] temp = lines.toArray(new String[lines.size()]);
        Entity[][] map = new Entity[temp.length][];

        //problem came from here
        for (int i = 0; i < temp.length; i+=1){
            map[i] = new Entity[temp[i].length()];
            for (int j = 0; j < temp[i].length(); j+=1){
                
                int adjJ = i*16; // the row x
                int adjI = j*16; // the column y

                char c = temp[i].charAt(j);

                switch(c){
                    case '0': //empty cell
                        break;
                    case '1':  
                        Obstacle hwall = new Obstacle(adjI, adjJ, app.loadImage("src/main/resources/horizontal.png"));
                        map[i][j] = hwall;
                        break;
                    case '2':
                        Obstacle vwall = new Obstacle(adjI, adjJ, app.loadImage("src/main/resources/vertical.png"));
                        map[i][j] = vwall;
                        break;
                    case '3':
                        Obstacle cWallUL = new Obstacle(adjI, adjJ, app.loadImage("src/main/resources/upLeft.png"));
                        map[i][j] = cWallUL;
                        break;
                    case '4':
                        Obstacle cWallUR = new Obstacle(adjI, adjJ, app.loadImage("src/main/resources/upRight.png"));
                        map[i][j] = cWallUR;
                        break;
                    case '5':
                        Obstacle cWallDL = new Obstacle(adjI, adjJ, app.loadImage("src/main/resources/downLeft.png"));
                        map[i][j] = cWallDL;
                        break;
                    case '6':
                        Obstacle cWallDR = new Obstacle(adjI, adjJ, app.loadImage("src/main/resources/downRight.png"));
                        map[i][j] = cWallDR;
                        break;
                    case '7':
                        Fruit fruit = new Fruit(adjI, adjJ, app.loadImage("src/main/resources/fruit.png"));
                        map[i][j] = fruit;
                        
                        break;
                    case '8':
                        Superfruit superFruit = new Superfruit(adjI, adjJ, app.loadImage("src/main/resources/superFruit.png"), timeFreightend);
                        map[i][j] = superFruit;
                        break;
                    case 'p':
                        Waka waka = new Waka(adjI-6, adjJ-6, app.loadImage("src/main/resources/playerRight.png"), speed, lives);
                        map[i][j] = waka;
                        app.setLocationWaka(waka);
                        break;
                    case 'c':
                        Chaser chaser = new Chaser(adjI-5, adjJ-5, app.loadImage("src/main/resources/chaser.png"), speed, modeLengths);
                        //-5
                        //Ghost ghost = new Ghost(adjI, adjJ-6, app.loadImage("src/main/resources/ghost.png"), speed);
                        map[i][j] = chaser;
                        app.setLocationChaser(chaser);
                        break;
                    case 'a':
                        Ambusher ambusher = new Ambusher(adjI-5, adjJ-5, app.loadImage("src/main/resources/ambusher.png"), speed, modeLengths);
                        //-5
                        //Ghost ghost = new Ghost(adjI, adjJ-6, app.loadImage("src/main/resources/ghost.png"), speed);
                        map[i][j] = ambusher;
                        app.setLocationAmbusher(ambusher);
                        break;
                    case 'i':
                        Ignorant ignorant = new Ignorant(adjI-5, adjJ-5, app.loadImage("src/main/resources/ignorant.png"), speed, modeLengths);
                        //-5
                        //Ghost ghost = new Ghost(adjI, adjJ-6, app.loadImage("src/main/resources/ghost.png"), speed);
                        map[i][j] = ignorant;
                        app.setLocationIgnorant(ignorant);
                        break;
                    case 'w':
                        Whim whim = new Whim(adjI-5, adjJ-5, app.loadImage("src/main/resources/whim.png"), speed, modeLengths);
                        //-5
                        //Ghost ghost = new Ghost(adjI, adjJ-6, app.loadImage("src/main/resources/ghost.png"), speed);
                        map[i][j] = whim;
                        app.setLocationWhim(whim);
                        break;
                }
            
            }
            
        }
    return map;
        

} 
}
