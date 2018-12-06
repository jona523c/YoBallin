package standard.ballin.levelstrategies;

import java.util.ArrayList;

import standard.ballin.Wall;

/**
 * Interface of LevelStrategy
 *
 * @author Frederik Nielsen
 */
public interface LevelStrategy {

    /**
     *  Calculates the stars for the level.
     */
    public int calculateStars(int time);

    /**
     *  Gets the level.
     */
    public String getLevel();

    /**
     *  gets stars earned on the level.
     */
    public int getStars();

    /**
     *  gets all walls for the level.
     */
    public ArrayList<Wall> getWalls();

    /**
     *  gets time needed for earning 2 stars.
     */
    public int getTime2();

    /**
     *  gets time needed for earning 3 stars.
     */
    public int getTime3();
}
