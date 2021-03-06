package standard.ballin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.hardware.*;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

import standard.ballin.levelstrategies.LevelStrategy;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.StrictMath.abs;

/**
 *  Game class: The actual game, which is the content view when GameActivity is opened.
 * @author Frederik Nielsen
 * @author Jonas Madsen
 *
 * Frederik Nielsen: Main author
 * Jonas Madsen: Changed timer to display seconds
 */
public class Game extends ConstraintLayout implements SensorEventListener {
    private Sensor accelerometer;
    private Ball ball;
    private ImageView header, restart, pause, finishline;
    private TextView levelText;
    private Chronometer timer;
    private Display display;
    private Rect rect;
    private ArrayList<Rect> rectWalls;
    private LevelStrategy levelStrategy;
    private Paint paint, paintWall;
    private SensorManager sensorManager;
    private int ballWidth, ballHeight;
    private static final float ballDiameter = 0.004f;
    private float scaleHeightFromDpi, scaleWidthFromDpi, heightDpi, widthDpi, sensorY, sensorX, currentX, currentY, horizontalCeiling, verticalCeiling;
    private long lastStamp;
    private boolean sensorUpdatedEnabled = true;
    private long start, lastPause;
    private long before;
    private long now = System.currentTimeMillis();
    private int stars;
    private int seconds;
    private boolean firsttime = true;
    private boolean gameover = false;

    /**
     * Initialize a game object, which contains the level that is selected.
     * @param context context calling the game
     * @param levelStrategy the Level to be used in this Game
     */
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
        setupLayout();

        scaleHeightFromDpi = heightDpi / 0.02f;
        scaleWidthFromDpi = widthDpi / 0.02f;
        ballWidth =  (int) (ballDiameter * scaleWidthFromDpi);
        ballHeight = (int) (ballDiameter * scaleHeightFromDpi);
        ball.setBackgroundResource(R.drawable.ball);
        ball.setLayerType(LAYER_TYPE_HARDWARE, null);
        addView(ball, new ViewGroup.LayoutParams(ballWidth, ballHeight));

        initializeDrawings();
    }

    /**
     * Initialize drawings, which doesn't change throughout the games lifecycle.
     */
    private void initializeDrawings() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paintWall = new Paint();
        paintWall.setColor(getResources().getColor(R.color.light_brown));

        rectWalls = new ArrayList<>();
        for(Wall w : levelStrategy.getWalls()) {
            w.scalePosX(scaleWidthFromDpi/21431.25f);
            w.scalePosY(scaleHeightFromDpi/21578.752f);
            w.scaleWallHeight(scaleHeightFromDpi/21578.752f);
            w.scaleWallWidth(scaleWidthFromDpi/21431.25f);
            rectWalls.add(new Rect(w.getPosX(), w.getPosY(), w.getWallWidth() + w.getPosX(), w.getWallHeight() + w.getPosY()));
        }
    }

    /**
     * Setup the ConstraintLayout of Game, which contains ImageViews.
     */
    private void setupLayout() {
        header = new ImageView(getContext());
        header.setId(R.id.header);
        header.setImageResource(R.drawable.header);
        LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, getResources().getDisplayMetrics()));
        layout.setMargins(layout.leftMargin, layout.topMargin, layout.rightMargin, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        header.setLayoutParams(layout);
        header.setScaleType(ImageView.ScaleType.CENTER_CROP);

        restart = new ImageView(getContext());
        restart.setImageResource(R.mipmap.restart_button);
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
        pause.setImageResource(R.mipmap.pause_button);
        pause.setId(R.id.pause_button);
        pause.setScaleType(ImageView.ScaleType.FIT_END);
        pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPause = SystemClock.elapsedRealtime();
                stopGame();
                timer.stop();
                ((GameActivity) getContext()).pauseDialog();
            }
        });
        LayoutParams layoutPause = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        pause.setLayoutParams(layoutPause);

        finishline = new ImageView(getContext());
        finishline.setId(R.id.finishline);
        finishline.setImageResource(R.drawable.finishline);
        LayoutParams layoutFinishline = new LayoutParams(LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()));
        finishline.setLayoutParams(layoutFinishline);

        timer = new Chronometer(getContext());
        timer.setId(R.id.timer);
        timer.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                seconds++;
            }
        });

        levelText = new TextView(getContext());
        levelText.setId(R.id.levelText);
        levelText.setText(getContext().getString(R.string.level)+" "+levelStrategy.getLevel());
        levelText.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        this.addView(header,0);
        this.addView(restart,1);
        this.addView(pause, 2);
        this.addView(finishline, 3);
        this.addView(timer,4);
        this.addView(levelText, 5);
        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(pause.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
        set.connect(pause.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
        set.setHorizontalBias(pause.getId(),1);

        set.connect(finishline.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,0);
        set.connect(finishline.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0);
        set.setVerticalBias(finishline.getId(),1);

        set.connect(timer.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
        set.connect(timer.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
        set.setHorizontalBias(timer.getId(),0.5f);

        set.connect(levelText.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID, ConstraintSet.TOP,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        set.connect(levelText.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
        set.connect(levelText.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
        set.setHorizontalBias(levelText.getId(),0.5f);

        set.applyTo(this);
    }

    /**
     * Checks if the ball collides with the ceiling.
     */
    public void ceilingCollisions() {
        final float xmax = horizontalCeiling;
        final float ymax = verticalCeiling;
        final float x = ball.getPosX();
        final float y = ball.getPosY();
        if (x > xmax) {
            ball.setPosX(xmax);
        } else if (x < -xmax) {
            ball.setPosX(-xmax);
        }
        if (y > ymax) {
            ball.setPosY(ymax);
        } else if (y < -ymax) {
            ball.setPosY(-ymax);
        }
    }


    /**
     * Updates the ball position and calls to see if ball is collided with the ceiling.
     * @param x The measured (by the sensor) acceleration in x-axis
     * @param y The measured (by the sensor) acceleration in y-axis
     * @param stamp The time this update was requested (in milliseconds)
     */
    public void updateBall(float x, float y, long stamp) {
        if(lastStamp != 0) {
            // Calculates time inbetween stamps (into seconds)
            float t = (stamp - lastStamp) / 1000f;
            ball.computePosition(x,y,t);
        }
        lastStamp = stamp;

        ceilingCollisions();

    }

    /**
     *  Calls the finishDialog method in GameActivity
     */
    private void finishDialog() {
        ((GameActivity) getContext()).finishDialog(stars, Integer.parseInt(levelStrategy.getLevel()));
    }

    /**
     *  Calls the gameDialog method in GameActivity
     */
    private void gameDialog() {
        ((GameActivity) getContext()).gameDialog();
    }

    /**
     * Gets the balls X position.
     * @return returns balls X position
     */
    public float getPosX() {
        return ball.getPosX();
    }

    /**
     * Gets the balls Y position.
     * @return returns balls Y position
     */
    public float getPosY() {
        return ball.getPosY();
    }

    /**
     * Returns true if the ball intersects the given rectangle.
     * @param ball The ball
     * @param rect Rectangle to check intersection with ball
     * @return true if ball intersects, false if not
     */
    public boolean intersects(Ball ball, Rect rect) {
        float circleX = abs((ball.getTranslationX()+ballWidth/2) - rect.centerX());
        float circleY = abs((ball.getTranslationY()+ballHeight/2) - rect.centerY());

        if (circleX > (rect.width()/2 + ballWidth/2)) { return false; }
        if (circleY > (rect.height()/2 + ballHeight/2)) { return false; }

        if (circleX <= (rect.width()/2)) { return true; }
        if (circleY <= (rect.height()/2)) { return true; }

        float cornerDistance_sq = (circleX - rect.width()/2)*(circleX - rect.width()/2) + (circleY - rect.height()/2)*(circleY - rect.height()/2);

        return (cornerDistance_sq <= (ballWidth) || cornerDistance_sq <= (ballHeight));
    }

    /**
     * This draws the objects to the canvas and updates the ball position. Gets called everytime a view is repositioned on the screen.
     * @param canvas The canvas object of the ConstraintLayout given by Android
     */
    public void onDraw(Canvas canvas) {
        if(firsttime) {
            gameDialog();
            timer.stop();
            stopGame();
        firsttime = false;}

        rect = new Rect(finishline.getLeft(), finishline.getTop(), finishline.getRight() , finishline.getBottom());
        for(Rect r : rectWalls) {
          canvas.drawRect(r, paintWall);
        }

        if(rect.contains((int) ball.getTranslationX(), (int) ball.getTranslationY())) {
            SoundPlayer.playSound(getContext(), SoundPlayer.VICTORY);
            stopGame();
            timer.stop();
            Log.d("Game", "Time before star calculation was: "+(seconds-4));
            stars = levelStrategy.calculateStars(seconds-4);
            Log.d("Game", "Stars for this level was calculated to: "+stars);
            SharedPreferences sharedPref = getContext().getSharedPreferences("stars", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if (sharedPref.getInt(levelStrategy.getLevel(), 0) < stars) {
                editor.putInt(levelStrategy.getLevel(), stars);
                editor.apply();
            }
            finishDialog();
            return;
        }

        if(!gameover) {
            for (Rect r : rectWalls) {
                if (intersects(ball, r)) {
                SoundPlayer.playSound(getContext(), SoundPlayer.DEFEAT);
                stopGame();
                timer.stop();
                defeatDialog();
                gameover = true;
                }
            }
         }
            if(sensorUpdatedEnabled) {before = now; }
            if(!sensorUpdatedEnabled) {
               start = System.currentTimeMillis()-before;
            }
            now = System.currentTimeMillis()- start;
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

    /**
     *  Calls the defeatDialog method in GameActivity
     */
    private void defeatDialog() {
        ((GameActivity) getContext()).defeatDialog();
    }

    /**
     * Called when there is a new sensor event.
     * @param event The sensor event
     */
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

    /**
     * Called when the accuracy of the registered sensor has changed.
     * @param sensor The ID of the sensor being monitored
     * @param accuracy The new accuracy of this sensor.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Called when the size of this view has changed.
     * @param w Current width of this view
     * @param h Current height of this view
     * @param oldw Old width of this view
     * @param oldh Old height of this view
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        currentX = (w - ballWidth) * 0.5f;
        currentY = (h - ballHeight) * 0.5f;
        horizontalCeiling = ((w / scaleWidthFromDpi - ballDiameter) * 0.5f);
        verticalCeiling = ((h / scaleHeightFromDpi - ballDiameter) * 0.5f);
    }

    /**
     * Stops the Game by unregistering the listener and keeping track of time paused.
     */
    public void stopGame() {
        sensorUpdatedEnabled = false;
        sensorManager.unregisterListener(this);
        start += System.currentTimeMillis()-before;
        lastPause = SystemClock.elapsedRealtime();
    }


    /**
     * Starts the game by registering the listener.
     */
    public void startGame() {
        timer.setBase(timer.getBase() + SystemClock.elapsedRealtime() - lastPause);
        timer.start();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorUpdatedEnabled = true;
    }

    /**
     * Restarts the game.
     */
    public void restartGame() {
        resetChronometer();
        seconds = 0;
        gameover = false;
        ball.setPosX(-0.006f);
        ball.setPosY(-0.03f);
        startGame();

    }

    /**
     *  Resets the timer.
     */
    public void resetChronometer() {
        timer.setBase(0);
        lastPause = 0;
    }

    /**
     *  Starts a new game. Only used when the game is first created.
     */
    public void newGame() {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorUpdatedEnabled = true;
    }
}
