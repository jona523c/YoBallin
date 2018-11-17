package standard.ballin;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import standard.ballin.levelstrategies.Level1Strategy;
import standard.ballin.levelstrategies.LevelStrategy;

/**
 * Activity class for Levels menu
 * @author Jonas Madsen
 */
public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_levels);
    }

    /**
     * Returns to the former activity
     * @param view
     */
    public void backButton(View view) {
        finish();
    }

    /**
     * Starts the Game activity
     * @param view
     */
    public void levelButton(View view) {
        Intent intent = new Intent (this, GameActivity.class);
        intent.putExtra("selectedLevel", 1);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
