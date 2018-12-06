package standard.ballin;

import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.signin.SignInOptions;


/**
 * Activity class for Main Menu
 * @author Jonas Madsen
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    ImageView soundButton, settingsButton, trophyButton;
    private boolean soundToggle;
    private static final int RC_LEADERBOARD_UI = 9004;
    private GoogleApiClient googleApiClient;


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

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        // Trophy shortcut starts ???
        trophyButton = findViewById(R.id.trophyView);
        trophyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO implement google signin and leaderboard
                /*Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_LEADERBOARD_UI);
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(signInIntent);
                if (result != null && result.isSuccess()) {
                    Games.getLeaderboardsClient(MainActivity.this, result.getSignInAccount())
                            .getLeaderboardIntent(getString(R.string.leaderboard_id))
                            .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                @Override
                                public void onSuccess(Intent intent) {
                                    startActivityForResult(intent, RC_LEADERBOARD_UI);
                                }
                            });
                } else {
                    GoogleSignIn.requestPermissions(MainActivity.this, 1, GoogleSignIn.getLastSignedInAccount(MainActivity.this),Games.SCOPE_GAMES_LITE);
                    if(GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(MainActivity.this), Games.SCOPE_GAMES_LITE)) {
                        Games.getLeaderboardsClient(MainActivity.this, GoogleSignIn.getLastSignedInAccount(MainActivity.this))
                                .getLeaderboardIntent(getString(R.string.leaderboard_id))
                                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                    @Override
                                    public void onSuccess(Intent intent) {
                                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                                    }
                                });
                    }
                }*/
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
