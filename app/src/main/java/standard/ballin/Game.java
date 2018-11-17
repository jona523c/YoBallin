package standard.ballin;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.*;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.*;
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
    private ImageView header, restart, pause, finishline;
    private Display display;
    private Rect rect, rectWall;
    private LevelStrategy levelStrategy;
    private Paint paint, paintWall;
    private SensorManager sensorManager;
    private int ballWidth, ballHeight;
    private float scale = getResources().getDisplayMetrics().density;
    private static final float ballDiameter = 0.005f;
    private float scaleHeightFromDpi, scaleWidthFromDpi, heightDpi, widthDpi, sensorY, sensorX, currentX, currentY, horizontalCeiling, verticalCeiling;
    private long lastStamp;
    private boolean sensorUpdatedEnabled = true;
    private float tempX = -0.006f;
    private float tempY = -0.03f;
    private long start;
    private long elapsed, before;

    public Game(Context context, LevelStrategy levelStrategy) {
        super(context);
        this.levelStrategy = levelStrategy;
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
        setupLayout();

        scaleHeightFromDpi = heightDpi / 0.0254f;
        scaleWidthFromDpi = widthDpi / 0.0254f;
        ballWidth =  (int) (ballDiameter * scaleWidthFromDpi+ 0.5f);
        ballHeight = (int) (ballDiameter * scaleHeightFromDpi + 0.5f);
        ball.setBackgroundResource(R.drawable.header);
        ball.setLayerType(LAYER_TYPE_HARDWARE, null);
        addView(ball, new ViewGroup.LayoutParams(ballWidth, ballHeight));

        initializeDrawings();
    }

    private void initializeDrawings() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paintWall = new Paint();
        paintWall.setColor(Color.GRAY);

        Wall w = levelStrategy.getWall();
        rectWall = new Rect(w.getPosX(), w.getPosY(), w.getWallWidth()+w.getPosX(), w.getWallHeight()+w.getPosY());

        // Cannot get finishline yet for some reason.
        //rect = new Rect(finishline.getLeft(), finishline.getTop(), finishline.getRight() , finishline.getBottom());

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
        LayoutParams layoutRestart = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        restart.setLayoutParams(layoutRestart);
        restart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

        pause = new ImageView(getContext());
        pause.setImageResource(R.mipmap.settings);
        pause.setId(R.id.pause_button);
        pause.setScaleType(ImageView.ScaleType.FIT_END);
        pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopGame();
                ((GameActivity) getContext()).pauseDialog();
            }
        });
        LayoutParams layoutPause = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        pause.setLayoutParams(layoutPause);

        finishline = new ImageView(getContext());
        finishline.setId(R.id.finishline);
        finishline.setBackgroundColor(Color.GREEN);
        LayoutParams layoutFinishline = new LayoutParams(LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()));
        finishline.setLayoutParams(layoutFinishline);


        this.addView(header,0);
        this.addView(restart,1);
        this.addView(pause, 2);
        this.addView(finishline, 3);
        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(pause.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
        set.connect(pause.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
        set.setHorizontalBias(pause.getId(),1);

        set.connect(finishline.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,0);
        set.connect(finishline.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0);
        set.setVerticalBias(finishline.getId(),1);

        set.applyTo(this);

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
        ((GameActivity) getContext()).finishDialog();
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
        // TODO graphical finishline instead of just green finishline.
        rect = new Rect(getViewById(R.id.finishline).getLeft(), getViewById(R.id.finishline).getTop(), getViewById(R.id.finishline).getRight() , getViewById(R.id.finishline).getBottom());
        canvas.drawRect(rectWall, paintWall);

        if(rect.contains((int) ball.getTranslationX(), (int) ball.getTranslationY())) {
            stopGame();
            finishDialog();
            return;
        }
        if(intersects(ball, rectWall)) {
            stopGame();
            defeatDialog();
            return;
        }
            if(sensorUpdatedEnabled) {before = System.currentTimeMillis(); }
            if(!sensorUpdatedEnabled) {
                start = System.currentTimeMillis()-before;
            }
            final long now = System.currentTimeMillis()- start;
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

    private void defeatDialog() {
        ((GameActivity) getContext()).defeatDialog();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        // sensor data recording
        if(getDisplay()==null) return;
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
        tempX = ball.getPosX();
        tempY = ball.getPosY();
        sensorUpdatedEnabled = false;
        sensorManager.unregisterListener(this);
    }

    public void startGame() {
        ball.setPosX(tempX);
        ball.setPosY(tempY);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorUpdatedEnabled = true;
        invalidate();
    }

    private void restartGame() {
        //TODO: Restart
        ball.setPosX(-0.006f);
        ball.setPosY(-0.03f);
        ball.setSpeedX(0);
        ball.setSpeedY(0);
    }
}
