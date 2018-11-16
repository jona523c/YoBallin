package standard.ballin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;

import standard.ballin.levelstrategies.Level1Strategy;
import standard.ballin.levelstrategies.LevelStrategy;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.StrictMath.abs;

// This is the game class.
public class Game extends ConstraintLayout implements SensorEventListener {
    private Sensor accelerometer;
    private Ball ball;
    private ImageView header, restart, pause;
    private Display display;
    private Canvas canvas;
    private Rect rect;
    private LevelStrategy levelStrategy;
    private Paint paint, paintWall;
    private SensorManager sensorManager;
    private int ballWidth, ballHeight;
    private float scale = getResources().getDisplayMetrics().density;
    private static final float ballDiameter = 0.005f;
    private float scaleHeightFromDpi, scaleWidthFromDpi, heightDpi, widthDpi, sensorY, sensorX, currentX, currentY, horizontalCeiling, verticalCeiling;
    private long lastStamp;

    public Game(Context context, LevelStrategy levelStrategy) {
        super(context);
        this.levelStrategy = levelStrategy;
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        setupLayout();

        header = findViewById(R.id.header);
        restart = findViewById(R.id.restart_button);

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

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paintWall = new Paint();
        paintWall.setColor(Color.GRAY);
    }

    private void setupLayout() {
        header = new ImageView(getContext());
        header.setId(R.id.header);
        header.setImageResource(R.drawable.header);
        LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, getResources().getDisplayMetrics()));
        layout.setMargins(layout.leftMargin, layout.topMargin, layout.rightMargin, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        header.setLayoutParams(layout);
        header.setScaleType(ImageView.ScaleType.CENTER_CROP);

        restart = new ImageView(getContext());
        restart.setImageResource(R.mipmap.sound_toggle_off);
        restart.setId(R.id.restart_button);
        // TODO: finish the button

        ConstraintLayout constraintLayout = this;
        ConstraintSet set = new ConstraintSet();
        constraintLayout.addView(header,0);
        constraintLayout.addView(restart,0);
    }

    private void createLevel() {

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
        if(lastStamp != 0) {
            float t = (stamp - lastStamp) / 1000.f;
            ball.computePosition(x,y,t);
        }
        lastStamp = stamp;

        ceilingCollisions();
    }

    private void finishDialog() {
        ((GameActivity) getContext()).confirmDialog();
    }

    public float getPosX() {
        return ball.getPosX();
    }

    public float getPosY() {
        return ball.getPosY();
    }

    public boolean intersects(Ball ball, Rect rect) {
        float circleX = abs((ball.getTranslationX()+ballWidth/2) - rect.centerX());
        float circleY = abs((ball.getTranslationY()+ballHeight/2) - rect.centerY());

        if (circleX > (rect.width()/2 + ballWidth/2)) { return false; }
        if (circleY > (rect.height()/2 + ballHeight/2)) { return false; }

        if (circleX <= (rect.width()/2)) { return true; }
        if (circleY <= (rect.height()/2)) { return true; }

        float cornerDistance_sq = (circleX - rect.width()/2)*(circleX - rect.width()/2) +
                (circleY - rect.height()/2)*(circleY - rect.height()/2);

        return (cornerDistance_sq <= (ballWidth) || cornerDistance_sq <= (ballHeight));
    }

    public void onDraw(Canvas canvas) {
        this.canvas = canvas;
        canvas.save();
        // TODO graphical finishline instead of just green finishline.
        rect = new Rect(getLeft(), getHeight()- (int) (0.0055*scaleHeightFromDpi), getWidth(), getHeight());
        canvas.drawRect(rect, paint);
        Wall w = levelStrategy.getWall();
        Rect rectWall = new Rect(w.getPosX(), w.getPosY(), w.getWallWidth()+w.getPosX(), w.getWallHeight()+w.getPosY());
        canvas.drawRect(rectWall, paintWall);

        if(rect.contains((int) ball.getTranslationX(), (int) ball.getTranslationY())) {
            stopGame();
            finishDialog();
            return;
        }
        if(intersects(ball, rectWall)) {
            stopGame();
            finishDialog();
            return;
        }
        super.onDraw(canvas);
        final long now = System.currentTimeMillis();
        final float sx = sensorX;
        final float sy = sensorY;

        updateBall(sx, sy, now);

        final float cx = currentX;
        final float cy = currentY;
        final float xs = scaleWidthFromDpi;
        final float ys = scaleHeightFromDpi;
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
