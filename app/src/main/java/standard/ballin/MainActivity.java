package standard.ballin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    ImageView soundButton;
    ImageView settingsButton;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MusicPlay.playAudio(getApplicationContext(), R.raw.dance);

        // TODO: soundButton change according to settings Sound
        soundButton = (ImageView) findViewById(R.id.soundView);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundButton.setActivated(!soundButton.isActivated());
                if (MusicPlay.isPlaying()) {
                    MusicPlay.stopAudio();
                } else {
                    MusicPlay.playAudio(getApplicationContext(), R.raw.dance);
                }
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

    /** Called when user clicks play button **/
    public void playButton(View view) {
        Intent intent = new Intent (this, LevelsActivity.class);
        startActivity(intent);
    }
}
