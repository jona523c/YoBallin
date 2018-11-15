package standard.ballin;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

public class GameActivity extends FragmentActivity {
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
    public void confirmDialog() {
        DialogFragment newFragment = new FinishDialog();
        newFragment.show(getSupportFragmentManager(), "finishline");
    }


}
