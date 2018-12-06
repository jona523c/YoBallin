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
    ImageView soundButton, settingsButton, trophyButton;
    private boolean soundToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Initializes soundPool
        SoundPlayer.initSounds(this);

        // Starts the background music
        Intent music = new Intent(this, MusicPlay.class);
        startService(music);

        // If sound shortcut is clicked, turn on or turn off sound.
        soundButton = findViewById(R.id.soundView);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soundToggle) {
                    SoundPlayer.turnOff();
                    MusicPlay.turnOff();
                    MusicPlay.pauseAudio();
                    soundToggle = false;
                } else {
                    SoundPlayer.turnOn();
                    MusicPlay.turnOn();
                    MusicPlay.resumeAudio();
                    soundToggle = true;
                }
                soundButton.setActivated(soundToggle);
            }
        });

        // Settings shortcut starts Settings activity
        settingsButton = findViewById(R.id.settingsView);
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

        soundToggle = MusicPlay.getPlayState() || SoundPlayer.getPlayState();
        soundButton = findViewById(R.id.soundView);
        soundButton.setActivated(soundToggle);
        super.onResume();
    }

    @Override
    protected void onPause () {
        MusicPlay.pauseAudio();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Intent music = new Intent(this, MusicPlay.class);
        stopService(music);
        SoundPlayer.release();
        super.onDestroy();
    }

    /**
     * Called when user clicks play button, starts the Levels screen
     * @param view view calling this method
     */
    public void playButton(View view) {
        SoundPlayer.playSound(this, SoundPlayer.BUTTON);
        Intent intent = new Intent (this, LevelsActivity.class);
        startActivity(intent);
    }

}
