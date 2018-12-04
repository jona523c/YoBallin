package standard.ballin.levelstrategies;

import java.util.ArrayList;

import standard.ballin.Wall;

/**
 * Interface of LevelStrategy
 *
 * @author Frederik Nielsen
 */
public interface LevelStrategy {

    public Wall getWall();

    public int calculateStars(int time);

    public String getLevel();

    public int getStars();

    public ArrayList<Wall> getWalls();

    public int getTime2();

    public int getTime3();
}
