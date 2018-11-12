import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.WindowManager;

import standard.ballin.Game;

public class GameActivity extends Activity {
    private PowerManager powerManager;
    private WindowManager windowManager;
    private Display display;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        display= windowManager.getDefaultDisplay();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        game = new Game(this);
        //TODO Set background of game
        setContentView(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        game.startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();

        game.stopGame();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
