package standard.ballin.levelstrategies;

import standard.ballin.Wall;

/**
 * Interface of LevelStrategy
 *
 * @author Frederik Nielsen
 */
public interface LevelStrategy {

    public Wall getWall();

    public int calculateStars(Long time);

    public String getLevel();
}
