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
import java.util.Scanner;
import java.util.logging.Level;

import standard.ballin.levelstrategies.*;

/**
 * Activity class for Levels menu
 * @author Jonas Madsen
 */
public class LevelsActivity extends AppCompatActivity {
    ImageView backButton;
    Map starMap;
    static Button level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, level11, level12, level13, level14, level15, level16, level17, level18, level19, level20;

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
        level5 = (Button) findViewById(R.id.level5);
        level6 = (Button) findViewById(R.id.level6);
        level7 = (Button) findViewById(R.id.level7);
        level8 = (Button) findViewById(R.id.level8);
        level9 = (Button) findViewById(R.id.level9);
        level10 = (Button) findViewById(R.id.level10);
        level11 = (Button) findViewById(R.id.level11);
        level12 = (Button) findViewById(R.id.level12);
        level13 = (Button) findViewById(R.id.level13);
        level14 = (Button) findViewById(R.id.level14);
        level15 = (Button) findViewById(R.id.level15);
        level16 = (Button) findViewById(R.id.level16);
        level17 = (Button) findViewById(R.id.level17);
        level18 = (Button) findViewById(R.id.level18);
        level19 = (Button) findViewById(R.id.level19);
        level20 = (Button) findViewById(R.id.level20);
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
        SoundPlayer.playSound(this, SoundPlayer.BUTTON);
        Intent intent = new Intent (this, GameActivity.class);
        Button b = (Button) view;
        intent.putExtra("selectedLevel", Integer.parseInt(b.getTag().toString()));
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
        updateStarsForLevel(level5, new Level5Strategy(this));
        updateStarsForLevel(level6, new Level6Strategy(this));
        updateStarsForLevel(level7, new Level7Strategy(this));
        updateStarsForLevel(level8, new Level8Strategy(this));
        updateStarsForLevel(level9, new Level9Strategy(this));
        updateStarsForLevel(level10, new Level10Strategy(this));
        updateStarsForLevel(level11, new Level11Strategy(this));
        updateStarsForLevel(level12, new Level12Strategy(this));
        updateStarsForLevel(level13, new Level13Strategy(this));
        updateStarsForLevel(level14, new Level14Strategy(this));
        updateStarsForLevel(level15, new Level15Strategy(this));
        updateStarsForLevel(level16, new Level16Strategy(this));
        updateStarsForLevel(level17, new Level17Strategy(this));
        updateStarsForLevel(level18, new Level18Strategy(this));
        updateStarsForLevel(level19, new Level19Strategy(this));
        updateStarsForLevel(level20, new Level20Strategy(this));


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
