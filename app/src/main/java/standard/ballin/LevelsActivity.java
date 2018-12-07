package standard.ballin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Map;
import java.util.logging.Level;

import standard.ballin.levelstrategies.*;

/**
 * Activity class for Levels menu
 * @author Jonas Madsen
 */
public class LevelsActivity extends AppCompatActivity {
    ImageView backButton;
    Map starMap;
    static Button level1, level2, level3, level4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_levels);

        backButton = (ImageView) findViewById(R.id.backView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        starMap = sharedPref.getAll();

        level1 = (Button) findViewById(R.id.level1);
        level2 = (Button) findViewById(R.id.level2);
        level3 = (Button) findViewById(R.id.level3);
        level4 = (Button) findViewById(R.id.level4);
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
    public void level1Button(View view) {
        SoundPlayer.playSound(this, SoundPlayer.BUTTON);
        Intent intent = new Intent (this, GameActivity.class);
        intent.putExtra("selectedLevel", 1);
        startActivity(intent);
    }

    public void level2Button(View view) {
        SoundPlayer.playSound(this, SoundPlayer.BUTTON);
        Intent intent = new Intent (this, GameActivity.class);
        intent.putExtra("selectedLevel", 2);
        startActivity(intent);
    }

    public void level3Button(View view) {
        SoundPlayer.playSound(this, SoundPlayer.BUTTON);
        Intent intent = new Intent (this, GameActivity.class);
        intent.putExtra("selectedLevel", 3);
        startActivity(intent);
    }

    public void level4Button(View view) {
        SoundPlayer.playSound(this, SoundPlayer.BUTTON);
        Intent intent = new Intent (this, GameActivity.class);
        intent.putExtra("selectedLevel", 4);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Make sure the shortcut is in the correct state
        if (MusicPlay.getPlayState()) {
            MusicPlay.resumeAudio();
        }
        updateStarsForLevel(level1, new Level1Strategy(this));
        updateStarsForLevel(level2, new Level2Strategy(this));
        updateStarsForLevel(level3, new Level3Strategy(this));
        updateStarsForLevel(level4, new Level4Strategy(this));

        super.onResume();
    }

    @Override
    protected void onPause () {
        MusicPlay.pauseAudio();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onPause();
    }

    private void updateStarsForLevel(Button button, LevelStrategy levelStrategy) {
        int stars = levelStrategy.getStars();
        Log.d("LevelsActivity", "Stars gotten from level: "+stars);
        switch (stars) {
            case 1: button.setBackgroundResource(R.drawable.level_one_star_button);
                    break;
            case 2: button.setBackgroundResource(R.drawable.level_two_star_button);
                    break;
            case 3: button.setBackgroundResource(R.drawable.level_three_star_button);
                    break;
            default: button.setBackgroundResource(R.drawable.level_zero_star_button);
                    break;
        }
    }
}
