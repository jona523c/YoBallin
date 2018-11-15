package standard.ballin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import static android.content.Context.SENSOR_SERVICE;

// This is the game class.
public class Game extends FrameLayout implements SensorEventListener {
    private Sensor accelerometer;
    private Ball ball;
    private Display display;
    private SensorManager sensorManager;
    private int ballWidth, ballHeight;
    private static final float ballDiameter = 0.005f;
    private float scaleHeightFromDpi, scaleWidthFromDpi, heightDpi, widthDpi, sensorY, sensorX, currentX, currentY, horizontalCeiling, verticalCeiling;
    private long lastStamp;

    public Game(Context context) {
        super(context);
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display = ((Activity) context).getWindowManager().getDefaultDisplay();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ball = new Ball(getContext());
        widthDpi = displayMetrics.xdpi;
        heightDpi = displayMetrics.ydpi;
        scaleHeightFromDpi = heightDpi / 0.0254f;
        scaleWidthFromDpi = widthDpi / 0.0254f;
        ballWidth =  (int) (ballDiameter * scaleWidthFromDpi+ 0.5f);
        ballHeight = (int) (ballDiameter * scaleHeightFromDpi + 0.5f);
        ball.setBackgroundResource(R.drawable.header);
        ball.setLayerType(LAYER_TYPE_HARDWARE, null);
        addView(ball, new ViewGroup.LayoutParams(ballWidth, ballHeight));
    }
    public void ceilingCollisions() {
        final float xmax = horizontalCeiling;
        final float ymax = verticalCeiling;
        final float x = ball.getPosX();
        final float y = ball.getPosY();
        if (x > xmax) {
            ball.setPosX(xmax);
            ball.setSpeedX(0);
        } else if (x < -xmax) {
            ball.setPosX(-xmax);
            ball.setSpeedX(0);
        }
        if (y > ymax) {
            ball.setPosY(ymax);
            ball.setSpeedY(0);
        } else if (y < -ymax) {
            ball.setPosY(-ymax);
            ball.setSpeedY(0);
        }
    }


    public void updateBall(float x, float y, long stamp) {
        long st = stamp;
        if(lastStamp != 0) {
            float t = (st - lastStamp) / 1000.f;
            ball.computePosition(x,y,t);
        }
        lastStamp = st;

        ceilingCollisions();
    }

    public float getPosX() {
        return ball.getPosX();
    }

    public float getPosY() {
        return ball.getPosY();
    }

    public void onDraw(Canvas canvas) {
        /*
         * Compute the new position of our object, based on accelerometer
         * data and present time.
         */
        final long now = System.currentTimeMillis();
        final float sx = sensorX;
        final float sy = sensorY;

        updateBall(sx, sy, now);

        final float cx = currentX;
        final float cy = currentY;
        final float xs = scaleWidthFromDpi;
        final float ys = scaleHeightFromDpi;
            /*
             * We transform the canvas so that the coordinate system matches
             * the sensors coordinate system with the origin in the center
             * of the screen and the unit is the meter.
             */
            final float x = cx + getPosX() * xs;
            final float y = cy + getPosY() * ys;
            ball.setTranslationX(x);
            ball.setTranslationY(y);
        invalidate();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        // sensor data recording
        if(getDisplay().getRotation()!= Surface.ROTATION_0) return;
        sensorY = event.values[1];
        sensorX = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        currentX = (w - ballWidth) * 0.5f;
        currentY = (h - ballHeight) * 0.5f;
        horizontalCeiling = ((w / scaleWidthFromDpi - ballDiameter) * 0.5f);
        verticalCeiling = ((h / scaleHeightFromDpi - ballDiameter) * 0.5f);
    }

    public void stopGame() {
        sensorManager.unregisterListener(this);
    }

    public void startGame() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
}
