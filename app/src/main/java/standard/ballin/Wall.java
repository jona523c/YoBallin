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

    public void scaleWallWidth(float scale) {
        this.wallWidth = (int) (wallWidth*scale);
    }

    public void scaleWallHeight(float scale) {
        this.wallHeight = (int) (wallHeight*scale);
    }

    public void scalePosX(float scale) {
        this.posX = (int) (posX*scale);
    }

    public void scalePosY(float scale) {
        this.posY = (int) (posY*scale);
    }

    public int getWallWidth() {
        return wallWidth;
    }

    public int getWallHeight() {
        return wallHeight;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
