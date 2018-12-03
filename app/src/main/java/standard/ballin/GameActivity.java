package standard.ballin;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;

import standard.ballin.levelstrategies.Level1Strategy;
import standard.ballin.levelstrategies.LevelStrategy;

/**
 * GameActivity class: activity that is opened when the game is launched.
 *
 * @author Frederik Nielsen
 * Frederik Nielsen: Main auther.
 */
public class GameActivity extends FragmentActivity {
    private static Game game;
    private SensorManager sensorManager;
    private Chronometer chronometer;
    private WindowManager windowManager;
    private LevelStrategy levelStrategy;

    /**
     * Gets the current game.
     * @return current game
     */
    public static Game getGame() {
        return game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        switch(getIntent().getIntExtra("selectedLevel", 0)) {
            case 1: {
                levelStrategy = new Level1Strategy(this);
                break;
            }

            default: {
                break;
            }
        }
        game = new Game(this, levelStrategy);

        //TODO Set background of game
        game.setBackgroundResource(R.drawable.ic_launcher_background);
        game.newGame();
        setContentView(game);
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MusicPlay.getPlayState()) {
            MusicPlay.resumeAudio();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicPlay.pauseAudio();

        game.stopGame();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void finishDialog(int stars) {
        FinishDialog finish = new FinishDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("stars", stars);
        finish.showDialog(this, bundle);
    }

    public void pauseDialog() {
        PauseDialog pause = new PauseDialog();
        pause.showDialog(this);

    }

    public void defeatDialog() {
        DefeatDialog defeat = new DefeatDialog();
        defeat.showDialog(this);
    }

    public void gameDialog() {
        GameDialog game = new GameDialog(1, 2, 3);
        game.showDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        game.stopGame();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
