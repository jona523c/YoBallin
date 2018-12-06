package standard.ballin;

import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.signin.SignInOptions;


/**
 * Activity class for Main Menu
 * @author Jonas Madsen
 */
public class MainActivity extends AppCompatActivity {
    ImageView soundButton, settingsButton, trophyButton;
    private boolean soundToggle;
    private static final int RC_LEADERBOARD_UI = 9004;


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

        // Trophy shortcut starts ???
        trophyButton = findViewById(R.id.trophyView);
        trophyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Games.getLeaderboardsClient(MainActivity.this, GoogleSignIn.getLastSignedInAccount(MainActivity.this))
                        .getLeaderboardIntent(getString(R.string.leaderboard_id))
                        .addOnSuccessListener(new OnSuccessListener<Intent>() {
                            @Override
                            public void onSuccess(Intent intent) {
                                startActivityForResult(intent, RC_LEADERBOARD_UI);
                            }
                        });*/
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
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent music = new Intent(this, MusicPlay.class);
        stopService(music);
        SoundPlayer.release();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
