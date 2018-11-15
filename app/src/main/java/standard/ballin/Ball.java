package standard.ballin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * This is the class which handles the ball. Its position and speed is contained.
 * new Position is computed taking its speed into account.
 */
public class Ball extends View {
    private float posX = -0.006f;
    private float posY = -0.04f;
    private float speedX, speedY;


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
     * @param x The position in x-axis
     * @param y The position in y-axis
     * @param t The time since last stamp
     *
     * computePosition calculates the new position of the ball. Using the speed and acceleration of the ball.
     */
    public void computePosition(float x, float y, float t) {
        float ax = -x/50;
        float ay = y/50;

        posX += speedX * t + ax * t * t / 2;
        posY += speedY * t + ax * t * t / 2;

        speedX += ax*t;
        speedY += ay*t;
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

    /**
     * @param x the new X speed of the ball.
     */
    public void setSpeedX(float x) {speedY = x; }

    /**
     * @param y the new Y speed of the ball.
     */
    public void setSpeedY(float y) {speedY = y; }
}
