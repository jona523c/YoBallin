package standard.ballin.levelstrategies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import standard.ballin.Wall;

import static java.lang.System.out;

public class Level1Strategy implements LevelStrategy {
    private Wall wall;

    public Level1Strategy() {
        wall = new Wall(400, 1000, 100, 500);
    }

    public Wall getWall() {
        return wall;
    }

}
