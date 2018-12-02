package standard.ballin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;


/**
 * Activity class for Main Menu
 * @author Jonas Madsen
 */
public class MainActivity extends AppCompatActivity {
    ImageView soundButton;
    ImageView settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Start soundPool
        SoundPlayer.initSounds(this);

        // Starts the background music
        Intent music = new Intent(this, MusicPlay.class);
        startService(music);

        // If sound shortcut is clicked, turn on or turn off sound.
        soundButton = (ImageView) findViewById(R.id.soundView);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicPlay.getPlayState()) {
                    MusicPlay.turnOff();
                    MusicPlay.pauseAudio();
                } else {
                    MusicPlay.turnOn();
                    MusicPlay.resumeAudio();
                }
                soundButton.setActivated(MusicPlay.getPlayState());
            }
        });

        // Settings shortcut starts Settings activity
        settingsButton = (ImageView) findViewById(R.id.settingsView);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        // Make sure the shortcut is in the correct state
        if (MusicPlay.getPlayState()) {
            MusicPlay.resumeAudio();
        }
        soundButton = (ImageView) findViewById(R.id.soundView);
        soundButton.setActivated(MusicPlay.getPlayState());
        super.onResume();
    }

    @Override
    protected void onPause () {
        MusicPlay.pauseAudio();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent music = new Intent(this, MusicPlay.class);
        stopService(music);
        // Release mediaPlayer to free memory
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onDestroy();
    }

    /**
     * Called when user clicks play button, starts the Levels screen
     * @param view
     */
    public void playButton(View view) {
        SoundPlayer.playSound(this, SoundPlayer.BUTTON);
        Intent intent = new Intent (this, LevelsActivity.class);
        startActivity(intent);
    }
}
