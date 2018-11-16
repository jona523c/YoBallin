package standard.ballin;

import java.util.ArrayList;

import standard.ballin.levelstrategies.LevelStrategy;

public class Level {
    LevelStrategy levelStrategy;

    public Level(LevelStrategy levelStrategy) {
        this.levelStrategy = levelStrategy;
    }

    public void checkBallAndWallCollisions() {

    }
}
