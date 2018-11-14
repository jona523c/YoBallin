package standard.ballin;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.ColorInt;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import standard.ballin.Game;

public class GameActivity extends Activity {
    private Game game;
    private SensorManager sensorManager;
    private WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        game = new Game(this);

        //TODO Set background of game
        game.setBackgroundResource(R.drawable.ic_launcher_background);

        setContentView(game);
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public void pauseButton(View view) {
        // TODO implement pause Button
    }
    public void restartButton(View view) {
        // TODO implement restart Button
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
