package standard.ballin.levelstrategies;

import java.util.ArrayList;

import standard.ballin.Wall;

public class Level1Strategy implements LevelStrategy {
    private Wall wall;

    public Level1Strategy() {
        wall = new Wall(400, 1000, 100, 500);
    }

    public Wall getWall() {
        return wall;
    }
}
