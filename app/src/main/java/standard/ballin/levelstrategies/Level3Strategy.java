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
public class Level3Strategy implements LevelStrategy {
    private Wall wall;
    private Context context;
    private ArrayList<Wall> walls;
    private int time3 = 5;
    private int time2 = 10;

    public Level3Strategy(Context context) {
        this.context = context;
        walls = new ArrayList<>();
        wall = new Wall(0, 500, 100, 950);
        walls.add(wall);
        walls.add(new Wall(700, 800, 400, 400));
        walls.add(new Wall(0, 800, 400, 400));
    }

    public Wall getWall() {
        return wall;
    }

    @Override
    public int calculateStars(int time) {
        if(time<time3) return 3;
        else if(time<time2) return 2;
        else return 1;
    }

    @Override
    public String getLevel() {
        return "3";
    }

    @Override
    public int getStars() {
        SharedPreferences sharedPref = context.getSharedPreferences("stars", Context.MODE_PRIVATE);
        return sharedPref.getInt("3", 0);
    }

    @Override
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    @Override
    public int getTime2() {
        return time2;
    }

    @Override
    public int getTime3() {
        return time3;
    }

}
