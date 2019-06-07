package standard.ballin;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import standard.ballin.levelstrategies.*;

/**
 * GameActivity class: activity that is opened when the game is launched.
 *
 * @author Frederik Nielsen
 * Frederik Nielsen: Main auther.
 */
public class GameActivity extends FragmentActivity {
    private static Game game;
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
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        switch(getIntent().getIntExtra("selectedLevel", 0)) {
            case 1: {
                levelStrategy = new Level1Strategy(this);
                break;
            }
            case 2: {
                levelStrategy = new Level2Strategy(this);
                break;
            }
            case 3: {
                levelStrategy = new Level3Strategy(this);
                break;
            }
            case 4: {
                levelStrategy = new Level4Strategy(this);
                break;
            }
            case 5: {
                levelStrategy = new Level5Strategy(this);
                break;
            }
            case 6: {
                levelStrategy = new Level6Strategy(this);
                break;
            }
            case 7: {
                levelStrategy = new Level7Strategy(this);
                break;
            }
            case 8: {
                levelStrategy = new Level8Strategy(this);
                break;
            }
            case 9: {
                levelStrategy = new Level9Strategy(this);
                break;
            }
            case 10: {
                levelStrategy = new Level10Strategy(this);
                break;
            }
            case 11: {
                levelStrategy = new Level11Strategy(this);
                break;
            }
            case 12: {
                levelStrategy = new Level12Strategy(this);
                break;
            }
            case 13: {
                levelStrategy = new Level13Strategy(this);
                break;
            }
            case 14: {
                levelStrategy = new Level14Strategy(this);
                break;
            }
            case 15: {
                levelStrategy = new Level15Strategy(this);
                break;
            }
            case 16: {
                levelStrategy = new Level16Strategy(this);
                break;
            }
            case 17: {
                levelStrategy = new Level17Strategy(this);
                break;
            }
            case 18: {
                levelStrategy = new Level18Strategy(this);
                break;
            }
            case 19: {
                levelStrategy = new Level19Strategy(this);
                break;
            }
            case 20: {
                levelStrategy = new Level20Strategy(this);
                break;
            }
            default: {
                break;
            }
        }
        game = new Game(this, levelStrategy);

        game.setBackgroundColor(getResources().getColor(R.color.sand));
        game.newGame();
        setContentView(game);
    }

    /**
     * Gets the windowManager.
     * @return windowManager
     */
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicPlay.pauseAudio();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        game.stopGame();
    }

    /**
     * Opens the finishdialog.
     * @param stars stars gotten from the current level.
     * @param level current level being played.
     */
    public void finishDialog(int stars, int level) {
        FinishDialog finish = new FinishDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("stars", stars);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("level", level+1);
        finish.showDialog(this, bundle, bundle2);
    }

    /**
     * Opens the pauseDialog.
     */
    public void pauseDialog() {
        PauseDialog pause = new PauseDialog();
        pause.showDialog(this);

    }

    /**
     * Opens the defeatDialog.
     */
    public void defeatDialog() {
        DefeatDialog defeat = new DefeatDialog();
        defeat.showDialog(this);
    }

    /**
     * Opens the gameDialog.
     */
    public void gameDialog() {
        GameDialog game = new GameDialog(levelStrategy.getTime2(), levelStrategy.getTime3());
        game.showDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
