package standard.ballin.levelstrategies;

import standard.ballin.Wall;

/**
 * Implementation of level 1.
 *
 * @author Frederik Nielsen
 */
public class Level1Strategy implements LevelStrategy {
    private Wall wall;

    public Level1Strategy() {
        wall = new Wall(400, 1000, 100, 500);
    }

    public Wall getWall() {
        return wall;
    }

}
