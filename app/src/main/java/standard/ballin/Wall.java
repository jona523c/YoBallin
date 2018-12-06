package standard.ballin;

import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

/**
 * A simple wall, which currently only is a rectangle.
 * @author Frederik Nielsen
 */
public class Wall {
    private int wallWidth, wallHeight;
    private int posX, posY;

    public Wall(int posX, int posY, int wallHeight, int wallWidth) {
        this.wallHeight = wallHeight;
        this.wallWidth = wallWidth;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     *  scales the wallWidth to fit all screens.
     */
    public void scaleWallWidth(float scale) {
        this.wallWidth = (int) (wallWidth*scale);
    }

    /**
     *  scales the wallHeight to fit all screens.
     */
    public void scaleWallHeight(float scale) {
        this.wallHeight = (int) (wallHeight*scale);
    }

    /**
     *  scales the posX to fit all screens.
     */
    public void scalePosX(float scale) {
        this.posX = (int) (getPosX()*scale);
    }

    /**
     *  scales the posY to fit all screens.
     */
    public void scalePosY(float scale) {
        this.posY = (int) (getPosY()*scale);
    }

    /**
     *  Gets the wallWidth.
     */
    public int getWallWidth() {
        return wallWidth;
    }

    /**
     *  Gets the wallHeight.
     */
    public int getWallHeight() {
        return wallHeight;
    }

    /**
     *  Gets the posX.
     */
    public int getPosX() {
        return posX;
    }

    /**
     *  Gets the posY.
     */
    public int getPosY() {
        return posY;
    }
}
