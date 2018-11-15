package standard.ballin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

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

    public void computePosition(float x, float y, float t) {
        float ax = -x/50;
        float ay = y/50;

        posX += speedX * t + ax * t * t / 2;
        posY += speedY * t + ax * t * t / 2;

        speedX += ax*t;
        speedY += ay*t;
    }

    public float getPosX() {
        return posX;
    }
    public float getPosY() {
        return posY;
    }

    public void setPosX(float x) {posX = x; }
    public void setPosY(float y) {posY = y; }
    public void setSpeedX(float x) {speedY = x; }
    public void setSpeedY(float y) {speedY = y; }
}
