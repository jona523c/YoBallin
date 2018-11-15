package standard.ballin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    ImageView soundButton;
    ImageView settingsButton;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        MusicPlay.playAudio(getApplicationContext(), R.raw.dance);

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
        soundButton = (ImageView) findViewById(R.id.soundView);
        soundButton.setActivated(MusicPlay.isPlaying());
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        MusicPlay.getMediaPlayer().release();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onDestroy();
    }

    /** Called when user clicks play button **/
    public void playButton(View view) {
        Intent intent = new Intent (this, LevelsActivity.class);
        startActivity(intent);
    }
}
