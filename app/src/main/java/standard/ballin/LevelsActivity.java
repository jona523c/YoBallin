package standard.ballin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Map;

/**
 * Activity class for Levels menu
 * @author Jonas Madsen
 */
public class LevelsActivity extends AppCompatActivity {
    ImageView backButton;
    Map starMap;
    static Button level;

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

        level = (Button) findViewById(R.id.level1);
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
        intent.putExtra("selectedLevel", 1);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        // Make sure the shortcut is in the correct state
        if (MusicPlay.getPlayState()) {
            MusicPlay.resumeAudio();
        }
        super.onResume();
    }

    @Override
    protected void onPause () {
        MusicPlay.pauseAudio();
        super.onPause();
    }

    public static void updateStarsForLevel(Bundle bundle) {
        int stars = bundle.getInt("stars");
        switch (stars) {
            case 1: level.setBackgroundResource(R.drawable.level_one_star_button);
                    break;
            case 2: level.setBackgroundResource(R.drawable.level_two_star_button);
                    break;
            case 3: level.setBackgroundResource(R.drawable.level_three_star_button);
                    break;
        }
    }
}
