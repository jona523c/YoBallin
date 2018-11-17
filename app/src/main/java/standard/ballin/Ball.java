package standard.ballin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * This is the class which handles the ball. Its position and speed is contained.
 * new Position is computed taking its speed into account.
 *
 * @author Frederik Nielsen
 * Frederik Nielsen: Main auther.
 */
public class Ball extends View {
    private float posX = -0.006f;
    private float posY = -0.03f;



    public Ball(Context context) {
        super(context);
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public Ball(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public Ball(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * computePosition calculates the new position of the ball. Using the speed and acceleration of the ball.
     * @param x The measured (by the sensor) acceleration in x-axis
     * @param y The measured (by the sensor) acceleration in y-axis
     * @param t The time since last stamp (in sec)
     *
     */
    public void computePosition(float x, float y, float t) {

        //acceleration is modified to fit the game.
        float ax = -x/200f;
        float ay = y/200f;

        //Calculated to fit speed of movement.
        posX += ax*t;
        posY += ay*t;

    }

    /**
     * @return the X position of the ball.
     */
    public float getPosX() {
        return posX;
    }

    /**
     * @param x The new X position of the Ball.
     */
    public void setPosX(float x) {posX = x; }

    /**
     * @return The Y position of the ball.
     */
    public float getPosY() {
        return posY;
    }

    /**
     * @param y The new Y position of the ball.
     */
    public void setPosY(float y) {posY = y; }
}
