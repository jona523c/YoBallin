package standard.ballin.levelstrategies;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import standard.ballin.Wall;

/**
 * Implementation of level 1.
 *
 * @author Frederik Nielsen
 */
public class Level1Strategy implements LevelStrategy {
    private Wall wall;
    private Context context;
    private ArrayList<Wall> walls;

    public Level1Strategy(Context context) {
        this.context = context;
        wall = new Wall(400, 1000, 100, 500);

        walls.add(wall);
    }

    public Wall getWall() {
        return wall;
    }

    @Override
    public int calculateStars(int time) {
        if(time<5) return 3;
        else if(time<10) return 2;
        else return 1;
    }

    @Override
    public String getLevel() {
        return "1";
    }

    @Override
    public int getStars() {
        SharedPreferences sharedPref = context.getSharedPreferences("stars", Context.MODE_PRIVATE);
        return sharedPref.getInt("1", 0);
    }

    @Override
    public ArrayList<Wall> getWalls() {
        return walls;
    }

}
