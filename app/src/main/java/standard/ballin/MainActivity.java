package standard.ballin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    ImageView soundButton;
    ImageView settingsButton;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Starts the background music
        MusicPlay.playAudio(getApplicationContext(), R.raw.flute);

        // If sound shortcut is clicked, turn on or turn off sound.
        soundButton = (ImageView) findViewById(R.id.soundView);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicPlay.isPlaying()) {
                    MusicPlay.pauseAudio();
                } else {
                    MusicPlay.resumeAudio();
                }
                soundButton.setActivated(MusicPlay.isPlaying());
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
        soundButton = (ImageView) findViewById(R.id.soundView);
        soundButton.setActivated(MusicPlay.isPlaying());
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // Release mediaplayer to free memory
        MusicPlay.getMediaPlayer().release();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onDestroy();
    }

    /** Called when user clicks play button, starts the Levels screen **/
    public void playButton(View view) {
        Intent intent = new Intent (this, LevelsActivity.class);
        startActivity(intent);
    }
}
